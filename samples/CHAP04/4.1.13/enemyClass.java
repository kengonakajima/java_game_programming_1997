class enemyClass 
{
    int x,y;
    int dx,dy;
    int xsiz,ysiz;
    int score;
    int hp;

    // �R���X�g���N�^
    enemyClass(int x , int y , int xsiz , int ysiz , int hp , int score){
        // �Ƃɂ���������������B
        this.x = x;            
        this.y = y;
        this.xsiz = xsiz;
        this.ysiz = ysiz;
        this.dx = 0;
        this.dy = 0;
        this.hp = hp;
    }

    // �N���X�̃��\�b�h
    public void moveOnce()
    {
        x += dx;
        y += dy;
    }

}