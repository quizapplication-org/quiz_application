package com.admin;

import com.useroperations.CommonConnection;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n   ****  Admin Operations  ****");
            System.out.println("1. Display all students' scores (ascending order)");
            System.out.println("2. Fetch a student's score by student ID");
            System.out.println("3. Add a question with 4 options");
            System.out.println("0. Exit");
            System.out.print("\nEnter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    FetchStudentScore.getAllStudentScores();
                    break;
                case 2:
                    System.out.print("Enter student ID: ");
                    int studentId = scanner.nextInt();
                    FetchStudentScore.getStudentScore(studentId);
                    break;
                case 3:
                    System.out.print("Enter question: ");
                    String question = scanner.nextLine();

                    System.out.print("Enter option A: ");
                    String optionA = scanner.nextLine();

                    System.out.print("Enter option B: ");
                    String optionB = scanner.nextLine();

                    System.out.print("Enter option C: ");
                    String optionC = scanner.nextLine();

                    System.out.print("Enter option D: ");
                    String optionD = scanner.nextLine();

                    System.out.print("Enter correct option (A/B/C/D): ");
                    String correctOption = scanner.nextLine();

                    InsertQueAns.insertQuestion(question, optionA, optionB, optionC, optionD, correctOption);
                    break;

                case 0:
                    System.out.println("Exiting... Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 0);

        scanner.close();
    }
}
