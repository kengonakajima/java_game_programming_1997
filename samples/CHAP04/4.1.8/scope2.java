public class scope2 extends java.applet.Applet
{
    int i = 20;
    public void init()
    {
        System.out.println( i );
        int i = 30;
        System.out.println( i );
    }
}
