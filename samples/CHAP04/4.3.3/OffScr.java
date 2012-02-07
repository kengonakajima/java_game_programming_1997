import java.awt.*;
public class OffScr extends java.applet.Applet implements Runnable
{
    Thread t;
    int x = 0;
    Image ofscr; // 仮想画面（オフスクリーン、ダブルバッファともいう）
    Graphics ofscr_g;       // 仮想画面を操作するためのGraphics

    public void init()
    {
        t = new Thread( this );
        t.start();
        // 次の行により、仮想画面は、真っ白または真っ黒に初期化される。
        // 使う前に、必要な色で塗りつぶしてやる必要がある。
        ofscr = createImage( 300,300 );  
        ofscr_g = ofscr.getGraphics();
    }
    public void paint( Graphics g )
    {
        ofscr_g.setColor( Color.white);
        ofscr_g.fillRect( 0 , 0 , 300, 300 );   // 全画面を消す。
        ofscr_g.setColor( Color.black );
        ofscr_g.fillRect( x,50,200,200 );       // 目的の四角を描画する。
        g.drawImage( ofscr , 0 , 0 , null );
    }
    public void update( Graphics g )
    {
        paint( g );
    }
    public void run()
    {
        while(true){
            x++;
            try{
                Thread.sleep( 100 );
            }catch( InterruptedException e ) {}
            repaint();
        }
    }
}
