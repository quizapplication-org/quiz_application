public class Question {
    int id;
    String questionText;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    char correctOption;

    public Question(int id, String questionText, String optionA, String optionB, String optionC, String optionD, char correctOption) {
        this.id = id;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
    }

    public void display(){
        System.out.println("Q"+id+": "+questionText);
        System.out.println("A. "+optionA);
        System.out.println("B. "+optionB);
        System.out.println("C. "+optionC);
        System.out.println("D. "+optionD);
    }
}
