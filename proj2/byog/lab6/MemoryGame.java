package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < n; i++) {
            randomString.append(CHARACTERS[rand.nextInt(CHARACTERS.length)]);
        }
        return randomString.toString();
    }

    public void drawFrame(String s) {
        StdDraw.clear();
        StdDraw.clear(Color.black);

        if (!gameOver) {
            Font GUIFont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(GUIFont);
            StdDraw.setPenColor(Color.white);
            StdDraw.textLeft(1, height - 1, "Round:" + round);
            StdDraw.textRight(width - 1, height - 1, ENCOURAGEMENT[round % ENCOURAGEMENT.length]);
            StdDraw.text(width / 2, height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.line(0, height - 2, width, height - 2);
        }

        Font font = new Font("Arial Bold", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);

        StdDraw.text(width / 2, height / 2, s);

        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++) {
            drawFrame(letters.substring(i, i + 1));
            StdDraw.pause(1000);
            drawFrame(" ");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String userInput = "";
        drawFrame(userInput);

        while (userInput.length() < n) {
            if (!StdDraw.hasNextKeyTyped()) {
                continue;
            }
            char key = StdDraw.nextKeyTyped();
            userInput = userInput + key;
            drawFrame(userInput);
        }
        StdDraw.pause(500);
        return userInput;
    }

    public void startGame() {
        round = 1;
        playerTurn = false;
        gameOver = false;

        while(!gameOver) {
            playerTurn = false;
            drawFrame("Round:" + round);
            StdDraw.pause(1000);
            String generatedString =  generateRandomString(round);
            flashSequence(generatedString);

            playerTurn = true;
            String userInput = solicitNCharsInput(round);
            if (userInput.equals(generatedString)) {
                drawFrame("Well done!");
                StdDraw.pause(500);
                round += 1;
            }
            else {
                gameOver = true;
                drawFrame("Game Over! You made it to round:" + round);
            }
        }
    }

}
