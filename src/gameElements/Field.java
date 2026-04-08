package gameElements;

import engine.MouseListener;
import utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Field extends JPanel {
    private static Tile[][] tiles;
    private int difficulty;
    private int bombNumber;
    private static boolean hasLost;
    private static int remainingTiles;
    private static int marksLeft;


    public Field(int difficulty, int gameStage){
        this.setName("Field");
        this.setBackground(Color.red);
        this.setPreferredSize(new Dimension(Constants.GAME_WIDTH, Constants.GAME_HEIGHT - Constants.TOP_PANEL_HEIGHT));

        switch (gameStage){
            case Constants.GAME_STAGE_PLAYING -> {
                FlowLayout layout = new FlowLayout();
                layout.setHgap(0);
                layout.setVgap(0);
                this.setLayout(layout);

                this.difficulty = difficulty;

                initializeField();
                loadBombs();
                remainingTiles = getTileNumber();
                marksLeft = bombNumber;
                hasLost = false;
            }
            case Constants.GAME_STAGE_VICTORY -> {
                this.setLayout(new BorderLayout());

                JLabel victoryLabel = new JLabel();
                victoryLabel.setName("Panel de victoria");
                victoryLabel.setIcon(new ImageIcon(Constants.RESOURCES_PATH + "victory_panel.png"));
                victoryLabel.setSize(this.getSize());
                victoryLabel.setBounds(0, 0, this.getWidth(), this.getHeight());

                this.add(victoryLabel);
            }
        }
    }

    public Field(int gameStage){
        this(Constants.DEFAULT_DIFFICULTY, gameStage);
    }

    private void initializeField(){
        tiles = new Tile[8 * difficulty][8 * difficulty];

        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++){
                tiles[i][j] = new Tile(i * tiles[i].length + j);
                this.add(tiles[i][j]);
            }
        }
    }

    private void loadBombs(){
        int bombId;
        switch (difficulty){
            case 1 -> bombNumber = 10;
            case 2 -> bombNumber = 40;
            case 3 -> bombNumber = 99;
        }
        for(int i = 0; i < bombNumber; i++){
            do {
                bombId = (int) (Math.random() * getTileNumber());
            } while (!tiles[getRow(bombId)][getColumn(bombId)].addBomb());
        }
    }


    public static void revealTile(int tileId){
        LinkedList<Tile> adjacentTiles = getAdjacentTiles(tileId);
        Tile tile = getTile(tileId);
        int adjacentBombs = 0;


        if (tile.hasBomb()) {
            // Acabar la partida
            if (!tile.isRevealed()) {
                tile.reveal(-1);
                remainingTiles--;
            }
            hasLost = true;
        } else {
            for (int i = 0; i < adjacentTiles.size(); i++) {
                if (adjacentTiles.get(i).hasBomb()) {
                    adjacentBombs++;
                }
            }

            if (!tile.isRevealed() && !tile.isMarked()) {
                tile.reveal(adjacentBombs);
                remainingTiles--;
            }

            if (adjacentBombs == 0) {
                for (int i = 0; i < adjacentTiles.size(); i++) {
                    Tile currentTile = adjacentTiles.get(i);
                    if (!currentTile.isRevealed() && !currentTile.isMarked()) {
                        revealTile(currentTile.getId());
                    }
                }
            }
        }
    }

    public void revealBombs(){
        for (int i = 0; i < tiles.length; i++){
            for (int j = 0; j < tiles[i].length; j++){
                Tile tile = tiles[i][j];
                if (tile.hasBomb() && !tile.isRevealed()){
                    tile.reveal(0);
                }
            }
        }
    }

    public static LinkedList<Tile> getAdjacentTiles(int tileId){
        LinkedList<Tile> result = null;
        if (isWithinBounds(tileId)){
            result = new LinkedList<>();
            for (int i = -1; i < 2; i++){
                for(int j = -1; j < 2; j++){
                    Tile tile = getTile(getRow(tileId) + i, getColumn(tileId) + j);
                    if (tile != null && tile.getId() != tileId){
                        result.add(tile);
                    }
                }
            }
        }

        return result;
    }

    public static Tile getTile(int tileId){
        Tile result = null;

        if (isWithinBounds(tileId)) {
            result = tiles[getRow(tileId)][getColumn(tileId)];
        }

        return result;
    }

    public static Tile getTile(int row, int column){
        Tile result = null;
        if (isWithinBounds(row, column)){
            result = tiles[row][column];
        }
        return result;
    }

    private static boolean isWithinBounds(int tileId){
        boolean result = true;

        if (tileId < 0 || tileId >= getTileNumber()){
            result = false;
//            System.out.println("Error, casilla fuera de los limites");
        }

        return result;
    }

    private static boolean isWithinBounds(int row, int column){
        return row >= 0 && row < tiles.length && column >= 0 && column < tiles[row].length;
    }

    private static int getRow(int tileId){
        return tileId / tiles[0].length;
    }

    private static int getColumn(int tileId){
        return tileId % tiles[0].length;
    }

    private static int getTileNumber(){
        return tiles.length * tiles[0].length;
    }

    public int getRemainingTiles(){
        return remainingTiles;
    }

    public int getBombNumber(){
        return bombNumber;
    }

    public static int getMarksLeft(){
        return marksLeft;
    }

    public static boolean hasLost(){
        return hasLost;
    }


    public static void mouseClicked(MouseEvent e){
        Tile tile = (Tile) e.getComponent();
//        System.out.println("Desde field");

        switch(e.getButton()){
            case MouseEvent.BUTTON1 -> {
                if (!tile.isMarked()){
                    revealTile(tile.getId());
                }
            }
            case MouseEvent.BUTTON3 -> {
                if (tile.isMarked()){
                    tile.unmark();
                    marksLeft++;
                }else {
                    if (marksLeft > 0){
                        tile.mark();
                        marksLeft--;
                    }
                }
            }
        }

    }

}
