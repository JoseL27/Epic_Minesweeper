package engine;

import java.awt.event.MouseEvent;

public class MouseListener implements java.awt.event.MouseListener {
    private int componentId;
    private static MouseListener listener = null;

    private MouseListener(){}

    public static MouseListener get(){
        if(listener == null){
            listener = new MouseListener();
        }

        return listener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println(e.getComponent().getName());
        Window.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Window.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Window.mouseExited(e);
    }
}
