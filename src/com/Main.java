package com;

import com.Registration.UserRegistration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        homepage();
    }
    public static void homepage(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter the choice: ");
        System.out.println("select 1 for student sign-up");
        System.out.println("Select 2 for Student login");
        System.out.println("select 3 for Admin login");

        int choice=sc.nextInt();
        switch(choice){
            case 1:
                UserRegistration.registrationDetails();
                break;
            case 2:
                UserRegistration.studentLogin();
                break;
            case 3:
                UserRegistration.adminLogin();
                break;
            default:
                System.out.println("Invalid Input");
                homepage();
        }
    }
}