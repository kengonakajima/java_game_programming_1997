public class flow extends java.applet.Applet
{
    public void init()
    {
        int i = 0;
        while( true ){
            i++;                     // i��1�������� ....(A)
            if( i > 10 ){
                break;               // i��10���傫���Ȃ����甲����B
            }
            System.out.println( i );    // ... (B)
        }
        System.out.println("End!");     // ... (C)
    }
}