import java.time.LocalDate;
import java.time.chrono.JapaneseDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.awt.event.*;
public class dateSelector implements ActionListener{
    JDialog dia;
    JComboBox<String> dd,mm,yy;
    JButton b;
    String d[]=new String [31];
    String m[]=new String [12];
    String y[]=new String [5];
    JLabel w;
    dateSelector(JFrame p)
    {
        dia=new JDialog(p,"Select Date", true);
        dia.setSize(400,100);
        dia.setLayout(new FlowLayout());
        for(int i=1;i<=31;i++)
        {
            d[i-1]=new String(i+"");
            
        }
        for(int i=1;i<=12;i++)
        {
            m[i-1]=new String(i+"");
        }
        int today=java.time.LocalDate.now().getYear();
        for(int i=0;i<5;i++)
        {
            y[i]=new String(today+"");
            today++;
        }
        dd=new JComboBox<String>(d);
        mm=new JComboBox<String>(m);
        yy=new JComboBox<String>(y);
        dia.add(new JLabel("Day: "));
        dia.add(dd);
        dia.add(new JLabel("       Month: "));
        dia.add(mm);
        dia.add(new JLabel("       Year: "));
        dia.add(yy);
        b=new JButton("Set");
        b.addActionListener(this);
        dia.add(b);
        
        w=new JLabel("");
        dia.add(w);
        dia.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String Udate=yy.getSelectedItem()+"-"+mm.getSelectedItem()+"-"+dd.getSelectedItem();
        //System.out.println(java.time.LocalDate.now().getYear());
        if(date(Integer.parseInt((String)dd.getSelectedItem()),Integer.parseInt((String)mm.getSelectedItem()),Integer.parseInt((String)yy.getSelectedItem())))
        {
            App.BookTicket.jt.setText(Udate);
        dia.dispose();
        
        }
        else
        {
            w.setText("<html> <font color=red align=center>Invalid date</font></html>");
        }
        
    }
    public static boolean  date(int d,int m,int y){
        int month[]={0,31,28,31,30,31,30,31,31,30,31,30,31};
        LocalDate dat = LocalDate.now();
        if(dat.getDayOfMonth()>=d && dat.getMonthValue()>=m && dat.getYear()>=y){
           return false;
        }else if(month[m]<d){
           if(m==2 && d>=29){
              if(y%400==0){
                 return true;
  
              }
              if(y %4==0){
                 return true;
  
              }
              if(y%100==0){
                 return false;
  
              }
  
           }
           return false;
  
        }
           return true;
      }
}
