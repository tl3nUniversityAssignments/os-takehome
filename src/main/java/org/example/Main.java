package org.example;

import java.util.Scanner;

public class Main {
    private static ComputationManager manager;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            command = scanner.nextLine();

            if (command.startsWith("group")) {
                int x = Integer.parseInt(command.split(" ")[1]);
                manager = new ComputationManager(x);
                System.out.println("Created new group with x = " + x);
            } else if (command.startsWith("new")) {
                String symbol = command.split(" ")[1];
                if (manager != null) {
                    System.out.println(symbol);

                    manager.addComponent(symbol);
                    System.out.println("Added new component with symbol: " + symbol);
                } else {
                    System.out.println("No group created. Use group <x> to create a group first.");
                }
            } else if (command.equals("run")) {
                if (manager != null) {
                    manager.run();
                } else {
                    System.out.println("No group created. Use group <x> to create a group first.");
                }
            } else if (command.equals("summary")) {
                if (manager != null) {
                    manager.summary();
                } else {
                    System.out.println("No group created. Use group <x> to create a group first.");
                }
            } else {
                System.out.println("Invalid command.");
            }
        }
    }
}