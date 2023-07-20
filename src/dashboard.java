import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class dashboard extends JFrame{
    JLabel h1,h2;
    JPanel p1,p2;
    JButton b1,b2,b3;
    dashboard()
    {
        super("Dashboad");
        if(!App.isAuthenticated)
            System.exit(1);
        setBounds(0,0,600,500);
        setLayout(new GridLayout(5,1));
        setResizable(false);
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
        p2.setLayout(new GridLayout(1,2,50,20));
        JLabel x=new JLabel("Hello "+ App.userName);
        x.setFont(new Font("SansSerif", Font.BOLD, 40));
        p2.add(x);
        JButton bl= new JButton("Logout");
        bl.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
                App.isAuthenticated=false;
                App.Login=new login("Logout Sucessfull");
                App.Login.setVisible(true);
                App.Dashboard.setVisible(false);
                
            }
            
        });
        bl.setBackground(Color.black);
        bl.setForeground(Color.white);
        bl.setBorder(new RoundB(15));
        bl.setFont(new myfont(25));
        p2.add(bl);
        add(p2);
        b1=new JButton("Book Ticket");
        b2=new JButton("Check Status");
        b3=new JButton("Manage Booking");
        new decoration(b1);
        new decoration(b2);
        new decoration(b3);

        add(b1);
        add(b2);
        add(b3);
        b3.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0) {
               new check(App.Dashboard,true);
            }
            
        });

        b2.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent arg0) {
               new check(App.Dashboard,false);
            }
            
        });
        b1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(App.BookTicket==null && App.isAuthenticated==true)
                {
                    App.BookTicket=new bookTicket();
                    App.BookTicket.setVisible(true);
                }
                else
                {
                    App.BookTicket.toFront();
                    App.BookTicket.requestFocus();
                }
                
            }
            
        });
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing (WindowEvent e) {    
                {
                    if(App.BookTicket!=null)
                    App.BookTicket.dispose();
                }
                App.Dashboard.dispose();
                App.isAuthenticated=false;
                App.Login=new login("Logout succesfull");
                App.Login.setVisible(true);
                


            }
        });
    }
   
   
}
