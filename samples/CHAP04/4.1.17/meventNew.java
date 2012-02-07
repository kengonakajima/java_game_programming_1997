import java.awt.*;
import java.awt.event.*;        // (*1)

// (*2)
public class meventNew extends java.applet.Applet implements MouseListener 
{
    public void init()
    {
        addMouseListener( this );  // (*3)
    }
    public void mouseClicked( MouseEvent ev ){  // (*4)
    }
    public void mouseEntered( MouseEvent ev ){  // (*4)
    }
    public void mouseExited( MouseEvent ev ){   // (*4)
    }
    public void mousePressed( MouseEvent ev ){  // (*4)
        getGraphics().drawOval( ev.getX(), ev.getY(), 20, 20 ); // (*5)
    }
    public void mouseReleased( MouseEvent ev ){  // (*4)
        getGraphics().drawRect( ev.getX(), ev.getY(), 20, 20 ); // (*5)
    }
}