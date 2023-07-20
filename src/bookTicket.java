import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;
import java.time.*;
 public class bookTicket extends JFrame implements ActionListener{
    JLabel h1,w;
    JPanel p1,p2,p3,p4,pw,psearch,pd;
    JComboBox Arr,Dep;
    JButton Search,date;
    String Cities[];
    route Route[];
    JTextField jt;
    


    bookTicket()
    {
        super("Book ticket");
       //setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(!App.isAuthenticated)
            System.exit(1);
        //setBounds(0,0,);
        setSize(600,500);
        
        setLayout(new FlowLayout());
        setResizable(false);
        //loding cities;
        Set<String> City_Set = new HashSet<String>();
        try{
            ResultSet rs=App.con.sql.executeQuery("SELECT Dept_city,Arri_city FROM route");
            while(rs.next())
            {
                City_Set.add(rs.getString(1));
                City_Set.add(rs.getString(2));    
            }
            Cities=new String [City_Set.size()];
            int c=0;
            for(String k:City_Set)
                Cities[c++]=new String(k);



            
            
        }
        catch (Exception e)
        {
            Cities=new String [1];
            Cities[0]="ERROR";


        }
        
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
       
        w=new JLabel();
        pw=new JPanel();
        pw.setLayout(new FlowLayout());
        pw.add(w);
        
        p1=new JPanel();
        p1.setLayout(new FlowLayout(FlowLayout.CENTER));
        Arr=new JComboBox<String>();
        Arr.setEditable(true);
        p1.add(new JLabel("From: "));
        p1.add(Arr);
        Arr.getEditor().getEditorComponent().addKeyListener(new Selecion(Arr));
        Dep=new JComboBox<String>();
        Dep.setToolTipText("Arrival city");
        Dep.setEditable(true);
        Dep.getEditor().getEditorComponent().addKeyListener(new Selecion(Dep));
        p1.add(new JLabel("    "));
        p1.add(new JLabel("To :"));
        p1.add(Dep);
        add(p1);

         ///date
         pd=new JPanel();
         pd.add(new JLabel("       "));
         pd.add(new JLabel("Date: "));
         LocalDate dat = LocalDate.now();
         String daa=dat.getYear()+"-"+dat.getMonthValue() +"-"+dat.getDayOfMonth();
         jt=new JTextField(daa,6);
         jt.setHorizontalAlignment(JTextField.CENTER);
         jt.setFont(new myfont(20));
         jt.setEditable(false);
         pd.add(jt);
        date =new JButton("Select");
        date.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                
                new dateSelector(App.BookTicket);
            }
            
        });
         pd.add(date);
         pd.add(new JLabel("       "));
         add(pd);
         //date

        add(pw);
        p3=new JPanel();
        p3.setLayout(new FlowLayout());
        Search=new JButton("  Search  ");
        new decoration(Search,10,Color.black,Color.WHITE,25);
        p3.add(new JLabel("------------------------------------------------------------"));
        p3.add(Search);
        p3.add(new JLabel("------------------------------------------------------------"));

        Search.addActionListener(this);
        Search.setName("search");
        add(p3);
        
        //search results;
        p4=new JPanel();
        p4.add(new JScrollPane());
        add(p4);
        psearch=new JPanel();
        
       
        add(psearch);

        addWindowListener(new WindowAdapter()
        {
            public void windowClosing (WindowEvent e) {    
                {
                    App.BookTicket.dispose();
                    App.BookTicket=null;
                    return;
                }
            }
        });

        
    }
    // public static void main(String[] args) {
    //     new bookTicket().setVisible(true);
    // }

    class Selecion extends KeyAdapter{
        JComboBox<String> Box;
        Selecion(final JComboBox<String> Box)
        {
            this.Box=Box;
        }
        @Override
        public void keyReleased(KeyEvent event) {
            String x=(String)Box.getEditor().getItem();
            Box.removeAllItems();
            for(String city:Cities)
            {
                if(city.toLowerCase().startsWith(x.toLowerCase()))
                    Box.addItem(city);
                Box.setSelectedItem(x);

            }
            Box.showPopup();
                
                    

            }
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //System.err.println();
        if(((JButton)e.getSource()).getName()=="search")
        {
            String Arrival=(String)Arr.getSelectedItem();
            String Departue=(String)Dep.getSelectedItem();
            if(Arrival==null|| Departue==null||Arrival.length()<3|| Departue.length()<3)
                w.setText("<html><font color=red>Plese select arrival and Departure city</font></html>");
            else
            {
                System.out.println("From: ("+ Arrival +") to :"+Departue);
                w.setText("");
                try{
                    ResultSet rs=App.con.sql.executeQuery("SELECT * FROM route WHERE Dept_city='"+Arrival+"' AND Arri_City='"+Departue+"'");
                    rs.last();
                    int count=rs.getRow()+1;
                    Route =new route[count];
                    rs.absolute(0);
                    psearch.removeAll();
                    psearch.setLayout(new GridLayout(count,1,0,20));
                    count=0;
                    String udate=jt.getText();
                    System.out.println("date "+udate);
                    while(rs.next())
                    {
                        
                       
                        Route[count++]=new route(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getInt(6),0);

                         

                    }
                    
                    
                    //psearch.setLayout(new FlowLayout());
                    //add(psearch);
                    if(count==0)
                    {
                        psearch.add(new JLabel("<html><font size=30 color=red align=center>No Buses are available</font></html>"));
                        revalidate();
                    }
                    for(route r:Route)
                    {
                        ///seat count
                        try
                        {
                            System.out.println("SELECT count(pnr) FROM reservation WHERE busId='"+r.bus_id+"' AND travelDate='"+udate+" 00:00:00'");
                            ResultSet rseats=App.con.sql.executeQuery("SELECT count(pnr) FROM reservation WHERE busId='"+r.bus_id+"' AND travelDate='"+udate+"'");
                            //System.out.println("SELECT count(pnr) FROM reservation WHERE busId='"+r.bus_id+"' AND travelDate='STR_TO_DATE(\""+udate+" 00:00:00\", \"%d-%m-%Y %H:%i:%s\")"););
                             int seats=30;
                             while(rseats.next())
                              {
                              seats-=rseats.getInt(1);
                              }
                              r.seats=seats;
                              r.date=udate;
                            //System.out.print("seats "+seats);

                        }
                        catch(Exception f)
                        {
                            System.out.println("error");
                            
                        }
                        



                        ///seat count

                        JPanel p=new JPanel();
                        p.setBackground(Color.GRAY);
                        p.setOpaque(true);
                        p.setLayout(new GridLayout(2,3));
                        p.add(new JLabel("Depart At: "+r.Deprt_at));
                        p.add(new JLabel(""));
                        p.add(new JLabel("Arrive By: "+r.Arrive_by));

                        p.add(new JLabel("Fare "+r.fare));
                        p.add(new JLabel("Seats: "+r.seats));
                        JButton bk=new JButton("Book");
                        bk.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent arg0) {
                                new deatails(App.BookTicket, r);
                                
                            }
                            
                        });
                        p.add(bk);
                        p.setBorder(new RoundB(20));
                        //System.out.println("hello macha");
                        psearch.add(p);



                        
                        revalidate(); 
                    }
                         

                    
                    
                    
                    
                }
                catch(Exception er)
                {
                    //System.out.println("unable to search "+er);
                };
                
                  
            }

                


        }
    }
    
   
    
 

    
    
}
