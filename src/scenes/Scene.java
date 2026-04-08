package scenes;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class Scene extends JPanel {

    public Scene(){

    }

    public abstract void update(float dt);
    public abstract void keyPressed(KeyEvent event);
    public abstract void keyReleased(KeyEvent event);
    public abstract void mouseClicked(MouseEvent e);
    public abstract void mousePressed(MouseEvent e);
    public abstract void mouseReleased(MouseEvent e);
    public abstract void mouseEntered(MouseEvent e);
    public abstract void mouseExited(MouseEvent e);
}
