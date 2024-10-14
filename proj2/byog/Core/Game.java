package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.SymbolDigraph;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Random;
import java.io.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 41;
    public static final int HUD_HEIGHT = 3;
    private static String lastDescription = "";
    private static final String SAVE_FILE_PATH = "savegame.ser";
    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        MainMenu.mainMenuLauncher();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char op = StdDraw.nextKeyTyped();
                if (op == 'N' || op == 'n') {
                    MainMenu.drawSeedFrame("");
                    break;
                }
                else if (op == 'q' || op == 'Q') {
                    System.exit(0);
                }
                else if (op == 'L' || op == 'l') {
                    loadGame();
                    break;
                }
            }
        }

        String seedString = MainMenu.getSeed();
        long seed = 0;
        if (isLongWithRange(seedString)) {
            seed = Long.parseLong(seedString);
        }
        else {
            MainMenu.drawSeedFrame("Your seed is illegal!");
            StdDraw.pause(1000);
            playWithKeyboard();
        }

        Random RANDOM = new Random(seed);

        TETile[][] world = worldInitialize();

        WorldCreator wc = new WorldCreator();
        wc.worldGenerator(RANDOM, world);

        ter.initialize(WIDTH, HEIGHT + HUD_HEIGHT, 0, 0);

        int startX, startY;
        do {
            startX = RANDOM.nextInt(WIDTH);
            startY = RANDOM.nextInt(HEIGHT);
        } while (world[startX][startY] != Tileset.FLOOR);

        Player player = new Player(startX, startY);
        player.drawPlayer(world);

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char op = StdDraw.nextKeyTyped();
                if (op == 'q' || op == 'Q') {
                    saveGame(world, RANDOM, player);
                    System.exit(0);
                }
                else {
                    player.move(op, world);
                }
            }
            ter.renderFrame(world);
            drawHUD(world);


            StdDraw.pause(50);
        }
    }

    public boolean isLongWithRange(String s) {
        try {
            Long.parseLong(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private TETile[][] worldInitialize() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        return world;
    }

    public void drawHUD(TETile[][] world) {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        if (mouseX >= 0 && mouseX < WIDTH && mouseY >= 0 && mouseY < HEIGHT) {
            TETile tileName = world[mouseX][mouseY];
            String description = tileName.description();


            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.setFont(StdDraw.getFont().deriveFont(15f));

            StdDraw.textLeft(1, HEIGHT + 1.5, description);
            StdDraw.show();
        }
    }

    public void saveGame(TETile[][] world, Random random, Player player) {
        GameState gameState = new GameState(world, random.nextLong(), player.getX(), player.getY());
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_PATH))) {
            out.writeObject(gameState);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public void loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE_PATH))) {
            GameState gameState = (GameState) in.readObject();

            // 通过存档的种子恢复随机生成器
            Random random = new Random(gameState.getRandomSeed());

            // 恢复游戏世界和玩家位置
            TETile[][] world = worldInitialize();
            world = gameState.getWorld(world);
            int playerX = gameState.getPlayerX();
            int playerY = gameState.getPlayerY();

            // 恢复玩家
            Player player = new Player(playerX, playerY);
            ter.initialize(WIDTH, HEIGHT + HUD_HEIGHT, 0, 0);
            player.drawPlayer(world);

            while (true) {
                if (StdDraw.hasNextKeyTyped()) {
                    char op = StdDraw.nextKeyTyped();
                    if (op == 'q' || op == 'Q') {
                        saveGame(world, random, player);  // 保存游戏
                        System.exit(0);
                    } else {
                        player.move(op, world);
                    }
                }
                ter.renderFrame(world);
                drawHUD(world);

                StdDraw.pause(50);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game: " + e.getMessage());
        }
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        long seed = Long.parseLong(input.replaceAll("[^0-9]", ""));
        Random random = new Random(seed);
        TETile[][] world = worldInitialize();

        WorldCreator wc = new WorldCreator();
        wc.worldGenerator(random, world);
        return world;
    }
}
