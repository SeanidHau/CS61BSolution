package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import javax.naming.directory.DirContext;
import java.util.LinkedList;
import java.util.Random;

public class Path {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 41;

    // 定义四个方向的坐标偏移量：上、下、左、右
    private static final int[][] DIRECTIONS = {{0, 2}, {0, -2}, {2, 0}, {-2, 0}};

    /** 使用洪水填充算法生成路径 */
    public void floodFillPaths(Random RANDOM, TETile[][] world) {
        int startX = RANDOM.nextInt(WIDTH / 2) * 2 + 1;
        int startY = RANDOM.nextInt(HEIGHT / 2) * 2 + 1;

        floodFill(startX, startY, RANDOM, world);
        simplifyMaze(world);
        removeEnclosedRooms(world);
        cleanWall(world);
        cleanEdgeWall(world);
    }

    /** 洪水填充路径 */
    private void floodFill(int x, int y, Random RANDOM, TETile[][] world) {
        if (x < 1 || y < 1 || x >= WIDTH - 1 || y >= HEIGHT - 1 || world[x][y] != Tileset.WALL) {
            return;
        }

        world[x][y] = Tileset.FLOOR;

        shuffleDirections(DIRECTIONS, RANDOM);

        for (int[] dir : DIRECTIONS) {
            int nextX = x + dir[0];
            int nextY = y + dir[1];

            if (nextX > 0 && nextX < WIDTH - 1 && nextY > 0 && nextY < HEIGHT - 1 && world[nextX][nextY] == Tileset.WALL) {
                world[(x + nextX) / 2][(y + nextY) / 2] = Tileset.FLOOR;

                floodFill(nextX, nextY, RANDOM, world);
            }
        }
    }

    public void simplifyMaze(TETile[][] world) {
        boolean changed;
        do {
            changed = false;
            for (int x = 1; x < WIDTH - 1; x++) {
                for (int y = 1; y < HEIGHT - 1; y++) {
                    if (world[x][y] == Tileset.FLOOR && isDeadEnd(x, y, world)) {
                        world[x][y] = Tileset.WALL;
                        changed = true;
                    }
                }
            }
        } while (changed);
    }

    public boolean isDeadEnd(int x, int y, TETile[][] world) {
        int wallCount = 0;
        for (int[] dir : DIRECTIONS) {
            int nextX = x + dir[0] / 2;
            int nextY = y + dir[1] / 2;
            if (nextX >= 0 && nextX < WIDTH && nextY >= 0 && nextY < HEIGHT && world[nextX][nextY] == Tileset.WALL) {
                wallCount++;
            }
        }

        return wallCount == 3;
    }

