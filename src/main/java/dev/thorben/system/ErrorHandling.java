package dev.thorben.system;

public class ErrorHandling {

    public static void handle(String message, Exception e) {
        System.out.println(message + " " + e.getMessage());
    }
}
