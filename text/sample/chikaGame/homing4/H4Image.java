/*
 *	Homing-4 Applet(Lower Class)
 *			Programmed by Chikara YOSHIDA  1996
 */

import java.applet.Applet;
import java.awt.*;
import java.lang.*;

public class H4Image extends Canvas {
    Thread thread;
    int key[];
    double x1[][], y1[][], x2[][], y2[][];
    double vx[], vy[];
    Color linecol[], titleColor;
    int counter, oldcounter;
    int sizex, sizey;
    final static int colornum = 64;
    final static int linenum = 64;
    final static int shipnum = 2;
    final static int display_linenum = 5;
    final static double shipspeed = 0.18;

    double cx, cy;
    int titlemode = 1;
    int gameover = 0;
    int level = 0;
    int score = 0;
    Math m;

    /** Resize */
    private void setsize() {
	sizex = size().width;
	sizey = size().height;
    }

    /** Constructor */
    public H4Image(int defx, int defy) {
	x1 = new double[shipnum][linenum];
	y1 = new double[shipnum][linenum];
	x2 = new double[shipnum][linenum];
	y2 = new double[shipnum][linenum];
	vx = new double[shipnum];
	vy = new double[shipnum];
	linecol = new Color[colornum];
	key = new int[10];
	counter = 0;
	titleColor = new Color(30, 50, 100);

 	for (int i = colornum - 1; i >= 0; i -= 1) {
	    int re, bl, gr;
	    re = 255 - 20 * i; if (re < 0) re = 0;
	    gr = 255 - 16 * i; if (gr < 0) gr = 0;
	    bl = 255 - 8 * i; if (bl < 40) bl = 40;
	    linecol[i] = new Color(re, gr, bl);
	}

	resize(defx, defy);
	setBackground(Color.black);
	setsize();
    }
    
    /** Graphics Clear */
    private void clear() {
	Graphics g;
	int i, x, y;

	setsize();
	g = getGraphics();
	if (g == null) return;

	g.setColor(Color.black);
	g.fillRect(0, 0, sizex, sizey);

	/* Star Drawing */
	for (i = 0; i < 50; i++) {
	    x = (int) (m.random() * sizex);
	    y = (int) (m.random() * sizey);

	    g.setColor(linecol[i % 14]);
	    g.drawLine(x, y, x, y);
	}
    }

    /** Game Start */
    public void start() {
	int i, ship;

	setsize();

	for (ship = 0; ship < shipnum; ship++) {
	    for (i = 0; i < linenum; i++) {
		x1[ship][i] = y1[ship][i] = 0;
		x2[ship][i] = y2[ship][i] = 0;
	    }
	}
	counter = 0;
	score = 0;
	level = 0;
	x1[0][0] = x2[0][0] = 0;
	y1[0][0] = y2[0][0] = sizey - 1;
	x1[1][0] = x2[1][0] = sizex - 1;
	y1[1][0] = y2[1][0] = sizey - 1;
	vx[0] = 0.2;
	vy[0] =	vx[1] = vy[1] = -0.2;
	titlemode = 0;
	gameover = 0;
	clear();
    }

    /* Draw Main Window */
    public void paint(Graphics g) {
	int i, j;
	if (g == null) return;

	/*  Draw Title  */

	if (titlemode != 0) {
	    g.setFont(new Font("TimesRoman",Font.ITALIC, 30));
	    for (i = 10; i >= 0; i--) {
		if (i == 0) g.setColor(Color.white);
		else if (i == 15 - counter) g.setColor(linecol[i]);
		else if (i == 15 - oldcounter) g.setColor(titleColor);
		else continue;
		
		g.drawString("Homing 4", sizex / 2 - 70 + i * 2, 40 + i / 2);
	    }

	    g.setFont(new Font("Helvetica",Font.PLAIN, 12));
	    g.drawString("Hit S key to start", sizex / 2 - 45, 150);
	    g.setFont(new Font("Helvetica",Font.PLAIN, 8));
	    
	    return;
	}

	g.setColor(Color.black);
	g.fillRect(0, 0, 80, 9);
	g.setColor(Color.white);
	g.drawString("SCORE " + score*10, 0, 9);

	/*  Draw Game Scene */ 

 	for (i = display_linenum * 3 - 3; i >= 0; i -= 3) {
	    j =  counter - i;
	    if (j < 0) j += linenum;

	    g.setColor(linecol[i]);
	    for (int k = 0; k < shipnum; k++) {
		g.drawLine((int)x1[k][j], (int)y1[k][j], 
			   (int)x2[k][j], (int)y2[k][j]);
	    }
	}
    }

