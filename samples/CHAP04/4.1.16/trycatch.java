public class trycatch extends java.applet.Applet
{

    int array[] = new int[5];    // 5�̗v�f�����z��

    public void init()
    {
        for(int i=0;i<10;i++){
            System.out.println("loop");
            try{                // try�ߎn�܂�
                array[i] = 500;
                System.out.println("it is ok");
            }catch( ArrayIndexOutOfBoundsException e ){  // catch�ߎn�܂�
                System.out.println( "exception");
            }
        }
        System.out.println("end.");
    }
}
