package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class MainMenu {
    private int width;
    private int height;

    public static void mainMenuLauncher() {
        MainMenu mainMenu = new MainMenu(36, 42);
        mainMenu.drawMainMenu();
    }

    public MainMenu(int width, int height) {
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }

    public void drawMainMenu() {
        Font GUIFont = new Font("Arial Bold", Font.PLAIN, 40);
        StdDraw.setFont(GUIFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width / 2, height / 4 * 3, "CS61B:  THE GAME");

        GUIFont = new Font("Arial Bold", Font.PLAIN, 20);
        StdDraw.setFont(GUIFont);
        StdDraw.text(width / 2, height / 2 + 2, "New Game (N)");
        StdDraw.text(width / 2, height / 2, "Load Game (L)");
        StdDraw.text(width / 2, height / 2 - 2, "Quit (Q)");

        StdDraw.show();
    }

    public static void drawSeedFrame(String s) {
        StdDraw.clear();
        StdDraw.clear(Color.black);

        Font GUIFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(GUIFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(36 / 2, 42 / 2 + 2, "Please enter your random seed:");
        StdDraw.text(36 / 2, 42 / 2 - 2, "Press S to finish.");
        StdDraw.text(36 / 2, 42 / 2 - 4, "Seed must be smaller than 9,223,372,036,854,775,807!");

        Font font = new Font("Arial Bold", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);

        StdDraw.text(36 / 2, 42 / 2, s);

        StdDraw.show();
    }

    public static String getSeed() {
        StringBuilder seed = new StringBuilder();

        while (true) {
            if (StdDraw.hasNextKeyTyped()) {
                char op = StdDraw.nextKeyTyped();
                if (op == 'S' || op == 's') {
                    return seed.toString();
                }
                else if (!Character.isDigit(op)) {
                    continue;
                }
                seed.append(op);
                drawSeedFrame(String.valueOf(seed));
            }
        }
    }
}
