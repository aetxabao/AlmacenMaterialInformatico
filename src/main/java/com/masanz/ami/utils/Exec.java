package com.masanz.ami.utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exec {

    public static String getHostName() {

        String command = "hostname";

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            return "nohostname";
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                return line;
            }
        } catch (IOException e) {
            return "nohostname";
        }

        try {
            proc.waitFor();
        } catch (InterruptedException e) {
            return "nohostname";
        }
        return "unknownhostname";
    }
}