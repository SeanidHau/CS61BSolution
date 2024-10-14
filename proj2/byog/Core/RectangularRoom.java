package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public abstract class RectangularRoom {
    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public RectangularRoom(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean canBeReplaced(TETile[][] world) {
        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                if (i >= world.length || j >= world[0].length || world[i][j] != Tileset.WALL) {
                    return false;
                }
            }
        }
        return true;
    }

    protected void fillArea(int startX, int startY, int endX, int endY, TETile[][] world) {
        for (int i = startX; i < endX; i++) {
            for (int j = startY; j < endY; j++) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public abstract void drawRoom(TETile[][] world);
}
