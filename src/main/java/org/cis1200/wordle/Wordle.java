package org.cis1200.wordle;

import java.io.*;
import java.util.*;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

/**
 * This class is a model for TicTacToe.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of TicTacToe,
 * visualized with Strings printed to the console.
 */
public class Wordle {
    private String answer;
    private int[][] boardColors;
    private char[][] boardLetters;
    private int round;

    private int inputIndex;
    private boolean gameOver;
    private LinkedList<String> guessed;

    private ArrayList<String> dictionary = new ArrayList<>();

    /**
     * Constructor sets up game state.
     */
    public Wordle() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("files/dictionary.txt"));
            String line = br.readLine();
            while (line != null) {
                dictionary.add(line);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Dictionary File Not Found");
        } catch (IOException e) {
            throw new RuntimeException("IOException in br read line");
        }

        reset();
    }

    public boolean loadSave() {
        try {
            FileInputStream saveFile = new FileInputStream("files/savedGame");
            ObjectInputStream ois = new ObjectInputStream(saveFile);

            this.answer = (String) ois.readObject();
            this.boardColors = (int[][]) ois.readObject();
            this.boardLetters = (char[][]) ois.readObject();
            this.round = ois.read();
            this.inputIndex = ois.read();
            this.gameOver = ois.readBoolean();
            this.guessed = (LinkedList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public boolean save() {
        try {
            FileOutputStream saveFile = new FileOutputStream("files/savedGame");
            ObjectOutputStream ois = new ObjectOutputStream(saveFile);

            ois.writeObject(this.answer);
            ois.writeObject(this.boardColors);
            ois.writeObject(this.boardLetters);
            ois.write(this.round);
            ois.write(this.inputIndex);
            ois.writeBoolean(this.gameOver);
            ois.writeObject(this.guessed);

        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public int getRound() {
        return this.round;
    }

    public int getInputIndex() {
        return this.inputIndex;
    }

    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param input to play in
     * @return whether the turn was successful
     * 
     *         public boolean playTurn(int c, int r) {
     *         if (board[r][c] != 0 || gameOver) {
     *         return false;
     *         }
     * 
     *         if (player1) {
     *         board[r][c] = 1;
     *         } else {
     *         board[r][c] = 2;
     *         }
     * 
     *         numTurns++;
     *         if (checkWinner() == 0) {
     *         player1 = !player1;
     *         }
     *         return true;
     *         }
     */
    public boolean addLetter(char input) {
        if (gameOver) {
            return false;
        }
        if (inputIndex > 4) {
            return false;
        } else {
            boardLetters[round][inputIndex] = input;
            boardColors[round][inputIndex] = 1;
            inputIndex++;
            if (inputIndex == 5) {
                addGuess();
                return true;
            }
            return false;
        }
    }

    public void delLetter() {
        if (inputIndex > 0) {
            inputIndex = inputIndex - 1;
            boardColors[round][inputIndex] = 0;
            boardLetters[round][inputIndex] = Character.MIN_VALUE;
        }
    }

    public boolean addGuess() {
        char[] row = boardLetters[round];
        String guess = String.valueOf(row);
        if (guess != null && dictionary.contains(guess)) {
            guessed.addLast(guess);
            checkLetters();
            isGameOver();
            inputIndex = 0;
            round = round + 1;
            return true;

        }
        return false;
    }

    /**
     * This method checks the letters in the guess to see which letters match the
     * correct answer.
     *
     * @return
     */
    public void checkLetters() {
        TreeMap<Character, Integer> answerFreq = new TreeMap<>();
        TreeMap<Character, Integer> guessFreq = new TreeMap<>();
        String guess = guessed.peekLast();

        for (int i = 0; i < 5; i++) {
            Integer val = answerFreq.get(answer.charAt(i));
            if (val != null) {
                answerFreq.put(answer.charAt(i), val + 1);
            } else {
                answerFreq.put(answer.charAt(i), 1);

            }
        }
        for (int k = 0; k < 5; k++) {
            Integer val = guessFreq.get(guess.charAt(k));
            if (val != null) {
                guessFreq.put(guess.charAt(k), val + 1);
            } else {
                guessFreq.put(guess.charAt(k), 1);

            }
        }

        for (int i = 0; i < 5; i++) {
            if (answer.charAt(i) == guess.charAt(i)) {
                boardColors[round][i] = 3;
                answerFreq.put(answer.charAt(i), answerFreq.get(answer.charAt(i)) - 1);
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if ((answer.charAt(i) == guess.charAt(j)) && (boardColors[round][j] != 3)
                        && (answerFreq.get(answer.charAt(i)) > 0)) {
                    boardColors[round][j] = 2;
                    answerFreq.put(answer.charAt(i), answerFreq.get(answer.charAt(i)) - 1);

                }
            }

        }
    }

    /*
     * for (int i = 0; i < 5; i++) {
     * if(checkedLetters.contains(String.valueOf(guess.charAt(i)))){
     * continue;
     * }
     * int ansCount = 0;
     * for (int j = 0; j < 5; j++) {
     * if (guess.charAt(i) == answer.charAt(j)) {
     * ansCount++;
     * }
     * }
     * int guessCount = 0;
     * for (int l = 0; l < 5; l++) {
     * if (guess.charAt(i) == guess.charAt(l)) {
     * guessCount++;
     * }
     * }
     * 
     * for (int k = 0; k < 5; k++) {
     * if (guess.charAt(k) == answer.charAt(k) && guess.charAt(i) ==
     * guess.charAt(k)) {
     * boardColors[round][k] = 3;
     * ansCount += -1;
     * guessCount += -1;
     * 
     * }
     * }
     * 
     * 
     * while (ansCount >0 && boardColors[round][i]!= 3) {
     * boardColors[round][i] = 2;
     * ansCount += -1;
     * guessCount += -1;
     * 
     * }
     * 
     * if (ansCount <1 || guessCount <1){
     * checkedLetters.add(String.valueOf(guess.charAt(i)));
     * 
     * }
     */

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {

        System.out.println("\n\nTurn " + round + ":\n");
        System.out.println("Color Array\n");
        System.out.println("answer:" + getAnswer() + "\n");
        for (int i = 0; i < boardColors.length; i++) {
            for (int j = 0; j < boardColors[i].length; j++) {
                System.out.print(boardColors[i][j]);
                if (j < 5) {
                    System.out.print(" | ");
                }
            }
            if (i < 5) {
                System.out.println("\n---------");
            }
        }
        System.out.println();
        System.out.println("\nLetter Array");

        for (int i = 0; i < boardLetters.length; i++) {
            for (int j = 0; j < boardLetters[i].length; j++) {
                System.out.print(boardLetters[i][j]);
                if (j < 5) {
                    System.out.print(" | ");
                }
            }
            if (i < 5) {
                System.out.println("\n---------");
            }
        }
        System.out.println();
        System.out.println("Game Over: " + gameOver);

    }

    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        boardColors = new int[6][5];
        boardLetters = new char[6][5];
        round = 0;
        inputIndex = 0;
        guessed = new LinkedList<>();
        gameOver = false;
        Random rand = new Random();
        int i = rand.nextInt(dictionary.size());
        answer = dictionary.get(i);
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public int getCellColor(int r, int c) {
        return boardColors[r][c];
    }

    public char getCellLetter(int r, int c) {
        return boardLetters[r][c];
    }

    public int[][] getBoardColors() {
        return boardColors;
    }

    public char[][] getBoardLetters() {
        return boardLetters;
    }

    public boolean isGameOver() {
        String guess = guessed.peekLast();
        if (guess == null) {
            return false;
        }
        if (guess.toLowerCase().equals(answer.toLowerCase()) || round == 6) {
            gameOver = true;
            return true;
        } else {
            return gameOver;
        }
    }

    public String getAnswer() {
        return answer;
    }

    public void fixAnswer(String input) {
        answer = input;
    }

    public LinkedList<String> getGuessed() {
        return guessed;
    }

    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Wordle t = new Wordle();
        t.fixAnswer("apple");

        t.addLetter('p');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('a');
        t.printGameState();

        t.addLetter('a');
        t.printGameState();

        t.addLetter('l');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('a');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('p');
        t.printGameState();

        t.addLetter('l');
        t.printGameState();

        t.addLetter('e');
        t.printGameState();

        System.out.println();

        System.out.println();
    }
}
