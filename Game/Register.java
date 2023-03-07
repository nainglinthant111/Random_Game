package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Register extends JFrame implements ActionListener {

    public JLabel userid,userpass,gender,email,phno,user_name,free,usertype;
    public  JTextField idtext,nametext,emailtext,phnotext;
    public JPasswordField passtext;
    public JRadioButton female,male;
    public ButtonGroup group;
    public JCheckBox user_type;
    public JPanel buttonp,radiop,userp,checkp;
    public JButton save,cancel;

    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public PreparedStatement pstmt,pst,pup;

    public Register(){
        this.setTitle("Add New Account");
        this.setSize(390, 450);
        this.setLocation(500, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userid=new JLabel(" User ID");
        userpass=new JLabel(" Password");
        gender=new JLabel(" Gender");
        email=new JLabel(" E-mail");
        phno=new JLabel(" Phone No");
        user_name=new JLabel(" User Name");
        free=new JLabel("");
        usertype=new JLabel("Account Create.");

        idtext=new JTextField(30);
        nametext=new JTextField(30);
        emailtext=new JTextField(30);
        phnotext=new JTextField(20);

        passtext=new JPasswordField(30);
        passtext.setEchoChar('*');

        female=new JRadioButton("Female");
        male=new JRadioButton("Male");
        group=new ButtonGroup();
        group.add(female);;
        group.add(male);
        radiop=new JPanel();
        radiop.add(male);
        radiop.add(female);

        user_type=new JCheckBox("User");
        checkp=new JPanel();
        checkp.add(user_type);
        checkp.add(usertype);

        save=new JButton("Save");
        cancel=new JButton("Cancel");
        save.addActionListener(this);
        cancel.addActionListener(this);
        buttonp=new JPanel();
        buttonp.add(save);
        buttonp.add(cancel);

        userp =new JPanel();
        userp.setLayout(new GridLayout(8,2));
        userp.setBorder(BorderFactory.createTitledBorder("Random Game"));
        userp.add(userid);
        userp.add(idtext);
        userp.add(user_name);
        userp.add(nametext);
        userp.add(userpass);
        userp.add(passtext);
        userp.add(gender);
        userp.add(radiop);
        userp.add(email);
        userp.add(emailtext);
        userp.add(phno);
        userp.add(phnotext);
        userp.add(free);
        userp.add(checkp);
        userp.add(buttonp);

        this.add(userp);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==save){

            int coil=100;
            String did=idtext.getText();
            String dname=nametext.getText();
            String dpass=passtext.getText();
            String dgender=male.isSelected() ? "Male" : "Female";
            String dusertype=user_type.isSelected() ? "User" : "Admin";
            String demail=emailtext.getText();
            String dphno=phnotext.getText();
            String message=""+dname;
            if(did.equals("")||dname.equals("")||dpass.equals("")||dgender.equals("")||dusertype.equals("")||demail.equals("")||dphno.equals("")){
                JOptionPane.showMessageDialog(save,"Check and Fill Your Information. Please Try Again","Error Message ",0);
            }else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager.getConnection(db, username, password);
                   // For new account create
                pstmt = connection.prepareStatement("insert into user values (?,?,?,?,?,?,?)");
                pstmt.setString(1,did);
                pstmt.setString(2,dname);
                pstmt.setString(3,dpass);
                pstmt.setString(4,dusertype);
                pstmt.setString(5,dgender);
                pstmt.setString(6,demail);
                pstmt.setString(7,dphno);
                pstmt.executeUpdate();

                //for new user add game coil
                pst=connection.prepareStatement("insert into gold values(?,?)");
                pst.setString(1,did);
                pst.setInt(2,coil);
                pst.executeUpdate();

                //for game coil pay to new user
                pup=connection.prepareStatement("update gold set game_coil= game_coil - ? where id=?");
                String ID="naing1";
                pup.setInt(1,coil);
                pup.setString(2,ID);
                pup.executeUpdate();

                JOptionPane.showMessageDialog(save, "Welcome, " + message + " Your account is successfully created");
                   //close preparedStatement and go to next page
                    dispose();
                    pup.close();
                    pst.close();
                    pstmt.close();
                    connection.close();
                Index ind=new Index();
                ind.setVisible(true);

                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                    //show dialog box error or duplicate primary key
                    JOptionPane.showMessageDialog(save,"Error <or> : "+ did +" is already exit.","Error Message",0);
                }
            }
        }else{
            //cancel button click go to Index page
            dispose();
            Index ind=new Index();
            ind.setVisible(true);

        }

        }

}
