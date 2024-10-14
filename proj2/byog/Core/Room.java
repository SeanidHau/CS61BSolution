package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 41;
    private static final int MAX_ROOM_SIZE = 10;
    private static final int MIN_ROOM_SIZE = 4;
    private static final int MAX_ROOM = 25;
    private static final int MIN_ROOM = 18;
    private static final int BORDER_OFFSET = 2;

    public void generateRooms(Random RANDOM, TETile[][] world) {
        int roomAccount = RANDOM.nextInt(MAX_ROOM - MIN_ROOM) + MIN_ROOM;
        int generatedRoom = 0;

        while (generatedRoom < roomAccount) {
            int roomWidth = RANDOM.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE) + MIN_ROOM_SIZE;
            int roomHeight = RANDOM.nextInt(MAX_ROOM_SIZE - MIN_ROOM_SIZE) + MIN_ROOM_SIZE;
            int startX = RANDOM.nextInt(WIDTH - roomWidth - 2 * BORDER_OFFSET) + BORDER_OFFSET;
            int startY = RANDOM.nextInt(HEIGHT - roomHeight - 2 * BORDER_OFFSET) + BORDER_OFFSET;

            RectangularRoom room = null;
            if (canBeReplaced(startX, startY, roomWidth, roomHeight, world)) {
                int roomType = RANDOM.nextInt(10);
                room = switch (roomType) {
                    case 0 -> new LShapedRoom(startX, startY, roomWidth, roomHeight, RANDOM);
                    case 1 -> new TShapedRoom(startX, startY, roomWidth, roomHeight, RANDOM);
                    case 2 -> new UShapedRoom(startX, startY, roomWidth, roomHeight);
                    default -> new SimpleRoom(startX, startY, roomWidth, roomHeight);
                };
                room.drawRoom(world);
                generatedRoom++;
            }
        }
    }

    private boolean canBeReplaced(int x, int y, int width, int height, TETile[][] world) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (i >= WIDTH || j >= HEIGHT || world[i][j] != Tileset.WALL) {
                    return false;
                }
            }
        }
        return true;
    }
}
