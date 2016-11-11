import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;




public class CreateNewUser implements ActionListener {

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField dateHiredField;
    private JTextField userNameField;
    private JPasswordField passWordField;

    public CreateNewUser(JTextField firstName, JTextField lastName, JTextField dateHired, JTextField username, JPasswordField password ){
        this.firstNameField = firstName;
        this.lastNameField = lastName;
        this.dateHiredField = dateHired;
        this.userNameField = username;
        this.passWordField = password;
    }

    /**
     *
     * @param e
     * takes an ActionEvent e and creates or veridies an employee
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        String firstName = this.firstNameField.getText();           //get the person's info
        String lastName = this.lastNameField.getText();
        Date dateHired = extractDate();
        String userName = this.userNameField.getText();
        String password = String.valueOf(this.passWordField.getPassword());

        if(firstName.equals("") || lastName.equals("") || dateHired == null || userName.equals("") || password.equals(""))  //check input
            JOptionPane.showMessageDialog(null, "Please enter all the required information");
        else{
            DB_Writer writer = new DB_Writer();
            int res = writer.createLogin(firstName, lastName, dateHired, userName, password);

            if(res > 0)
                JOptionPane.showMessageDialog(null, "Login created! Login using the main screen to continue");
            else
                JOptionPane.showMessageDialog(null, "User already exists! Please login using the main screen");
        }
    }

    /**
     *
     * @return Date toRet
     * check for the date hired
     * returns it to method actionPerformed
     */

    public Date extractDate(){
        Date toRet = null;

        if(!this.dateHiredField.getText().equals("")){              //if date hired is not null, get the dated hired
            String dateEntered = this.dateHiredField.getText();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            try {
                long selectedTimeInMillis = df.parse(dateEntered).getTime();
                toRet = new Date(selectedTimeInMillis);
            } catch (ParseException e) {
                return null;
            }
        }

        return toRet;
    }
}