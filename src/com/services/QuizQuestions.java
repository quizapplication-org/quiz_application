package com.services;

import com.Main;
import com.entity.Question;
import com.exceptions.CustomQuizException;
import com.utility.ConnectionString;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizQuestions {
    public static List<Question> loadQuestions(){
        List<Question> questions=new ArrayList<>();
       /* questions.add(new com.entity.Question(1,"What is JVM in Java?","Java Virtual Method","Java Very com.Main","Java Virtual Machine","Java Verified Machine",'C'));
        questions.add(new com.entity.Question(2,"Which keyword is used to inherit a class in Java?","implement","inherits","extends","super",'C'));
        questions.add(new com.entity.Question(3,"What is the default value of a boolean variable in Java?","true","false","0","null",'B'));
        questions.add(new com.entity.Question(4,"Which of the following is not a Java keyword?","static","Boolean","void","private",'B'));
        questions.add(new com.entity.Question(5,"Which method is the entry point of a Java program?","start()","main()","run()","init()",'B'));
        questions.add(new com.entity.Question(6,"Which of these is used to handle exceptions in Java?","try-catch","do-while","for-each","if-else",'A'));
        questions.add(new com.entity.Question(7,"What does the 'final' keyword mean in Java?","Cannot be inherited","Can be modified","Is static","Is public",'A'));
        questions.add(new com.entity.Question(8,"Which collection class allows you to access elements by a unique key?","List","Set","Map","Queue",'C'));
        questions.add(new com.entity.Question(9,"Which of the following is true about interfaces in Java?","They can have constructors","They can contain instance fields","They can have static methods","They can't be implemented by classes",'C'));
        questions.add(new com.entity.Question(10,"Which operator is used for comparing two values in Java?","=","==","!=","<>",'B'));*/
        int questionNumber=1;
        try {
            Connection connection =ConnectionString.getConnection();
            Statement s=connection.createStatement();

            ResultSet rs = s.executeQuery("SELECT * FROM questionSet");
            if(!rs.isBeforeFirst()){
                throw new CustomQuizException("No data Found.");
            }

            while(rs.next()){
                Question question=new Question();
                String questionText=rs.getString("questionText");
                String optionA=rs.getString("optionA");
                String optionB=rs.getString("optionB");
                String optionC=rs.getString("optionC");
                String optionD=rs.getString("optionD");
                String correctOption=rs.getString("correctOption");

                question.setQuestionText(questionText);
                question.setOptionA(optionA);
                question.setOptionB(optionB);
                question.setOptionC(optionC);
                question.setOptionD(optionD);
                question.setCorrectOption(correctOption.charAt(0));
                question.setId(questionNumber++);
                questions.add(question);
            }

            rs.close();
            s.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return questions;
    }

    public void studentQuiz(int studentId){
        boolean check = checkStudentAttempt(studentId);
        try{
        if (check) {
            throw new CustomQuizException("Sorry,You have already given the test.");
        } else {
            List<Question> questions = loadQuestions();
            int score = 0;
            Scanner scanner = new Scanner(System.in);
            for (Question q : questions) {
                q.display();
                System.out.println("Enter your answer(A/B/C/D): ");
                char answer = Character.toUpperCase(scanner.next().charAt(0));
                if (answer == q.getCorrectOption()) {
                    score++;
                }
            }
            createTable();
            saveToDB(studentId, score);
            System.out.println("Quiz Completed for StudentId:" + studentId + " Your score out of " + questions.size() + " is: " + score);
            Main.homepage();
        }
        }catch(CustomQuizException e){
            System.out.println("Exception Occur: "+e.getMessage());
            Main.homepage();
        }
    }
    public static void createTable(){

        Connection connection = null;
        try {
            connection = ConnectionString.getConnection();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS studentScore ("
                    + "studentId INT PRIMARY KEY,"
                    + "score INT,"
                    + "numberOfAttempt INT,"
                    + "createdDate DATE"
                    + ")";

            PreparedStatement ps=connection.prepareStatement(createTableSQL);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void saveToDB(int studentId,int score){
        try {
            Connection connection = ConnectionString.getConnection();
            PreparedStatement ps=connection.prepareStatement("insert into studentScore(studentId,score) values(?,?)");
            ps.setInt(1,studentId);
            ps.setInt(2,score);
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayAllStudents(){
        try {
            Connection connection =ConnectionString.getConnection();
            Statement s=connection.createStatement();
           // ResultSet rs=s.executeQuery("select * from studentScore ORDER BY score ASC");
            ResultSet rs = s.executeQuery("SELECT * FROM studentScore ORDER BY score ASC");
            if(!rs.isBeforeFirst()){
                throw new CustomQuizException("No data Found.");
            }

            while(rs.next()){
            int studId=rs.getInt("studentId");
            int score=rs.getInt("score");
            System.out.println("For student ID "+studId+" score is "+score);
            }

            rs.close();
            s.close();
            connection.close();

        }catch (CustomQuizException ce){
            System.out.println("Custom Exception: "+ce.getMessage());
            Main.homepage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void getStudentById(int studentId){
        try {
            Connection connection =ConnectionString.getConnection();
            PreparedStatement ps=connection.prepareStatement("select * from studentScore where studentId="+studentId);
            ResultSet rs = ps.executeQuery();
            if(!rs.isBeforeFirst()){
                throw new CustomQuizException("No data Found for student Id "+studentId);
            }
            while(rs.next()){
                int studId=rs.getInt("studentId");
                int score=rs.getInt("score");
                System.out.println("For student ID "+studId+" score is "+score);
            }
            ps.execute();
            connection.close();
        }catch (CustomQuizException ce){
            System.out.println("Custom Exception: "+ce.getMessage());
            Main.homepage();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createQuestionSetTable(){

        Connection connection = null;
        try {
            connection = ConnectionString.getConnection();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS questionSet ("
                    + "quetionId INT AUTO_INCREMENT PRIMARY KEY,"
                    + "questionText VARCHAR(250),"
                    + "optionA VARCHAR(100),"
                    + "optionB VARCHAR(100),"
                    + "optionC VARCHAR(100),"
                    + "optionD VARCHAR(100),"
                    + "correctOption VARCHAR(1)"
                    + ")";

            PreparedStatement ps=connection.prepareStatement(createTableSQL);
            ps.execute();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void addQuestionSet(){
        createQuestionSetTable();
        try {
            Scanner sc=new Scanner(System.in);
            System.out.println("Enter com.entity.Question Text");
            String questionText=sc.nextLine();
            System.out.println("Enter Option A");
            String optionA= sc.nextLine();
            System.out.println("Enter Option B");
            String optionB= sc.nextLine();
            System.out.println("Enter Option C");
            String optionC= sc.nextLine();
            System.out.println("Enter Option D");
            String optionD= sc.nextLine();
            System.out.println("Enter Option Correct option");
            char correctOption=Character.toUpperCase(sc.next().charAt(0));

            Connection connection =ConnectionString.getConnection();
            PreparedStatement ps=connection.prepareStatement("insert into questionSet(questionText,optionA,optionB,optionC,optionD,correctOption) values(?,?,?,?,?,?)");
            ps.setString(1,questionText);
            ps.setString(2,optionA);
            ps.setString(3,optionB);
            ps.setString(4,optionC);
            ps.setString(5,optionD);
            ps.setString(6, String.valueOf(correctOption));
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean checkStudentAttempt(int studentId) {
        String query = "SELECT * FROM studentScore WHERE studentId = ?";
        try (
                Connection connection = ConnectionString.getConnection();
                PreparedStatement ps = connection.prepareStatement(query)
        ) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.isBeforeFirst();  // True if at least one row exists
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}


