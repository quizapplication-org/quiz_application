package com.Registration;

import com.Main;
import com.entity.User;
import com.exceptions.CustomQuizException;
import com.services.QuizQuestions;
import com.utility.ConnectionString;

import java.sql.*;
import java.util.Scanner;

public class UserRegistration {
    public static void registrationDetails(){
        User user=new User();
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the first name ");
        String firstName=sc.next();
        System.out.println("Enter Last Name ");
        String lastName=sc.next();
        System.out.println("Enter Username ");
        String userName=sc.next();
        System.out.println("Enter Password ");
        String password=sc.next();
        System.out.println("Re-enter Password ");
        String repassword= sc.next();
        System.out.println("Enter City ");
        String city=sc.next();
        System.out.println("Enter Mail-Id ");
        String mailId=sc.next();
        System.out.println("Enter Mobile Number ");
        long mobileNo=sc.nextLong();

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        user.setRePassword(repassword);
        user.setCity(city);
        user.setEmailId(mailId);
        user.setMobileNo(mobileNo);


        if(password.equals(repassword)){
            saveStudentInformation(user);
            System.out.println("Welcome,"+user.getFirstName()+" your account has been created successfully.");
            System.out.println("Continue Login (Y/N)?");
            String str=sc.next();

            if(str.equalsIgnoreCase("y")) {
                studentLogin();
            }else{
                Main.homepage();
            }
        }else{
            System.out.println("Password does not matched,please Re-enter the password");
        }
    }

    public static void  saveStudentInformation(User user){
         createUserTable();
        try {  //making DB connection
            Connection connection = ConnectionString.getConnection();
            PreparedStatement ps=connection.prepareStatement("insert into userDetails(firstName,lastName,userName,password,city,mailId,mobileNo) values(?,?,?,?,?,?,?)");
            ps.setString(1,user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3,user.getUserName());
            ps.setString(4,user.getPassword());
            ps.setString(5,user.getCity());
            ps.setString(6,user.getEmailId());
            ps.setLong(7,user.getMobileNo());

            ps.execute();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createUserTable(){

        Connection connection = null;
        try {
            connection = ConnectionString.getConnection();
            String createTableSQL = "CREATE TABLE IF NOT EXISTS userDetails ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY ,"
                    + "firstName VARCHAR(100) ,"
                    + "lastName VARCHAR(100) ,"
                    + "userName VARCHAR(100) ,"
                    + "password VARCHAR(100) ,"
                    + "city VARCHAR(100) ,"
                    + "mailId VARCHAR(100) ,"
                    + "mobileNo BIGINT,"
                    + "createdDate DATE"
                    + ")";

            PreparedStatement ps=connection.prepareStatement(createTableSQL);
            ps.execute();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
public static void studentLogin(){
        Scanner sc=new Scanner(System.in);
    System.out.println("Enter Username");
    String userName= sc.next();
    System.out.println("Enter Password");
    String password=sc.next();
    try {
        Connection connection =ConnectionString.getConnection();
        PreparedStatement ps=connection.prepareStatement("select * from userDetails where userName=? AND password=?");
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        if(!rs.isBeforeFirst()){
            throw new CustomQuizException("Invalid Username or Password.");
        }else{
            System.out.println("Account logged-in Successfully.");
            System.out.println("Do you want to start Quiz(Y/N)?");
            String str=sc.next();
            if(str.equalsIgnoreCase("y")) {
                QuizQuestions quizQuestions=new QuizQuestions();
               if(rs.next()){
                   int id=rs.getInt("id");
                   quizQuestions.studentQuiz(id);
               }
            }else{
               Main.homepage();
            }
            }
        connection.close();
    }catch (CustomQuizException ce){
        System.out.println("Custom Exception: "+ce.getMessage());
        Main.homepage();
    }
    catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
     public static void adminLogin(){
        Scanner sc=new Scanner(System.in);
         System.out.println("Enter Admin username");
          String username=sc.next();
         System.out.println("Enter Admin password");
         String password=sc.next();
         if(username.equals("admin") && password.equals("admin")){
             System.out.println("Logged-in Successfully");
             System.out.println("Enter choice");
             System.out.println("Enter 1: Display all students score.");
             System.out.println("Enter 2: Fetch student score by using id");
             System.out.println("Enter 3: Add question with 4 options into database");
             System.out.println("Enter 4: Go to Homepage");
             int choice=sc.nextInt();

             switch (choice){
                 case 1:
                     QuizQuestions.displayAllStudents();
                     Main.homepage();
                     break;
                 case 2:
                     System.out.println("Enter Student Id");
                     int studentId=sc.nextInt();
                     QuizQuestions.getStudentById(studentId);
                     Main.homepage();
                     break;
                 case 3:
                     QuizQuestions.addQuestionSet();
                     System.out.println("com.entity.Question added successfully...!");
                     Main.homepage();
                     break;
                 case 4:
                     Main.homepage();
                     break;
             }
         } else{
             System.out.println("Invalid Admin credential,Please try again.");
             Main.homepage();
         }
     }
}
