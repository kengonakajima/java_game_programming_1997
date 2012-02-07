/*
 *	Video Game Applet "31"
 *				Programmed by Chikara YOSHIDA 1996
 */

import java.applet.Applet;
import java.awt.*;
import java.lang.*;
import java.util.Date;

public class Ball extends Applet implements Runnable {
    Thread thread;
    double bx[], by[];
    double ox[], oy[];
    double vx[], vy[];
    Math m;
    System sys;
    int score = 0;
    int titleflg = 1;
    static int ballnum = 31;

    final static int ballmax = 31;
    final static int ballsize = 6;
    final static int ballsize1 = 5;
    final static double ballspeed = 6.0;
    final static int mysize = 16;
    final static int mysize1 = 15;

    final static int my_xmin = 0;
    final static int my_ymin = 0;
    int my_xmax;
    int my_ymax;

    final static int b_xmin = 0;
    final static int b_ymin = 0;
    int b_xmax;
    int b_ymax;


    public void calcSize() {
	my_xmax = size().width - mysize;
	my_ymax = size().height - mysize;
	b_xmax = size().width - ballsize;
	b_ymax = size().height - ballsize;
    }

    public void newgame() {
	int i;
	for (i = 0; i < ballmax; i++) {
	    bx[i] = by[i] = 0;
	    vx[i] = vy[i] = -ballspeed;
	}
	bx[ballmax] = by[ballmax] = 300;
	ballnum = 1;
	score = 0;
	calcSize();

	Graphics g = getGraphics();
	if (g != null) {
		g.clearRect(0, 0, size().width, size().height);
		g.setFont(new Font("Helvetica",Font.PLAIN, 12));
	}
	titleflg = 0;
    }
    
    public void init() {
	bx = new double[ballmax+1];
	by = new double[ballmax+1];
	ox = new double[ballmax+1];
	oy = new double[ballmax+1];
	vx = new double[ballmax];
	vy = new double[ballmax];
	
	setBackground(Color.black);
	resize(320, 240);
	newgame();
	titleflg = 1;
    }

    public void start() {
	if (thread == null) {
	    thread = new Thread(this);
	    thread.start();
	}
    }
    
    public void stop() {
	if (thread != null) {
	    thread.stop();
	    thread = null;
	}
    }
    
    Color c[] = {Color.black, 
		   Color.white, Color.red, Color.pink, Color.blue, 
		   Color.yellow, Color.green, Color.magenta, Color.cyan};

    public void paint(Graphics g) {
	int i, j;

	if (titleflg != 0) {
	    g.setFont(new Font("Helvetica",Font.BOLD, 30));
	    g.setColor(Color.white);
	    g.drawString("31/Java", my_xmax/2 - 50, 100);

	    g.setFont(new Font("Helvetica",Font.PLAIN, 12));
	    g.clearRect(0, 0, 100, 29);
	    g.drawString("SCORE "+ score * 10, 0, 9);
	}
    }

    public void move(Graphics g) {
	int i, j;
	g.setColor(Color.white);

	g.clearRect((int)ox[ballmax], (int)oy[ballmax], mysize+1, mysize+1);
	g.drawRect((int)bx[ballmax], (int)by[ballmax], mysize, mysize);
	ox[ballmax] = bx[ballmax]; oy[ballmax] = by[ballmax];

	for (i = 0; i < ballmax; i++) {
	    j = (i * 8) % ballmax;
	    if (j >= ballnum) continue;

	    if (i % 4 == 0) g.setColor(c[i / 4 + 1]);
	    g.clearRect((int)ox[j], (int)oy[j], ballsize, ballsize);
	    g.fillOval((int)bx[j], (int)by[j], ballsize1, ballsize1);
	    ox[j] = bx[j]; oy[j] = by[j];

	    /* Collision Detection */
	    double dx = -bx[ballmax] + bx[j];
	    double dy = -by[ballmax] + by[j];
	    if (dx > -ballsize1 && dx < mysize1 &&
		dy > -ballsize1 && dy < mysize1) {
		/* GAME OVER */
		titleflg = 1;
		return;
	    }
	}

    }

    public void run() {
	int i;

	while (true) {
	    if (titleflg != 0) {
		paint(getGraphics());
		try {
		  Thread.sleep(180); 
		} catch (InterruptedException e){}
		continue;
	    }

	    try { Thread.sleep(20); } catch (InterruptedException e){}
	    score ++;
	    if (score % 100 == 0 && ballnum < ballmax) ballnum++;
	    for (i = 0; i < ballnum; i++) {
		bx[i] += vx[i];
		by[i] += vy[i];
		if ((vx[i]<0 && bx[i]<0) || (vx[i]>0 && bx[i]>b_xmax)) {
		    vx[i] = -vx[i];
		    vy[i] += m.random() * 0.4 - 0.2;
		}
		if ((vy[i]<0 && by[i]<0) || (vy[i]>0 && by[i]>b_ymax)) {
		    vy[i] = -vy[i];
		    vx[i] += m.random() * 0.4 - 0.2;
		}
	    }
	    move(getGraphics());
	}
    }

    public boolean mouseMove(java.awt.Event evt, int x, int y) {
	x -= mysize / 2;
	y -= mysize / 2;
	if (x < my_xmin) bx[ballmax] = my_xmin;
	else if (x > my_xmax) bx[ballmax] = my_xmax;
	else bx[ballmax] = x;

	if (y < my_ymin) by[ballmax] = my_ymin;
	else if (y > my_ymax) by[ballmax] = my_ymax;
	else by[ballmax] = y;

	return true;
    }
    public boolean mouseDown(java.awt.Event evt, int x, int y) {
	if (titleflg != 0) newgame();
	return mouseMove(evt, x, y);
    }
}
