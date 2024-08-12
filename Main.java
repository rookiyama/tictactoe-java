import java.util.*;

public class Main {
    static Scanner terminal = new Scanner(System.in);
    static int[][] gameTable = {{0,0,0},
                                {0,0,0},
                                {0,0,0}};
    static int chosenMode = 0;
    static boolean gameIsRunning = false;
    static int playerTurn = 1;
    static int gamesPlayed = 0;
    static int p1Wins = 0;
    static int p2Wins = 0;

    static void displayInstructions() {
        if (!gameIsRunning) {
            System.out.println("Main Menu:\n1. Play\n2. Stats\n3. Exit");
            chosenMode = terminal.nextInt();
            System.out.print("\n");
            switch (chosenMode) {
                case 1:
                    gameIsRunning = true;
                    gameCycle();
                    break;
                case 2:
                    if (gamesPlayed > 0) {
                        System.out.println("Games played in a single session of Tic Tac Toe: "+ gamesPlayed +"\nPlayer 1 Wins: "+ p1Wins +" (Win Rate: "+ ((double) p1Wins / gamesPlayed) * 100 +"%)\nPlayer 2 Wins: "+ p2Wins +" (Win Rate: "+ ((double) p2Wins / gamesPlayed) * 100 +"%)\n");
                    } else {
                        System.out.println("Games played in a single session of Tic Tac Toe: 0\nPlayer 1 Wins: 0 (Win Rate: 100%)\nPlayer 2 Wins: 0 (Win Rate: 100%)\n");
                    }
                    chosenMode = 0;
                    displayInstructions();
                    break;
                case 3:
                    System.out.println("You have left Tic Tac Toe.");
                    System.exit(0);
                default:
                    System.out.println("Invalid mode. Please enter a valid choice.\n");
                    displayInstructions();
                    break;
            }
        } else {
            if (playerTurn == 1) {
                System.out.println("You are Player 1. Please enter an input from 1-9 depending on where you want to place your symbol. (The order goes from left to right, top to bottom.)");
            } else if (playerTurn == 2) {
                System.out.println("You are Player 2. Please enter an input from 1-9 depending on where you want to place your symbol. (The order goes from left to right, top to bottom.)");
            }
        } 
    }

    static void displayTable() {
        for (int i = 0; i < gameTable.length; ++i) {
            for (int j = 0; j < gameTable[i].length; ++j) {
                switch (gameTable[i][j]) {
                    case 0 -> System.out.print(".");
                    case 1 -> System.out.print("X");
                    case 2 -> System.out.print("O");
                }
            }
            System.out.print("\n");
        }
    }

    static void setSymbolToTable(int i, int j) {
        if (gameTable[i][j] == 0) {
            if (playerTurn == 1) {
                gameTable[i][j] = 1;
            } else if (playerTurn == 2) {
                gameTable[i][j] = 2;
            }     
        } else {
            System.out.println("There is already a symbol in this place. Please enter another input.");
            inputSymbol();
        }
    }

    static void inputSymbol() {
        int place = 0;
        System.out.print("Input: ");
        while (place <= 0 || place > 9) {
            try {
                place = terminal.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                terminal.next(); // clear the invalid input
            }
        }
        place -= 1;
        switch (place) {
            case 0 -> setSymbolToTable(0,0);
            case 1 -> setSymbolToTable(0,1);
            case 2 -> setSymbolToTable(0,2);
            case 3 -> setSymbolToTable(1,0);
            case 4 -> setSymbolToTable(1,1);
            case 5 -> setSymbolToTable(1,2);
            case 6 -> setSymbolToTable(2,0);
            case 7 -> setSymbolToTable(2,1);
            case 8 -> setSymbolToTable(2,2);
        }
    }

    static void resetGameVars() {
        gamesPlayed++;
        gameIsRunning = false;
        playerTurn = 1;
        chosenMode = 0;
        for (int i = 0; i < gameTable.length; ++i) {
            for (int j = 0; j < gameTable.length; ++j) {
                gameTable[i][j] = 0;
            }
        }
    }
    
    static void checkGameState(int playerTurn) {
        if (
            gameTable[0][0] == playerTurn && gameTable[1][0] == playerTurn && gameTable[2][0] == playerTurn ||
            gameTable[0][1] == playerTurn && gameTable[1][1] == playerTurn && gameTable[2][1] == playerTurn ||
            gameTable[0][2] == playerTurn && gameTable[1][2] == playerTurn && gameTable[2][2] == playerTurn ||
            gameTable[0][0] == playerTurn && gameTable[0][1] == playerTurn && gameTable[0][2] == playerTurn ||
            gameTable[1][0] == playerTurn && gameTable[1][1] == playerTurn && gameTable[1][2] == playerTurn ||
            gameTable[2][0] == playerTurn && gameTable[2][1] == playerTurn && gameTable[2][2] == playerTurn ||
            gameTable[0][0] == playerTurn && gameTable[1][1] == playerTurn && gameTable[2][2] == playerTurn ||
            gameTable[0][2] == playerTurn && gameTable[1][1] == playerTurn && gameTable[2][0] == playerTurn)
            {
            displayTable();
            System.out.println("Player "+ playerTurn +" has won this game of Tic Tac Toe!");
            if (playerTurn == 1) {
                p1Wins++;
            } else if (playerTurn == 2) {
                p2Wins++;
            }
            resetGameVars();
            gameCycle();
        }
        int filledUpSlots = 0;
        for (int i = 0; i < gameTable.length; ++i) {
            for (int j = 0; j < gameTable.length; ++j) {
                if (gameTable[i][j] != 0) {
                    filledUpSlots++;
                }
            }
        }
        if (filledUpSlots == 9) {
            displayTable();
            System.out.println("This game of Tic Tac Toe is a draw!");
            resetGameVars();
            gameCycle();
        }
    }

    static void gameCycle() {
        if (gameIsRunning) {
            if (chosenMode == 1) {
                displayInstructions();
                displayTable();
                inputSymbol();
                checkGameState(playerTurn);
                if (playerTurn == 1) {
                    playerTurn = 2;
                } else if (playerTurn == 2) {
                    playerTurn = 1;
                }
                gameCycle();
            }
        } else {
            displayInstructions();
        }
    }

    public static void main(String[] args) {
        System.out.println("# ROOK'S TIC TAC TOE #\n   Written in Java!\n");
        displayInstructions();
    }
}