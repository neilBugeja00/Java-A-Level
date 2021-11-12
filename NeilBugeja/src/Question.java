
import javax.swing.JDialog;
import javax.swing.JFrame;

public abstract class Question {
    
    private String questionText;
    private String answerText;
    private int score = 0;
    
    protected JDialog questionFrame;

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public abstract boolean playQuestion(JFrame f);
    
}
