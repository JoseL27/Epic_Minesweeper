package scenes;

import engine.KeyListener;
import engine.MouseListener;
import engine.Window;
import gameElements.Field;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameScene extends Scene {

    private Field field;
    private JLayeredPane topPanel;
    private JLabel marksLabel;
    private JLabel chronoLabel;
    private JButton resetButton;
    private int difficulty;
    private int ticksElapsed;
    private boolean gameIsRunning;

    public GameScene(){
//        System.out.println("En la escena de juego");
        this.setName("Escena de juego");
        this.setLayout(new BorderLayout());
        this.setBackground(Color.blue);
        this.setFocusable(true);
        this.addKeyListener(KeyListener.get());
        this.setPreferredSize(new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT));

        this.addMouseListener(MouseListener.get());

        loadTopPanel();
        loadNewField(Constants.GAME_STAGE_PLAYING);
        repaint();

        startGame();
    }

    @Override
    public void update(float dt) {

        if (gameIsRunning) {
            updateChrono();
            updateMarks();

            if (Field.hasLost()){
                endGame(false);
            }else if (field.getRemainingTiles() <= field.getBombNumber()) {
                endGame(true);
            }
        }

        requestFocus();
        field.requestFocus();
   }

    public void startGame(){
        gameIsRunning = true;
        resetChrono();
    }

    public void endGame(boolean isVictory){
        gameIsRunning = false;

        if (isVictory){
            loadNewField(Constants.GAME_STAGE_VICTORY);
//            System.out.println("Victoria!");
            gameIsRunning = false;
        }else{
            field.revealBombs();
//            System.out.println("Derrota");
            gameIsRunning = false;
        }
    }

    private void updateChrono(){
        ticksElapsed++;
        if (ticksElapsed >= Constants.TICKS_PER_SECOND){
            ticksElapsed = 0;
            chronoLabel.setText(String.valueOf(Integer.parseInt(chronoLabel.getText()) + 1));
        }
    }

    private void resetChrono(){
        ticksElapsed = 0;
        chronoLabel.setText("0");
    }

    private void updateMarks(){
        marksLabel.setText(String.valueOf(field.getMarksLeft()));
        topPanel.remove(marksLabel);
        topPanel.add(marksLabel);
    }


    private void loadTopPanel(){
        topPanel = new JLayeredPane();
        topPanel.setPreferredSize(new Dimension(Constants.GAME_WIDTH, Constants.TOP_PANEL_HEIGHT));
        topPanel.setLayout(null);

        JLabel backgroundCenterLabel =  new JLabel();
        backgroundCenterLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "panel.png"));
        backgroundCenterLabel.setSize(new Dimension(1000, 100));

        JLabel backgroundLeftLabel = new JLabel();
        backgroundLeftLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "panel_left.png"));
        backgroundLeftLabel.setBounds(0, 0, 100, Constants.TOP_PANEL_HEIGHT);


        JLabel backgroundRightLabel = new JLabel();
        backgroundRightLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "panel_right.png"));
        backgroundRightLabel.setBounds(Constants.GAME_WIDTH - 100, 0, 100, Constants.TOP_PANEL_HEIGHT);


        marksLabel = new JLabel();
        marksLabel.setText(String.valueOf(Field.getMarksLeft()));
        marksLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "flag.png"));
        marksLabel.setSize(new Dimension(200, 75));
        marksLabel.setFont(new Font("Dubai medium" , Font.PLAIN, 70));
        marksLabel.setIconTextGap(15);
        marksLabel.setForeground(Color.white);
        marksLabel.setOpaque(false);
        marksLabel.setHorizontalAlignment(JLabel.CENTER);
        marksLabel.setVerticalAlignment(JLabel.CENTER);
        marksLabel.setHorizontalTextPosition(JLabel.RIGHT);
        marksLabel.setVerticalTextPosition(JLabel.CENTER);
        marksLabel.setBounds(Constants.GAME_WIDTH / 4 - (marksLabel.getWidth() / 2),
                (Constants.TOP_PANEL_HEIGHT / 2) - (marksLabel.getHeight() / 2),
                marksLabel.getWidth(), marksLabel.getHeight());

        chronoLabel = new JLabel();
        chronoLabel.setText("0");
        chronoLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "chrono.png"));
        chronoLabel.setSize(new Dimension(200, 75));
        chronoLabel.setFont(new Font("Dubai medium" , Font.PLAIN, 70));
        chronoLabel.setIconTextGap(20);
        chronoLabel.setForeground(Color.white);
        chronoLabel.setOpaque(false);
        chronoLabel.setHorizontalAlignment(JLabel.CENTER);
        chronoLabel.setVerticalAlignment(JLabel.CENTER);
        chronoLabel.setHorizontalTextPosition(JLabel.RIGHT);
        chronoLabel.setVerticalTextPosition(JLabel.CENTER);
        chronoLabel.setBounds(Constants.GAME_WIDTH / 4 * 3 - (chronoLabel.getWidth() / 2),
                (Constants.TOP_PANEL_HEIGHT / 2) - (chronoLabel.getHeight() / 2),
                chronoLabel.getWidth(), chronoLabel.getHeight());


        resetButton = new JButton();
        resetButton.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "reset.png"));
        resetButton.setBorder(BorderFactory.createEmptyBorder());
        resetButton.setSize(new Dimension(75, 75));
        resetButton.setOpaque(false);
        resetButton.addActionListener(e -> {
//            System.out.println("Boton pulsado");
            loadNewField(Constants.GAME_STAGE_PLAYING);
            startGame();
        });
        resetButton.setBounds(Constants.GAME_WIDTH / 2 - resetButton.getWidth() / 2,
                Constants.TOP_PANEL_HEIGHT / 2 - resetButton.getHeight() / 2,
                resetButton.getWidth(), resetButton.getHeight());


        topPanel.add(backgroundCenterLabel, JLayeredPane.DEFAULT_LAYER);
        topPanel.add(backgroundLeftLabel, JLayeredPane.PALETTE_LAYER);
        topPanel.add(backgroundRightLabel, JLayeredPane.PALETTE_LAYER);
        topPanel.add(chronoLabel, JLayeredPane.MODAL_LAYER);
        topPanel.add(marksLabel, JLayeredPane.MODAL_LAYER);
        topPanel.add(resetButton, JLayeredPane.MODAL_LAYER);

        this.add(topPanel, BorderLayout.NORTH);
    }

    private void loadNewField(int stage){
        if (!(field == null)){
            this.remove(field);
        }
        field = new Field(stage);

        this.add(field, BorderLayout.CENTER);

        startGame();
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Window.get().changeScene(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {

    }


    public void mouseClicked(MouseEvent e){
//        System.out.println("Desde gamescene");
        if (gameIsRunning && e.getComponent().getName().contains("Tile")){
            Field.mouseClicked(e);
        }
    }

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}
