
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

public class MultipleChoiceQuestion extends Question {

    private String picPath;
    private String[] answers = new String[4];

    private JRadioButton answerLabel1;
    private JRadioButton answerLabel2;
    private JRadioButton answerLabel3;
    private JRadioButton answerLabel4;

    public MultipleChoiceQuestion(String s) {
        String[] x = s.split("#");
        picPath = "Files//Pics//" + x[0];
        setQuestionText(x[1]);
        setAnswerText(x[2]);
        for (int i = 2; i <= 5; i++) {
            answers[i - 2] = x[i];
        }
    }

    public boolean playQuestion(JFrame f) {
        showQuestion(f);
        return true;
    }

    public void showQuestion(JFrame f) {
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean answerCorrect = false;
                if (answerLabel1.isSelected()) {
                    if (answers[0].equals(getAnswerText())) {
                        answerCorrect = true;
                    }
                }

                if (answerLabel2.isSelected()) {
                    if (answers[1].equals(getAnswerText())) {
                        answerCorrect = true;
                    }
                }

                if (answerLabel3.isSelected()) {
                    if (answers[2].equals(getAnswerText())) {
                        answerCorrect = true;
                    }
                }

                if (answerLabel4.isSelected()) {
                    if (answers[3].equals(getAnswerText())) {
                        answerCorrect = true;
                    }
                }

                if (answerCorrect) {
                    setScore(1);
                } else {
                    setScore(0);
                }
                questionFrame.dispose();
            }
        });

        
        
        // To shuffle the possable answers
        Random randomGenerator = new Random();
        for(int i = 0;i < 10;i++){
            int r1 = randomGenerator.nextInt(4);
            int r2 = randomGenerator.nextInt(4);
            String temp;
            temp = answers[r1];
            answers[r1] = answers[r2];
            answers[r2] = temp;
        }
        //
        
        Font myFont = new Font("Arial", Font.BOLD, 14);
        JLabel picLabel = new JLabel(new ImageIcon(picPath));
        JLabel questionLabel = new JLabel(getQuestionText());
        questionLabel.setFont(myFont);
        answerLabel1 = new JRadioButton(answers[0]);
        answerLabel2 = new JRadioButton(answers[1]);
        answerLabel3 = new JRadioButton(answers[2]);
        answerLabel4 = new JRadioButton(answers[3]);

        ButtonGroup bg = new ButtonGroup();
        bg.add(answerLabel1);
        bg.add(answerLabel2);
        bg.add(answerLabel3);
        bg.add(answerLabel4);

        questionFrame = new JDialog(f, true);
        questionFrame.setLayout(new BoxLayout(questionFrame.getContentPane(), BoxLayout.Y_AXIS));
        questionFrame.add(picLabel);
        questionFrame.add(questionLabel);
        questionFrame.add(answerLabel1);
        questionFrame.add(answerLabel2);
        questionFrame.add(answerLabel3);
        questionFrame.add(answerLabel4);
        questionFrame.add(new JSeparator());

        JPanel p = new JPanel(new FlowLayout());
        p.add(confirmButton);
        questionFrame.add(p);

        questionFrame.pack();
        questionFrame.setVisible(true);
    }

}
