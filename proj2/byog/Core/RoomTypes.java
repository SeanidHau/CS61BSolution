package byog.Core;

import byog.TileEngine.TETile;
import java.util.Random;

/** SimpleRoom：普通矩形房间 */
class SimpleRoom extends RectangularRoom {
    public SimpleRoom(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawRoom(TETile[][] world) {
        fillArea(x, y, x + width, y + height, world);  // 填充矩形区域
    }
}

/** LShapedRoom：L形房间 */
class LShapedRoom extends RectangularRoom {
    private Random random;

    public LShapedRoom(int x, int y, int width, int height, Random random) {
        super(x, y, width, height);
        this.random = random;
    }

    @Override
    public void drawRoom(TETile[][] world) {
        int breakpoint = random.nextInt(width / 2) + 1;
        fillArea(x, y, x + width, y + height / 2, world);  // 水平部分
        fillArea(x, y, x + breakpoint, y + height, world);  // 垂直部分
    }
}

/** TShapedRoom：T形房间 */
class TShapedRoom extends RectangularRoom {
    private Random random;

    public TShapedRoom(int x, int y, int width, int height, Random random) {
        super(x, y, width, height);
        this.random = random;
    }

    @Override
    public void drawRoom(TETile[][] world) {
        int extensionHeight = random.nextInt(height / 2) + 1;
        fillArea(x, y, x + width / 2, y + height, world);  // 竖直部分
        fillArea(x + width / 4, y + height - extensionHeight, x + width - width / 4, y + height, world);  // 横向部分
    }
}

/** UShapedRoom：U形房间 */
class UShapedRoom extends RectangularRoom {
    public UShapedRoom(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void drawRoom(TETile[][] world) {
        fillArea(x, y, x + width / 4, y + height, world);  // 左边竖直部分
        fillArea(x + width - width / 4, y, x + width, y + height, world);  // 右边竖直部分
        fillArea(x, y, x + width, y + height / 3, world);  // 底部部分
    }
}
