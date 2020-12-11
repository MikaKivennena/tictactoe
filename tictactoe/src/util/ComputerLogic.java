package tictactoe.src.util;

/**
* ComputerLogic class contains all the code that's being used to prevent player from winning
*
*This class has several methods that are used by one single method that is called in the main class of this app
*I wanted to avoid having several different methods being called in the main.
*
*@author  Mika Kivennenä
*/
public class ComputerLogic {
    static int marksInARow = 0;
    static int posY = 0;
    static int posX = 0;
    static boolean markPlaced = false;
    static int diagonalLength;

    /**
    *placeMark method places computer mark to a slot determined by multiple methods.
    *
    *This class has multiple methods to check the arrays if player has a chance to win.
    *This placeMark method tries to run each of the methods to go through array as long as
    *it has not placed it's own mark down yet. 
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is the player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@param requiredAmount is the amount needed for a win. This is used to calculate different positions for mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public String[][] placeMark(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark, int requiredAmount) {
        markPlaced = false;
        
        // Try to win the match before trying to block player from winning.
        if(!markPlaced) {
            arrayToCheck = tryToWin(arrayToCheck, playerMark, computerMark, emptyMark, requiredAmount);
        }
        // if a mark has not been placed this turn, check the rows to see if there's a need to place a mark
        if(!markPlaced) {
            arrayToCheck = markRow(arrayToCheck, playerMark, computerMark, emptyMark, requiredAmount);
        }
        // if a mark has not been placed this turn, check the columns to see if there's a need to place a mark
        if(!markPlaced) {
            arrayToCheck = markColumn(arrayToCheck, playerMark, computerMark, emptyMark, requiredAmount);
        } 
        // if a mark has not been placed this turn, check the check the diagonal lines to see if there's a need to place a mark
        if(!markPlaced) {
            arrayToCheck = markDiagonalTopLeft(arrayToCheck, playerMark, computerMark, emptyMark, requiredAmount);
        } 
        // if a mark has not been placed this turn, check the diagonal lines to see if there's a need to place a mark
        if(!markPlaced) {
            arrayToCheck = markDiagonalBotLeft(arrayToCheck, playerMark, computerMark, emptyMark, requiredAmount);
        } 
        // If there's no immediate threat of player winning, randomly place a mark.
        if(!markPlaced) {
            arrayToCheck = randomizeSlot(arrayToCheck, playerMark, computerMark, emptyMark);
        }
        return arrayToCheck;
    }
    /**
    *markRow method places computer mark if there's a chance for player to win.
    *
    *This method places a computer mark on a horizontal line to block the player from winning if there's a need for it.
    *Goes through each row and calculates if player has half or more marks in a row from the required amount then places a mark trying to prevent player from having a clear chance to win.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is human player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@param requiredAmount is the amount needed for a win. This is used to calculate different positions for mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public static String[][] markRow(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark, int requiredAmount) {
        for(int i = 0; i < arrayToCheck.length; i++) {
            marksInARow = 0;
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if(arrayToCheck[i][j] == playerMark) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }
                
                try {
                    // If theres a space between two player marks, add your own mark.
                    if(arrayToCheck[i][j] == playerMark && arrayToCheck[i][j+1] == emptyMark && arrayToCheck[i][j+2] == playerMark) {
                        arrayToCheck[i][j+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    // if there's two player marks in a row, if possible place one in the end of that row.
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[i][j+1] == emptyMark ) {
                        arrayToCheck[i][j+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    // If not possible to place mark at the end of the row. Place it at the start of it if possible.
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[i][j+1] != emptyMark  && arrayToCheck[i][j-marksInARow] == emptyMark) {
                        arrayToCheck[i][j-marksInARow] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    
                    
                } catch(Exception e){}
            }
            if(markPlaced) {break;}
        }
        return arrayToCheck;
    }

    /**
    *markColumn method places computer mark if there's a chance for player to win.
    *
    *This method places a computer mark on a vertical line to block the player from winning if there's a need for it.
    *Goes through each column and calculates if player has half or more marks in a row from the required amount then places a mark trying to prevent player from having a clear chance to win.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is human player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@param requiredAmount is the amount needed for a win. This is used to calculate different positions for mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public static String[][] markColumn(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark, int requiredAmount) {
        for(int i = 0; i < arrayToCheck.length; i++) {
            marksInARow = 0;
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if(arrayToCheck[j][i] == playerMark) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }
                try {
                    // If theres a space between two player marks, add your own mark.
                    if(arrayToCheck[j][i] == playerMark && arrayToCheck[j+1][i] == emptyMark && arrayToCheck[j+2][i] == playerMark) {
                        arrayToCheck[j+1][i] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    // if there's two player marks in a row, if possible place one in the end of that row.
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[j+1][i] == emptyMark) {
                        arrayToCheck[j+1][i] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    // If not possible to place mark at the end of the row. Place it at the start of it if possible.
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[j+1][i] != emptyMark && arrayToCheck[j-marksInARow][i] == emptyMark) {
                        arrayToCheck[j-marksInARow][i] = computerMark;
                        markPlaced = true;
                        break;
                    }
                } catch(Exception e){}
            }
            if(markPlaced) {break;}
        }
        return arrayToCheck;
    }
    /**
    *markDiagonalTopLeft method places computer mark if there's a chance for player to win.
    *
    *This method places a computer mark on a diagonal line to block the player from winning if there's a need for it.
    *Goes through each diagonal line and calculates if player has half or more marks in a row from the required amount then places a mark trying to prevent player from having a clear chance to win.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is human player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@param requiredAmount is the amount needed for a win. This is used to calculate different positions for mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public static String[][] markDiagonalTopLeft(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark, int requiredAmount) {
        diagonalLength = arrayToCheck.length-1;
        for(int i = 0; i < arrayToCheck.length; i++) {
            posY = 0;
            posX = i;
            marksInARow = 0;
            for (int j = 0; j <= diagonalLength; j++) {
                if(arrayToCheck[posY][posX] == playerMark) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }
                        
                try {
                    if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY][posX+1] == emptyMark) {
                        arrayToCheck[posY+1][posX+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    else if(arrayToCheck[posY][posX] == playerMark && arrayToCheck[posY+1][posX+1] == emptyMark && arrayToCheck[posY+2][posX+2] == playerMark) {
                        arrayToCheck[posY+1][posX+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY+1][posX+1] != emptyMark && arrayToCheck[posY-marksInARow][posX-marksInARow] == emptyMark) {
                        arrayToCheck[posY-marksInARow][posX-marksInARow] = computerMark;
                        markPlaced = true;
                        break;
                    }
                } catch(Exception e){}
                posY++;
                posX++;
            }

            if(markPlaced) {break;}
            diagonalLength--;
        }
        
        // check the diagonal lines from top left to bottom right. Moving from top left to bottom left to see if there's a need to place a mark
        if(!markPlaced) {
            diagonalLength = arrayToCheck.length-1;
            for(int i = 0; i < arrayToCheck.length; i++) {
                posY = i;
                marksInARow = 0;
                for (int j = 0; j <= diagonalLength; j++) {
                    if(arrayToCheck[posY][j] == playerMark) {
                        marksInARow++;
                    } else {
                        marksInARow = 0;
                    }

                    try {
                        if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY+1][j+1] == emptyMark) {
                            arrayToCheck[posY+1][j+1] = computerMark;
                            markPlaced = true;
                            break;
                        }
                        else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY+1][j+1] != emptyMark && arrayToCheck[posY-marksInARow][j-marksInARow] == emptyMark) {
                            arrayToCheck[posY-marksInARow][j-marksInARow] = computerMark;
                            markPlaced = true;
                            break;
                        }
                        else if(arrayToCheck[posY][j] == playerMark && arrayToCheck[posY+1][j+1] == emptyMark && arrayToCheck[posY+2][j+2] == playerMark) {
                            arrayToCheck[posY+1][j+1] = computerMark;
                            markPlaced = true;
                            break;
                        }
                    } catch(Exception e){}

                    if(posY != arrayToCheck.length-1) {   
                        posY++;
                    }
                }
                if(markPlaced) {break;}
                diagonalLength--;
            }
        }
        return arrayToCheck;
    }
    /**
    *markDiagonalBotLeft method places computer mark if there's a chance for player to win.
    *
    *This method places computer mark after going through diagonal lines from bottom left to top right corner if there is a need for one.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is human player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@param requiredAmount is the amount needed for a win. This is used to calculate different positions for mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public static String[][] markDiagonalBotLeft(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark, int requiredAmount) {
        // Checks the diagonal line moving from bottom left corner to top right corner moving up after each iteration.
        diagonalLength = arrayToCheck.length-1;
        for(int i = arrayToCheck.length-1; i >= 0; i--) {
            posY = i;
            marksInARow = 0;
            for (int j = 0; j <= diagonalLength; j++) {
                if(arrayToCheck[posY][j] == playerMark) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }
                    
                try {
                    if(arrayToCheck[posY][j] == playerMark && arrayToCheck[posY-1][j+1] == emptyMark && arrayToCheck[posY-2][j+2] == playerMark) {
                        arrayToCheck[posY-1][j+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY-1][j+1] == emptyMark) {
                        arrayToCheck[posY-1][j+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY-1][j+1] != emptyMark && arrayToCheck[posY+marksInARow][j-marksInARow] == emptyMark) {
                        arrayToCheck[posY+marksInARow][j-marksInARow] = computerMark;
                        markPlaced = true;
                        break;
                    }
                } catch(Exception e){}
                    
                posY--;
            }
            if(!markPlaced) { break; }
            diagonalLength--;
        }

        // Checks the diagonal line moving from bottom left corner to top right corner moving up after each iteration.
        if(!markPlaced) {
            for(int i = 0; i < arrayToCheck.length; i++) {
                posY = arrayToCheck.length-1;
                posX = i;
                marksInARow = 0;
                for (int j = 0; j <= diagonalLength; j++) {
                    if(arrayToCheck[posY][posX] == playerMark) {
                        marksInARow++;
                    } else {
                        marksInARow = 0;
                    }
    
                    try {
                        if(arrayToCheck[posY][posX] == playerMark && arrayToCheck[posY-1][posX+1] == emptyMark && arrayToCheck[posY-2][posX+2] == playerMark) {
                            arrayToCheck[posY-1][posX+1] = computerMark;
                            markPlaced = true;
                            break;
                        }
                        else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY-1][posX+1] == emptyMark) {
                            arrayToCheck[posY-1][posX+1] = computerMark;
                            markPlaced = true;
                            break;
                        }
                        else if(marksInARow >= ((requiredAmount/2)+1) && arrayToCheck[posY-1][posX+1] != emptyMark && arrayToCheck[posY+marksInARow][posX-marksInARow] == emptyMark) {
                            arrayToCheck[posY+marksInARow][posX-marksInARow] = computerMark;
                            markPlaced = true;
                            break;
                        }
                    } catch(Exception e){}
                    posY--;
                    posX++;
                }
                if(markPlaced) { break; }
                diagonalLength--;
            }
        }
        return arrayToCheck;
    }

    /**
    *randomizeSlot method places computer mark to a randomly assigned slot.
    *
    *This method places to computer mark to a randomly decided slot if it hasn't detected player being close to winning.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is human player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public static String[][] randomizeSlot(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark) {
        while(!markPlaced) {
            int row = (int) (Math.random() * arrayToCheck.length);
            int column = (int) (Math.random() * arrayToCheck.length);

            if(arrayToCheck[row][column] == emptyMark) {
                arrayToCheck[row][column] = computerMark;
                markPlaced = true;
            }
        }
        return arrayToCheck;
    }

    /**
    *tryToWin method calculates if the computer is close to winning horizontally or vertically and then places it's mark if it can win.
    *
    *This method places to computer mark to a randomly decided slot if it hasn't detected player being close to winning.
    *
    *@param arrayToCheck is the array that is being inspected
    *@param playerMark is human player mark that computer is trying to prevent from winning
    *@param computerMark is the computers mark that is being placed after this method has run
    *@param emptyMark is the empty mark that allows computer to place it's mark
    *@param requiredAmount is the amount needed for a win. This is used to calculate different positions for mark
    *@return returns a 2D-array that acts as the gameboard
    *@author Mika Kivennenä
    */
    public static String[][] tryToWin(String[][] arrayToCheck, String playerMark, String computerMark, String emptyMark, int requiredAmount) {
        // Try to win by going through each row from left to right
        for(int i = 0; i < arrayToCheck.length; i++) {
            marksInARow = 0;
            for (int j = 0; j < arrayToCheck[i].length; j++) {
                if(arrayToCheck[i][j] == computerMark) {
                    marksInARow++;
                } else {
                    marksInARow = 0;
                }
                try {
                    // if there's two marks in a row, if possible place one in the end of that row.
                    if(marksInARow == (requiredAmount-1) && arrayToCheck[i][j+1] == emptyMark ) {
                        arrayToCheck[i][j+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    // if not possible to add in front of the row. Try to add to the start of the row.
                    else if(marksInARow == (requiredAmount-1) && arrayToCheck[i][j+1] != emptyMark  && arrayToCheck[i][j-marksInARow] == emptyMark) {
                        arrayToCheck[i][j-marksInARow] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    // If theres a space between two marks, add your own mark.
                    else if(arrayToCheck[i][j] == computerMark && arrayToCheck[i][j+1] == emptyMark && arrayToCheck[i][j+2] == computerMark) {
                        arrayToCheck[i][j+1] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    
                    
                } catch(Exception e){}
            }
            if(markPlaced) {break;}
        }
        // If mark is not placed then try to place it by going through each column
        if(!markPlaced) {
            for(int i = 0; i < arrayToCheck.length; i++) {
                marksInARow = 0;
                for (int j = 0; j < arrayToCheck[i].length; j++) {
                    if(arrayToCheck[j][i] == computerMark) {
                        marksInARow++;
                    } else {
                        marksInARow = 0;
                    }
                    try {
                        // if not possible to add in front of the row. Try to add to the start of the row.
                        if(marksInARow == (requiredAmount-1) && arrayToCheck[j+1][i] == emptyMark) {
                            arrayToCheck[j+1][i] = computerMark;
                            markPlaced = true;
                            break;
                        }
                        else if(marksInARow == (requiredAmount-1) && arrayToCheck[j+1][i] != emptyMark && arrayToCheck[j-marksInARow][i] == emptyMark) {
                            arrayToCheck[j-marksInARow][i] = computerMark;
                            markPlaced = true;
                            break;
                        }
                        // If theres a space between two marks, add your own mark.
                        else if(arrayToCheck[j][i] == computerMark && arrayToCheck[j+1][i] == emptyMark && arrayToCheck[j+2][i] == computerMark) {
                        arrayToCheck[j+1][i] = computerMark;
                        markPlaced = true;
                        break;
                    }
                    } catch(Exception e){}
                }
                if(markPlaced) {break;}
            }
        }
        return arrayToCheck;
    }
}