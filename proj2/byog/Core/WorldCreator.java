package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;
public class WorldCreator {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 41;

    public void worldGenerator(Random RANDOM, TETile[][] world) {
        fillWall(world);

        // Generate the Rooms.
        Room roomGenerator = new Room();
        roomGenerator.generateRooms(RANDOM, world);

        Path pathGenerator = new Path();
        pathGenerator.floodFillPaths(RANDOM, world);

        LockedDoor lockedDoorGenerator = new LockedDoor();
        lockedDoorGenerator.generateLockedDoor(RANDOM, world);
    }

    /** Initialize the world to filled with the wall. */
    private void fillWall(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.WALL;
            }
        }
    }
}
