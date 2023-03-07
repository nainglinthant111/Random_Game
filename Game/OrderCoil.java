package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class OrderCoil extends JFrame implements ActionListener {
    String userid;
    public JLabel money, commend;
    public JTextField inmoney, incommend,showlab;
    public JButton ok, clear,home;
    public JPanel main_p;


    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public PreparedStatement pst;
    public int rs;

    public OrderCoil(String playerid) {
        userid = playerid;
        this.setTitle("Random Game");
        this.setSize(400, 200);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        money = new JLabel("Purchase Amount(mmk)");
        commend = new JLabel("Payment Type ");
        inmoney = new JTextField();
        incommend = new JTextField();
        showlab=new JTextField();
        showlab.setText("           One coin per Kyats.");
        showlab.setEditable(false);
        ok = new JButton("Ok");
        clear = new JButton("Clear");
        home=new JButton("Back");
        home.addActionListener(this);
        ok.addActionListener(this);
        clear.addActionListener(this);
        main_p = new JPanel();
        main_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        main_p.setPreferredSize(new Dimension(370, 100));
        main_p.setMaximumSize(new Dimension(370, 100));
        main_p.setBorder(BorderFactory.createTitledBorder("Order Coin"));
        main_p.setLayout(new GridLayout(3, 2));
        main_p.add(money);
        main_p.add(inmoney);
        main_p.add(commend);
        main_p.add(incommend);
        main_p.add(ok);
        main_p.add(clear);


        this.add(showlab);
        this.add(main_p);
        this.add(home);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {

            if (inmoney.getText().equals("")||incommend.getText().equals("")) {
                JOptionPane.showMessageDialog(ok,"Fill purchase amount and payment type.","Error",0);
                System.out.println("ok");
                 } else {

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(db, username, password);

                    pst = connection.prepareStatement("insert into order_coin values (null,?,?,?)");
                    pst.setString(1, inmoney.getText());
                    pst.setString(2, incommend.getText());
                    pst.setString(3, userid);
                    rs = pst.executeUpdate();
                    pst.close();
                    connection.close();
                    MainPage mp = new MainPage(userid);
                    dispose();

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

        }else if(e.getSource()==clear) {
            incommend.setText("");
            inmoney.setText("");
        }else if(e.getSource()==home){
           PlayGame pg=new PlayGame(userid);
            dispose();
        }

    }

}
