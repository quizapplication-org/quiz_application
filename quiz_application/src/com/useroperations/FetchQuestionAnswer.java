package com.useroperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FetchQuestionAnswer {

    public static void fetchQuestions() {

        Scanner sc = new Scanner(System.in);
        int score = 0; // total score

        String selectSQL = "SELECT queId, question, option_a, option_b, option_c, option_d, correct_option FROM question_answer";

        try (Connection conn = com.useroperations.CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {

            // Iterate over the result set and display the questions and options
            while (rs.next()) {
                int queId = rs.getInt("queId");
                String question = rs.getString("question");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctOption = rs.getString("correct_option");
//
                System.out.println("Question: " + queId);
                System.out.println(question);
                System.out.println("A: " + optionA);
                System.out.println("B: " + optionB);
                System.out.println("C: " + optionC);
                System.out.println("D: " + optionD);

                System.out.print("Enter your answer (A/B/C/D): ");
                char userAnswer = Character.toUpperCase(sc.next().charAt(0)); // Normalize to uppercase

                // Validate answer
                if (String.valueOf(userAnswer).equalsIgnoreCase(correctOption)) {
                    score++;
                }

                System.out.println("------------------------------------------------");
            }

            System.out.println("Your total score: " + score);

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching questions", e);
        }
    }

    public static void main(String[] args) {
        fetchQuestions();
    }
}



