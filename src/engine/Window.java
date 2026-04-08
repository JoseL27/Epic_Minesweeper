package engine;

import scenes.GameScene;
import scenes.MenuScene;
import scenes.Scene;
import utils.Constants;
import utils.Time;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Window extends JFrame implements Runnable{

    private Graphics2D graphics;
    private boolean running;

    private static Window window = null;
    private static Scene currentScene = null;

    private Window(){
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(true);
        this.setVisible(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static Window get(){
        if (window == null){
            window = new Window();
        }

        return window;
    }

    public void changeScene(int scene){
        if (currentScene != null){
            window.remove(currentScene);
        }

        switch(scene){
            case 0 -> currentScene = new MenuScene();
            case 1 -> currentScene = new GameScene();
//            default -> System.out.println("Escena desconocida, indice " + scene);
        }

        window.add(currentScene);
        window.revalidate();
        window.repaint();
        currentScene.requestFocusInWindow();
        window.pack();
    }

    public void setColor(Color color){
        graphics.setColor(color);
        graphics.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    public void run(){
//        System.out.println("Ventana inicializada");

        init();
        loop();

        this.dispose();
    }


    public void init(){
        //Centra la pantalla
        Point esquinaSuperiorIzquierda = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        esquinaSuperiorIzquierda.move((int)(esquinaSuperiorIzquierda.getX() - Constants.GAME_WIDTH / 2.0), (int)(esquinaSuperiorIzquierda.getY() - Constants.GAME_HEIGHT / 2.0));

        changeScene(0);
        this.setVisible(true);
        this.setResizable(false);
        this.setLocation(esquinaSuperiorIzquierda);
        this.pack();

        graphics = (Graphics2D)this.getGraphics();
    }

    public void loop(){
        float startTime = Time.getTime();
        float endTime;
        float dt = 0.0f;
        float secondsPerTick = 1.0f / Constants.TICKS_PER_SECOND;
        running = true;

        while (running){
            if (dt >= secondsPerTick) {
                currentScene.update(dt);
                dt -= secondsPerTick;
            }

            endTime = Time.getTime();
            dt += endTime - startTime;
            startTime = endTime;
        }


    }

    public void stop(){
        window.running = false;
    }

    public static void keyPressed(KeyEvent event){
        currentScene.keyPressed(event);
    }

    public static void keyReleased(KeyEvent event){
        currentScene.keyReleased(event);
    }

    public static void mouseClicked(MouseEvent e){
//        System.out.println("Desde window");
        currentScene.mouseClicked(e);
    }

    public static void mouseEntered(MouseEvent e){
        currentScene.mouseEntered(e);
    }

    public static void mouseExited(MouseEvent e){
        currentScene.mouseExited(e);
    }


}
