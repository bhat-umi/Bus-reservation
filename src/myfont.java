import java.awt.*;

public class myfont extends Font {
    myfont()
    {
        super("SansSerif", Font.BOLD, 60);
    }
    myfont(int x,int y)
    {
        super("SansSerif", x,y);
    }
    myfont(int s)
    {
        super("SansSerif", Font.PLAIN,s);
    }
}
