package utils;


public class Constants {
    public static final String RESOURCES_PATH = "/home/jose/dev/Buscaminas/Buscaminas/resources";
    public static final int TICKS_PER_SECOND = 30;
    public static final int SCREEN_WIDTH = 1920;
    public static final int SCREEN_HEIGHT = 1080;
    public static final String SCREEN_TITLE = "Buscaminas";
    public static final int TILE_HEIGTH = 75;
    public static final int TOP_PANEL_HEIGHT = 100;
    public static final int DEFAULT_DIFFICULTY = 1;
    public static final int GAME_WIDTH = TILE_HEIGTH * 8 * DEFAULT_DIFFICULTY;
    public static final int GAME_HEIGHT = TOP_PANEL_HEIGHT + GAME_WIDTH;
    public static final int GAME_STAGE_PLAYING = 0;
    public static final int GAME_STAGE_VICTORY = 1;

}
