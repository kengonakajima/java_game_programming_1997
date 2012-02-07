import java.applet.Applet;  
import java.awt.*;
public class shinkou extends Applet  // ”š‚ğ•\¦‚·‚éB
{
   int i=0, r=0;
   public void init()
   {
       int k = 2;
       k = k + 2; r = tasu( k , i );
   }
   public void paint( Graphics g )
   {
       g.drawString( Integer.toString( r ) , 30 , 30 );
   }
   int tasu( int a, int b )
   {
       return a + b;
   }
}