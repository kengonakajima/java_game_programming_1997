public class keisan extends java.applet.Applet
{
    public void init()
    {
        // ��Ŏg���\��̕ϐ��́A�g���O�ɐ錾���Ȃ���΂Ȃ�Ȃ��B
        // �錾�́A�^�� ���O[,���O,...]; �Ƃ��邱�Ƃōs���B
        // �錾���Ă��Ȃ��ϐ����g�����Ƃ���ƁA�R���p�C�����ɃG���[���o��B
        int ia = 100;
        int ib = 808;
        int ic = -20000;
        int id = 1;
        double da = 100.0; // ����ɂ́A'='���g���B
        double db = 3.0;

        System.out.println( ia + ib );  // �����Z
        System.out.println( ia - ib );  // �����Z
        System.out.println( ia * ib );  // �����Z
        System.out.println( ia / ib );  // ����Z�i�����j
        System.out.println( da / db );  // ����Z�i�����j
        System.out.println( ib % ia );  // ����Z�̗]��
        System.out.println( ( ia * ib) + ic );  // �J�b�R���g����B
        // �J�b�R�������Ȃ��Ă��A���w�̂悤�Ȓ��J�b�R�͎g���Ȃ��B
        System.out.println( ( ( (id+1) *(id+1) + 1) * (id+1)) + 1);

        // ���������ǂ����𔻒肷��B����͑���Ƃ͈قȂ�A'=='���g���B
        System.out.println(  ia == 100 );
        // �قȂ��Ă��邩�ǂ����𔻒肷��B
        System.out.println(  ia != ib );
        // �召�֌W���r����B
        System.out.println( ia > 808 );
        // �P�ɔے肷��B
        System.out.println( !false );

        // ���̍s�̂悤�ɂ���ƁA�R���p�C���ł��Ȃ��B�Ȃ����낤���H
        // int ii = ( 100 > 200 );  
        // �E�ӂ�boolean�^�ŁA���ӂ�int�^�Ȃ̂ŁA����ł��Ȃ��̂��B
    }
}