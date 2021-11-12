
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class QuizMenu extends JFrame implements ActionListener {

    private JButton addStudent = new JButton("Add Pupil");
    private JButton searchPupil = new JButton("Search Pupil");
    private JButton showScores = new JButton("Show Scores");
    private JButton questionsQuiz = new JButton("Start Questions Quiz");
    private JButton pictureQuiz = new JButton("Start Picture Quiz");
    private JButton mixedQuiz = new JButton("Start Mixed Questions Quiz");

    private ArrayList<Pupil> pupils = new ArrayList<>();
    private ArrayList<Question> multipleChoiceQuestions = new ArrayList<>();
    private ArrayList<Question> qAndAQuestions = new ArrayList<>();

    public QuizMenu() {
        // As soon as program starts, load all data from files
        loadQuestionAndAnswerQuestions();
        loadMultipleChoiceQuestions();
        loadPupilsInfoFromFile();
        setTitle("Educational Quiz");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(addStudent);
        add(searchPupil);
        add(showScores);
        add(questionsQuiz);
        add(pictureQuiz);
        add(mixedQuiz);

        // To continue from here
        addStudent.addActionListener(this);
        showScores.addActionListener(this);
        questionsQuiz.addActionListener(this);
        pictureQuiz.addActionListener(this);
        searchPupil.addActionListener(this);
        mixedQuiz.addActionListener(this);

        pack();
        setVisible(true);

        // When progam ends, save back all pupils data to file
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                savePupilsInfoToFile();
            }
        });
    }

    // Linear Search for Pupil by ID
    private Pupil getPupilWithId(int idPupil) {
        for (int i = 0; i < pupils.size(); i++) {
            if (pupils.get(i).getStudentid() == idPupil) {
                return pupils.get(i);
            }
        }
        return null;
    }

    private void loadMultipleChoiceQuestions() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("Files//MultipleChoiceQuestions.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                multipleChoiceQuestions.add(new MultipleChoiceQuestion(str));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestionAndAnswerQuestions() {
        try {
            BufferedReader in = new BufferedReader(new FileReader("Files//QuestionAndAnswer.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                qAndAQuestions.add(new QuestionAndAnswer(str));
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Taken from notes
    private void savePupilsInfoToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream("pupils.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(pupils);
            out.close();
            fileOut.close();
        } catch (Exception e) {

        }
    }

    // Taken from notes
    private void loadPupilsInfoFromFile() {
        try {
            FileInputStream fileIn = new FileInputStream("pupils.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pupils = (ArrayList<Pupil>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            System.out.println("Files not loaded. Problem with file.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add Pupil":
                addPupil();
                break;
            case "Show Scores":
                DisplayPupilsTable x = new DisplayPupilsTable(pupils);
                x.setVisible(true);
                break;
            case "Start Questions Quiz":
                playQuiz(get5RandomQuestions(qAndAQuestions), "Question And Answer Quiz");
                break;
            case "Start Picture Quiz":
                playQuiz(get5RandomQuestions(multipleChoiceQuestions), "Picture Quiz");
                break;
            case "Start Mixed Questions Quiz":
                ArrayList<Question> all = multipleChoiceQuestions;
                all.addAll(qAndAQuestions);
                playQuiz(get5RandomQuestions(all), "Mixed Questions Quiz");
                break;
            case "Search Pupil":
                searchPupil();
                break;
        }
    }

    // Taken from notes
    public String getTodaysDate() {  // dd-MM-yyyy
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            return sdf.format(new Date());
        } catch (Exception e) {
            return "";
        }
    }

    private void playQuiz(ArrayList<Question> questionsToAsk, String quizTitle) {
        int score = 0;
        for (int i = 0; i < 5; i++) {
            questionsToAsk.get(i).playQuestion(this);
            score = score + questionsToAsk.get(i).getScore();
        }
        int n = JOptionPane.showConfirmDialog(this,
                "You've got a score of " + score + ". Do you want to save your score?", "Total Score", JOptionPane.YES_NO_OPTION);
        if (n == 0) {
            String pupleId = JOptionPane.showInputDialog("Enter your pupil ID:");
            try {
                Pupil puple = getPupilWithId(Integer.parseInt(pupleId));
                if (puple == null) {
                    JOptionPane.showMessageDialog(null, "Pupli not registered", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Score scorePoints = new Score();
                    scorePoints.setScore(score);
                    scorePoints.setTotalPossableScore(questionsToAsk.size());
                    scorePoints.setQuizType(quizTitle);
                    scorePoints.setDate(getTodaysDate());
                    puple.getScores().add(scorePoints);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Invalid Pupil ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ArrayList<Question> get5RandomQuestions(ArrayList<Question> allQuestions) {
        // To get 5 different random questions
        ArrayList<Integer> allQuestionNumbers = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            allQuestionNumbers.add(i);
        }

        Random randomGenerator = new Random();

        ArrayList<Question> ans = new ArrayList<>();
        for (int i = 0; i < allQuestions.size(); i++) {
            int r = randomGenerator.nextInt(allQuestionNumbers.size());
            int pos = allQuestionNumbers.get(r).intValue();
            ans.add(allQuestions.get(pos));
            allQuestionNumbers.remove(r);
        }
        return ans;
    }

    private void addPupil() {
        PubilForm t = new PubilForm(pupils);
        t.setVisible(true);
    }

    private void searchPupil() {
        String searchKey = JOptionPane.showInputDialog("Enter pupil's ID or NAME and SURNAME");
        // First try to search pupil by ID
        Pupil p = searchPupilById(searchKey);
        if (p != null) {
            PubilForm form = new PubilForm(pupils);
            form.usedInEditMode(p);
            form.setVisible(true);
        } else {
            // If pupil not found, then try to search pupil by NAME and SURNAME
            ArrayList<Pupil> results = searchPupilByNameAndSurname(searchKey);
            new DisplayPupilsTable(results);
        }
    }

    private Pupil searchPupilById(String id) {
        // Linear Search Implementation
        for (int i = 0; i < pupils.size(); i++) {
            if ((pupils.get(i).getStudentid() + "").equals(id)) {
                return pupils.get(i);
            }
        }
        return null;
    }

    private ArrayList<Pupil> searchPupilByNameAndSurname(String ns) {
        // Linear Search Implementation
        ArrayList<Pupil> searchResults = new ArrayList<>();
        for (int i = 0; i < pupils.size(); i++) {
            if (pupils.get(i).getNameAndSurname().equalsIgnoreCase(ns)) {
                searchResults.add(pupils.get(i));
            }
        }
        return searchResults;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Do nothing
        }

        new QuizMenu();
    }

}
