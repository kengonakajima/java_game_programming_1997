import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;

public class win extends Applet
{
  MyWindow w=null;
  public void init()
  {
     w = new MyWindow(100,100);
  }
  public boolean keyDown( Event ev , int c )
  {
    if( w == null ) return true; 
    if( c=='h' ) w.moveRect(-4,0);
    if( c=='l' ) w.moveRect(4,0);
    if( c=='j' ) w.moveRect(0,4);
    if( c=='k' ) w.moveRect(0,-4);
    if( c=='c' ) w.hide();
    if( c=='s' ) w.show();
    return true;
  }
}
class MyWindow extends Frame
{
  Image offscr;
  Graphics offscr_g;
  int xsize = 100 , ysize = 100;
  int rectx=0 , recty=50;
  
  MyWindow(int x,int y)
  {
    super("mywindow");
    xsize = x;
    ysize = y;
    resize( xsize,ysize);
    show();  // createImageするより先にshowがある必要がある。
    offscr = createImage( xsize , ysize );
    offscr_g = offscr.getGraphics();
  }
  public void moveRect( int xdif, int ydif )
  {
    rectx += xdif;
    recty += ydif;
    repaint();
  }
  public void update( Graphics g )
  {
     paint( g );
  }
  public void paint(Graphics g )
  {
    offscr_g.setColor( Color.white );
    offscr_g.fillRect( 0 , 0 , xsize , ysize );
    offscr_g.setColor( Color.black );
    offscr_g.fillRect( rectx , recty , 10,10);
    g.drawImage( offscr , 0 , 0 , null );
  }
  public boolean keyDown( Event ev , int c )
  {
    hide();
    return true;
  }

}



