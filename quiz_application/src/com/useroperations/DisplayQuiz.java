package com.useroperations;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DisplayQuiz {

    public static void fetchAndSubmitAnswers(int studentId) {
        if (hasStudentAlreadyAttemptedQuiz(studentId)) {
            System.out.println("You have already attempted the quiz. You cannot take it again.");
            return;
        }

        Scanner sc = new Scanner(System.in);
        int score = 0;

        String selectSQL = "SELECT queId, question, option_a, option_b, option_c, option_d, correct_option FROM question_answer";
        String insertAnswerSQL = "INSERT INTO student_answers (student_id, question_id, user_answer, correct_answer, is_correct) VALUES (?, ?, ?, ?, ?)";
        String insertScoreSQL = "INSERT INTO student_scores (student_id, score) VALUES (?, ?)";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSQL);
             PreparedStatement insertAnswerStmt = conn.prepareStatement(insertAnswerSQL);
             PreparedStatement insertScoreStmt = conn.prepareStatement(insertScoreSQL);
             ResultSet rs = selectStmt.executeQuery()) {

            while (rs.next()) {
                int queId = rs.getInt("queId");
                String question = rs.getString("question");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctOption = rs.getString("correct_option");

                System.out.println("Question ID: " + queId);
                System.out.println(question);
                System.out.println("A: " + optionA);
                System.out.println("B: " + optionB);
                System.out.println("C: " + optionC);
                System.out.println("D: " + optionD);
                System.out.print("Enter your answer (A/B/C/D): ");
                char userAnswer = Character.toUpperCase(sc.next().charAt(0));

                boolean isCorrect = String.valueOf(userAnswer).equalsIgnoreCase(correctOption);
                if (isCorrect) score++;

                insertAnswerStmt.setInt(1, studentId);
                insertAnswerStmt.setInt(2, queId);
                insertAnswerStmt.setString(3, String.valueOf(userAnswer));
                insertAnswerStmt.setString(4, correctOption);
                insertAnswerStmt.setBoolean(5, isCorrect);
                insertAnswerStmt.executeUpdate();

                System.out.println("------------------------------------------------");
            }

            insertScoreStmt.setInt(1, studentId);
            insertScoreStmt.setInt(2, score);
            insertScoreStmt.executeUpdate();

            System.out.println("Your total score: " + score);

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching or storing answers", e);
        }
    }

    public static void displayStudentScore(int studentId) {
        String scoreQuery = "SELECT score FROM student_scores WHERE student_id = ? ORDER BY id DESC LIMIT 1";
        String countQuery = "SELECT COUNT(*) AS total FROM question_answer";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement scoreStmt = conn.prepareStatement(scoreQuery);
             PreparedStatement countStmt = conn.prepareStatement(countQuery)) {

            scoreStmt.setInt(1, studentId);
            ResultSet scoreRs = scoreStmt.executeQuery();
            ResultSet countRs = countStmt.executeQuery();

            if (scoreRs.next() && countRs.next()) {
                int score = scoreRs.getInt("score");
                int total = countRs.getInt("total");

                // Custom display format
                System.out.println("You scored " + score + " out of " + total);

                displayQuestionsWithAnswers(studentId);
            } else {
                System.out.println("No score found for Student ID " + studentId);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving student score: " + e.getMessage());
        }
    }


    private static void displayQuestionsWithAnswers(int studentId) {
        String questionSQL = "SELECT qa.queId, qa.question, qa.option_a, qa.option_b, qa.option_c, qa.option_d, qa.correct_option, sa.user_answer " +
                "FROM question_answer qa " +
                "JOIN student_answers sa ON qa.queId = sa.question_id " +
                "WHERE sa.student_id = ?";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(questionSQL)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int queId = rs.getInt("queId");
                String question = rs.getString("question");
                String optionA = rs.getString("option_a");
                String optionB = rs.getString("option_b");
                String optionC = rs.getString("option_c");
                String optionD = rs.getString("option_d");
                String correctOption = rs.getString("correct_option");
                String userAnswer = rs.getString("user_answer");

                String correctAnswerText = getOptionText(correctOption, optionA, optionB, optionC, optionD);
                String userAnswerText = getOptionText(userAnswer, optionA, optionB, optionC, optionD);

                System.out.println("Question ID   : " + queId);
                System.out.println("Question      : " + question);
                System.out.println("Correct Answer: " + correctOption + ". " + correctAnswerText);
                System.out.println("Your Answer   : " + userAnswer + ". " + userAnswerText);
                System.out.println("------------------------------------------------");
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving detailed question results: " + e.getMessage());
        }
    }

    private static String getOptionText(String optionLetter, String a, String b, String c, String d) {
        switch (optionLetter.toUpperCase()) {
            case "A": return a;
            case "B": return b;
            case "C": return c;
            case "D": return d;
            default: return "Invalid Option";
        }
    }


    private static boolean hasStudentAlreadyAttemptedQuiz(int studentId) {
        String query = "SELECT COUNT(*) FROM student_scores WHERE student_id = ?";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error checking previous quiz attempt: " + e.getMessage());
        }

        return false;
    }
}
