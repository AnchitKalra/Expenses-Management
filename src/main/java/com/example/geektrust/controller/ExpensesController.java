package com.example.geektrust.controller;

import com.example.geektrust.exception.Houseful;
import com.example.geektrust.exception.Incorrect;
import com.example.geektrust.exception.NotFound;
import com.example.geektrust.service.ExpensesService;

public class ExpensesController {

    private ExpensesService service = new ExpensesService();
    public void moveIn(String s[]) throws Houseful {
        service.moveIn(s);
    }

    public void spend(String s[]) throws NotFound {
        service.spend(s);
    }

    public void clearDues(String []input) throws Incorrect, NotFound {
        service.clearDues(input);
    }
    public void dues(String input[]) throws NotFound{
        service.dues(input);
    }

    public void moveOut(String []s) {
        service.moveOut(s);
    }
}
