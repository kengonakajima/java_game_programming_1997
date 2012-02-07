public class bool extends java.applet.Applet
{
    public void init()
    {
        System.out.println( true && true );
        System.out.println( true && false );
        System.out.println( true || false );
        System.out.println( false || false );

        boolean b = true;
        System.out.println( !b );
        System.out.println( true && (false || true) );
    }
}