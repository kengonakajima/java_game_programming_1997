public class hairetu1 extends java.applet.Applet
{
    public void init()
    {
        // ��{�^�̏ꍇ
        
        // �Ƃ肠����10���̗̈���m�ۂ���B
        int ia[] = new int[10]; 
        // �ŏ��ɒl�����Ă������Ƃ��ł���B
        int ib[] = { 111, 2, 3, 4, 5, 6, 7, 8, 999 };
        // [] �̒��̐������A�u�Y���i�������j�v�Ƃ����B
        System.out.println( ib[0] );    
        System.out.println( ib[8] );

        // �ȉ��̂悤�ɁA2����(�\�A�s��)�ɂ��ł���B
        char  ic[][] = new char[100][200]; 
        short id[][] = {
            { 606, 707, 808, 909 } ,  // 2�����̏������͂��̂悤�ɂ���B
            { 123, 234, 345, 456 } ,  // 2�����ȏ�A������ł��ł���B
            { 0, 1, 2, 3 }
        };
        System.out.println( id[2][2] );
        
        // ���̍s�͎��s������ƃG���[�B����Ȕԍ��̗v�f�͂Ȃ��B
        System.out.println( id[5][6] );    // �R�����g�A�E�g

        // boolean�́A2��ނ̒l�����Ȃ��B
        boolean ba[] = { true , false , true , true };  
        // �z��̃T�C�Y�𓾂�ɂ͎��̂悤�ɂ���B
        System.out.println( ba.length );   
   }
}
