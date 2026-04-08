package gameElements;

import javax.swing.*;

import engine.MouseListener;
import utils.Constants;


public class Tile extends JLabel {
    private int id;
    private boolean hasBomb;
    private boolean isRevealed;
    private boolean isMarked;

    /*
    * Posible solucion para el listener:
    * tener en tile una lista con las casillas adyacentes, inicializarla desde field usando getAdjacentTiles
     */
    public Tile(int id){
        this.id = id;

        this.setName("Tile " + id);
        this.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "tile.png"));

        this.addMouseListener(MouseListener.get());
    }

    public boolean addBomb(){
        boolean result = !hasBomb;
        hasBomb = true;
        return result;
    }

    public void reveal(int adjacentBombs){
        ImageIcon newIcon;

        if (hasBomb){
            if (adjacentBombs == -1){
                newIcon = new ImageIcon(Constants.RESOURCES_PATH + "clicked_bomb.png");
            }else{
                newIcon = new ImageIcon(Constants.RESOURCES_PATH + "bomb.png");
            }
        }else if(adjacentBombs == 0){
            newIcon = new ImageIcon(Constants.RESOURCES_PATH + "casillaLibre.png");
        }else{
            newIcon = new ImageIcon(Constants.RESOURCES_PATH + "casilla" + adjacentBombs + ".png");
        }

        this.setIcon(newIcon);
        isRevealed = true;
    }

    public void mark(){
        if (!isRevealed) {
            isMarked = true;
            ImageIcon newIcon = new ImageIcon(Constants.RESOURCES_PATH + "markedTile.png");
            this.setIcon(newIcon);
        }
    }

    public void unmark(){
        if (!isRevealed) {
            isMarked = false;
            ImageIcon newIcon = new ImageIcon(Constants.RESOURCES_PATH + "tile.png");
            this.setIcon(newIcon);
        }
    }

    public boolean hasBomb(){
        return hasBomb;
    }

    public boolean isRevealed(){
        return isRevealed;
    }

    public boolean isMarked(){
        return isMarked;
    }

    public int getId(){
        return id;
    }
}
