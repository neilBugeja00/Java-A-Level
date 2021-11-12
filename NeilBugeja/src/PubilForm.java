
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class PubilForm extends JFrame implements ActionListener {

    private JTextField idField = new JTextField(15);
    private JTextField nameField = new JTextField(25);
    private JTextField surnameField = new JTextField(25);
    private JTextField dobField = new JTextField(15);
    private JRadioButton maleRB = new JRadioButton("Boy");
    private JRadioButton femaleRB = new JRadioButton("Girl");
    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");

    private ArrayList<Pupil> allPupils;
    private Pupil pupilToEdit = null;

    public PubilForm(ArrayList<Pupil> allPupils) {
        this.allPupils = allPupils;
        setTitle("Pupil Form");
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        add(getFormLine(idField, "ID               "));
        add(getFormLine(nameField, "Name          "));
        add(getFormLine(surnameField, "Surname     "));
        add(getFormLine(dobField, "Date of Birth [dd-mm-yyyy]  "));
        add(getFormLine(maleRB, femaleRB, "Gender      "));
        add(new JSeparator());
        add(getFormLine(okButton, cancelButton));

        maleRB.setSelected(true);
        ButtonGroup bg = new ButtonGroup();
        bg.add(maleRB);
        bg.add(femaleRB);
        okButton.addActionListener(this);
        cancelButton.addActionListener(this);
        idField.setText(getNextStudentId() + "");
        idField.setEditable(false);
        pack();
    }

    public void usedInEditMode(Pupil p) {
        pupilToEdit = p;
        idField.setText(pupilToEdit.getStudentid() + "");
        nameField.setText(pupilToEdit.getName());
        surnameField.setText(pupilToEdit.getSurname());
        dobField.setText(pupilToEdit.getDob());
        if (pupilToEdit.getGender().equalsIgnoreCase("BOY")) {
            maleRB.setSelected(true);
            femaleRB.setSelected(false);
        } else {
            maleRB.setSelected(false);
            femaleRB.setSelected(true);
        }
        okButton.setText("Edit");
        cancelButton.setText("Delete");
    }

    private int getNextStudentId() {
        if (allPupils.size() == 0) {
            return 1;
        } else {
            return allPupils.get(allPupils.size() - 1).getStudentid() + 1;
        }
    }

    public boolean isDateValid(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            sdf.parse(date);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean doDataValidation() {
        String errorMsg = "";
        if (nameField.getText().trim().length() == 0) {
            errorMsg = errorMsg + "Name is required\n";
        }

        if (surnameField.getText().trim().length() == 0) {
            errorMsg = errorMsg + "Surname is required\n";
        }

        if (isDateValid(dobField.getText().trim()) == false) {
            errorMsg = errorMsg + "Invalid date of birth\n";
        }

        if (errorMsg.length() == 0) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, errorMsg);
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancel")) {
            this.dispose();
        } else if (e.getActionCommand().equals("Edit")) {
            if (doDataValidation() == true) {
                pupilToEdit.setName(nameField.getText().trim());
                pupilToEdit.setSurname(surnameField.getText().trim());
                pupilToEdit.setDob(dobField.getText().trim());
                if (maleRB.isSelected()) {
                    pupilToEdit.setGender("BOY");
                } else {
                    pupilToEdit.setGender("GIRL");
                }
                JOptionPane.showMessageDialog(rootPane, "Pupil Information Eddited Successfully");
                this.dispose();
            }
        } else if (e.getActionCommand().equals("Delete")) {
            allPupils.remove(pupilToEdit);
            JOptionPane.showMessageDialog(rootPane, "Pupil Information Removed Successfully");
            this.dispose();
        } else { // Ok was pressed
            // Check that the data is valid
            if (doDataValidation() == true) {
                Pupil s = new Pupil(getNextStudentId(),
                        nameField.getText().trim(),
                        surnameField.getText().trim(),
                        dobField.getText().trim());
                if (maleRB.isSelected()) {
                    s.setGender("BOY");
                } else {
                    s.setGender("GIRL");
                }
                allPupils.add(s);
                JOptionPane.showMessageDialog(this, "New pupil added successfullly");
                this.dispose();
            }
        }
    }

    // The following getFormLine methods were taken from notes //////////////////////////////////////////////////
    public JPanel getFormLine(JComponent component, String labelText) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lab = new JLabel(labelText);
        p.add(lab);
        p.add(component);
        return p;
    }

    public JPanel getFormLine(JComponent component1, JComponent component2, String labelText) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lab = new JLabel(labelText);
        p.add(lab);
        p.add(component1);
        p.add(component2);
        return p;
    }

    public JPanel getFormLine(JButton button1, JButton button2) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(button1);
        p.add(button2);
        return p;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////
}