    public void cleanWall(TETile[][] world) {
        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < HEIGHT - 1; y++) {
                if (world[x][y] != Tileset.WALL) {
                    continue;
                }

                int wallCount = 0;
                boolean isEdge = false;

                for (int[] dir : DIRECTIONS) {
                    int nextX = x + dir[0] / 2;
                    int nextY = y + dir[1] / 2;
                    if (nextX >= 0 && nextX < WIDTH && nextY >= 0 && nextY < HEIGHT) {
                        if (world[nextX][nextY] == Tileset.WALL || world[nextX][nextY] == Tileset.NOTHING) {
                            wallCount++;
                        }
                    }
                    if (world[x + 1][y + 1] == Tileset.FLOOR || world[x + 1][y - 1] == Tileset.FLOOR || world[x - 1][y + 1] == Tileset.FLOOR || world[x - 1][y - 1] == Tileset.FLOOR ) {
                        isEdge = true;
                    }
                }

                if (wallCount == 4 && !isEdge) {
                    world[x][y] = Tileset.NOTHING;
                }
            }
        }
    }

    public void cleanEdgeWall(TETile[][] world) {
        for (int x = 0; x < WIDTH; x++) {
            cleanSingleEdgeWall(x, 0, world);
            cleanSingleEdgeWall(x, HEIGHT - 1, world);
        }

        for (int y = 0; y < HEIGHT; y++) {
            cleanSingleEdgeWall(0, y, world);
            cleanSingleEdgeWall(WIDTH - 1, y, world);
        }
    }

    public void cleanSingleEdgeWall(int x, int y, TETile[][] world) {
        int wallCount = 0;
        boolean isEdge = false;
        for (int[] dir : DIRECTIONS) {
            int nextX = x + dir[0] / 2;
            int nextY = y + dir[1] / 2;
            if (nextX < 0 || nextX >= WIDTH || nextY < 0 || nextY >= HEIGHT) {
                wallCount++;
                continue;
            }
            if (world[nextX][nextY] == Tileset.WALL || world[nextX][nextY] == Tileset.NOTHING) {
                wallCount++;
            }
        }

        if ((x + 1 < WIDTH && y + 1 < HEIGHT && world[x + 1][y + 1] == Tileset.FLOOR) ||
                (x + 1 < WIDTH && y - 1 >= 0 && world[x + 1][y - 1] == Tileset.FLOOR) ||
                (x - 1 >= 0 && y + 1 < HEIGHT && world[x - 1][y + 1] == Tileset.FLOOR) ||
                (x - 1 >= 0 && y - 1 >= 0 && world[x - 1][y - 1] == Tileset.FLOOR)) {
            isEdge = true;
        }

        if (wallCount == 4 && !isEdge) {
            world[x][y] = Tileset.NOTHING;
        }
    }
    public void removeEnclosedRooms(TETile[][] world) {
        boolean[][] visited = new boolean[WIDTH][HEIGHT];

        // 遍历整个地图，查找每一个可能的封闭房间
        for (int x = 1; x < WIDTH - 1; x++) {
            for (int y = 1; y < HEIGHT - 1; y++) {
                if (world[x][y] == Tileset.FLOOR && !visited[x][y]) {
                    if (isEnclosedRoom(x, y, world, visited)) {
                        fillRoomWithNothing(x, y, world);
                    }
                }
            }
        }
    }

    private boolean isEnclosedRoom(int x, int y, TETile[][] world, boolean[][] visited) {
        // 使用 BFS 或 DFS 检查房间是否是完全封闭的
        // 如果发现任何边界是开放的（不是墙），则不是封闭房间
        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});
        boolean isEnclosed = true;

        while (!queue.isEmpty()) {
            int[] pos = queue.removeFirst();
            int currX = pos[0];
            int currY = pos[1];

            // 如果已经访问过，跳过
            if (visited[currX][currY]) continue;
            visited[currX][currY] = true;

            // 检查四个方向
            for (int[] dir : DIRECTIONS) {
                int nextX = currX + dir[0] / 2;
                int nextY = currY + dir[1] / 2;

                // 如果到达地图边缘，说明不是封闭的房间
                if (nextX <= 0 || nextX >= WIDTH - 1 || nextY <= 0 || nextY >= HEIGHT - 1) {
                    isEnclosed = false;
                } else if (world[nextX][nextY] == Tileset.FLOOR && !visited[nextX][nextY]) {
                    queue.add(new int[]{nextX, nextY});
                }
            }
        }

        return isEnclosed;
    }

    private void fillRoomWithNothing(int x, int y, TETile[][] world) {
        // 使用 BFS 或 DFS 将封闭的房间填充为 Tileset.NOTHING
        LinkedList<int[]> queue = new LinkedList<>();
        queue.add(new int[]{x, y});

        while (!queue.isEmpty()) {
            int[] pos = queue.removeFirst();
            int currX = pos[0];
            int currY = pos[1];

            // 如果已经是 NOTHING，跳过
            if (world[currX][currY] == Tileset.NOTHING) continue;

            world[currX][currY] = Tileset.NOTHING;

            // 检查四个方向
            for (int[] dir : DIRECTIONS) {
                int nextX = currX + dir[0] / 2;
                int nextY = currY + dir[1] / 2;

                if (nextX > 0 && nextX < WIDTH - 1 && nextY > 0 && nextY < HEIGHT - 1 && world[nextX][nextY] == Tileset.FLOOR) {
                    queue.add(new int[]{nextX, nextY});
                }
            }
        }
    }

    /** 洗牌算法打乱方向顺序 */
    private void shuffleDirections(int[][] directions, Random RANDOM) {
        for (int i = directions.length - 1; i > 0; i--) {
            int j = RANDOM.nextInt(i + 1);
            int[] temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }
    }
}
