/*
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2023/2024
 KELOMPOK 1 :
 - Arayzi Rayyansyah 5026221194
 - Muhammad Hasan Kamal 5026221173
 - Celine Auriel 5026221004
 - Devy Relliani Saffiyah 5026221189
 - Dia Naufal Abiyyu Tsaqif 5026221042
 */

package sudoku;

import java.util.Random;
import java.util.Stack;

public class Puzzle {
    // All variables have package access
    // The numbers on the puzzle
    public static final int GRID_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;
    int[][] numbers = new int[GRID_SIZE][GRID_SIZE];
    Cell[][] puzzleCell = new Cell[GRID_SIZE][GRID_SIZE];
    // The clues - isGiven (no need to guess) or need to guess
    boolean[][] isGiven = new boolean[GRID_SIZE][GRID_SIZE];
    // Constructor
    public Puzzle() {
        super();
    }

    // Generate a new puzzle given the number of cells to be guessed, which can be used
    //  to control the difficulty level.
    // This method shall set (or update) the arrays numbers and isGiven

    public void newPuzzle(int levelGame) {
        numbers = new int[GRID_SIZE][GRID_SIZE];
        //sudoku generator initial value
        int initialvalue = 10;
        int initialcount = 0;
        do {
            for (int i=0;i<GRID_SIZE;i++){
                for (int j=0;j<GRID_SIZE;j++){
                    numbers[i][j]=0;
                    puzzleCell[i][j]= new Cell(i,j,numbers[i][j]);
                    isGiven[i][j] = false;
                }
            }
            while (initialcount < initialvalue) {
                int ranrow = getRandomNumber(0, 9);
                int rancol = getRandomNumber(0, 9);
                int ranvalue = getRandomNumber(1, 9);
                if (numbers[ranrow][rancol] == 0) {
                    if (validValue(ranrow, rancol, ranvalue)) {
                        numbers[ranrow][rancol] = ranvalue;
                        initialcount += 1;
                        Cell temp = new Cell(ranrow, rancol,numbers[ranrow][rancol]);
                        temp.islocked = true;
                        puzzleCell[ranrow][rancol] = temp;
                    }
                }
            }
        } while (!solve());
        int givenlimit = 0;
        int rowcolgivenlimit=0;
        if (levelGame==1){
            givenlimit = getRandomNumber(50,5);
            rowcolgivenlimit = 8;
        }
        else if (levelGame==2){
            givenlimit = getRandomNumber(35,10);
            rowcolgivenlimit = 6;
        }
        else if (levelGame==3){
            givenlimit = getRandomNumber(20,5);
            rowcolgivenlimit = 4;
        }
        int givencount = 0;
        int[] columnlimit = new int[9];
        int[] rowlimit = new int[9];
        int counter=0;
        while (givencount<givenlimit){
            System.out.println(givencount);
            int randomrow = getRandomNumber(0,9);
            int randomcol = getRandomNumber(0,9);
            if (isGiven[randomrow][randomcol]==false){
                if (columnlimit[randomcol]<rowcolgivenlimit && rowlimit[randomrow]<rowcolgivenlimit){
                    isGiven[randomrow][randomcol]=true;
                    columnlimit[randomcol]+=1;
                    rowlimit[randomrow]+=1;
                    givencount+=1;
                }
            }
        }
    }
    public int getRandomNumber(int min, int max) {
        Random random = new Random();
        int randnum = random.nextInt(max)+min;
        return randnum;
    }
    public boolean validValue(int row, int col, int value) {
        boolean valid = true;
        for (int i=0;i<GRID_SIZE;i++){
            if (numbers[i][col]==value) {
                valid=false;
                break;
            }
        }
        for (int i=0;i<GRID_SIZE;i++){
            if (numbers[row][i]==value) {
                valid = false;
                break;
            }
        }
        int subgridrow = row-row%3;
        int subgridcol = col-col%3;
        for (int i=0;i<SUBGRID_SIZE;i++){
            for (int j=0;j<SUBGRID_SIZE;j++){
                if (numbers[subgridrow+i][subgridcol+j]==value){
                    valid=false;
                    break;
                }
            }
        }
        return valid;
    }
    public boolean solve(){
        Stack<Cell> stack = new Stack<>();
        int curRow = 0;
        int curCol = 0;
        int curValue =1;
        int time = 0 ;
        while(stack.size() < 81){
            time++;
            if(puzzleCell[curRow][curCol].islocked){
                Cell lockedCell = puzzleCell[curRow][curCol];
                stack.push(lockedCell);
                curCol+=1;
                if (curCol>=9){
                    curRow+=1;
                    curCol=0;
                }
                continue;
            }
            for (;curValue <= 10 ; curValue++){
                if (validValue(curRow, curCol, curValue)){
                    break;
                }
            }
            if(curValue <= 9){
                Cell cell = new Cell(curRow, curCol, curValue);
                numbers[curRow][curCol] = curValue;
                stack.push(cell);
                curCol+=1;
                if (curCol>=9){
                    curRow+=1;
                    curCol=0;
                }
                curValue = 1;
            }else{
                System.out.println("cek2 "+time);
                if (stack.size() > 0) {
                    // Assign to a Cell variable the top of the stack (stack.pop())
                    Cell cell = stack.pop();
                    // while the Cell is locked
                    while (cell.islocked) {
                        // if stack size is greater than 0
                        if (stack.size() > 0) {
                            // assign to the Cell variable the top of the stack (i.e. pop)
                            cell = stack.pop();
                        } else {
                            // print out the number of steps (time)
                            // return false (no solution found)
                            System.out.println("Number of steps: " + time);
                            return false;
                        }
                    }
                    // assign to curRow the row value of the Cell
                    curRow = cell.row;
                    // assign to curCol the col value of the Cell
                    curCol = cell.col;
                    // assign to curValue the value of the Cell + 1
                    curValue = cell.number + 1;
                    // set the value of the board Cell at curRow, curCol to 0
                    numbers[curRow][curCol] =  0;
                } else {
                    // print out the number of steps (time)
                    // return false (no solution found)
                    System.out.println("Number of steps: " + time);
                    return false;
                }
            }
        }
        return true;
    }
}