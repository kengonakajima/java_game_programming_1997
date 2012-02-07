/*

ゲームのアプレットのプログラムのスケルトン。読者が変更して、どんどん
試したらいいところは色を変える。ダブルバッファ、スレッド、なども
スケルトンに含めてしまう。色によって、レベルを分ける。さらに、色が
ついていないところについての解説も、本にある場合は、ページ番号を添える。

変更可能個所に、レベルを付けるのもいいかもしれない。何いろは、初心者でも
変更できて、とか。


appletのサイズについては、変更個所を1個所に限定したいので、html
だけ書けばいい、というようにして、そのあとappletの方で、サイズを得れば
よいか。

----------------------------------------------------------------------

*/

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

	/* 変更可能個所、数値のところだけ色を変える。 */

	public final int width = 400;		/* pixel */
	public final int height = 400;		/* pixel */
	public final int interval = 500;	/* milli second */

	public void init()
	{


		thread = new Thread(this);
		double_buffer = createImage( width , height );
		dg = double_buffer.getGraphics();

		resize( width , height );

		thread.start();

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

	}

	void doIt()
	{

		/* 変更可能個所 */
		
		dg.drawString( Integer.toString( cron ) , 50 ,cron*10  );
		
	}


}


























