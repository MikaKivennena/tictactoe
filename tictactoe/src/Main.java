package tictactoe.src;

import tictactoe.src.util.*;
import java.io.Console;
/**
* TicTacToe is a game where you compete against AI to see who first gets required amount of marks in a row correct
*
* This app asks the user how large of a game area they want
* This is the main class but there are few additional classes that are Math, Arrays and MyConsole.
* There's documentation on each class and the methods they hold in.
*
*@author  Mika Kivennenä
*/
public class Main {
    static final int min = 3;
    static final int max = 20;
    static final String errorMessageOutOfRange = "Your number was out of the range of " + min + "-" + max;
    static final String errorMessageNotNumber = "Please give an actual number";
    static final String askBoardSizeMessage = "Give a single number. This will determine the size of the game board. etc. input 5 produces 5x5 gameboard\nMinimum board size is " + min + " and max is " + max;
    static final String playerMark = "[X]";
    static final String computerMark = "[O]";
    static final String emptyMark = "[ ]";
 
    static Console c = System.console();

    /**
    * Main method of the program uses two utility classes to help navigate game
    *
    * The size of the gameboard and amount required to win are both decided by the user.
    * This game will run until the user or pc wins or they run out of space making it a draw.
    * It's possible to have an immediate rematch with a different size of a gameboard.
    *
    *@author  Mika Kivennenä
    */
    public static void main(String[] args) {
        ArrayHandler arrHandler = new ArrayHandler();
        ComputerLogic computerLogic = new ComputerLogic();
        boolean gameRunning = true;
        boolean playerTurn, computerTurn = false;
        boolean gameOver = false;
        boolean playerWon, computerWon, gameDraw = false;
        int minRequired, maxRequired;
        int playerWin = 0;
        int computerWin = 0; 
        int draw = 0;

        while(gameRunning) {
            // Ask user for the size of the playing area.
            System.out.println(askBoardSizeMessage);
            int areaSize = askUserInput(min, max, errorMessageOutOfRange, errorMessageNotNumber);
            String[][] gameBoard = new String[areaSize][areaSize];
            maxRequired = areaSize;
            // If the gameboard is ten or over then change minimum required marks in a row to 5
            if(areaSize >= 10) {
                minRequired = 5;
            } else {
                minRequired = 3;
            }
            
            String errorRequiredOutOfRange = "Your number was out of the range of " + minRequired + "-" + maxRequired;
            System.out.println("Now choose how many marks in a row is required to win the game. Minimum is " + minRequired + " and max is " + maxRequired);
            int amountRequired = askUserInput(minRequired, maxRequired, errorRequiredOutOfRange, errorMessageNotNumber);

            // Fills the gameboard with empty squares
            for(int i=0; i<gameBoard.length; i++) {
                for(int j=0; j<gameBoard[i].length; j++) {
                    gameBoard[i][j] = emptyMark;
                }
            }
            // prints the empty gameboard
            drawGameBoard(gameBoard);
            
            while(!gameOver) {
                int row = 0;
                int column = 0;
                playerTurn = true;
                while(playerTurn) {
                    System.out.println("It's your turn player. Give the row you want to place your mark on it has to be a number between: " + "0" + " and " + (areaSize-1));
                    row = askUserInput(0, areaSize-1, errorMessageOutOfRange, errorMessageNotNumber);
                    System.out.println("Now give the number for column you want to place your mark");
                    column = askUserInput(0, areaSize-1, errorMessageOutOfRange, errorMessageNotNumber);
                    if(gameBoard[row][column] == emptyMark) {
                        gameBoard[row][column] = playerMark;
                        playerTurn = false;
                        computerTurn = true;
                    } else {
                        System.out.println("This slot is taken. Try again");
                    }
                    playerWon = arrHandler.checkForWin(gameBoard, playerMark, amountRequired, gameOver);
                    if(playerWon) {
                        gameOver = true;
                        System.out.println("You beat the computer!");
                        playerWin++;
                    }
                }

                if(gameOver) { break; }
                // Draws the gameboard to update the changes for the player to see
                drawGameBoard(gameBoard);
                
                while(computerTurn) {
                    // Uses ComputerLogic class to place its mark on the gameboard.
                    gameBoard = computerLogic.placeMark(gameBoard, playerMark, computerMark, emptyMark, amountRequired);
                    computerTurn = false;
                    System.out.println("\nComputer has placed a mark\n");
                    // Uses ArrayHandler class to see if computer has won the game after its round.
                    computerWon = arrHandler.checkForWin(gameBoard, computerMark, amountRequired, gameOver);

                    if(computerWon) {
                        gameOver = true;
                        System.out.println("Computer beat you!");
                        computerWin++;
                    }
                }
                
                if(gameOver) {break;}
                drawGameBoard(gameBoard);

                // if there hasn't been a winner before the round is over this checks if there's any empty spaces. If not it's a draw.
                gameDraw = arrHandler.checkForDraw(gameBoard, emptyMark);
                if(gameDraw) {
                    gameOver = true;
                    System.out.println("This ended in a draw. Well played");
                    draw++;
                }
            }
            // Prints out the last state of the gameboard.
            drawGameBoard(gameBoard);

            // Asks the user if they want a rematch. Loops until the user answers Y or N.
            boolean rematch = false;
            while(!rematch) {
                System.out.println("Would you like play again?(Y/N)");
                String answer = c.readLine();

                if(answer.equalsIgnoreCase("Y")) {
                    System.out.println("Playing again.");
                    rematch = true;
                    gameOver = false;
                    playerWon = false;
                    computerWon = false;
                    gameDraw = false;
                } else if (answer.equalsIgnoreCase("N")) {
                    gameRunning = false;
                    rematch = true;
                } else {
                    System.out.println("Please answer with Y for YES or N for NO");
                }
            }
            
        }
        
        System.out.println("You won: " + playerWin);
        System.out.println("Computer won: " + computerWin);
        System.out.println("Draws: " + draw);  
    }

