
import java.applet.Applet;
public class exception extends Applet
{

	int array[] = new int[5];    // 10$B8D$NMWAG$r;}$DG[Ns(B

	public void init()
	{
		for(int i=0;i<10;i++){
			System.out.println("loop");
			array[i] = 500;
		}
		System.out.println("end.");
	}
}
