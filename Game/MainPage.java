package Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.FixedHeightLayoutCache;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.*;

import static javax.swing.UIManager.getString;

public class MainPage extends JFrame implements ActionListener {


    public JMenuBar menubar;
    public JMenu menulabel;
    public JMenuItem userinfo,play,result,feture,logout;
    public JLabel name,uid,gold,gender,email,phno;
    public JTextField showname,showid,showcoil,showgender,showemail,showphno;
    public String dbname,dbid,dbgender,dbemail,dbphno;
    public int dbgold;
    public JPanel user_p;

    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public PreparedStatement pst,pgold;
    public ResultSet rs,res;

    String userid;
    String adminid="naing1";
    public MainPage(String loginid){
        userid=loginid;
        this.setTitle("Random Game");
        this.setSize(700, 450);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        menubar=new JMenuBar();
        menulabel=new JMenu("Home");
        play=new JMenuItem("");
        if(userid.equals(adminid)){
            play.setText("Sale Coin");
        }else{
            play.setText("Play Game");
        }
        userinfo=new JMenuItem("Account Information");
        result=new JMenuItem("History");
        feture = new JMenuItem("");
        if(userid.equals(adminid)) {
            feture.setText("Manage Account");
        }else{
            feture.setText("Future plan");
        }
        logout=new JMenuItem("Logout");

        this.setJMenuBar(menubar);

        menubar.add(menulabel);
        menulabel.add(userinfo);
        menulabel.add(play);
        menulabel.add(result);
        menulabel.add(feture);
        menulabel.addSeparator();
        menulabel.add(logout);
        play.addActionListener(this);
        result.addActionListener(this);
        feture.addActionListener(this);
        logout.addActionListener(this);





        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(db,username,password);
            pst=connection.prepareStatement("select * from user where id=?");
            pst.setString(1,userid);
            rs=pst.executeQuery();

            while (rs.next()){
                dbname=rs.getString("name");
                dbid=rs.getString("id");
                dbgender=rs.getString("gender");
                dbemail=rs.getString("email");
                dbphno=rs.getString("phoneno");
            }
            pgold=connection.prepareStatement("select game_coil from gold where id=?");
            pgold.setString(1,userid);
            res=pgold.executeQuery();
            while (res.next()) {
                dbgold =res.getInt("game_coil");
            }
            pst.close();
            connection.close();

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();

        }
        name=new JLabel("User Name");
        showname=new JTextField("");
        showname.setText(dbname);
        showname.setEditable(false);

        uid=new JLabel("User ID");
        showid=new JTextField("");
        showid.setText(dbid);
        showid.setEditable(false);

        gold=new JLabel("Game Coin");
        showcoil=new JTextField("");
        showcoil.setText(String.valueOf(dbgold));
        showcoil.setEditable(false);

        gender=new JLabel("Gender");
        showgender=new JTextField("");
        showgender.setEditable(false);
        showgender.setText(dbgender);

        email=new JLabel("Email");
        showemail=new JTextField("");
        showemail.setText(dbemail);
        showemail.setEditable(false);

        phno=new JLabel("Pnone No");
        showphno=new JTextField("");
        showphno.setEditable(false);
        showphno.setText(dbphno);

        user_p=new JPanel();
        user_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        user_p.setPreferredSize(new Dimension(400, 400));
        user_p.setMaximumSize(new Dimension(400, 400));
        user_p.setBorder(BorderFactory.createTitledBorder("Account Information"));
        user_p.setLayout(new GridLayout(7,2));

        user_p.add(name);
        user_p.add(showname);
        user_p.add(uid);
        user_p.add(showid);
        user_p.add(gender);
        user_p.add(showgender);
        user_p.add(email);
        user_p.add(showemail);
        user_p.add(phno);
        user_p.add(showphno);
        user_p.add(gold);
        user_p.add(showcoil);



        this.add(user_p);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==play){


            if(userid.equals(adminid)){
                new SaleCoin();
                dispose();
            }else {
                PlayGame pg= new PlayGame(userid);
                dispose();

            }


        } else if (e.getSource()==result) {
            History hi=new History(userid);
            dispose();
        }
        else if (e.getSource()==feture) {
            if(userid.equals(adminid)) {
                ManageAccount ma=new ManageAccount();
                dispose();
            }else{
                JOptionPane.showMessageDialog(feture, "This feature can't use, wait future plan!", "Alert", 1);
            }

            }
        else  {
            new Index();
            dispose();
        }


    }


}
