public class trycatch extends java.applet.Applet
{

    int array[] = new int[5];    // 5個の要素をもつ配列

    public void init()
    {
        for(int i=0;i<10;i++){
            System.out.println("loop");
            try{                // try節始まり
                array[i] = 500;
                System.out.println("it is ok");
            }catch( ArrayIndexOutOfBoundsException e ){  // catch節始まり
                System.out.println( "exception");
            }
        }
        System.out.println("end.");
    }
}
