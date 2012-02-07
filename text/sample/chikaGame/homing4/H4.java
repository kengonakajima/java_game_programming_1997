/*
 *	Homing-4 Applet(Upper Class)
 *			Programmed by Chikara YOSHIDA  1996
 */

import java.applet.Applet;
import java.awt.*;
 
public class H4 extends Applet implements Runnable {
    Thread thread;
    H4Image img;

    /** Initialize */
    public void init() {
	int i;
	img = new H4Image(300, 200);

	add("North", new Label("Homing Game Applet", Label.CENTER));
	add("Center", img);
    }

    /** Applet Start */
    public void start() {
	if (thread == null) {
	    thread = new Thread(this);
	    thread.start();
	}
	if (img == null) return;
	img.releaseKey(2);
	img.releaseKey(4);
	img.releaseKey(6);
	img.releaseKey(8);
    }

    /** Applet Stop */
    public void stop() {
	if (thread != null) {
	    thread.stop();
	    thread = null;
	}
    }

    /* Main Routine */
    public void run() {
	while (true) {
	    try {
		Thread.sleep(30);
	    } catch (InterruptedException e) { 
		break;
	    }
	    img.move();
	}
    }

    /* Key Down Event Handler */
    public boolean keyDown(Event e, int key) {
	if (img == null) return true;
	switch(key) {
	  case 's':	    img.start(); break;
	  case 'u':	    img.pushKey(8); img.releaseKey(2); break;
	  case 'h':	    img.pushKey(4); img.releaseKey(6); break;
	  case 'k':	    img.pushKey(6); img.releaseKey(4); break;
	  case 'm':	    img.pushKey(2); img.releaseKey(8); break;
	}
	return true;    
    }

    /* Key Up Event Handler */
    public boolean keyUp(Event e, int key) {
	if (img == null) return true;
	switch(key) {
	  case 'u':	    img.releaseKey(8); break;
	  case 'h':	    img.releaseKey(4); break;
	  case 'k':	    img.releaseKey(6); break;
	  case 'm':	    img.releaseKey(2); break;
	}
	return true;
    }
}
