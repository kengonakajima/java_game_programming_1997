public class hairetu2 extends java.applet.Applet
{
    public void init()
    {

        // ������͓��ʂɁA���̂悤�ɂ��ď������ł���B
        String sb[] = { "this" , "is" , "a" , "test.\n" };
        // ������̑����Z�͊�{�B
        System.out.println( sb[0] + sb[1] + sb[2] + sb[3] );
        // ������̔z��
        String sa[] = new String[4];    
        // null�ƕ\�������B���e���Ȃ��A�Ƃ����Ӗ��B
        System.out.println( sa[2] );    
        sa[2] = "Music"; // ��������ē��e�����āA
        System.out.println( sa[2] );    // �\�������....
    }
}
