package Game;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Random;



public class PlayGame extends JFrame implements ActionListener {
    String playerid,dataname;
    String dbwin="win";
    String dblose="lose";
    int dbcoin;
    public JLabel showText;
    public JTextArea showmessage,rno;
    public JButton buy, compare, home;
    public JTextField showcoin, enterno;
    public JPanel play_p, game_p, ad_p;

    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public PreparedStatement pgold,plose,pwin,pgame,pdel,padd,pwadd,pwdel;
    public ResultSet rs;
    public int rswin,rsadd,rsdel,rswadd,rswdel;
    public ResultSet rsgame;

    public int relose;
    int coin=10;
    int wincoin=500;

    public Random randomValue = new Random();
    int random = randomValue.nextInt(99);
    String adminid="naing1";


    public PlayGame(String userid) {
        playerid = userid;
        this.setTitle("Random Game");
        this.setSize(700, 450);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(db, username, password);
            pgold = connection.prepareStatement("select * from gold where id=?");
            pgold.setString(1, playerid);
            rs = pgold.executeQuery();
            while (rs.next()) {
                dbcoin = rs.getInt("game_coil");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        showmessage = new JTextArea();
        showmessage.setText("   You can start playing the Random Number \"Game\".\n " +"        Winner prize is 500 coin.\n"+
                "       It's cost 10 coil per game.\n" +
                "        Let's start the game..."  );
        showmessage.setEditable(false);
        rno = new JTextArea("");
        rno.setText("   The random number is : ??????   ");
        rno.setEditable(false);

        showcoin = new JTextField("");
        showcoin.setText("My Game coin is : " + String.valueOf(dbcoin));
        showcoin.setEditable(false);

        showText = new JLabel("You can Guess 0 To 99 ! Enter Guess Number ");
        enterno = new JTextField();

        buy= new JButton("Order Coin");
        compare = new JButton("Ok");
        home = new JButton("Home");
        buy.addActionListener(this);
        compare.addActionListener(this);
        home.addActionListener(this);
        ad_p = new JPanel();
        ad_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        ad_p.setPreferredSize(new Dimension(350, 150));
        ad_p.setMaximumSize(new Dimension(350, 150));
        ad_p.add(showmessage);
        ad_p.add(buy);
        ad_p.add(home);

        game_p = new JPanel();
        game_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        game_p.setPreferredSize(new Dimension(350, 250));
        game_p.setMaximumSize(new Dimension(350, 250));
        game_p.setLayout(new GridLayout(6, 1));
        game_p.add(rno);
        game_p.add(showcoin);
        game_p.add(showText);
        game_p.add(enterno);
        game_p.add(compare);

        play_p = new JPanel();
        play_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        play_p.setPreferredSize(new Dimension(400, 400));
        play_p.setMaximumSize(new Dimension(400, 400));
        play_p.setBorder(BorderFactory.createTitledBorder("Play Game"));
        play_p.add(ad_p);
        play_p.add(game_p);
        this.add(play_p);
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==home){
            dispose();
            MainPage mp = new MainPage(playerid);
        }
        if (e.getSource() == buy) {
            OrderCoil oc = new OrderCoil(playerid);
            dispose();
        }

                if (e.getSource() == compare) {
                    int inputvalue = Integer.parseInt(enterno.getText());
                    if(enterno.equals("")){
                        JOptionPane.showMessageDialog(compare,"Fill your guess number!","Fill Number",0);
                    }

                    if(dbcoin<10){
                        JOptionPane.showMessageDialog(compare,"Your coin is insufficient, Please fill the coin!","Please Buy",0);

                    }else {

                        try {
                        Class.forName("com.mysql.jdbc.Driver");
                        connection = DriverManager.getConnection(db, username, password);
                        System.out.println("ok");
                        pgame = connection.prepareStatement("select * From user where id=?");
                        pgame.setString(1, playerid);
                        rsgame = pgame.executeQuery();
                        while (rsgame.next()) {
                            dataname = rsgame.getString("name");

                            pdel = connection.prepareStatement("update gold set game_coil= game_coil - ? where id=?");
                            pdel.setInt(1, coin);
                            pdel.setString(2, playerid);
                            rsdel = pdel.executeUpdate();

                            padd = connection.prepareStatement("update gold set game_coil= game_coil + ? where id=?");
                            padd.setInt(1, coin);
                            padd.setString(2, adminid);
                            rsadd = padd.executeUpdate();

                            pdel.close();
                            padd.close();
                            pgame.close();
                            connection.close();

                        }
                        System.out.println(dataname);

                    } catch (SQLException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }

                    if (random == inputvalue) {
                        JOptionPane.showMessageDialog(compare, "You win! you get 500 coin. Random Number is :" + random + ". Input  Numbrer is :" + inputvalue, "WINNER ", 1);
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            connection = DriverManager.getConnection(db, username, password);
                            pwin = connection.prepareStatement("insert into result value(?,?,?)");

                            pwin.setString(1, playerid);
                            pwin.setString(2, dataname);
                            pwin.setString(3, dbwin);
                            rswin = pwin.executeUpdate();
                            pwdel = connection.prepareStatement("update gold set game_coil= game_coil - ? where id=?");
                            pwdel.setInt(1, wincoin);
                            pwdel.setString(2, adminid);
                            rswdel = pwdel.executeUpdate();

                            pwadd = connection.prepareStatement("update gold set game_coil= game_coil + ? where id=?");
                            pwadd.setInt(1, wincoin);
                            pwadd.setString(2, playerid);
                            rswadd = pwadd.executeUpdate();
                            pwin.close();
                            pwadd.close();
                            pwdel.close();
                            connection.close();

                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        dispose();
                        PlayGame pg = new PlayGame(playerid);
                    } else {
                        String dbresult = "lose";
                        JOptionPane.showMessageDialog(compare, "You  Lose! you lose 10 coin. Random Number is :" + random + ". Input  Numbrer is :" + inputvalue, "LOSER", 0);
                        try {
                            Class.forName("com.mysql.jdbc.Driver");
                            connection = DriverManager.getConnection(db, username, password);
                            plose = connection.prepareStatement("insert into result value(null,?,?,?)");
                            plose.setString(1, playerid);
                            plose.setString(2, dataname);
                            plose.setString(3, dblose);
                            relose = plose.executeUpdate();
                            plose.close();
                            connection.close();

                        } catch (SQLException | ClassNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        dispose();
                        PlayGame pg = new PlayGame(playerid);
                    }


                }
            }


    }
}









