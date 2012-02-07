
import java.applet.Applet;
public class exception extends Applet
{

	int array[] = new int[5];    // 10個の要素を持つ配列

	public void init()
	{
		for(int i=0;i<10;i++){
			System.out.println("loop");
			array[i] = 500;
		}
		System.out.println("end.");
	}
}
