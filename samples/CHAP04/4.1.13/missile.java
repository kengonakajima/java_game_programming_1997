import java.awt.*;

class missile extends enemyClass    // このextendsが重要。
{
    missile(int x , int y)
    {
        super( x, y, 10, 30, 5, 10 );
        dx = 0;
        dy = 5;
    }
    public void drawThis(Graphics g)
    {
        g.fillRect( x,y,xsiz,ysiz );
    }
}