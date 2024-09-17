// NOTICE: The code is not completed, it can't work!!

package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {

    public static class Position {
        int x = 0;
        int y = 0;
        public Position(int xi, int yi) {
            x = xi;
            y = yi;
        }
    }

    /** Calculate the width of the ith row.
     * @param s The size of the hexagon.
     * @param i The row number where i = 0 is the top.
     * @return
     */
    public static int hexWidthCalculate(int s, int i) {
        int width = 0;
        if (i < s) {
            width = s + 2 * i;
        }
        else {
            width = 3 * s - i - 1;
        }
        return width;
    }

    /** Calculate the space before the ith row.
     * @param s The size of the hexagon.
     * @param i The row number where i = 0 is the top.
     * @return
     */
    public static int hexLineBeginPosX(int s, int i) {
        if (i >= s) {
            i = 2 * s - i - 1;
        }
        return -i;
    }

    public static void drawHexagon(TETile[][] world, Position p, int length, TETile t) {
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            world[p.x + i][p.y] = TETile.colorVariant(t, 32, 32, 32, random);
        }
    }

    public static void addHexagon(TETile[][] world, Position p, int s, TETile t) {
        if (s < 2) {
            throw new IllegalArgumentException("size must be at least 2.");
        }

        p.y -= 2 * s - 1;
        for (int i = 0; i < 2 * s; i++) {
            int rowY = p.y + i;
            int rowX = p.x + hexLineBeginPosX(s, i);
            int length = hexWidthCalculate(s, i);
            Position startP = new Position(rowX, rowY);
            drawHexagon(world, startP, length, t);
        }
    }

    public static Position findPosition(Position currentPos, int s) {
        if (currentPos.y == 5) {
            return new Position(7, 8);
        }
        int newX = currentPos.x + 3 * s + 1;
        int newY = currentPos.y;
        if (newX > 30) {
            newX = newX - 30;
            newY = newY + s;
        }
        if (newY == 29) {
            return new Position(12, 29);
        }
        return new Position(newX, newY);
    }

    public static void main(String[] args) {
        int WIDTH = 30;
        int HEIGHT = 30;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        int size = 3;
        Position startPos = new Position(10, 10);
        for (int i = 0; i < 5; i++) {
            addHexagon(world, startPos, size, Tileset.FLOWER);
            startPos = findPosition(startPos, size);
        }

        ter.renderFrame(world);
    }
}

