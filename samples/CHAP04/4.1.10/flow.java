public class flow extends java.applet.Applet
{
    public void init()
    {
        int i = 0;
        while( true ){
            i++;                     // i‚É1‚ğ‰Á‚¦‚é ....(A)
            if( i > 10 ){
                break;               // i‚ª10‚æ‚è‘å‚«‚­‚È‚Á‚½‚ç”²‚¯‚éB
            }
            System.out.println( i );    // ... (B)
        }
        System.out.println("End!");     // ... (C)
    }
}