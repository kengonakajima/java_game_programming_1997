public class hairetu2 extends java.applet.Applet
{
    public void init()
    {

        // 文字列は特別に、次のようにして初期化できる。
        String sb[] = { "this" , "is" , "a" , "test.\n" };
        // 文字列の足し算は基本。
        System.out.println( sb[0] + sb[1] + sb[2] + sb[3] );
        // 文字列の配列
        String sa[] = new String[4];    
        // nullと表示される。内容がない、という意味。
        System.out.println( sa[2] );    
        sa[2] = "Music"; // こうやって内容を入れて、
        System.out.println( sa[2] );    // 表示すると....
    }
}
