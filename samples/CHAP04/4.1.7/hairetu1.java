public class hairetu1 extends java.applet.Applet
{
    public void init()
    {
        // 基本型の場合
        
        // とりあえず10個分の領域を確保する。
        int ia[] = new int[10]; 
        // 最初に値を入れておくことができる。
        int ib[] = { 111, 2, 3, 4, 5, 6, 7, 8, 999 };
        // [] の中の数字を、「添字（そえじ）」という。
        System.out.println( ib[0] );    
        System.out.println( ib[8] );

        // 以下のように、2次元(表、行列)にもできる。
        char  ic[][] = new char[100][200]; 
        short id[][] = {
            { 606, 707, 808, 909 } ,  // 2次元の初期化はこのようにする。
            { 123, 234, 345, 456 } ,  // 2次元以上、いくらでもできる。
            { 0, 1, 2, 3 }
        };
        System.out.println( id[2][2] );
        
        // 次の行は実行させるとエラー。そんな番号の要素はない。
        System.out.println( id[5][6] );    // コメントアウト

        // booleanは、2種類の値しかない。
        boolean ba[] = { true , false , true , true };  
        // 配列のサイズを得るには次のようにする。
        System.out.println( ba.length );   
   }
}
