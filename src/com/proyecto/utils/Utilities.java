package com.proyecto.utils;

import java.util.Scanner;

public class Utilities {

    private static Scanner keyboard = new Scanner(System.in);

    public static int getInt() {
        int result = 0;
        boolean valid = false;
        do {
            try {
                result = Integer.parseInt(keyboard.nextLine());
                valid = true;

            } catch (IllegalStateException ex) {
                keyboard = new Scanner(System.in);
            } catch (NumberFormatException ex) {
            } catch (Exception ex) {
            }
        } while (!valid);
        return result;
    }

    public static String getString() {
        String result = "";
        boolean valid = false;
        do {
            try {
                result = keyboard.nextLine();
                valid = true;

            } catch (Exception ex) {
                System.out.println("Error unknown. Please, try it again: ");
            }
        } while (!valid);
        return result;
    }

}