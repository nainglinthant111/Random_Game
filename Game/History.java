package Game;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class History extends JFrame implements ActionListener {

    String playerid;
    String histroyTitle[] ={"NO","ID","Name","Result"};
    String dno="";
    String did="";
    String dname="";
    String dresult="";
    public JTable histroyTable;
    public JPanel historyp;
    public JButton home;
    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public ResultSet rs;
    PreparedStatement pst;
    public History(String userid){
        playerid=userid;
        this.setTitle("Random Game");
        this.setSize(500, 450);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(histroyTitle);
        model.addRow(histroyTitle);
        histroyTable= new JTable();
        histroyTable.setEnabled(false);
        histroyTable.setModel(model);
        histroyTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        histroyTable.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(histroyTable);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(db,username,password);
            pst= connection.prepareStatement("select * from result");
            rs=pst.executeQuery();
            int row=0;
            int no=1;
            while (rs.next()){
                dno=String.valueOf(rs.getInt("resultid"));
                did=rs.getString("id");
                dname=rs.getString("name");
                dresult=rs.getString("result");
                model.addRow(new Object[]{no, did, dname, dresult});
                row++;
                no++;
            }
            if (row< 1) {
                JOptionPane.showMessageDialog(null, "No Record Found", "Error", JOptionPane.ERROR_MESSAGE);
                String id="naing1";
                new MainPage(id);
                dispose();
            }

            pst.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        home=new JButton("Home");
        home.setAlignmentX(Component.CENTER_ALIGNMENT);
        home.addActionListener(this);
        historyp=new JPanel();
        historyp.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyp.add( histroyTable);

        this.setLayout(new BorderLayout());
        this.add(historyp);
        this.add(home,BorderLayout.SOUTH);
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==home){
            MainPage mp=new MainPage(playerid);
            dispose();
        }

    }
}
