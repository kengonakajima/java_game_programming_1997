public class kihon extends java.applet.Applet
{
    public void init()
    {
        int i = 200, ia = 808;  // int�͐���������ϐ�
        System.out.println( i );  // �\������B
        System.out.println( i + ia ); // �����Z�̌��ʂ�\������B

        short s = -8008; // int��32�r�b�g�ɑ΂��āAshort��16�r�b�g�B
        System.out.println( s );

        long l = 9096060680808080808L; // long�͂���ȑ傫�Ȑ�������B
        System.out.println( l );

        byte y = (byte)808; // ���E���z�����l������Ƃǂ��Ȃ�̂��낤�H
        System.out.println( y );

        char b = 'a'; // char��1����������ϐ��B���̂悤�ɂ��Ďg���B
        System.out.println( b );

        boolean t = false; // boolean�^��true��false��2��ނ����l���Ȃ��B
        System.out.println( t );

        double d = 3.1415926; // double�͎���������ϐ��B
        System.out.println( d );

        float f = (float)1.41421356; // float�́Adouble�̐��x���Ⴂ�B
        System.out.println( f );
    }
}