    public void pushKey(int key_no) {
	key[key_no] = 1;
    }

    public void releaseKey(int key_no) {
	key[key_no] = 0;
    }

    /** Move coordinate of my- and enemy-ship */

    private void calcspeed() {
	double enemyspeed, enemyspeed2, enemyVt;
	level = score / 100;
	enemyspeed = 0.20 + level * 0.03;
	enemyspeed2 = 0.08 + level * 0.02;
	enemyVt = 1.0 + level * 0.02;

	if (key[2] != 0) vy[0] += shipspeed;
	if (key[8] != 0) vy[0] -= shipspeed;
	if (key[4] != 0) vx[0] -= shipspeed;
	if (key[6] != 0) vx[0] += shipspeed;

	if (x1[0][counter] < x1[1][counter]) vx[1] -= enemyspeed;
	else vx[1] += enemyspeed;
	if (y1[0][counter] < y1[1][counter]) vy[1] -= enemyspeed;
	else vy[1] += enemyspeed;
	if (vx[1] < -enemyVt) vx[1] += enemyspeed2;
	if (vx[1] >  enemyVt) vx[1] -= enemyspeed2;
	if (vy[1] < -enemyVt) vy[1] += enemyspeed2;
	if (vy[1] >  enemyVt) vy[1] -= enemyspeed2;
    }

    /** Collision detection */

    final static double cor = 2;			/* boundary size */
    public void atari() {
	double dx, dy;

	dx = cx - x1[1][counter];
	dy = cy - y1[1][counter];
	if (cx < 0 || cy < 0 || cx > sizex || cy > sizey) gameover = 1;
	if (dx < cor && dx > -cor && dy < cor && dy > -cor) gameover = 1;
    }

    /** Game main processing routine */
    public void move() {
	int i;

	oldcounter = counter;
	calcspeed();
	setsize();
	counter++;
	if (counter >= linenum) counter = 0;

	if (titlemode != 0) {
	    paint(getGraphics());
	    return;
	}

	if (gameover != 0) {
	    gameover++;
	    if (gameover >= 30) titlemode = 1;
	    for (i = 0; i < shipnum; i++) {
		double p = m.random() * 6.28318;
		double r1 = m.sqrt(gameover + 2.0) * 10.0;
		double r2 = m.sqrt(gameover - 2.0) * 10.0;

		if (gameover < 15) {
		    x1[i][counter] = cx + m.cos(p) * r1;
		    y1[i][counter] = cy + m.sin(p) * r1;
		    x2[i][counter] = cx + m.cos(p) * r2;
		    y2[i][counter] = cy + m.sin(p) * r2;
		} else {
		    x1[i][counter] = y1[i][counter] = 
		    x2[i][counter] = y2[i][counter] = 0;
		}
	    }
	    
	    paint(getGraphics());
	    return;
	}
	if (score == 1) clear();
	score++;
	for (i = 0; i < shipnum; i++) {
	    x1[i][counter] = x1[i][oldcounter] + vx[i];
	    y1[i][counter] = y1[i][oldcounter] + vy[i];
	    x2[i][counter] = x1[i][counter] - vx[i] * 4;
	    y2[i][counter] = y1[i][counter] - vy[i] * 4;
	}
	cx = x1[0][counter]; cy = y1[0][counter];
	atari();
	paint(getGraphics());
    }
}

