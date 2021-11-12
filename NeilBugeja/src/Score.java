
import java.io.Serializable;


public class Score implements Serializable{
    private String date;
    private String quizType;
    private int score;
    private int totalPossableScore;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuizType() {
        return quizType;
    }

    public void setQuizType(String quizType) {
        this.quizType = quizType;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalPossableScore() {
        return totalPossableScore;
    }

    public void setTotalPossableScore(int totalPossableScore) {
        this.totalPossableScore = totalPossableScore;
    }
    
    public String toString(){
        return date + "   " + quizType + "   " + score;
    }
}