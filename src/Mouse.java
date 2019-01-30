import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Mouse implements MouseListener, MouseMotionListener
{

    private static int x = 0;
    private static int y = 0;
    private static boolean buttons[] = new boolean[5];

    /*
     * Class methods
     */
    public static int getX()
    {
        return x;
    }

    public static int getY()
    {
        return y;
    }

    public static boolean isButtonDown(int button)
    {
        if (button > 0 && button < buttons.length)
        {
            return buttons[button];
        }
        return false;
    }

    /*
     * Listeners
     */
    public void mouseDragged(MouseEvent e)
    {
        buttons[e.getButton()] = true;
        x = e.getX();
        y = e.getY();
    }

    public void mouseMoved(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
    }

    public void mouseClicked(MouseEvent e)
    {
    }

    public void mouseEntered(MouseEvent e)
    {
    }

    public void mouseExited(MouseEvent e)
    {
    }

    public void mousePressed(MouseEvent e)
    {
        buttons[e.getButton()] = true;
    }

    public void mouseReleased(MouseEvent e)
    {
        buttons[e.getButton()] = false;
    }

}

