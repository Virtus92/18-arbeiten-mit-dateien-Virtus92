package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("Möchtest du:");
            System.out.println(" 0. Beenden");
            System.out.println(" 1. Alle Personen anzeigen");
            System.out.println(" 2. Eine Person anlegen");
            int choice = sc.nextInt();
            if (choice == 1) {
                Person.showPersons();
            } else if (choice == 2) {
                Person.createPerson();
            } else if (choice == 0) {
                break;
            } else {
                System.out.println("Fehler, versuchs erneut.");
            }
        }
    }
}