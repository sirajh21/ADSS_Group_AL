package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

public class Main {


    public static void main(String[] args) throws SQLException, IOException {
        vmain();
    }

    public static void vmain() throws NumberFormatException, IOException {
        // System.out.println(new File("superli.db").getAbsolutePath());

        System.out.println("1 stock , 2 supplier, 0 exit");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int menu = Integer.parseInt(reader.readLine());


        switch (menu) {
            case 1:
                try {
                    try {
                        Stockmain.mainMenu();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case 2:
                try {
                    MainController.mainMenu();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;


            case 0:
                break;

        }

    }
}