package com.Registration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Student_Registration {

    public static void getStudentRegistrationCall() throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Student ID:");
        int stdId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter City:");
        String city = scanner.nextLine();

        System.out.println("Enter Mail ID:");
        String mailId = scanner.nextLine();

        System.out.println("Enter Mobile Number:");
        long mobileNumber = scanner.nextLong();
        scanner.nextLine(); // consume newline

        System.out.println("Enter Username:");
        String userName = scanner.nextLine();

        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        studentRegistration(stdId, firstName, lastName, city, mailId, mobileNumber, userName, password);
        System.out.println("Data inserted successfully.");
    }

    public static void studentRegistration(int stdId, String firstName, String lastName, String city, String mailId, long mobileNumber, String userName, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Updated driver class
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "mj@5857");

            String sql = "INSERT INTO Std (StdId, firstName, lastName, city, mailId, mobileNumber, userName, pass) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, stdId);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, city);
            preparedStatement.setString(5, mailId);
            preparedStatement.setLong(6, mobileNumber);
            preparedStatement.setString(7, userName);
            preparedStatement.setString(8, password);



            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data inserted into database successfully.");
            }


            connection.close();
        } catch (Exception e) {
            // System.out.println("Error: " + e.getMessage());
        }
    }
}
