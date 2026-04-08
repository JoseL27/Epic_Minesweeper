package scenes;

import engine.*;
import engine.Window;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MenuScene extends Scene {
    JLabel backgroundLabel;
    JLabel titleLabel;
    JLabel playLabel;
    JLabel mensajeJugar;
    boolean animationDarkening;


    public MenuScene(){
//        System.out.println("En la escena de menu");
        this.setName("Escena de menu");
        this.setLayout(null);
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(KeyListener.get());
        this.setPreferredSize(new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT - Constants.TOP_PANEL_HEIGHT));

        backgroundLabel = new JLabel();

        backgroundLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "background.png"));
        backgroundLabel.setSize(new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT - Constants.TOP_PANEL_HEIGHT));

        titleLabel = new JLabel();

        titleLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "titulo_test.png"));
        titleLabel.setSize(new Dimension(400, 86));
        titleLabel.setOpaque(false);
        titleLabel.setBounds(Constants.GAME_WIDTH / 2 - titleLabel.getWidth() / 2,
                Constants.GAME_HEIGHT / 6 - titleLabel.getHeight() / 2,
                   titleLabel.getWidth(), titleLabel.getHeight());

        playLabel = new JLabel();

        playLabel.setName("Boton jugar");
        playLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "play.png"));
        playLabel.setSize(new Dimension(200, 111));
        playLabel.setBounds(Constants.GAME_WIDTH / 2 - playLabel.getWidth() / 2,
                (Constants.GAME_HEIGHT - Constants.TOP_PANEL_HEIGHT) / 2 - playLabel.getHeight() / 2,
                playLabel.getWidth(), playLabel.getHeight() );
        playLabel.addMouseListener(MouseListener.get());

        mensajeJugar = new JLabel();

        mensajeJugar.setFont(new Font("Dubai medium" , Font.PLAIN, 60));
        mensajeJugar.setForeground(Color.white);
        mensajeJugar.setBackground(Color.black);
        mensajeJugar.setOpaque(true);
        mensajeJugar.setText("Pulse espacio para jugar");
        mensajeJugar.setVerticalAlignment(JLabel.CENTER);
        mensajeJugar.setHorizontalAlignment(JLabel.CENTER);
        mensajeJugar.setSize(new Dimension(Constants.GAME_WIDTH, 100));
        mensajeJugar.setBounds(Constants.GAME_WIDTH / 2 - mensajeJugar.getWidth() / 2,
                (Constants.GAME_HEIGHT - Constants.TOP_PANEL_HEIGHT) / 2 - mensajeJugar.getHeight() / 2,
                    Constants.GAME_WIDTH, 100);

        this.add(titleLabel);
        this.add(playLabel);
        this.add(backgroundLabel);
//        this.add(mensajeJugar);
        animationDarkening = true;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        g.setColor(Color.white);
    }

    private void textAnimation(){
        int changeFactor = animationDarkening ? -5 : 5;

        mensajeJugar.setForeground(new Color(mensajeJugar.getForeground().getRed() + changeFactor,
            mensajeJugar.getForeground().getGreen() + changeFactor,
            mensajeJugar.getForeground().getBlue() + changeFactor));

        if (mensajeJugar.getForeground().equals(Color.black)){
            animationDarkening = false;
        }
        if (mensajeJugar.getForeground().equals(Color.white)){
            animationDarkening = true;
        }
    }

    public void update(float dt){
//        System.out.println(1.0 / dt + " FPS");

        requestFocusInWindow();
        textAnimation();

    }

    public void keyPressed(KeyEvent event){
        switch (event.getKeyCode()){
            case KeyEvent.VK_SPACE -> Window.get().changeScene(1);
        }
    }

    public void keyReleased(KeyEvent event){}

    public void mouseClicked(MouseEvent e){
//        System.out.println("Desde gamescene");
        if (e.getComponent().getName().equals("Boton jugar")){
//            System.out.println("Pulsado el boton");
            Window.get().changeScene(1);
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {
        if (e.getComponent().getName().equals("Boton jugar")) {
            playLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "play_hover.png"));
        }
    }

    public void mouseExited(MouseEvent e) {
        if (e.getComponent().getName().equals("Boton jugar")) {
            playLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "play.png"));
        }
    }
}
