import javax.swing.*;
public class App {
   public static connect con=null;
   static String userName="";
   static boolean isAuthenticated=false;
   static  login Login;
   static dashboard Dashboard;
   static bookTicket BookTicket=null;
    public static void main(String[] args) throws Exception {
      UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
       
       SwingUtilities.invokeLater(() -> {
       Login= new login("");
       Login.setVisible(true);
    });
    }
}
