import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.event.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
public class deatails extends JDialog implements ActionListener {
    JPanel h1,h2,h3,h4,h5,h6,h7,h8;
    JTextField pname,page,pconatact;
    route r;
    JComboBox<String> Gender;
    JLabel w;
    deatails(JFrame f,route r)
    {
        super(f,"Fill Details",true);
        this.r=r;
        setLayout(new FlowLayout());
        h1=new JPanel();
        JLabel temp;
        temp=new JLabel("   From: "+r.Deprt+"   To: "+r.Arrive+"     ");
        temp.setFont(new myfont(25));
        h1.add(temp);
        add(h1);
        h2=new JPanel();
        temp=new JLabel("ON: "+ r.date+"  Depart at:"+r.Deprt_at +"Arrive by: "+r.Arrive_by);
        temp.setFont(new myfont(15));
        add(temp);
        ///geting bus no ;
        String Platenum="";
        try{
            ResultSet rs=App.con.sql.executeQuery("SELECT plate_number FROM bus where id='"+r.bus_id+"'");
            while(rs.next())
                Platenum=rs.getString(1);
        }
        catch(Exception x){};
        ///geting bus no
        h3=new JPanel();
        temp=new JLabel("Bus no.: "+Platenum.toUpperCase()+" Fare: " +r.fare);
        temp.setFont(new myfont(25));
        h3.add(temp);
        add(h3);
        //divider//
        h4=new JPanel();
        temp=new JLabel("<html>------------------------------<font color=red>Pasenger Deatails</font>---------------------------------</html>");
        temp.setHorizontalAlignment(JLabel.CENTER);
        h4.add(temp);
        add(h4);
        h5=new JPanel();
        temp=new JLabel("   Name:  ");
        pname=new JTextField("",10);
        h5.add(temp);
        h5.add(pname);
        temp=new JLabel("       Age:   ");
        page=new JTextField(10);
        h5.add(temp);
        h5.add(page);
        add(h5);
        h6=new JPanel();
        temp=new JLabel("Contact:   ");
        pconatact=new JTextField("0123456789",10);
        h6.add(temp);
        h6.add(pconatact);
        temp=new JLabel("       Gender: ");
        h6.add(temp);
        String gen[]={"Male","Female","Other"};
        Gender=new JComboBox<String>(gen);
        h6.add(Gender);
        add(h6);
        h7=new JPanel();
        w=new JLabel("");
    
        w.setForeground(Color.blue);
        w.setHorizontalAlignment(JLabel.CENTER);
        h7.add(new JLabel("                                                    "));
        h7.add(w);
        h7.add(new JLabel("                                                    "));
        add(h7);
        h8=new JPanel();
        h8.add(new JLabel("                                 "));
        JButton bokb =new JButton("Book");
        new decoration(bokb,20);
        h8.add(bokb);
        h8.add(new JLabel("                                 "));
        bokb.addActionListener(this);
        add(h8);
        setSize(500,350);
        setVisible(true);    
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String name=pname.getText();
        int age;
        String contact=pconatact.getText();
        String gender=(String)Gender.getSelectedItem();
        try{
            age=Integer.parseInt(page.getText());
        }
        catch(Exception xx)
        {
            w.setText("Please fill correct details");
            return;
        }
        if(name.length()<3 || contact.length()!=10 ||age>99|| age<0)
            w.setText("Please fill correct details");
        else
        {
            w.setText("Hold on, just a Second");
            //revalidate();
            //System.out.println("please wait");
           
            Object x=e.getSource();
            JButton b=(JButton)(x);
            b.setEnabled(false);
           
            
            book(r.bus_id, name, age,contact, r.date,gender);
        }
            
    }



    private void book(int Bus_ID, String Pname, int Page, String pcontact, String TravelDate,String g) {
        PreparedStatement statement = null;
        //String sql="INSERT INTO reservation VALUES('"+pnr+"',"+Bus_ID+",'"+Pname+"','"+pcontact+"',"+Page+",'"+TravelDate+"')";
        String sql="INSERT INTO reservation VALUES(?,?,?,?,?,?,?,?)";
        String pnr=pnrGen();
        //System.out.println(sql);
       
        try
        {
           
            statement=App.con.con.prepareStatement(sql);
            statement.setString(1, pnr);
            statement.setInt(2, Bus_ID);
            statement.setString(3, Pname);
            statement.setString(4, pcontact);
            statement.setInt(5, Page);
            statement.setString(6, TravelDate);
            statement.setString(7,g );
            statement.setString(8,App.userName);
            if(statement.executeUpdate()==0)
            {
                w.setText("Please try again");
            }
            else
            {
                ///System.out.println("done");
                
                SwingUtilities.invokeLater(() -> {

                    try {
                        Thread.sleep(2000);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    w.setText("Booking confirmed PNR: "+pnr);
                 });
                
                //book(r.bus_id, name, age,contact, r.date);
            }


            
        
        }
        catch (SQLException e ) {
            e.printStackTrace();
          }
    }


    public String pnrGen()
    {
        Random r=new Random();
        String x="";
        for(int i=0;i<=2;i++)
        {
            int k=r.nextInt(26)+65;
            x+=(char)k;
            
        }
        x+=(r.nextInt(9)+1);
        x+='-';
        x+=(r.nextInt(9)+1);
        for(int i=0;i<2;i++)
        {
            
            int k=r.nextInt(26)+65;
            x+=(char)k;
        }
        //System.out.println(x);
        return x;

    }
}
