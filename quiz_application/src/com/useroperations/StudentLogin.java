package com.useroperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentLogin {

    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM Student_Data WHERE username = ? AND password = ?";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();  // true if a match is found

        } catch (Exception e) {
            System.out.println("Error during login: " + e.getMessage());
            return false;
        }
    }

//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        System.out.println("Enter Username: ");
//        String username = sc.next();
//
//        System.out.println("Enter Password: ");
//        String password = sc.next();
//
//        if (validateLogin(username, password)) {
//            System.out.println("Login successful!");
//
//            // Call the next class/method here to display quiz questions
//            // Example:
//            // QuizMenu.showMenu(username);
//
//        } else {
//            System.out.println("Invalid username or password. Try again.");
//            System.out.println("Please enter the vaid credentials or register first");
//        }
//    }

    public static Integer loginAndReturnId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String sql = "SELECT student_id FROM student_data WHERE username = ? AND password = ?";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int studentId = rs.getInt("student_id");
                System.out.println("Login successful! Welcome, " + username);
                return studentId;
            } else {
                System.out.println("Invalid username or password. Please register if you haven’t already.");
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }



}
