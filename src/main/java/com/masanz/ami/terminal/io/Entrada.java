package com.masanz.ami.terminal.io;

import java.util.Scanner;

public class Entrada {

    public static int leerEntero(String txt, int min, int max) {
        Scanner teclado = new Scanner(System.in);
        int v = -1;
        while (v < min || v > max) {
            System.out.printf("%s [%d-%d]: ", txt, min, max);
            v = teclado.nextInt();
        }
        teclado.nextLine();
        return v;
    }

    public static double leerDecimalPositivo(String txt) {
        Scanner teclado = new Scanner(System.in);
        double v = -1.0;
        while (v < 0) {
            try {
                System.out.printf("%s: ", txt);
                v = teclado.nextDouble();
            } catch (Exception e) {  }
        }
        teclado.nextLine();
        return v;
    }

    public static String leerString(String txt) {
        Scanner teclado = new Scanner(System.in);
        String s = "";
        while (s.trim().isEmpty()) {
            System.out.printf("%s: ", txt);
            s = teclado.nextLine();
        }
        return s;
    }

}
