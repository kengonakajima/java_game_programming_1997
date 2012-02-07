class enemyClass 
{
    int x,y;
    int dx,dy;
    int xsiz,ysiz;
    int score;
    int hp;

    // コンストラクタ
    enemyClass(int x , int y , int xsiz , int ysiz , int hp , int score){
        // とにかく初期化をする。
        this.x = x;            
        this.y = y;
        this.xsiz = xsiz;
        this.ysiz = ysiz;
        this.dx = 0;
        this.dy = 0;
        this.hp = hp;
    }

    // クラスのメソッド
    public void moveOnce()
    {
        x += dx;
        y += dy;
    }

}