package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

import java.io.Serializable;

public class GameState implements Serializable{
    private static final long serialVersionUID = 1L;
    private String[][] worldTiles;
    private long randomSeed;
    private int playerX;
    private int playerY;

    public GameState(TETile[][] world, long randomSeed, int playerX, int playerY) {
        this.worldTiles = new String[world.length][world[0].length];
        for (int x = 0; x < world.length; x++) {
            for (int y = 0; y < world[0].length; y++) {
                worldTiles[x][y] = world[x][y].description();
            }
        }
        this.randomSeed =randomSeed;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    public TETile[][] getWorld(TETile[][] world) {
        for (int x = 0; x < worldTiles.length; x++) {
            for (int y = 0; y < worldTiles[0].length; y++) {
                world[x][y] = descriptionToTile(worldTiles[x][y]);  // 通过描述还原图块
            }
        }
        return world;
    }

    private TETile descriptionToTile(String description) {
        switch (description) {
            case "nothing":
                return Tileset.NOTHING;
            case "floor":
                return Tileset.FLOOR;
            case "wall":
                return Tileset.WALL;
            case "player":
                return Tileset.PLAYER;
            case "locked door":
                return Tileset.LOCKED_DOOR;
            default:
                return Tileset.NOTHING;  // 默认返回
        }
    }

    public long getRandomSeed() {
        return randomSeed;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
}
