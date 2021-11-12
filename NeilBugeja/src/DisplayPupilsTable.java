
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class DisplayPupilsTable extends JFrame  implements ListSelectionListener {

    private ArrayList<Pupil> toShow;
    private JTable t;
    
    public DisplayPupilsTable(ArrayList<Pupil> toShow) {
        setTitle("Members Table");
        this.toShow = toShow;
        String[] headers = {"ID", "Name", "Surname", "Date of Birth", "Gender", };
        String[][] content = new String[toShow.size()][5];

        for (int i = 0; i < toShow.size(); i++) {
            content[i][0] = toShow.get(i).getStudentid() + "";
            content[i][1] = toShow.get(i).getName();
            content[i][2] = toShow.get(i).getSurname();
            content[i][3] = toShow.get(i).getDob();
            content[i][4] = toShow.get(i).getGender();
        }

        t = new JTable(content, headers);
        setLayout(new BorderLayout());
        add(t, BorderLayout.CENTER);
        add(t.getTableHeader(), BorderLayout.NORTH);
        pack();
        setVisible(true);
        
        ListSelectionModel cellSelectionModel = t.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(this);
    }
    
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) {
            Pupil pupil = toShow.get(t.getSelectedRow());
            pupil.showScores();
        }
    }
}