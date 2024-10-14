package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.SymbolDigraph;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;
public class Player {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 41;
    private int x;
    private int y;
    private TETile player;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.player = Tileset.PLAYER;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(char direction, TETile[][] world) {
        int newX = x;
        int newY = y;

        switch (direction) {
            case 'W' :
            case 'w' :
                newY += 1;
                break;
            case 'S' :
            case 's' :
                newY -= 1;
                break;
            case 'A' :
            case 'a' :
                newX -= 1;
                break;
            case 'D' :
            case 'd' :
                newX += 1;
                break;
        }

        if (newX >= 0 && newX < WIDTH && newY >= 0 && newY < HEIGHT && world[newX][newY] != Tileset.WALL && world[newX][newY] != Tileset.LOCKED_DOOR) {
            world[x][y] = Tileset.FLOOR;
            x = newX;
            y = newY;
            world[x][y] = Tileset.PLAYER;
        }
    }

    public void generatePlayer(TETile[][] world, Random RANDOM) {
        int startX, startY;
        do {
            startX = RANDOM.nextInt(WIDTH);
            startY = RANDOM.nextInt(HEIGHT);
        } while (world[startX][startY] != Tileset.FLOOR);
        x = startX;
        y = startY;
        world[x][y] = Tileset.PLAYER;
    }

    public void drawPlayer(TETile[][] world) {
        world[x][y] = Tileset.PLAYER;
    }

}
