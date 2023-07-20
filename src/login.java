import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class login extends JFrame implements ActionListener{
    JLabel h1 ,l1,l2,w1;
    JPanel p1,p2,p3,p4;
    JTextField userName;
    JPasswordField passwd;
    JButton Blogin;
    login(String w)
    {
        super("Login");
        setBounds(0,0,500,400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new GridLayout(4,1,100,2));
        h1=new JLabel("  GO BUS  ");
        h1.setFont(new Font("SansSerif", Font.BOLD, 60));
        h1.setOpaque(true);
        h1.setBackground(Color.white);
        p1=new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        p1.setOpaque(true);
        p1.setBackground(Color.LIGHT_GRAY);
        p1.add(h1);
        
        
        
        add(p1);
        p2=new JPanel();
        
        p2.setLayout(new GridLayout(3,2));
        JLabel x=new JLabel("Login: ");
        x.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 15));
        p2.add(x);
        p2.add(new JLabel(" "));
        l1=new JLabel("User Name :");
        //l1.setHorizontalAlignment(JLabel.RIGHT);
        l1.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
        l2=new JLabel ("Password :");
        //l2.setHorizontalAlignment(JLabel.RIGHT);
        l2.setFont(new Font("SansSerif", Font.CENTER_BASELINE, 20));
        userName=new JTextField(10);
        passwd= new JPasswordField(10);
        p2.add(l1);
        p2.add(userName);
        p2.add(l2);
        p2.add(passwd);
        add(p2);
        p3=new JPanel();
        Blogin= new JButton("Login");
        new decoration(Blogin,20);
        p3.add(Blogin);

        Blogin.addActionListener(this);
        add(p3);
        w1=new JLabel(w);
        p4=new JPanel();
        p4.add(w1);
        add(p4);


        


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String uname=userName.getText();
        String pass=new String(passwd.getPassword());
        String error="";
        ResultSet rs;
        if(uname.length()==0 || pass.length()==0)
            error="Enter user name and password ";
        else
        {
            try{
                if(App.con==null)
                    App.con=new connect();
                rs=App.con.sql.executeQuery("SELECT * FROM USERS WHERE userId='"+uname+"' AND passwd='"+pass+"'");
                if(rs.next())
                {

                    if(rs.getString(3).equals("1"))
                    {
                        new Admin();
                        return;
                    }
                    error="<font color=green>login success</font>";
                    App.userName=rs.getString("userID");
                    App.isAuthenticated=true;
                    SwingUtilities.invokeLater(() -> {
                        App.Dashboard=new dashboard();
                        App.Dashboard.setVisible(true);
                        this.setVisible(false);
                    });
                }
                    
                else
                    error="Invalid userName or password";
        
            }
            catch(Exception ex)
            {
                error="Server unreachable ";
                App.con=null;
                
            }
            
        }
        System.out.println(uname +" "+ pass);
        String Ferror=error;
    SwingUtilities.invokeLater(() -> {
        w1.setText("<html><font color=red>"+Ferror+"</font></html>");
    });
    }


    
}
