//package com.admin;
//
//import com.useroperations.CommonConnection;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class FetchStudentScore extends CommonConnection {
//
//    // Existing method
//    public static void getStudentScore(int studentId) {
//        String selectSQL = "SELECT student_id, score FROM student_scores WHERE student_id = ?";
//
//        try (Connection conn = CommonConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
//
//            pstmt.setInt(1, studentId);
//
//            try (ResultSet rs = pstmt.executeQuery()) {
//                if (rs.next()) {
//                    int student_id = rs.getInt("student_id");
//                    int score = rs.getInt("score");
//                    System.out.println("Student ID [" + student_id + "] scored: " + score);
//                } else {
//                    System.out.println("No student found with ID: " + studentId);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(); // or use proper logging
//        }
//    }
//
//    // 🔽 New method to get all student scores with full names
//    public static void getAllStudentScores() {
//        String query = "SELECT sd.student_id, CONCAT(sd.first_name, ' ', sd.last_name) AS full_name, ss.score " +
//                "FROM student_data sd " +
//                "JOIN student_scores ss ON sd.student_id = ss.student_id " +
//                "ORDER BY ss.score ASC";
//
//        try (Connection conn = CommonConnection.getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(query);
//             ResultSet rs = pstmt.executeQuery()) {
//
//            System.out.println("------------------------------------------------");
//            System.out.println("Student Scores in Ascending Order:\n");
//
////            while (rs.next()) {
////                int studentId = rs.getInt("student_id");
////                String fullName = rs.getString("full_name");
////                int score = rs.getInt("score");
////
////                System.out.println("student ID: " + studentId + ", Name: " + fullName + ", Score: " + score);
////            }
//
//            // Print table header with row number column
//            System.out.printf("%-5s %-12s %-20s %-6s%n", "No.", "Student ID", "Full Name", "Score");
//            System.out.println("------------------------------------------------");
//
//// Initialize row counter
//            int count = 1;
//
//// Iterate through the ResultSet and print each row
//            while (rs.next()) {
//                int studentId = rs.getInt("student_id");
//                String fullName = rs.getString("full_name");
//                int score = rs.getInt("score");
//
//                System.out.printf("%-5d %-12d %-20s %-6d%n", count, studentId, fullName, score);
//                count++;
//            }
//
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}


package com.admin;

import com.useroperations.CommonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FetchStudentScore extends CommonConnection {

    /**
     * Fetches and displays a single student's score with full name.
     */
    public static void getStudentScore(int studentId) {
        String selectSQL = "SELECT sd.student_id, sd.first_name, sd.last_name, ss.score " +
                "FROM student_data sd " +
                "JOIN student_scores ss ON sd.student_id = ss.student_id " +
                "WHERE sd.student_id = ?";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("student_id");
                    String fullName = rs.getString("first_name") + " " + rs.getString("last_name");
                    int score = rs.getInt("score");

                    System.out.println("--------------------------");
                    System.out.println("Student ID : " + id);
                    System.out.println("Name       : " + fullName);
                    System.out.println("Score      : " + score);
                    System.out.println("--------------------------");

                } else {
                    System.out.println("No student found with ID: " + studentId);
                }
            }

        } catch (Exception e) {
            System.err.println("Error fetching student score: " + e.getMessage());
        }
    }

    /**
     * Fetches and displays all students' scores in ascending order with row numbers.
     */
    public static void getAllStudentScores() {
        String query = "SELECT sd.student_id, CONCAT(sd.first_name, ' ', sd.last_name) AS full_name, ss.score " +
                "FROM student_data sd " +
                "JOIN student_scores ss ON sd.student_id = ss.student_id " +
                "ORDER BY ss.score ASC";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("--------------------------------------------------");
            System.out.println("Student Scores in Ascending Order:\n");

            System.out.printf("%-5s %-12s %-20s %-6s%n", "No.", "Student ID", "Full Name", "Score");
            System.out.println("--------------------------------------------------");

            int count = 1;
            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String fullName = rs.getString("full_name");
                int score = rs.getInt("score");

                System.out.printf("%-5d %-12d %-20s %-6d%n", count, studentId, fullName, score);
                count++;
            }

        } catch (Exception e) {
            System.err.println("Error fetching all student scores: " + e.getMessage());
        }
    }
}



