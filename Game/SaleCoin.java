package Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SaleCoin extends JFrame implements ActionListener {
    String[] orderTitle = {"ID", "Player ID", "Amount", "Commend"};
    String dborderid = "";
    String dbid = "";
    String dbamount = "";
    String dbcommend = "";
    String adminid = "naing1";
    public JPanel sale_p, order_p, admin_p;
    public JTable order_table;
    public JTextField inputid, inorder;
    public JButton sale_coin, home;
    public JLabel playerid, order_id;
    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public PreparedStatement pst, pdel, pcoin, paddplayer_coin;
    public ResultSet rs;
    int res, regame, rsplayer_coin;

    public SaleCoin() {


        this.setTitle("Random Game");
        this.setSize(700, 450);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(orderTitle);
        model.addRow(orderTitle);
        order_table = new JTable();
        order_table.setEnabled(false);
        order_table.setModel(model);
        order_table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        order_table.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(order_table);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(db, username, password);
            pst = connection.prepareStatement("select * from order_coin");
            rs = pst.executeQuery();
            int row = 0;
            while (rs.next()) {
                dborderid = String.valueOf(rs.getInt("oderid"));
                dbid = rs.getString("id");
                dbamount = rs.getString("mmk");
                dbcommend = rs.getString("commend");
                model.addRow(new Object[]{dborderid, dbid, dbamount, dbcommend});
                row++;
            }
            if (row < 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                String id = "naing1";
                new MainPage(id);
                dispose();
            }

            pst.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        order_p = new JPanel();
        order_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        order_p.add(order_table);

        sale_coin = new JButton("Sale Coin");
        sale_coin.addActionListener(this);
        home = new JButton("Back Home");
        home.addActionListener(this);
        inputid = new JTextField(15);
        inorder = new JTextField(15);
        playerid = new JLabel("Player ID");
        order_id = new JLabel("Order ID");
        admin_p = new JPanel();
        admin_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        admin_p.setPreferredSize(new Dimension(350, 100));
        admin_p.setMaximumSize(new Dimension(350, 100));
        admin_p.setBorder(BorderFactory.createTitledBorder("Sale Order"));
        admin_p.setLayout(new GridLayout(3, 2));
        admin_p.add(order_id);
        admin_p.add(inorder);
        admin_p.add(playerid);
        admin_p.add(inputid);
        admin_p.add(home);
        admin_p.add(sale_coin);


        sale_p = new JPanel();
        sale_p.setAlignmentX(Component.CENTER_ALIGNMENT);
        sale_p.setPreferredSize(new Dimension(400, 400));
        sale_p.setMaximumSize(new Dimension(400, 400));
        sale_p.setBorder(BorderFactory.createTitledBorder("Order Game coin"));
        sale_p.setLayout(new BorderLayout());
        sale_p.add(order_table);
        sale_p.add(admin_p, BorderLayout.SOUTH);


        this.add(sale_p);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == sale_coin) {
            if (inorder.equals("") || inputid.equals("")) {
                JOptionPane.showMessageDialog(sale_coin, "Fill Order Id and Player Id", "Error", 0);
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    connection = DriverManager.getConnection(db, username, password);


                    pdel = connection.prepareStatement("DELETE FROM order_coin WHERE oderid=?");
                    pdel.setInt(1, Integer.parseInt(inorder.getText()));
                    res = pdel.executeUpdate();

                    pcoin = connection.prepareStatement("update gold set game_coil= game_coil - ? where id=?");
                    int salegold = Integer.parseInt(dbamount);
                    pcoin.setString(2, adminid);
                    pcoin.setInt(1, salegold);
                    regame = pcoin.executeUpdate();

                    paddplayer_coin = connection.prepareStatement("update gold set game_coil= game_coil + ? where id=?");
                    paddplayer_coin.setString(2, inputid.getText());
                    paddplayer_coin.setInt(1, salegold);
                    rsplayer_coin = paddplayer_coin.executeUpdate();


                    JOptionPane.showMessageDialog(sale_coin, "Successfully Sale Coin", "Sale", 1);
                    pdel.close();
                    pcoin.close();
                    paddplayer_coin.close();
                    connection.close();
                    dispose();
                    new SaleCoin();

                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(sale_coin, "You Can't Sale ! Please Recheck", "Sale", 0);

                }
            }
        } else {
            dispose();
            new MainPage(adminid);
        }

    }
}


