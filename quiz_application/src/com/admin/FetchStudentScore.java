//package com.admin;
//
//import com.useroperations.CommonConnection;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class FetchStudentScore extends CommonConnection {
//
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
//                    int id = rs.getInt("student_id");
//                    int score = rs.getInt("score");
//                    System.out.println("Student ID: " + id + ", Score: " + score);
//                } else {
//                    System.out.println("No student found with ID: " + studentId);
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(); // or use proper logging
//        }
//    }
//}


package com.admin;

import com.useroperations.CommonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FetchStudentScore extends CommonConnection {

    // Existing method
    public static void getStudentScore(int studentId) {
        String selectSQL = "SELECT student_id, score FROM student_scores WHERE student_id = ?";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setInt(1, studentId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("student_id");
                    int score = rs.getInt("score");
                    System.out.println("Student ID: " + id + ", Score: " + score);
                } else {
                    System.out.println("No student found with ID: " + studentId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace(); // or use proper logging
        }
    }

    // 🔽 New method to get all student scores with full names
    public static void getAllStudentScores() {
        String query = "SELECT sd.student_id, CONCAT(sd.first_name, ' ', sd.last_name) AS full_name, ss.score " +
                "FROM student_data sd " +
                "JOIN student_scores ss ON sd.student_id = ss.student_id " +
                "ORDER BY ss.score ASC";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("Student Scores (Ascending Order):");
            System.out.println("----------------------------------");

            while (rs.next()) {
                int studentId = rs.getInt("student_id");
                String fullName = rs.getString("full_name");
                int score = rs.getInt("score");

                System.out.println("ID: " + studentId + ", Name: " + fullName + ", Score: " + score);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
