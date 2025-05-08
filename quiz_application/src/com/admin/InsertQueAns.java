package com.admin;

import com.useroperations.CommonConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertQueAns extends CommonConnection {

    public static void insertQuestion(String question, String optionA, String optionB,
                                      String optionC, String optionD, String correctOption) {
        String insertSQL = "INSERT INTO question_answer (question, option_a, option_b, option_c, option_d, correct_option) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, question);
            pstmt.setString(2, optionA);
            pstmt.setString(3, optionB);
            pstmt.setString(4, optionC);
            pstmt.setString(5, optionD);
            pstmt.setString(6, correctOption.toUpperCase());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Question inserted successfully.");
            } else {
                System.out.println("Insertion failed.");
            }

        } catch (Exception e) {
            e.printStackTrace(); // Use proper logging in real applications
        }
    }
}
