package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.example.Main.sc;

public class Person {
    private final String firstName;
    private final String lastName;
    private final String dateOfBirth;
    private final String address;
    private final String gender;


    public Person(String firstName, String lastName, String dateOfBirth, String address, String gender){
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.gender = gender;
    }

    public String write() {
        return "\"" + firstName + "\",\"" + lastName + "\",\"" + dateOfBirth + "\",\"" + address + "\",\"" + gender + "\"";
    }

    @Override
    public String toString() {
        return "Name - " + firstName + " " + lastName + "\nDate of birth - " + dateOfBirth + "\nAddress - " + address + "\nGender - " + gender + "\n";
    }

    public static void createPerson() throws IOException {

        System.out.println("Gib den Vornamen der Person ein:");
        String vorname = sc.nextLine();
        System.out.println("Gib den Nachnamen der Person ein:");
        String nachname = sc.nextLine();
        System.out.println("Gib das Geburtsdatum der Person ein:");
        String geburtsdatum = sc.nextLine();
        System.out.println("Gib die Adresse der Person ein:");
        String adresse = sc.nextLine();
        System.out.println("Gib das Geschlecht der Person ein:");
        String geschlecht = sc.nextLine();

        Person newPerson = new Person(vorname, nachname, geburtsdatum, adresse, geschlecht);
        System.out.println(newPerson);
        BufferedWriter writer = new BufferedWriter(new FileWriter("personen.csv", true));
        writer.append(newPerson.write()).append("\n");
        writer.close();
    }

    public static void showPersons() throws IOException {
        //Methode 1
        List<String> allLines = Files.readAllLines(Path.of("personen.csv"));
        Person[] personen = new Person[5];
        int i = 0;
        for (String line : allLines) {
            String[] csvFields = line.split(",\"");
            for (int j = 0; j < csvFields.length; j++) {
                String temp = csvFields[j];
                csvFields[j] = temp.substring(1, temp.length() - 1);
            }
            if (csvFields[0].equals("firstname")) {
                continue;
            }
            personen[i] = new Person(csvFields[0], csvFields[1], csvFields[2], csvFields[3] + ", " + csvFields[4], csvFields[5]);
            System.out.println(personen[i].toString());
            i++;
        }

        //Methode 2
//        BufferedReader reader = new BufferedReader(new FileReader("personen.csv"));
//        Person[] personen = new Person[5];
//        String current;
//        for (int i = 0; i < 3; i++) {
//            current = reader.readLine();
//
//            String[] csvFields = current.split(",");
//            for (int j=0; j<csvFields.length; j++) {
//                String temp = csvFields[j];
//                csvFields[j] = temp.substring(1, temp.length()- 1);
//            }
//            if (csvFields[0].equals("firstname")) {
//                continue;
//            }
//            personen[i] = new Person(csvFields[0], csvFields[1], csvFields[2], csvFields[3] + ", " + csvFields[4], csvFields[5]);
//            System.out.println(personen[i].toString());
//        }
    }
}
