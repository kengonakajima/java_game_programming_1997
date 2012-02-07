public class exception extends java.applet.Applet
{

    int array[] = new int[5];    // 5ŒÂ‚Ì—v‘f‚ð‚à‚Â”z—ñ

    public void init()
    {
        for(int i=0; i<10; i++){
            System.out.println("loop");
            array[i] = 500;
        }
        System.out.println("end.");
    }
}
