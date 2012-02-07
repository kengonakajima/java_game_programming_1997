import java.applet.Applet;

public class aho extends Applet implements Runnable
{
	int a[] = new int[10];
	Thread t[] = new Thread[10];

	public void init()
	{
		System.out.println("min:" + Thread.MIN_PRIORITY + " max:"+Thread.MAX_PRIORITY);
		
		for(int i= 0 ; i < 10; i++){
			t[i] = new Thread( this );
			t[i].start();
			a[i]=0;
		}

	}
	int c = 0;
	public void run()
	{
		while( true){
			
			a[c]++;
			System.out.println(a[0]+" "+a[1]+" "+a[2]+" "+a[3]+" "+a[4]+
			" "+a[5]+" "+a[6]+" "+a[7]+" "+a[8]+" "+a[9]);
			c++; if ( c == 10 ){ c = 0;}
			
			try{
				Thread.sleep(100 );
			}catch( InterruptedException e ){}
		}
	}

}
