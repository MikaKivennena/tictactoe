package  tictactoe.src.util;
/**
*ArrayHandler class contains methods to inspect a 2D-array from every direction.
*
*checkForWin method of this class goes through each method that inspects the array for amount of marks required to win.
*
*@author  Mika Kivennenä
*/
public class ArrayHandler {
    int marksInARow;
    boolean gameWon = false;
    /**
    * checkForWin uses different methods to see if there's a winner in the game
    *
    * Uses different methods in this class that go through the given array in multiple ways.
    * Returns a boolean value on if there's required amount of marks in a row.
    *
    *@param arrayToCheck is the array that is being inspected.
    *@param markToCheck this is the mark that is being looked for in the array.
    *@param amountRequired this is the integer value needed to win.
    *@param gameOver this is a boolean that is used to reset gameWon boolean after each game.
    *@return returns a boolean value if the mark that is being checked wins or not.
    *@author Mika Kivennenä
    */
    public boolean checkForWin(String[][] arrayToCheck, String markToCheck, int amountRequired, boolean gameOver) {
        gameWon = gameOver;
        if(!gameWon) {
            gameWon = checkColumns(arrayToCheck, markToCheck, amountRequired);
        }
        if(!gameWon) { 
            gameWon = checkRows(arrayToCheck, markToCheck, amountRequired);
        }
        if(!gameWon) { 
            gameWon = checkDiagonalLines(arrayToCheck, markToCheck, amountRequired);
        }
        return gameWon;
    }

    /**
    *checkForDraw 
    *
    *Method goes through each square to see if there isn't any empty squares after player have taken their turns.
    *If there aren't any empty spaces in the array then this will return as true.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param markToCheck this is the mark that is being looked for in the array.
    *@return returns a boolean value if the game is draw or not
    *@author Mika Kivennenä
    */
    public boolean checkForDraw(String[][] arrayToCheck, String markToCheck) {
        int emptySpaces = 0;
        for(int i = 0; i < arrayToCheck.length; i++) {
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if(arrayToCheck[i][j] == markToCheck) {
                    emptySpaces++;
                }
            }
        }
        if(emptySpaces > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
    *checkRows method goes through each row to see if pc or player has won the game
    *
    *Goes through each row from left to right counting the given marks to check if there's enough of them in a row to make this return as true.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param markToCheck this is the mark that is being looked for in the array.
    *@param amountRequired this is the integer value needed to win
    *@return returns a boolean value if the mark that is being checked wins or not.
    *@author Mika Kivennenä
    */
    public boolean checkRows(String[][] arrayToCheck, String markToCheck, int amountRequired) {
        for(int i = 0; i < arrayToCheck.length; i++) {
            marksInARow = 0;
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if(arrayToCheck[i][j] == markToCheck) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }

                if(marksInARow == amountRequired) {
                    gameWon = true;
                    break;
                }
            }
            if(gameWon) { break; }
        }
        return gameWon;
    }
    
    /**
    * checkColumns method goes through each column to see if pc or player has won the game
    *
    *Goes through each column from top to bottom counting the given marks to check if there's enough of them in a row to make this return as true.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param markToCheck this is the mark that is being looked for in the array.
    *@param amountRequired this is the integer value needed to win
    *@return returns a boolean value if the mark that is being checked wins or not.
    *@author Mika Kivennenä
    */
    public boolean checkColumns(String[][] arrayToCheck, String markToCheck, int amountRequired) {
        for(int i = 0; i < arrayToCheck.length; i++) {
            marksInARow = 0;
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if(arrayToCheck[j][i] == markToCheck) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }

                if(marksInARow == amountRequired) {
                    gameWon = true;
                    break;
                }
            }
            if(gameWon) { break; }
        }
        return gameWon;
    }

    /**
    * checkDiagonalLines method goes through diagonal lines
    *
    * The method goes through each diagonal line starting from top left going to bottom right 
    * Then it goes through diagonal lines from bottom left to top right
    *
    *@param arrayToCheck is the array that is being inspected
    *@param markToCheck this is the mark that is being looked for in the array.
    *@param amountRequired this is the integer value needed to win
    *@return returns a boolean value if the mark that is being checked wins or not.
    *@author Mika Kivennenä
    */
    public boolean checkDiagonalLines(String[][] arrayToCheck, String markToCheck, int amountRequired) {
        int diagonalLength = arrayToCheck.length-1;
        int posY = 0;
        int posX = 0;
        // This checks the array diagonally from top left to lower right moving right after each iteration
        for(int i = 0; i < arrayToCheck.length; i++) {
            posY = 0;
            posX = i;
            marksInARow = 0;
            for (int j = 0; j <= diagonalLength; j++) {
                if(arrayToCheck[posY][posX] == markToCheck) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }

                if(marksInARow == amountRequired) {
                    gameWon = true;
                    break;
                }
                posY++;
                posX++;
            }
            if(gameWon) { break; }
            diagonalLength--;
        }

        // This checks the array diagonally from left upper corner to right lower corner moving down after each iteration
        if(!gameWon) {
            diagonalLength = arrayToCheck.length-1;
            for(int i = 0; i < arrayToCheck.length; i++) {
                posY = i;
                marksInARow = 0;
                for (int j = 0; j <= diagonalLength; j++) {
                    if(arrayToCheck[posY][j] == markToCheck) {
                        marksInARow++;
                    } else {
                        marksInARow = 0;
                    }

                    if(marksInARow == amountRequired) {
                        gameWon = true;
                        break;
                    }

                    if(posY != arrayToCheck.length-1) {   
                        posY++;
                    }
                }
                if(gameWon) { break; }
                diagonalLength--;
            }
        }
        // Checks the diagonal line moving from bottom left corner to top right corner moving up after each iteration.
        if(!gameWon) {
            diagonalLength = arrayToCheck.length-1;
            for(int i = arrayToCheck.length-1; i >= 0; i--) {
                posY = i;
                marksInARow = 0;
                for (int j = 0; j <= diagonalLength; j++) {
                    if(arrayToCheck[posY][j] == markToCheck) {
                        marksInARow++;
                    } else {
                        marksInARow = 0;
                    }
    
                    if(marksInARow == amountRequired) {
                        gameWon = true;
                        break;
                    }
                    posY--;
                }
                if(gameWon) { break; }
                diagonalLength--;
            }
        }
        // Checks the diagonal line moving from bottom left corner to top right corner moving right after each iteration.
        if(!gameWon) {
            for(int i = 0; i < arrayToCheck.length; i++) {
                posY = arrayToCheck.length-1;
                posX = i;
                marksInARow = 0;
                for (int j = 0; j <= diagonalLength; j++) {
                    if(arrayToCheck[posY][posX] == markToCheck) {
                        marksInARow++;
                    } else {
                        marksInARow = 0;
                    }
    
                    if(marksInARow == amountRequired) {
                        gameWon = true;
                        break;
                    }
                    posY--;
                    posX++;
                }
                if(gameWon) { break; }
                diagonalLength--;
            }
        }
        return gameWon;
    }
}

    