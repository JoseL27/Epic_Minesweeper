package engine;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyListener extends KeyAdapter {

    private static KeyListener listener = null;

    private KeyListener(){}

    public static KeyListener get(){
        if (listener == null){
            listener = new KeyListener();
        }

        return listener;
    }

    public void keyPressed(KeyEvent event){
//        System.out.println(event.getKeyCode());
        Window.keyPressed(event);
    }

    public void keyReleased(KeyEvent event){
        Window.keyReleased(event);

    }
}
