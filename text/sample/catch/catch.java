import java.applet.Applet;
public class trycatch extends Applet
{

	int array[] = new int[5];    // 10$B8D$NMWAG$r;}$DG[Ns(B

	public void init()
	{
		for(int i=0;i<10;i++){
			System.out.println("loop");
			try{
				array[i] = 500;
			}catch( ArrayIndexOutOfBoundsException e ){
				System.out.println( "exception");
			}
		}
		System.out.println("end.");
	}
}
