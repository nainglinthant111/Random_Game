package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Index extends JFrame implements ActionListener {

    //Declare for GUI create variable
    public JLabel userlab,userpass,userid,create;
    public JButton login,register;
    public ButtonGroup group;
    public JRadioButton admin,user;
    public JTextField idtext;
    public JPasswordField passtext;

    public  JPanel loginp,userp,mainp,regp,genderp,labp;

    //for declare database connection
   public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";

    public String sql;
    public ResultSet rs;



    public Index() {
        this.setTitle("Random Number Game");
        this.setSize(400,250);
        this.setLocation(500,0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userlab=new JLabel("User Login");
        labp=new JPanel();
        labp.add(userlab);

        userpass=new JLabel("   Password");
        userid=new JLabel("   User-ID");
        create=new JLabel("Create new account.   ");
        idtext=new JTextField(20);
        passtext=new JPasswordField(20);
        passtext.setEchoChar('*');

        userp=new JPanel();
        userp.setLayout(new GridLayout(2,2));
        userp.add(userid);
        userp.add(idtext);
        userp.add(userpass);
        userp.add(passtext);


        admin=new JRadioButton("Admin");
        user=new JRadioButton("User");
        group=new ButtonGroup();
        group.add(admin);
        group.add(user);
        genderp=new JPanel();
        genderp.add(user);
        genderp.add(admin);

        login=new JButton("Login");
        login.addActionListener(this);
        login.setSize(30,60);
        loginp=new JPanel();
        loginp.add(login);

        register=new JButton("Create Account");
        register.addActionListener(this);
        register.setSize(30,60);

        regp=new JPanel();
        regp.add(create);
        regp.add(register);

        mainp=new JPanel();
        mainp.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainp.setPreferredSize(new Dimension(300, 250));
        mainp.setMaximumSize(new Dimension(300, 250));
        mainp.setBorder(BorderFactory.createTitledBorder("User Login"));
        mainp.setLayout(new GridLayout(5,0));
        mainp.add(labp);
        mainp.add(userp);
        mainp.add(genderp);
        mainp.add(loginp);
        mainp.add(regp);

        this.add(mainp);
        this.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == login) {
            String user_id = idtext.getText();
            String user_pass = passtext.getText();
            String user_type = admin.isSelected() ? "Admin" : "User";
            String msg = "" + user_id;
            Boolean b=false;

            if (user_id.equals("") || user_pass.equals("") || user_type.equals("")) {
                JOptionPane.showMessageDialog(login, "Please fill require your information");
            } else

                try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(db, username, password);
                        Statement stmt=connection.createStatement();
                        sql = "select * from user where id='" + user_id + "' and password='" + user_pass + " ' and u_type='"+ user_type +"'";
                        rs=stmt.executeQuery(sql);
                        while(rs.next()){
                            b=true;
                            String id=rs.getString("id");
                            MainPage mp=new MainPage(id);
                            dispose();

                        }
                    if (!b) {
                        JOptionPane.showMessageDialog(this, "Invalid Login. Please Register First!", "Error Message", 0);
                    }

                } catch (ClassNotFoundException | SQLException ex) {
                        ex.printStackTrace();
                    }

        }else{
            new Register();
            dispose();
        }

    }

    public static void main(String[] args) {
        new Index();
    }
}

