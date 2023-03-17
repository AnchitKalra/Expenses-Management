package com.example.geektrust;

import com.example.geektrust.controller.ExpensesController;
import com.example.geektrust.exception.Houseful;
import com.example.geektrust.exception.Incorrect;
import com.example.geektrust.exception.NotFound;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            // the file to be opened for reading
            FileInputStream fis = new FileInputStream(args[0]);
            Scanner sc = new Scanner(fis); // file to be scanned
            ExpensesController expensesController = new ExpensesController();
            // returns true if there is another line to read
            while (sc.hasNextLine()) {
               //Add your code here to process input commands

                String s = sc.nextLine();
                String input[] = s.split(" ");
                try {
                    if (input[0].equalsIgnoreCase("MOVE_IN")) {
                        expensesController.moveIn(input);
                    }

                    if (input[0].equalsIgnoreCase("spend")) {
                        expensesController.spend(input);
                    }
                    if (input[0].equalsIgnoreCase("clear_due")) {
                        expensesController.clearDues(input);
                    }
                    if (input[0].equalsIgnoreCase("dues")) {
                        expensesController.dues(input);
                    }
                    if (input[0].equalsIgnoreCase("move_out")) {
                        expensesController.moveOut(input);
                    }
                }catch (Houseful houseful) {
                    System.out.println(houseful.getMessage());
                }
                catch (NotFound notFound) {
                    System.out.println(notFound.getMessage());
                }
                catch (Incorrect incorrect) {
                    System.out.println(incorrect.getMessage());
                }




            }
            sc.close(); // closes the scanner
        } catch (IOException e) {
        }
    }
}
