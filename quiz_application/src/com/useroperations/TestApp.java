//package com.quizapplication;
//
//import java.util.Scanner;
//
//public class TestApp {
//
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int choice;
//        Integer loggedInStudentId = null;
//
//        do {
//            System.out.println("********  Welcome to Quiz System  ********");
//            System.out.println("1. Student Registration");
//            System.out.println("2. Student Login");
//            System.out.println("3. Start Quiz");
//            System.out.println("0. Exit");
////            System.out.println("-----------------------------------------------------");
//            System.out.print("Enter your choice: ");
//
//            choice = scanner.nextInt();
//            scanner.nextLine(); // consume newline
//
//            switch (choice) {
//                case 1:
//                    StudentRegistration.registerStudentFromInput();  // Assumes method exists to take input inside
//                    break;
//                case 2:
//                    loggedInStudentId = StudentLogin.loginAndReturnId(); // You’ll create this method to return student ID
//                    break;
//                case 3:
//                    if (loggedInStudentId != null) {
//                        DisplayQuiz.fetchAndSubmitAnswers(loggedInStudentId);
//                    } else {
//                        System.out.println("Please login first to start the quiz.");
//                    }
//                    break;
//                case 0:
//                    System.out.println("Exiting... Thank you!");
//                    break;
//                default:
//                    System.out.println("Invalid choice. Please try again.");
//            }
//
//            System.out.println();
//
//        } while (choice != 0);
//
//        scanner.close();
//    }
//}


package com.useroperations;

import java.util.Scanner;

public class TestApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Integer loggedInStudentId = null;

        do {
            System.out.println("---------  Welcome to Quiz System  ----------");
            System.out.println("1. Student Registration");
            System.out.println("2. Student Login");
            System.out.println("3. Start Quiz");
            System.out.println("4. View Your Score");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    StudentRegistration.registerStudentFromInput();
                    break;
                case 2:
                    loggedInStudentId = StudentLogin.loginAndReturnId();
                    break;
                case 3:
                    if (loggedInStudentId != null) {
                        DisplayQuiz.fetchAndSubmitAnswers(loggedInStudentId);
                    } else {
                        System.out.println("Please login first to start the quiz.");
                    }
                    break;
                case 4:
                    if (loggedInStudentId != null) {
                        DisplayQuiz.displayStudentScore(loggedInStudentId);
                    } else {
                        System.out.println("Please login first to view your score.");
                    }
                    break;
                case 0:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();

        } while (choice != 0);

        scanner.close();
    }
}
