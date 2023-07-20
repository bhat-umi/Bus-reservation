import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Manage extends JDialog implements ActionListener{
    JTextField name,age,contact;
    JButton ubtn,dbtn;
    JLabel msg;
    String PNR;
    check parent;
    Manage(check par,String PNR) 
    {
        super(par,true);
        this.PNR=PNR;
        this.parent=par;
        setSize(400,350);
        setLayout(new FlowLayout());
       
        JPanel p=new JPanel();
        p.add(new JLabel("Name"));
        
        name=new JTextField(10);
        p.add(name);
        p.add(new JLabel("              "));
        p.add(new JLabel("Contact"));
      
        contact=new JTextField(10);
        p.add(contact);
        add(p);

        p=new JPanel();
        p.add(new JLabel("Age"));
        
        age=new JTextField(9);
        p.add(age);
        p.add(new JLabel("              "));
        p.add(new JLabel("                                              "));
        
        add(p);
        p=new JPanel();
        p.add(new JLabel("-------------------------------------------------------------------"));
        add(p);

        try (ResultSet rs = App.con.sql.executeQuery("SELECT * FROM reservation WHERE pnr='"+PNR+"'")) {
            while(rs.next())
            {
                name.setText(rs.getString("pname"));
                contact.setText(rs.getString("pcontact"));
                age.setText(rs.getString("page"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        p=new JPanel();
        ubtn=new JButton("  UPDATE  ");
        p.add(ubtn);
        ubtn.setName("update");
        p.add(new JLabel("          "));
     
        dbtn=new JButton("  DELETE  ");
        p.add(dbtn);
        dbtn.setName("delete");
        ubtn.addActionListener(this);
        dbtn.addActionListener(this);
        add(p);
        msg=new JLabel();
        p=new JPanel();
        p.add(new JLabel(               ));
        p.add(msg);
        p.add(new JLabel(               ));
        add(p);

        setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent eve) {
        JButton btn=(JButton)eve.getSource();
        if(btn.getName().equals("update"))
        {
            String sql="update reservation set pname = ?, page= ?,pcontact =? where pnr=?";
            PreparedStatement statement = null;
            try {
                statement=App.con.con.prepareStatement(sql);
                statement.setString(1, name.getText());
            
                statement.setString(2,age.getText());
                statement.setString(3,contact.getText());
                //statement.setInt(3, Integer.parseInt(contact.getText()));
                statement.setString(4, PNR);
                if(statement.executeUpdate()!=0)
                {
                    JOptionPane.showMessageDialog(this,"Bookinng updated");
                    this.dispose();
                    parent.dispose();
                }
                else
                {
                    
                    msg.setText("<html><font color=red size=20> unable to update");
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return;
        }
        String sql="delete from  reservation where pnr=?";
        PreparedStatement statement = null;
        try {
                statement=App.con.con.prepareStatement(sql);
                statement.setString(1, PNR);
                if(statement.executeUpdate()!=0)
                {
                    JOptionPane.showMessageDialog(this,"Bookinng removed");
                    this.dispose();
                    parent.dispose();
                    
                }
                else
                    JOptionPane.showMessageDialog(this,"Please try aagin");
                

        }
        catch (Exception x){}

       
        
    }
}
