public class keisan extends java.applet.Applet
{
    public void init()
    {
        // 後で使う予定の変数は、使う前に宣言しなければならない。
        // 宣言は、型名 名前[,名前,...]; とすることで行う。
        // 宣言していない変数を使おうとすると、コンパイル時にエラーが出る。
        int ia = 100;
        int ib = 808;
        int ic = -20000;
        int id = 1;
        double da = 100.0; // 代入には、'='を使う。
        double db = 3.0;

        System.out.println( ia + ib );  // 足し算
        System.out.println( ia - ib );  // 引き算
        System.out.println( ia * ib );  // かけ算
        System.out.println( ia / ib );  // 割り算（整数）
        System.out.println( da / db );  // 割り算（小数）
        System.out.println( ib % ia );  // 割り算の余り
        System.out.println( ( ia * ib) + ic );  // カッコも使える。
        // カッコが多くなっても、数学のような中カッコは使えない。
        System.out.println( ( ( (id+1) *(id+1) + 1) * (id+1)) + 1);

        // 等しいかどうかを判定する。これは代入とは異なり、'=='を使う。
        System.out.println(  ia == 100 );
        // 異なっているかどうかを判定する。
        System.out.println(  ia != ib );
        // 大小関係を比較する。
        System.out.println( ia > 808 );
        // 単に否定する。
        System.out.println( !false );

        // 次の行のようにすると、コンパイルできない。なぜだろうか？
        // int ii = ( 100 > 200 );  
        // 右辺がboolean型で、左辺がint型なので、代入できないのだ。
    }
}