package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class LockedDoor {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 41;
    public void generateLockedDoor(Random RANDOM, TETile[][] world) {
        int doorX, doorY;
        do {
            doorX = RANDOM.nextInt(WIDTH - 2) + 1;
            doorY = RANDOM.nextInt(HEIGHT- 2) + 1;
        } while (!isLegal(doorX, doorY, world) || world[doorX][doorY] != Tileset.WALL);

        world[doorX][doorY] = Tileset.LOCKED_DOOR;
    }

    public boolean isLegal(int x, int y, TETile[][] world) {
        int wallCount = 0, nothingCount = 0, floorCount = 0;
        int[][] pos = {{x - 1, y - 1}, {x - 1, y + 1}, {x + 1, y - 1}, {x + 1, y + 1}};
        for (int[] p : pos) {
            if (world[p[0]][p[1]] == Tileset.WALL) {
                wallCount++;
            }
            if (world[p[0]][p[1]] == Tileset.NOTHING) {
                nothingCount++;
            }
            if (world[p[0]][p[1]] == Tileset.FLOOR) {
                floorCount++;
            }
        }
        return (wallCount == 2 && nothingCount == 1 && floorCount == 1);
    }
}
