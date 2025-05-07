package com.Registration;




import java.util.Scanner;

public class WelCome {

    public static void main(String[] args) {
        System.out.println("** Welcome To Group B Quiz Application **");
        System.out.println("Please choose an operation:");
        System.out.println("1. Register as a new student");


        Scanner scanner = new Scanner(System.in);
        int choice;

        try {
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    Student_Registration.getStudentRegistrationCall();
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } catch (Exception e) {

        } finally {
            scanner.close();
        }
    }
}
