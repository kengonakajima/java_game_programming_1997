import java.applet.Applet;
import java.awt.*;

public class mouse extends Applet
{
	Image offscreen;
        Graphics og;

        public void init()
        {
                offscreen = createImage( size().width , size().height);
		og = offscreen.getGraphics();
        }
	public void paint( Graphics g ){
		g.drawImage( offscreen , 0 , 0  , null );
	}
	public void update( Graphics g ){
		paint( g );
	}

        public boolean mouseDown(Event e , int x , int y){
                System.out.println( "Mouse down! X:" + x + " Y:" + y );
                return true;
        }
        public boolean mouseUp( Event e , int x , int y ){
                System.out.println( "Mouse up X:" + x + " Y:" + y );
                return true;
        }
        public boolean mouseDrag( Event e , int x , int y){
                System.out.println( "Mouse drag X:" + x + " Y:" + y );
                return true;
        }
        public boolean mouseMove( Event e , int x , int y){
                System.out.println( "Mouse move X:" + x + " Y:" + y );
		og.fillRect( x ,  y , 10,10);
		repaint();
                return true;
        }
        public boolean mouseEnter( Event e , int x , int y){
                System.out.println( "Mouse enter X:" + x + " Y:" + y );
                return true;
        }
        public boolean mouseExit( Event e , int x , int y){
                System.out.println( "Mouse exit X:" + x + " Y:" + y );
                return true;
        }

}
