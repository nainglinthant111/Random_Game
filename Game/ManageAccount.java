package Game;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManageAccount extends JFrame implements ActionListener {

            public String[] userTitle={"UserID","Name","GameCoin","Gender","E-mail","PhoneNo"};
            String dbid="";
            String dbname="";
            String dbcoin="";
            String dbgender="";
            String dbemail="";
            String dbphno="";
            public  JLabel inputid;
            public JTextField inputbox;
            JButton home,delete;
            JPanel mainp,adminp;
            JTable userTable;
    //for declare database connection
    public Connection connection;
    public String db = "jdbc:mysql://localhost/Random_Game";
    public String username = "root";
    public String password = "Naing111";
    public ResultSet rs;
    public ResultSet rsgold;
    public int redeluser,rsdelgold,rsdelresult,rsupcoin,rsdelorder;

    PreparedStatement pst,pgold,pdeluser,pdelgold,pdelresult,pupcoin,pdelorder;
    public String adminid="naing1";
    public ManageAccount(){

        this.setTitle("Random Game");
        this.setSize(700, 450);
        this.setLocation(200, 0);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        inputid=new JLabel("Input ID");
        home=new JButton("Home");
        delete=new JButton("Delete Account");
        home.addActionListener(this);
        delete.addActionListener(this);
        inputbox=new JTextField(20);
        adminp=new JPanel();
        mainp=new JPanel();
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(userTitle);
        model.addRow(userTitle);
        userTable=new JTable();
        userTable.setEnabled(false);
        userTable.setModel(model);
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        userTable.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(userTable);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        try{
            String inputType="USER";
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(db,username,password);
            pst= connection.prepareStatement("select * from user where u_type=?");
            pst.setString(1,inputType);
            rs=pst.executeQuery();
            int row=0;
            while (rs.next()){
                dbid=rs.getString("id");
                dbname=rs.getString("name");
                dbemail=rs.getString("email");
                dbgender=rs.getString("gender");
                dbphno=rs.getString("phoneno");

                pgold= connection.prepareStatement("select * from gold where id=?");
            pgold.setString(1,dbid);
            rsgold=pgold.executeQuery();
            while (rsgold.next()) {
                dbcoin=rsgold.getString("game_coil");
            }
                model.addRow(new Object[]{dbid,dbname, dbcoin, dbgender,dbemail,dbphno});
                row++;
            }
            pgold.close();
            pst.close();
            connection.close();

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        adminp.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminp.setPreferredSize(new Dimension(200, 100));
        adminp.setMaximumSize(new Dimension(200, 100));
        adminp.setBorder(BorderFactory.createTitledBorder("Manage Account"));
        adminp.setAlignmentX(Component.CENTER_ALIGNMENT);
        adminp.setLayout(new GridLayout(2,2));
        adminp.add(inputid);
        adminp.add(inputbox);
        adminp.add(home);
        adminp.add(delete);


        mainp.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainp.add( userTable);
        this.setLayout(new BorderLayout());
        this.add(mainp);
        this.add(adminp,BorderLayout.SOUTH);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String dbudelid=inputbox.getText();
        if (e.getSource()==delete){
            if(dbudelid.equals("")){
                JOptionPane.showMessageDialog(delete,"Fill user ID you want to delete!","Delete",0);
            }
            try {

                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection(db, username, password);

                pupcoin=connection.prepareStatement("update gold set game_coil=game_coil + ? where id=?");
                pupcoin.setInt(1,Integer.parseInt(dbcoin));
                pupcoin.setString(2,adminid);
                rsupcoin=pupcoin.executeUpdate();

                pdeluser=connection.prepareStatement("delete from user where id=?");
                pdeluser.setString(1,dbudelid);
                redeluser=pdeluser.executeUpdate();

                pdelgold= connection.prepareStatement("delete from gold where id=?");
                pdelgold.setString(1,dbudelid);
                rsdelgold=pdelgold.executeUpdate();

                pdelresult= connection.prepareStatement("delete from result where id=?");
                pdelresult.setString(1,dbudelid);
                rsdelresult=pdelresult.executeUpdate();

                pdelorder=connection.prepareStatement("delete from order_coin where id=?");
                pdelorder.setString(1,dbudelid);
                rsdelorder=pdelorder.executeUpdate();


                pupcoin.close();
                pdeluser.close();
                pdelgold.close();
                pdelresult.close();
                pdelorder.close();
                connection.close();

            } catch (SQLException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            dispose();
            new ManageAccount();

        }else{
            dispose();
            MainPage mp=new MainPage(adminid);
        }

    }

   }
