public class hairetu3 extends java.applet.Applet
{
    public void init()
    {
        MyClass mca[] = new MyClass[3];
        mca[0] = new MyClass(); // 1つずつコンストラクタを呼ぶ。
        mca[1] = new MyClass();
        mca[2] = new MyClass();
        // forループ命令を使うのもよい考え。以下は上と同じ意味。
        for(int i = 0 ; i < 3 ; i++ ){
            mca[i] = new MyClass();
        }
    }
}
class MyClass     // 中身がないけど、クラス。
{
}
