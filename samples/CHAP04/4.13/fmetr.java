import java.awt.*;

public class fmetr extends java.applet.Applet
{
    String font_names[];
    public void init(){
        font_names = Toolkit.getDefaultToolkit().getFontList();
    }
    public void paint(Graphics g ){

        for(int i=0;i<font_names.length;i++){
            Font f = new Font( font_names[i] , Font.PLAIN , 20 );
            g.setFont( f ); 
            String s = "GAME OVER";
            g.drawString( s , 10,40*i+20 );
            FontMetrics fm= g.getFontMetrics();  // FontMetrics‚ð“¾‚éB
            int width = fm.stringWidth( s );     // •¶Žš—ñ‚Ì’·‚³‚ðŒvŽZ‚·‚éB
            g.drawString( ""+width , 10,40*i+40 );

        }
    }
}