
import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class game extends Applet implements Runnable
{

	Thread thread;
	Image double_buffer;
	Graphics dg;

	int cron=0;

	public int width,height;			// 単位はピクセル
	public final int interval = 50;		// ミリ秒

	MenuBar mb;
	player p ;

	int mode = 0;
	Choice c;

	public void init()
	{
		setLayout( new BorderLayout() );

		c = new Choice();

		c.addItem("tosoku");
		c.addItem("kansei");
		c.addItem("shutan");
		c.addItem("zenkin");
		add( "South",c );

		width = size().width;
		height = size().height;

		thread = new Thread(this);
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		thread.start();

		//変更可能個所

		p = new player( width , height , 8 , (double) 4 ,(double)1,(double)0.1 );
		mode = p.TOSOKU;

	}

	public void paint( Graphics g )
	{
		g.drawImage( double_buffer , 0 , 0 , null );

	}
	public void update( Graphics g )
	{
		paint( g );
	}
	public void run()
	{
		while( true )
		{
			
			cron++;

			try{
				Thread.sleep( interval );
			} catch( InterruptedException e ){}

			doIt();
			repaint();
		}


	}

        final int UP = 1004; 
        final int DOWN = 1005;
        final int LEFT = 1006;
        final int RIGHT = 1007;

	boolean down[] = new boolean[4];

        public boolean keyDown( Event e , int key){

		if( key == UP ) down[0] = true;
		if( key == RIGHT ) down[1] = true;
		if( key == DOWN ) down[2] = true;
		if( key == LEFT ) down[3] = true;
		
		return true;
        }
        public boolean keyUp( Event e , int key ){

		if( key == UP ) down[0] = false;
		if( key == RIGHT ) down[1] = false;
		if( key == DOWN ) down[2] = false;
		if( key == LEFT ) down[3] = false;

		return true;
        }

	void doIt()
	{

		// 変更可能個所
		// ここにプログラムを加えていってみましょう。
		dg.setColor( Color.white );
		dg.fillRect( 0 ,0, 300,50 );
		dg.setColor( Color.black );
		dg.drawString( Integer.toString( cron ) , 0 ,20  );
		p.move( down , mode );
		draw();

	}
	void draw()
	{
		dg.setColor( Color.white);
		dg.fillRect( (int) p.getX() - 10 , (int)p.getY()-10 , 30,30);
		dg.setColor( Color.black );
		dg.fillRect( (int) p.getX() , (int)p.getY() , 8,8 );
		
	}

	public boolean action( Event e , Object o ){
		if( e.target == c ){
			String s;
			s = o.toString();
			if( s.equals("kansei")){
				mode = p.KANSEI;
			} 
			if( s.equals("tosoku")){
				mode = p.TOSOKU;
			}
			if( s.equals("shutan")){
				mode = p.SHUTAN;
			}
			if( s.equals("zenkin")){
				mode = p.ZENKIN;
			}
			System.out.println( e );
			System.out.println( o );
		}
		return true;

	}

}

