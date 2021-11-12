import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;


public class QuestionAndAnswer extends Question {

    private String picPath;
    private JTextField answerField = new JTextField(10);
    
    public QuestionAndAnswer(String s){
        String[] x = s.split("#");
        picPath = "Files//Pics//" + x[0];
        setQuestionText(x[1]);
        setAnswerText(x[2]);
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
                if(answerField.getText().trim().equalsIgnoreCase(getAnswerText())){
                    answerCorrect = true;
                }else{
                    answerCorrect = false;
                }

                if (answerCorrect) {
                    setScore(1);
                } else {
                    setScore(0);
                }
                questionFrame.dispose();
            }
        });

        Font myFont = new Font("Arial", Font.BOLD, 14);
        JLabel picLabel = new JLabel(new ImageIcon(picPath));
        JLabel questionLabel = new JLabel(getQuestionText());
        questionLabel.setFont(myFont);
        
        questionFrame = new JDialog(f, true);
        questionFrame.setLayout(new BoxLayout(questionFrame.getContentPane(), BoxLayout.Y_AXIS));
        questionFrame.add(picLabel);
        questionFrame.add(questionLabel);
        questionFrame.add(answerField);
        questionFrame.add(new JSeparator());

        JPanel p = new JPanel(new FlowLayout());
        p.add(confirmButton);
        questionFrame.add(p);

        questionFrame.pack();
        questionFrame.setVisible(true);
    }
}
