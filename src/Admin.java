import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin  extends JFrame implements ActionListener{
    JPanel man,Captain,routes,bus,user;
    CardLayout crd;
    static JLabel Status;
    Admin()
    {
        super("Admin");
        setBounds(0,0,850,500);
        setLayout(new BorderLayout());
        add(Brow("CAPTAIN","ROUTES","BUS","USER"),BorderLayout.NORTH);
        Status=new JLabel("XXXX");
        
        man=new JPanel();
        // man.setBounds(10,10,450,550);
        crd=new CardLayout();
        man.setLayout(crd);
        
        Captain=new JPanel();
        Captain.setBounds(0,0,200,600);
        Captain.setLayout(new BoxLayout(Captain, BoxLayout.Y_AXIS));
        routes=new JPanel();
        routes.setLayout(new BoxLayout(routes, BoxLayout.Y_AXIS));
        bus=new JPanel();
        bus.setLayout(new BoxLayout(bus, BoxLayout.Y_AXIS));
        user=new JPanel();
        user.setLayout(new BoxLayout(user, BoxLayout.Y_AXIS));
        man.add(Captain,"captain");
        man.add(routes,"routes");
        man.add(bus,"bus");
        man.add(user,"user");
        add(man,BorderLayout.CENTER);
        add(Status,BorderLayout.SOUTH);
        connect con=new connect();
        //setLayout(new FlowLayout());
       // add(row("id","name","ratting"));
        try{

        ResultSet rs = con.sql.executeQuery("select * from captain");
            while(rs.next())
            {
                Captain.add(rowC(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                //setLayout(new GridLayout(++row,1,0,0));
            }
             rs = con.sql.executeQuery("select * from bus");
            while(rs.next())
            {
                bus.add(rowB(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                //setLayout(new GridLayout(++row,1,0,0));
            }
            rs = con.sql.executeQuery("select * from USERS");
            while(rs.next())
            {
                user.add(rowu(rs.getString(1),rs.getString(2),rs.getString(3)));
                //setLayout(new GridLayout(++row,1,0,0));
            }
            rs = con.sql.executeQuery("select * from route");
            while(rs.next())
            {
                routes.add(row(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6)));
                //setLayout(new GridLayout(++row,1,0,0));
            }


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        setVisible(true);


    }

    JPanel rowC(String Cid,String Cname,String Age,String Crat)
    {
        JPanel p=new JPanel();
        setLayout(new FlowLayout());
        JTextField cid=new JTextField(Cid,10);
        JTextField age=new JTextField(Age,10);
        JTextField name=new JTextField(Cname,10);
        JTextField rat=new JTextField(Crat,10);
        cid.setEditable(false);
        p.add(cid);
        p.add(name);
        p.add(rat);
        p.add(age);
        JButton b=new JButton("Update");
        b.addActionListener(new Cpral(cid,name,age,rat));
        p.add(b);

        return p;


    }

    JPanel row(String Bid,String DC,String AC,String DT,String AT,String FA)
    {
        JPanel p=new JPanel();
        setLayout(new FlowLayout());
        JTextField bid=new JTextField(Bid,10);
        JTextField dc=new JTextField(DC,10);
        JTextField ac=new JTextField(AC,10);
        JTextField dt=new JTextField(DT,10);
        JTextField at=new JTextField(AT,10);
        JTextField fa=new JTextField(FA,10);
        
        p.add(bid);
        bid.setEditable(false);
        p.add(dc);
        p.add(ac);
        p.add(dt);
        p.add(at);
        p.add(fa);

        JButton b=new JButton("Update");
        b.addActionListener(new Roral(bid,dc,ac,dt,at,fa));
        p.add(b);

        return p;


    }

    JPanel rowB(String Bid,String Cid,String Plate,String Type)
    {
        JPanel p=new JPanel();
        setLayout(new FlowLayout());
        JTextField bid=new JTextField(Bid,10);
        JTextField cid=new JTextField(Cid,10);
        JTextField plate=new JTextField(Plate,10);
        JTextField type=new JTextField(Type,10);
        bid.setEditable(false);
        p.add(bid);
        p.add(cid);
        p.add(plate);
        p.add(type);

        JButton b=new JButton("Update");
        b.addActionListener(new Bural(bid,cid,plate,type));
        p.add(b);

        return p;


    }
    JPanel rowu(String User,String Pass,String Admin)
    {
        JPanel p=new JPanel();
        setLayout(new FlowLayout());
        JTextField user=new JTextField(User,10);
        JTextField password=new JTextField(Pass,10);
        JTextField admin=new JTextField(Admin,10);
        p.add(user);
        user.setEditable(false);
        p.add(password);
        p.add(admin);

        JButton b=new JButton("Update");
        b.addActionListener(new Usral(user,password,admin));
        p.add(b);

        return p;


    }
    JPanel Brow(String b1,String b2,String b3,String b4)
    {
        JPanel p=new JPanel();
        setLayout(new FlowLayout());
        JButton cid=new JButton(b1);
        cid.setName(b1);
        JButton name=new JButton(b2);
        name.setName(b2);
        JButton rat=new JButton(b3);
        rat.setName(b3);
        JButton user=new JButton(b4);
        user.setName(b4);
        user.addActionListener(this);
        rat.addActionListener(this);
        name.addActionListener(this);
        cid.addActionListener(this);
        p.add(cid);
        p.add(name);
        p.add(rat);
        p.add(user);
        return p;
    }
    
  

    @Override
    public void actionPerformed(ActionEvent bt) {
        // TODO Auto-generated method stub
        JButton btn=(JButton)bt.getSource();
        //"CAPTAIN","ROUTES","BUS","USER"));
        switch(btn.getName())
        {
            case "CAPTAIN":
                crd.show(man,"captain");
                break;
            case "BUS":
                crd.show(man,"bus");
                break;
            case "USER":
            crd.show(man,"user");
            break;
            case "ROUTES":
            crd.show(man,"routes");
            break;
            
                


            
        }
        
    }
    

    
}

class Bural implements ActionListener
{
    JTextField t1,t2,t3,t4;
    Bural(JTextField t1,JTextField t2,JTextField t3,JTextField t4)
    {
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
        this.t4=t4;
       

    }
    @Override
    public void actionPerformed(ActionEvent e) {


        String sql="update bus set captain_Id = ?, plate_number= ?,bus_type =? where Id=?";
        PreparedStatement statement = null;
        try {
            //
            //connect con=new connect();
            statement=App.con.con.prepareStatement(sql);
            statement.setString(1, t2.getText());
        
            statement.setString(2,t3.getText());
            statement.setString(3,t4.getText());
            statement.setString(4,t1.getText());
            if(statement.executeUpdate()==0)
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");
            else
            Admin.Status.setText("<html> <font color=green>UPDATED Successfully");

        }
        catch (Exception ee)

        {
            System.out.println(ee);
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");

        }

        // TODO Auto-generated method stub

       //Admin.Status.setText(t1.getText()+" "+t2.getText()+" " +t3.getText()+t4.getText());
    }

}

class Roral implements ActionListener
{
    JTextField t1,t2,t3,t4,t5,t6;
    Roral(JTextField t1,JTextField t2,JTextField t3,JTextField t4,JTextField t5,JTextField t6)
    {
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
        this.t4=t4;
        this.t5=t5;
        this.t6=t6;
       

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub


        String sql="update route set  Dept_city= ?, Arri_City= ?,Dept_At =? ,Arriv_By=?, fare =?where Bus_id=?";
        PreparedStatement statement = null;
        try {
            //
            //connect con=new connect();
            statement=App.con.con.prepareStatement(sql);
            statement.setString(1, t2.getText());
        
            statement.setString(2,t3.getText());
            statement.setString(3,t4.getText());
            statement.setString(4,t5.getText());
            statement.setString(5,t6.getText());
            statement.setString(6,t1.getText());
            if(statement.executeUpdate()==0)
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");
            else
            Admin.Status.setText("<html> <font color=green>UPDATED Successfully");

        }
        catch (Exception ee)

        {
            System.out.println(ee);
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");

        }
      // Admin.Status.setText(t1.getText()+" "+t2.getText()+" " +t3.getText()+" "+t4.getText()+t5.getText()+t6.getText());
    }

}

class Cpral implements ActionListener
{
    JTextField t1,t2,t3,t4;
    Cpral(JTextField t1,JTextField t2,JTextField t3,JTextField t4)
    {
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
        this.t4=t4;
       

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        String sql="update captain set name = ?, age= ?,rating =? where Id=?";
        PreparedStatement statement = null;
        try {
            //
            //connect con=new connect();
            statement=App.con.con.prepareStatement(sql);
            statement.setString(1, t2.getText());
        
            statement.setString(2,t3.getText());
            statement.setString(3,t4.getText());
            statement.setString(4,t1.getText());
            if(statement.executeUpdate()==0)
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");
            else
            Admin.Status.setText("<html> <font color=green>UPDATED Successfully");

        }
        catch (Exception ee)

        {
            System.out.println(ee);
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");

        }
       
    }

}
class Usral implements ActionListener
{
    JTextField t1,t2,t3;
    Usral(JTextField t1,JTextField t2,JTextField t3)
    {
        this.t1=t1;
        this.t2=t2;
        this.t3=t3;
       

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        String sql="update USERS set passwd = ?, isAdmin= ?where userId=?";
        PreparedStatement statement = null;
        try {
            //
            
            statement=App.con.con.prepareStatement(sql);
            statement.setString(1, t2.getText());
        
            statement.setString(2,t3.getText());
            statement.setString(3,t1.getText());
            //statement.setString(4,t1.getText());
            if(statement.executeUpdate()==0)
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");
            else
            Admin.Status.setText("<html> <font color=green>UPDATED Successfully");

        }
        catch (Exception ee)

        {
            System.out.println(ee);
            Admin.Status.setText("<html> <font color=red>ERROR TRY AGAIN");

        }
       /// Admin.Status.setText(t1.getText()+" "+t2.getText()+" " +t3.getText());
    }

}


  
