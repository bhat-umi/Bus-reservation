import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
public class check extends JDialog implements ActionListener{
    JPanel p1,p2,p3,p4,p5;
    JTextField jt;
    JButton jb;
    JLabel booking;
    String Pname,Pcontact,Pgender,bus_no,Page,from,to,depar,arri,fare,book_by,gender,date,type;
    boolean Mp=false;
    JButton mbtn;
    JPanel Mngpnl=null;
    check(dashboard r,boolean Mp)
    {
        super(r,true);
        if(Mp)
            setTitle("Manage");
        else
            setTitle("Check Status");
        this.Mp=Mp;
        setBounds(100,100,450,500);
        setResizable(false);
        setLayout(new FlowLayout());
        p1=new JPanel();
        JLabel temp=new JLabel("Enter PNR to Check Status");
        temp.setFont(new myfont(25));
        temp.setHorizontalAlignment(JLabel.CENTER);
        p1.add(temp);
        add(p1);
        p2=new JPanel();

        jt=new JTextField(15);
        jt.setHorizontalAlignment(JTextField.CENTER);
        jt.setFont(new myfont(20));
        jb=new JButton("Search");
        jb.setName("Search");
        jb.addActionListener(this);
        new decoration(jb,20);
    
        p2.add(jt);
        add(p2);
        p3=new JPanel();
        p3.add(new JLabel("------------------------------------------------------------"));
        p3.add(jb);
        p3.add(new JLabel("------------------------------------------------------------"));
        add(p3);
        p4=new JPanel();
        add(p4);
        p5=new JPanel();
        add(p5);
        booking=new JLabel("");
       
        mbtn=new JButton("Manage");
        mbtn.setName("Manage");
        new decoration(mbtn,20);
        mbtn.addActionListener(this);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String pnr=jt.getText();
        int bus_id=0;
        Pname=null;
        JButton btn=(JButton)e.getSource();
       
        
        if(btn.getName().equals("Manage"))
        {
            new Manage(this, pnr);
           // System.out.println("Hello");
            return;

        }
        try
        {
            ResultSet rs=App.con.sql.executeQuery("SELECT * FROM reservation WHERE pnr='"+pnr+"'");
            while(rs.next())
            {
                Pname=rs.getString("pname");
                Page=rs.getString("page");
                date=rs.getString("travelDate");
                Pcontact=rs.getString("pcontact");
                bus_id=rs.getInt("busId");
                gender=rs.getString("Gender");
                book_by=rs.getString("bookedBy");
                System.out.println(Pname+" "+Page+" "+date+" ");
            }   
            if(Pname!=null)
             rs=App.con.sql.executeQuery("SELECT * FROM route WHERE Bus_id ='"+bus_id+"'");
            while(rs.next())
            {
                from=rs.getString("Dept_city");
                to=rs.getString("Arri_City");
                depar=rs.getString("Dept_At");
                arri=rs.getString("Arriv_By");
                fare=rs.getString("fare");
                
                System.out.println(from+" "+to);
            }
            if(Pname!=null)
             rs=App.con.sql.executeQuery("SELECT * FROM bus WHERE Id='"+bus_id+"'");
             while(rs.next())
             {
                 bus_no=rs.getString("plate_number");
                 type=rs.getString("bus_type");
                 System.out.println(bus_no+" "+type);
             }
            booking.setText("<html><font color=red>Booking not found</font></html>");
            if( Mngpnl!=null)
            {
            
                Mngpnl.setVisible(false);
                Mngpnl=null;
            }
                
            
            booking.setHorizontalAlignment(JLabel.CENTER);
            booking.setFont(new myfont(20));
             if(Pname!=null)
             {
                String suffix="Mrs";
                String f="<font color=red>";
                String fw="<font color=white>";
                String fc="</font>";
                //System.out.println(gender);
                if(gender.equals("Male"))
                     suffix="Mr";
                booking.setText("<html> <center> "+f+ date+fc+"<br>" + f+from+fc+"("+depar+")  to " +f+to+fc+"("+arri+")<br>Bus No("+f+bus_no+fc+") Bus Type("+type+")<br><b>Passenger</b><br> "+suffix +". "+Pname +"("+Page+") "+Pcontact+fw+"<br><br>Booked By: "+book_by+fc+"</center></html>");
                                


        if(Mp && Mngpnl ==null)
        {
            Mngpnl=new JPanel();
            Mngpnl.setVisible(true);
            Mngpnl.add(new JLabel("------------------------"));
            
            Mngpnl.add(mbtn);
            Mngpnl.add(new JLabel("------------------------"));
        
            add(Mngpnl);
        }

                

             }
             p5.add(booking);
             revalidate();
             
        }
        catch(Exception ex){};
        
        
    }
    
}
