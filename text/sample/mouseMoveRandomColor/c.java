// $B$$$/$D$+$N?'$r=P$9%5%s%W%k(B
// $B%^%&%9$rF0$+$5$J$$$H?J$^$J$$$+$iCm0U(B

import java.applet.Applet;
import java.awt.*;
import java.util.*;

public class c extends Applet 
{

	Image offscr;
	Graphics og;
	int xi=0;

	public void init()
	{
		offscr = createImage( size().width  , size().height );
		og = offscr.getGraphics();
		
	}
	public void paint(Graphics g){

		g.drawImage( offscr , 0 , 0 , null );
	}

	public void update(Graphics g){
		paint(g);
	}

	public boolean mouseMove( Event e , int x , int y){
		xi++;
		int r = new Random(x).nextInt();
		Color c = new Color( r&255,(r>>>8)&255,(r>>>16)&255);
		og.setColor( c );
		og.fillRect( ( xi % 32)*16 , (xi / 32)*16 , 16 , 16);
		repaint();
		return true;
	}

}
