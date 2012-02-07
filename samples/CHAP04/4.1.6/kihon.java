public class kihon extends java.applet.Applet
{
    public void init()
    {
        int i = 200, ia = 808;  // intは整数を入れる変数
        System.out.println( i );  // 表示する。
        System.out.println( i + ia ); // 足し算の結果を表示する。

        short s = -8008; // intが32ビットに対して、shortは16ビット。
        System.out.println( s );

        long l = 9096060680808080808L; // longはこんな大きな数も入る。
        System.out.println( l );

        byte y = (byte)808; // 限界を越えた値を入れるとどうなるのだろう？
        System.out.println( y );

        char b = 'a'; // charは1文字を入れる変数。このようにして使う。
        System.out.println( b );

        boolean t = false; // boolean型はtrueとfalseの2種類しか値がない。
        System.out.println( t );

        double d = 3.1415926; // doubleは実数を入れる変数。
        System.out.println( d );

        float f = (float)1.41421356; // floatは、doubleの精度が低い。
        System.out.println( f );
    }
}