public class hairetu3 extends java.applet.Applet
{
    public void init()
    {
        MyClass mca[] = new MyClass[3];
        mca[0] = new MyClass(); // 1���R���X�g���N�^���ĂԁB
        mca[1] = new MyClass();
        mca[2] = new MyClass();
        // for���[�v���߂��g���̂��悢�l���B�ȉ��͏�Ɠ����Ӗ��B
        for(int i = 0 ; i < 3 ; i++ ){
            mca[i] = new MyClass();
        }
    }
}
class MyClass     // ���g���Ȃ����ǁA�N���X�B
{
}
