import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class textingapp {
    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "ACe9972b266ecdfb6951a8dff630024fac";
    public static final String AUTH_TOKEN = "70d9ba15889fc62ac1d0e147b68e9d42";
    private JTextField bodyContent;
    private JButton Send;
    private JTextField phoneNumber;
    private JPanel mainPanel;
    private JButton newContact;
    private JComboBox contactSelector;

    public textingapp() {
        Send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String number = "";
                if(phoneNumber.getText().length() < 3){
                    number = contactSelector.getSelectedItem().toString().replace(" ","").replace("-","");
                }
                else{
                    number = phoneNumber.getText();
                }
                Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

                Message message = Message.creator(new PhoneNumber(number), //THIS IS THE TO NUMBER
                        new PhoneNumber("+18339883385"), //THIS IS THE TWILIO NUMBER
                        bodyContent.getText()).create();

                System.out.println(message.getSid());
            }
        });
        bodyContent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        phoneNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });

        List Contacts = getContacts();
        for(int item = 0;item < Contacts.size();item ++){
            contactSelector.addItem(Contacts.get(item));
        }

        newContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String newContact = JOptionPane.showInputDialog(null,"Enter new contacts phone number");

                PrintWriter out = null;
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter("assets\\Contacts", true)));
                    out.println("\n" + newContact);
                }catch (IOException a) {
                    System.err.println(a);
                }finally{
                    if(out != null){
                        out.close();
                    }
                }
            }
        });
    }

    public static List getContacts(){
        List<String> Contacts = new ArrayList<String>();
        try{
            File file = new File("assets\\Contacts");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                Contacts.add(input.nextLine());
            }
        }catch(java.io.FileNotFoundException fileName){
            System.out.println("FILE NOT FOUND");
        }

        System.out.println(Contacts);
        return Contacts;
    }
    public static void main(String[] args) {

        JFrame frame = new JFrame("Texting App");
        frame.setContentPane(new textingapp().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}