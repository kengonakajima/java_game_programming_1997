// キー操作でキャラクタを動かすサンプル。カーソルキーで操作することができる。
// カーソルキーがないキーボードのために、多少改造したほうがよい。

import java.applet.Applet;
import java.awt.*;

public class key extends Applet implements Runnable
{
        Image offscr;
        Graphics og;
        int x , y;      // 動く四角の位置
	boolean down[] = new boolean [4]; // down[0]が上、以下右回り

        Thread t;        // キャラを継続的に動かすためにスレッドを使う。
        final int UP = 1004; // 定数はこのように最初に定義しておきましょう。
        final int DOWN = 1005; // intの前にfinalをつけると、「変更できない」という意味になります。
        final int LEFT = 1006;  // こうすることで、プログラムの意味がわかりやすくなります。
        final int RIGHT = 1007;

        public void init( ){
                offscr = createImage( size().width , size().height );
                og = offscr.getGraphics();
                og.setColor( Color.white );
                og.fillRect( 0 , 0 , size().width , size().height);

                x = size().width/2;  // 最初は真ん中にいる。
                y = size().height/2;

		for(int i = 0 ; i < 4 ; i++) down[i] = false;

                t = new Thread( this );
                t.start();
		
		
        }
        public boolean keyDown( Event e , int key){
		System.out.println( key );
		if( key == UP ) down[0] = true;
		if( key == RIGHT ) down[1] = true;
		if( key == DOWN ) down[2] = true;
		if( key == LEFT ) down[3] = true;
		
		return true;
        }
        public boolean keyUp( Event e , int key ){
		System.out.println( key );
		if( key == UP ) down[0] = false;
		if( key == RIGHT ) down[1] = false;
		if( key == DOWN ) down[2] = false;
		if( key == LEFT ) down[3] = false;

		return true;
        }
        public void run()
        {
                while(true){
               
                        if( down[0]) y = y - 4;
                        if( down[1]) x = x + 4;
                        if( down[2] ) y = y + 4;
                        if( down[3] ) x = x - 4;

                        og.setColor( Color.white );
                        og.fillRect( 0 , 0, size().width , size().height);
                        og.setColor( Color.black );
                        og.fillRect( x,y,10,10);

			repaint();
			
			try{
                                Thread.sleep(70);
                        }catch( InterruptedException e ){}
                }
        }
	public void update( Graphics g){
		paint(g);
	}
	public void paint( Graphics g ){
		try {
			g.drawImage(offscr , 0 , 0 , this );
		} catch( NullPointerException e ){
			System.out.println("null paint");
		}
	}



}



