public class flow extends java.applet.Applet
{
    public void init()
    {
        int i = 0;
        while( true ){
            i++;                     // iに1を加える ....(A)
            if( i > 10 ){
                break;               // iが10より大きくなったら抜ける。
            }
            System.out.println( i );    // ... (B)
        }
        System.out.println("End!");     // ... (C)
    }
}