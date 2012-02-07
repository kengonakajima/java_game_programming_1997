public class str extends java.applet.Applet
{
    public void init()
    {
        String hoge="I am a";     // こうやって文字列を入れる。
        String hoge2 = hoge + "boy";     // 足し算すると、つながる。
        System.out.println(hoge2+".");   // どんどんつなげる。
    }
}