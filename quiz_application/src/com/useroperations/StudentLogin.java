package com.useroperations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class StudentLogin {

//    public static void main(String[] args) {

    public static Integer loginAndReturnId () {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String sql = "SELECT student_id FROM student_data WHERE username = ? AND password = ?";

        try (Connection conn = com.useroperations.CommonConnection.getConnection();
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
                System.out.println("Do you want to register as a new student? (yes/no)");

                String ch = sc.next();
                if(ch.equals("yes"))
                {
                    StudentRegistration.registerStudentFromInput();
                }
                else
                {
                    System.out.println("\nOkay, thank you!");
                    loginAndReturnId();
                }
                return null;
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return null;
        }
    }



}
