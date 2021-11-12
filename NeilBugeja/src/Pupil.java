
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;


public class Pupil implements Serializable{
    
    private int studentid;
    private String name;
    private String surname;
    private String dob;
    private String gender;
    
    private ArrayList<Score> scores = new ArrayList<>();

    public Pupil(int studentid, String name, String surname, String dob) {
        this.studentid = studentid;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
    }
    
     // Sort Scores
    public ArrayList<Score> sortScores() {
        int n = scores.size();
        Score[] arr = scores.toArray(new Score[n]);
        for (int j = 0; j < arr.length; j++) {
            for (int i = j + 1; i < arr.length; i++) {
                if (arr[i].getScore() > arr[j].getScore()) {
                    Score t = arr[j];
                    arr[j] = arr[i];
                    arr[i] = t;
                }
            }
        }
        scores = new ArrayList<Score>(Arrays.asList(arr));
        return scores;
    }
    
    public void showScores(){
        // First sort scors
        scores = sortScores();
        String toDisplay = "";
        for(int i = 0;i < scores.size();i++){
            toDisplay = toDisplay + "    " + scores.get(i).toString() + "\n";
        }
        JOptionPane.showMessageDialog(null,name + " " + surname + "\n" + toDisplay);
    }

    public Score getTopScore(){
        if(scores.size() > 0){
            Score topScore = scores.get(0);
            for(int i = 1;i < scores.size();i++){
                if(scores.get(i).getScore() > topScore.getScore()){
                    topScore = scores.get(i);
                }
            }
            return topScore;
        }else{
            return null;
        }
    }
    
    public String getNameAndSurname(){
        return name + " " + surname;
    }
    
    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    } 

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}