import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class QuizQuestions {
    public static List<Question> loadQuestions(){
        List<Question> questions=new ArrayList<>();
        questions.add(new Question(1,"What is JVM in Java?","Java Virtual Method","Java Very Main","Java Virtual Machine","Java Verified Machine",'C'));
        questions.add(new Question(2,"Which keyword is used to inherit a class in Java?","implement","inherits","extends","super",'C'));
        questions.add(new Question(3,"What is the default value of a boolean variable in Java?","true","false","0","null",'B'));
        questions.add(new Question(4,"Which of the following is not a Java keyword?","static","Boolean","void","private",'B'));
        questions.add(new Question(5,"Which method is the entry point of a Java program?","start()","main()","run()","init()",'B'));
        questions.add(new Question(6,"Which of these is used to handle exceptions in Java?","try-catch","do-while","for-each","if-else",'A'));
        questions.add(new Question(7,"What does the 'final' keyword mean in Java?","Cannot be inherited","Can be modified","Is static","Is public",'A'));
        questions.add(new Question(8,"Which collection class allows you to access elements by a unique key?","List","Set","Map","Queue",'C'));
        questions.add(new Question(9,"Which of the following is true about interfaces in Java?","They can have constructors","They can contain instance fields","They can have static methods","They can't be implemented by classes",'C'));
        questions.add(new Question(10,"Which operator is used for comparing two values in Java?","=","==","!=","<>",'B'));

        return questions;
    }

    public void studentQuiz(int studentId) throws SQLException {

       List<Question> questions=loadQuestions();
       int score=0;
        Scanner scanner=new Scanner(System.in);
       for(Question q:questions){
           q.display();
           System.out.println("Enter your answer(A/B/C/D): ");
           char answer=Character.toUpperCase(scanner.next().charAt(0));
           if(answer==q.correctOption){
             score++;
           }

       } createTable();
       saveToDB(studentId,score);
        System.out.println("Quiz Completed for StudentId:" +studentId+" Your score out of 10 is: "+score);
        Main.homepage();
    }
    public static void createTable() throws SQLException {

       Connection connection =ConnectionString.getConnection();
        String createTableSQL = "CREATE TABLE IF NOT EXISTS studentScore ("
                + "studentId INT PRIMARY KEY,"
                + "score INT,"
                + "numberOfAttempt INT,"
                + "createdDate DATE"
                + ")";

        PreparedStatement ps=connection.prepareStatement(createTableSQL);
        ps.execute();
        connection.close();

    }

    public static void saveToDB(int studentId,int score){
        try {
            Connection connection =ConnectionString.getConnection();
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
                System.out.println("No data Found.");
            }

            while(rs.next()){
            int studId=rs.getInt("studentId");
            int score=rs.getInt("score");
            System.out.println("For student ID "+studId+" score is "+score);
            }

            rs.close();
            s.close();
            connection.close();

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
                System.out.println("No data Found for student Id "+studentId);
            }
            while(rs.next()){
                int studId=rs.getInt("studentId");
                int score=rs.getInt("score");
                System.out.println("For student ID "+studId+" score is "+score);
            }
            ps.execute();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
