import java.applet.Applet;
import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class snake extends Applet implements Runnable
{
	Thread thread;
	Image double_buffer;
	Graphics dg;
	int cron=0;

	int width ,height;
	int interval = 30;	// milli second 

	public void init()
	{
		thread = new Thread(this);
		width = size().width;
		height = size().height;
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		thread.start();

		initSnake();
		gameStart();
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

	int snake_max=1000;
	int snake_unit=12;
	int snake_len=5;
	int snake_start_len=5;
	int snake_dir;  // $BJ}8~$O??>e$,(B0$B$G1&<~$j$K(B7$B$^$G$N(B8$BJ}8~$H$7$^$9!#(B
	double dx[] = { 0,1.41,2,1.41,0,-1.41,-2,-1.41};  // $B$=$l$>$l$NJ}8~$N0\F0NL(B
	double dy[] = { -2,-1.41,0,1.41,2,1.41,0,-1.41};
	double snake_x[] = new double[snake_max];
	double snake_y[] = new double[snake_max];
	double snakehead_x , snakehead_y;
	int score = 0;
	double snake_xsiz = 36;
	double snake_ysiz = 36;
	final double SNAKE_SPEED = 3;

	void initSnake(){
		for(int i=0;i<snake_max;i++){
			snake_x[i] = snake_y[i] = -1000;   //$B@$3&$N2L$F$K$7$F$*$/(B
		}
		snakehead_x = width/2;
		snakehead_y = height/2;
		snake_dir = 2;
		snake_len = snake_start_len;
	}

	public boolean keyDown(Event e , int c ){
		if( c == 'h' ){ 
			snake_dir--;  // $B:82sE>(B
		}
		if( c == 'j' ){
			snake_dir++;  // $B1&2sE>(B
		}
		// $B>o$KCM$,(B0$B$+$i(B7$B$N4V$K$J$k$h$&$K$9$k(B
		if( snake_dir >= 7 ) snake_dir-=8;
		if( snake_dir < 0 )snake_dir +=8;
		
		return true;
	}
	void moveSnake(){
		snakehead_x += dx[snake_dir] * SNAKE_SPEED;
		snakehead_y += dy[snake_dir] * SNAKE_SPEED;
		if( snakehead_x < 0 || snakehead_y < 0 || snakehead_x > width ||
		   snakehead_y > height ){
			snakeDie();
		}
		snake_x[cron % snake_max ] = snakehead_x;
		snake_y[cron % snake_max ] = snakehead_y;
		
	}
	void snakeDie(){
		initSnake();
		gameStart();
	}
	void gameStart(){
		score = 0;
		snake_len = 5;
	}
	double tmpx[] = new double[ 200];   
	double tmpy[] = new double[ 200];
	void drawSnake(){
		dg.setColor( fg );

		for(int i=0;i<snake_len ; i++){
			if( ( cron - (i*snake_unit))<0 )continue;
			int index = ( cron - (i * snake_unit) ) % snake_max;
			tmpx[i] = snake_x[index];
			tmpy[i] = snake_y[index];
			dg.fillRect( (int)snake_x[index],(int)snake_y[index],
						(int)snake_xsiz , (int)snake_ysiz );
		}
		// $B<+J,$NBN$KEv$?$k$H;`$L!#(B
		for(int j=1;j<snake_len;j++){
			if( snakehead_x+snake_xsiz > tmpx[j] && 
			    snakehead_x < tmpx[j]+snake_xsiz  &&
			    snakehead_y+snake_ysiz > tmpy[j] && 
			    snakehead_y < tmpy[j]+snake_ysiz  )
			{
				snakeDie();
				return;
			}
			    
		}
	}

	Color fg = new Color( 0xffaa44 );
	Color bg = new Color( 0x110921);
	void doIt()
	{
		dg.setColor( bg );
		dg.fillRect(0, 0 , width , height );
		moveSnake();
		drawSnake();

		// $BFq0WEYD4@0(B
		if( (cron % 200 )==0){
			snake_len++;
		}
		dg.setColor( fg );
		dg.drawString( "Score: " + score , 30,30);

		score += snake_len-snake_start_len;
	}
}
