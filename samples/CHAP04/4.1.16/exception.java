public class exception extends java.applet.Applet
{

    int array[] = new int[5];    // 5�̗v�f�����z��

    public void init()
    {
        for(int i=0; i<10; i++){
            System.out.println("loop");
            array[i] = 500;
        }
        System.out.println("end.");
    }
}