    /**
    *askUserInput asks user to give a number between two values. 
    *
    *This method takes in two ints and two strings that represent the minimum and maximum accepted values.
    *The two string represent the error messages that are printed out if the user gives false input.
    *
    *@param min smallest possible integer that user can give.
    *@param max biggest possible integer that user can give.
    *@param errorOutOfRange This is the given message that terminal will print if the number is out of range of min and max.
    *@param errorNotNumber this is printed if the user input is something else than a number.
    *@return returns a user input integer
    *@author Mika Kivennenä
    */
    public static int askUserInput(int min, int max, String errorOutOfRange, String errorNotNumber) {
        boolean correctInput = false;
        int userInput = 0;

        while(!correctInput) { 
            try {
                userInput = Integer.parseInt(c.readLine());
                if(userInput >= min && userInput <= max) {
                    correctInput = true;
                } else {
                    System.out.println(errorOutOfRange);
                }
            } catch(NumberFormatException e) {
            System.out.println(errorMessageNotNumber);
            }
        }

        return userInput;
    }

    /**
    * drawGameBoard method prints the current state of the gameboard.
    *
    * Takes in a 2D-array that is printed out. Also adds numbers on top row and on the left side to make it easier to locate the wanted square.
    *
    *@param arrayToPrint takes in a 2D-array that is printed out.
    *@author Mika Kivennenä
    */
    public static void drawGameBoard(String[][] arrayToPrint) {
        System.out.print("    ");
        for(int x = 0; x < arrayToPrint.length; x++) {
            if(x < 9){
                System.out.print(x + "  ");
            } else {
                System.out.print(x + " ");
            }  
        }
        System.out.println();
        for(int i = 0; i < arrayToPrint.length; i++) {
            // prints numbers in front of each row to notify which row it is.
            if(i <= 9){
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            
            for (int j = 0; j < arrayToPrint[i].length; j++) {
                System.out.print(arrayToPrint[i][j]);
            }
            System.out.println();
        }
    }
}