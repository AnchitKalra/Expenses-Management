package com.example.geektrust.service;

import com.example.geektrust.exception.Houseful;
import com.example.geektrust.exception.Incorrect;
import com.example.geektrust.exception.NotFound;
import com.example.geektrust.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ExpensesService {

    private List<Person> personList = new ArrayList<>();
    public void moveIn(String []input) throws Houseful {
        if(Person.maxMembers > personList.size()) {
            Person person = new Person();
            person.setName(input[1]);
            Person.nameList.add(input[1]);
            personList.add(person);
            System.out.println("SUCCESS");
            if(personList.size() == 2 || personList.size() == 3) {
                initMap();
            }
        }
        else {
            throw new Houseful("HOUSEFUL");
        }
    }

    public void initMap() {
        for (Person person : personList) {
                Map<String, Integer> map = person.getMap();
                String name = person.getName();
                for(String s : Person.nameList) {
                    if(s.equalsIgnoreCase(name)) {
                        continue;
                    }
                    map.put(s, 0);
                }
        }
    }
    public void spend(String []input) throws NotFound{
        int amount = Integer.parseInt(input[1]);
        if(input.length == 5) {
            List<String> namesList = new ArrayList<>();
            namesList.add(input[2]);
            namesList.add(input[3]);
            namesList.add(input[4]);

            String lender = input[2];
            Person p = new Person();
            Person p1 = new Person();
            Person p2 = new Person();
            for(Person person : personList) {
                if(person.getName().equalsIgnoreCase(lender)) {
                    p = person;
                }
                if(person.getName().equalsIgnoreCase(input[3])) {
                    p1 = person;
                }
                if(person.getName().equalsIgnoreCase(input[4])) {
                    p2 = person;
                }
            }
            String borrower1 = input[3];
            String borrower2 = input[4];

            for(String s : namesList) {
                if(!Person.nameList.contains(s)) {
                    throw new NotFound("MEMBER_NOT_FOUND");
                }
            }
            int currentAmount = amount / Person.maxMembers;
            Map<String, Integer> map = p.getMap();

            Map<String, Integer> map1 = p1.getMap();

            Map<String, Integer> map2 = p2.getMap();

            List<Integer> pList = new ArrayList<>();
            List<String> pnList = new ArrayList<>();


            if(map.get(borrower1) > 0) {
                pList.add(map.get(borrower1));
                pnList.add(borrower1);
            }
            if(map.get(borrower2) > 0) {
                pList.add(map.get(borrower2));
                pnList.add(borrower2);
            }

            if(!pList.isEmpty()) {
                int amountBorrowed = pList.get(0);
                String name = pnList.get(0);
                pList.remove(0);
                pnList.remove(0);
                if(currentAmount > amountBorrowed) {
                    map.put(name, 0);
                    if(!pList.isEmpty()) {
                        int amountBorrowed2 = pList.get(0);
                        String name2 = pnList.get(0);
                        pList = new ArrayList<>();
                        pnList = new ArrayList<>();
                        if(currentAmount > amountBorrowed2) {
                            map.put(name2, 0);
                            if(map1.get(name2) > 0) {
                                int amt = currentAmount - amountBorrowed2;
                                map1.put(name2, map1.get(name2) + amt);
                            }

                            else if(map2.get(name) > 0) {
                                int amt = currentAmount - amountBorrowed;
                                map2.put(name, map2.get(name) + amt);
                            }
                            else {
                                map1.put(lender, currentAmount - amountBorrowed);
                                map2.put(lender, currentAmount - amountBorrowed2);
                            }
                        }
                        else if(currentAmount < amountBorrowed2) {
                            map.put(name2, amountBorrowed2 - currentAmount);
                        }
                        else {
                            map.put(name2, 0);
                        }
                    }
                    else {

                        if(map1.get(borrower2) > 0) {
                            int amt = map1.get(borrower2);
                            int actualAmt = currentAmount - amt;
                            if(actualAmt < 0) {
                                map1.put(lender, currentAmount + currentAmount);
                                map1.put(borrower2, Math.abs(actualAmt));
                                map2.put(lender, 0);
                            } else if (actualAmt > 0) {
                                map1.put(lender, currentAmount - amountBorrowed + amt);
                                map2.put(lender, map2.get(lender) + currentAmount - amt);
                            }
                            else {
                                map2.put(lender, 0);
                                map1.put(lender, 2 * currentAmount);
                            }

                        } else if (map2.get(borrower1) > 0) {
                            int amt = map2.get(borrower1);
                            int actualAmt = currentAmount - amountBorrowed - amt;
                            if(actualAmt > 0) {
                                map1.put(lender, actualAmt);
                                map2.put(lender, currentAmount + amt + map2.get(lender));
                                map2.put(borrower1, 0);
                            }
                            else if(actualAmt < 0) {
                                map1.put(lender, 0);
                                map2.put(lender, 2 * currentAmount);
                                map2.put(borrower1, Math.abs(actualAmt));
                            }
                            else {
                                map1.put(lender, 0);
                                map2.put(lender, 2 * currentAmount);
                                map2.put(borrower1, 0);
                            }
                        }


                    }
                }
                else if(currentAmount < amountBorrowed) {
                    int p1Amount = amountBorrowed - currentAmount;
                    map.put(name, amountBorrowed - currentAmount);
                    if(!pList.isEmpty()) {
                        int amountBorrowed2 = pList.get(0);
                        String name2 = pnList.get(0);
                        pList = new ArrayList<>();
                        pnList = new ArrayList<>();
                        if(currentAmount > amountBorrowed2) {
                            map.put(name2, 0);
                            int pAmount = currentAmount - amountBorrowed2;
                            if(pAmount > p1Amount) {
                                map.put(name, 0);
                                map2.put(name, map2.get(name) + p1Amount);
                                map2.put(lender, pAmount - p1Amount);
                            } else if (pAmount < p1Amount) {
                                map.put(name, p1Amount - pAmount);
                                map2.put(name,map2.get(name) + pAmount);
                            }
                            else {
                                map.put(name, 0);
                                map2.put(name, map2.get(name) + pAmount);
                            }
                        }
                        else if(currentAmount < amountBorrowed2) {
                            map.put(name2, amountBorrowed2 - currentAmount);
                        }
                        else {
                            map.put(name2, 0);
                        }
                    }
                    else {
                        map2.put(lender, map2.get(lender) + currentAmount);
                    }

                }
                else {
                    map.put(name, 0);
                    if(!pList.isEmpty()) {
                        int amountBorrowed2 = pList.get(0);
                        String name2 = pnList.get(0);
                        pList = new ArrayList<>();
                        pnList = new ArrayList<>();
                        if(currentAmount > amountBorrowed2) {
                            map.put(name2, 0);
                            map2.put(lender, currentAmount - amountBorrowed2);
                        }
                        else if(currentAmount < amountBorrowed2) {
                            map.put(name2, amountBorrowed2 - currentAmount);
                        }
                        else {
                            map.put(name2, 0);
                        }
                    }
                    else {
                        map2.put(lender, map2.get(lender) + currentAmount);
                    }
                }

            }
            else {
                map1.put(lender, map1.get(lender) + currentAmount);
                map2.put(lender, map2.get(lender) + currentAmount);
            }




        }
        else {
            String lender = input[2];
            String borrower = input[3];

            Person person = new Person();
            for (Person p : personList) {
                if (p.getName().equalsIgnoreCase(lender)) {
                    person = p;
                }
            }
            if(!Person.nameList.contains(lender) || !Person.nameList.contains(borrower)) {
                throw new NotFound("MEMBER_NOT_FOUND");
            }
            Map<String, Integer> map = person.getMap();
            Map<String, Integer> map1;
            int lend = 0;
            String personName = "";
            for (String s : Person.nameList) {
                if (map.containsKey(s)) {
                    if(map.get(s) > 0) {
                        lend += map.get(s);
                        personName = s;
                    }
                }
            }
            amount = Integer.parseInt(input[1]);
            amount /= 2;
            for (Person p : personList) {
                if (p.getName().equalsIgnoreCase(borrower)) {
                    person = p;
                }
            }
            map1 = person.getMap();
            int amountBorrowed = 0;
            if (map1.containsKey(personName)) {
                amountBorrowed = map1.get(personName);
            }
            //amountBorrowed += lend;
            if (amount < lend) {
                amountBorrowed += amount;
                lend -= amount;
                if(map.containsKey(personName))
                map.put(personName, lend);
                if(map1.containsKey(personName))
                map1.put(personName, amountBorrowed);
            } else if (amount > lend) {
                amountBorrowed += lend;
                amount -= lend;
                lend = 0;
                if(map.containsKey(personName))
                map.put(personName, lend);
                if(map1.containsKey(lender))
                map1.put(lender, amount);
                if(map1.containsKey(personName))
                map1.put(personName, amountBorrowed);
            } else {
                amountBorrowed += lend;
                amount = 0;
                lend = 0;
                if(map.containsKey(personName))
                map.put(personName, lend);
                if(map1.containsKey(lender))
                map1.put(lender, 0);
                if(map1.containsKey(personName))
                map1.put(personName, amountBorrowed);
            }
        }

        System.out.println("SUCCESS");
    }

    public void clearDues(String input[]) throws Incorrect, NotFound {
            String person1 = input[1];
            String lender = input[2];
            Integer amount = Integer.parseInt(input[3]);
            Person person = new Person();
            for (Person p : personList) {
                if (p.getName().equalsIgnoreCase(person1)) {
                    person = p;
                }
            }
            if(person.getName() == null) {
                throw new NotFound("MEMBER_NOT_FOUND");
            }
            Map<String, Integer> map = person.getMap();
            int amountBorrowed = 0;
            if (map.containsKey(lender)) {
                amountBorrowed = map.get(lender);
            }
            else {
                throw new NotFound("MEMBER_NOT_FOUND");
            }
            if (amount > amountBorrowed) {
                throw new Incorrect("INCORRECT_PAYMENT");
            }
            amountBorrowed -= amount;
            map.put(lender, amountBorrowed);
            System.out.println(amountBorrowed);
        }


    public void dues(String input[]) throws NotFound{

        String name = input[1];

        Person person = new Person();
        for(Person p : personList) {
            if(p.getName().equalsIgnoreCase(name)) {
                person = p;
            }
        }
        if(person.getName() == null) {
            throw new NotFound("MEMBER_NOT_FOUND");
        }
        Map<String, Integer> map = person.getMap();
        List<String> nameList = new ArrayList<>();
        List<Integer> amountList = new ArrayList<>();
        for(String s : Person.nameList) {
            if(map.containsKey(s)) {
              int amount = map.get(s);
              if(amountList.size() > 0) {
                  if(amountList.get(0) < amount) {
                      int temp = amountList.get(0);
                      amountList = new ArrayList<>();
                      amountList.add(amount);
                      amountList.add(temp);
                      String tempName = nameList.get(0);
                      nameList = new ArrayList<>();
                      nameList.add(s);
                      nameList.add(tempName);
                  }
                  else if(amountList.get(0) == amount) {
                      amountList.add(amount);
                      nameList.add(s);
                      Collections.sort(nameList);
                  }
                  else {
                      amountList.add(amount);
                      nameList.add(s);
                  }

              }
              else {
                  amountList.add(amount);
                  nameList.add(s);
              }

            }

        }
        for (int i = 0; i < nameList.size(); i++) {
            System.out.println(nameList.get(i) + " " + amountList.get(i));
        }
    }

    public void moveOut(String input[]) {
        try {
            Boolean flag1 = false;
            Boolean flag2 = false;
            Person person = new Person();
            String name = input[1];
            for (Person p : personList) {
                if (p.getName().equalsIgnoreCase(name)) {
                    person = p;
                }
            }
            Map<String, Integer> map = person.getMap();
            int amount = 0;
            for (String s : Person.nameList) {
                if (map.containsKey(s)) {
                    amount += map.get(s);
                }
            }
            if (amount == 0) {
                flag1 = true;
            }
            for (String s : Person.nameList) {
                if (s.equalsIgnoreCase(name)) {
                    continue;
                }
                for (Person p : personList) {
                    if (p.getName().equalsIgnoreCase(s)) {
                        person = p;
                        map = person.getMap();
                        if (map.containsKey(name)) {
                            amount += map.get(name);
                        }
                    }
                }
            }
            if (amount == 0) {
                flag2 = true;
            }

            if (flag1 && flag2) {
                System.out.println("SUCCESS");
            } else {
                System.out.println("FAILURE");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
