class enemyClass 
{
	int x,y;
	int dx,dy;
	int xsiz,ysiz;
	int score;
	int hp;

	// $B%3%s%9%H%i%/%?(B
	enemyClass(int x , int y ,int xsiz , int ysiz ,int hp,int score){
		// $B$H$K$+$/=i4|2=$r$9$k(B
		this.x = x;            
		this.y = y;
		this.xsiz = xsiz;
		this.ysiz = ysiz;
		this.dx = 0;
		this.dy = 0;
		this.hp = hp;
	}

	// $B%/%i%9$N%a%=%C%I(B
	public void moveOnce()
	{
		x += dx;
		y += dy;
	}

}
