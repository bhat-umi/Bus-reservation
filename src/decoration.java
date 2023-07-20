import javax.swing.JButton;
import java.awt.*;
public class decoration {
    decoration(JButton B)
    {
        B.setBackground(Color.white);
        B.setForeground(Color.black);
        B.setBorder(new RoundB(55));
        B.setFont(new myfont(30));
    }
    decoration(JButton B,int r,Color f,Color b)
    {
        B.setBackground(b);
        B.setForeground(f);
        B.setBorder(new RoundB(r));
        B.setFont(new myfont(30));
    }
    decoration(JButton B,int r,Color f,Color b,int s)
    {
        B.setBackground(b);
        B.setForeground(f);
        B.setBorder(new RoundB(r));
        B.setFont(new myfont(s));
    }
    decoration(JButton B,int s)
    {
        B.setBackground(Color.white);
        B.setForeground(Color.black);
        B.setBorder(new RoundB(10));
        B.setFont(new myfont(s));
    }
}
