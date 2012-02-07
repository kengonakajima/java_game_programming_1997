import java.applet.Applet;
public class scope extends Applet
{
        int i = 20;
        public void init()
        {
		System.out.println( i );
		int i = 30;
		System.out.println( i );
        }


}
