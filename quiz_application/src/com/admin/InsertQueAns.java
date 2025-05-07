package com.admin;
import com.useroperations.CommonConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertQueAns extends CommonConnection {

    public static void insertQuestions() {
        String insertSQL = "INSERT INTO question_answer (question, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = CommonConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Question 1
            pstmt.setString(1, "Which method is the entry point of a Java program?");
            pstmt.setString(2, "start()");
            pstmt.setString(3, "init()");
            pstmt.setString(4, "main()");
            pstmt.setString(5, "run()");
            pstmt.setString(6, "C");
            pstmt.addBatch();

            // Question 2
            pstmt.setString(1, "Which of the following is not a Java primitive type?");
            pstmt.setString(2, "int");
            pstmt.setString(3, "boolean");
            pstmt.setString(4, "String");
            pstmt.setString(5, "char");
            pstmt.setString(6, "C");
            pstmt.addBatch();

            // Question 3
            pstmt.setString(1, "Which keyword is used to prevent inheritance?");
            pstmt.setString(2, "final");
            pstmt.setString(3, "static");
            pstmt.setString(4, "private");
            pstmt.setString(5, "sealed");
            pstmt.setString(6, "A");
            pstmt.addBatch();

            // Question 4
            pstmt.setString(1, "Which of the following is a marker interface?");
            pstmt.setString(2, "Serializable");
            pstmt.setString(3, "Runnable");
            pstmt.setString(4, "AutoCloseable");
            pstmt.setString(5, "Comparable");
            pstmt.setString(6, "A");
            pstmt.addBatch();

            // Question 5
            pstmt.setString(1, "Which method is called automatically when an object is garbage collected?");
            pstmt.setString(2, "destroy()");
            pstmt.setString(3, "finalize()");
            pstmt.setString(4, "dispose()");
            pstmt.setString(5, "clean()");
            pstmt.setString(6, "B");
            pstmt.addBatch();

            // Question 6
            pstmt.setString(1, "What does the this keyword refer to?");
            pstmt.setString(2, "The current class");
            pstmt.setString(3, "The parent class");
            pstmt.setString(4, "The current object");
            pstmt.setString(5, "The static context");
            pstmt.setString(6, "C");
            pstmt.addBatch();

            // Question 7
            pstmt.setString(1, "Which keyword is used to define a constant in Java?");
            pstmt.setString(2, "const");
            pstmt.setString(3, "static");
            pstmt.setString(4, "final");
            pstmt.setString(5, "constant");
            pstmt.setString(6, "C");
            pstmt.addBatch();

            // Question 8
            pstmt.setString(1, "Which Java keyword is used to define a subclass?");
            pstmt.setString(2, "implements");
            pstmt.setString(3, "inherits");
            pstmt.setString(4, "extends");
            pstmt.setString(5, "super");
            pstmt.setString(6, "C");
            pstmt.addBatch();

            // Question 9
            pstmt.setString(1, "Which access modifier allows access within the same package and subclasses?");
            pstmt.setString(2, "public");
            pstmt.setString(3, "protected");
            pstmt.setString(4, "private");
            pstmt.setString(5, "default");
            pstmt.setString(6, "B");
            pstmt.addBatch();

            // Question 10
            pstmt.setString(1, "Which operator is used for object reference comparison in Java?");
            pstmt.setString(2, "==");
            pstmt.setString(3, "=");
            pstmt.setString(4, "equals()");
            pstmt.setString(5, "!=");
            pstmt.setString(6, "A");
            pstmt.addBatch();

            // Execute all inserts
            pstmt.executeBatch();
            System.out.println("10 new Java questions inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting questions", e);
        }
    }

    public static void main(String[] args) {
        insertQuestions();
    }
}
