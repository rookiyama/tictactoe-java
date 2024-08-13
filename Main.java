import java.util.*;
public class Main {
    static Scanner terminal = new Scanner(System.in);
    static int[][] gameTable = {{0,0,0}, {0,0,0}, {0,0,0}};
    static int gamesPlayed, p1Wins, p2Wins = 0;
    static boolean gameIsRunning = false;
    static int player = 1;
    static void displayMainMenu() {
        System.out.print("Main Menu:\n1. Play\n2. Stats\n3. Exit\nInput: ");
        int input = terminal.nextInt(); System.out.print("\n");
        switch (input) {
            case 1: // start the game
                gameIsRunning = true; gameCycle();
                break;
            case 2: // show stats screen
                displayStatsScreen();
            case 3: // leave the game
                System.exit(0);
            default: // invalid input
                displayMainMenu();
                break;
         }
    }
    static void displayStatsScreen() {
        String print = (gamesPlayed > 0) ? "// Games played in a single session of Tic Tac Toe: "+ gamesPlayed +"\n// Player 1 Wins: "+ p1Wins +" (Win Rate: "+ ((double) p1Wins / gamesPlayed) * 100 +"%)\n// Player 2 Wins: "+ p2Wins +" (Win Rate: "+ ((double) p2Wins / gamesPlayed) * 100 +"%)\n" : "// Games played in a single session of Tic Tac Toe: 0\n// Player 1 Wins: 0 (Win Rate: 100%)\n// Player 2 Wins: 0 (Win Rate: 100%)\n";
        System.out.println(print);
        int input = 0;
        while (input != 1) {
            try {
                System.out.print("Type 1 to go back to Main Menu: ");
                input = terminal.nextInt();
            } catch (InputMismatchException e) {
                terminal.next();
            }
        }
        System.out.print("\n");
        displayMainMenu();
    }
    static void displayTable() {
        for (int row = 0; row < gameTable.length; ++row) {
            for (int col = 0; col < gameTable[row].length; ++col) {
                switch (gameTable[row][col]) {
                    case 0 -> System.out.print(".");
                    case 1 -> System.out.print("X");
                    case 2 -> System.out.print("O");
                }
            }
            System.out.print("\n");
        }
    }
    static void setSymbolToTable(int row, int col, int plr) {
        if (gameTable[row][col] == 0) {
            gameTable[row][col] = plr;
        } else {
            System.out.println("⚠️ There is already a symbol in this place. Please enter another input.");
            inputSymbol(plr);
        }
    }
    static void inputSymbol(int plr) {
        int place = 0;
        System.out.print("Input: ");
        while (place <= 0 || place > 9) {
            try {
                place = terminal.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Invalid input. Please enter a number between 1 and 9.");
                terminal.next();
            }
        }
        place -= 1;
        switch (place) {
            case 0 -> setSymbolToTable(0,0,plr);
            case 1 -> setSymbolToTable(0,1,plr);
            case 2 -> setSymbolToTable(0,2,plr);
            case 3 -> setSymbolToTable(1,0,plr);
            case 4 -> setSymbolToTable(1,1,plr);
            case 5 -> setSymbolToTable(1,2,plr);
            case 6 -> setSymbolToTable(2,0,plr);
            case 7 -> setSymbolToTable(2,1,plr);
            case 8 -> setSymbolToTable(2,2,plr);
        }
    }
    static void resetGameVars() {
        gamesPlayed++;
        gameIsRunning = false;
        player = 1;
        for (int row = 0; row < gameTable.length; ++row) {
            for (int col = 0; col < gameTable.length; ++col) {
                gameTable[row][col] = 0;
            }
        }
    }
    static void checkGameState(int player) {
        if ( // winning patterns (by row, column, and diagonals)
                gameTable[0][0] == player && gameTable[1][0] == player && gameTable[2][0] == player ||
                gameTable[0][1] == player && gameTable[1][1] == player && gameTable[2][1] == player ||
                gameTable[0][2] == player && gameTable[1][2] == player && gameTable[2][2] == player ||
                gameTable[0][0] == player && gameTable[0][1] == player && gameTable[0][2] == player ||
                gameTable[1][0] == player && gameTable[1][1] == player && gameTable[1][2] == player ||
                gameTable[2][0] == player && gameTable[2][1] == player && gameTable[2][2] == player ||
                gameTable[0][0] == player && gameTable[1][1] == player && gameTable[2][2] == player ||
                gameTable[0][2] == player && gameTable[1][1] == player && gameTable[2][0] == player
            ) 
        {
            displayTable();
            System.out.println("[ANNOUNCER] Player "+ player +" has won this game of Tic Tac Toe!");
            switch (player) {
                case 1 -> p1Wins++;
                case 2 -> p2Wins++;
            }
            resetGameVars();
            gameCycle();
        }
        int filledUpSlots = 0;
        for (int row = 0; row < gameTable.length; ++row) {
            for (int col = 0; col < gameTable.length; ++col) {
                if (gameTable[row][col] != 0) {
                    filledUpSlots++;
                }
            }
        }
        if (filledUpSlots == 9) {
            displayTable();
            System.out.println("[ANNOUNCER] This game of Tic Tac Toe is a draw!");
            resetGameVars();
            gameCycle();
        }
    }
    static void gameCycle() {
        if (gameIsRunning) {
            System.out.println("[ANNOUNCER] You are Player "+ player +". Please enter an input from 1-9 depending on where you want to place your symbol. (The order goes from left to right, top to bottom.)");
            displayTable();
            inputSymbol(player);
            checkGameState(player);
            if (player == 1) {
                player = 2;
            } else if (player == 2) {
                player = 1;
            }
            gameCycle();
        } else {
            displayMainMenu();
        }
    }
    public static void main(String[] args) {
        System.out.println("# ROOK'S TIC TAC TOE #\n   Written in Java!\n");
        displayMainMenu();
    }
}