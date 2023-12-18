package tictactoe.TTTConsoleNonOO;
import java.util.Scanner;
import javax.swing.JOptionPane;
/**
 * Tic-Tac-Toe: Two-player, console-based, non-graphics, non-OO version.
 * All variables/methods are declared as static (i.e., class)
 *  in this non-OO version.
 */
public class TTTConsoleNonOO {
   // Define named constants for:
   //  1. Player: using CROSS and NOUGHT
   //  2. Cell contents: using CROSS, NOUGHT and NO_SEED
   public static final int CROSS   = 0;
   public static final int NOUGHT  = 1;
   public static final int NO_SEED = 2;

   // The game board
   public static final int ROWS = 3, COLS = 3;  // number of rows/columns
   public static int[][] board = new int[ROWS][COLS]; // EMPTY, CROSS, NOUGHT

   // The current player
   public static int currentPlayer;  // CROSS, NOUGHT

   // Define named constants to represent the various states of the game
   public static final int PLAYING    = 0;
   public static final int DRAW       = 1;
   public static final int CROSS_WON  = 2;
   public static final int NOUGHT_WON = 3;
   // The current state of the game
   public static int currentState;

   public static Scanner in = new Scanner(System.in); // the input Scanner

   /** The entry main method (the program starts here) */
   public static void main(String[] args) {
      // Initialize the board, currentState and currentPlayer
      initGame();

      // Play the game once
      do {
            stepGame();
            paintBoard();

            if (currentState == CROSS_WON) {
                JOptionPane.showMessageDialog(null, "'X' won!\nThank you for playing!");
                break;
            } else if (currentState == NOUGHT_WON) {
                JOptionPane.showMessageDialog(null, "'O' won!\nThank you for playing!");
                break;
            } else if (currentState == DRAW) {
                JOptionPane.showMessageDialog(null, "It's a Draw!\nThank you for playing!");
                break;
            }
         // Switch currentPlayer
         currentPlayer = (currentPlayer == CROSS) ? NOUGHT : CROSS;
      } while (currentState == PLAYING); // repeat if not game over

      // prompt to play again
      while (true) {
         initGame();
         int response = JOptionPane.showConfirmDialog(null, "Play again?", "Tic Tac Toe", JOptionPane.YES_NO_OPTION);
         if (response != JOptionPane.YES_OPTION) {
             JOptionPane.showMessageDialog(null, "Bye!");
             System.exit(0);
         }
     }
 }

   /** Initialize the board[][], currentState and currentPlayer for a new game*/
   public static void initGame() {
      for (int row = 0; row < ROWS; ++row) {
         for (int col = 0; col < COLS; ++col) {
            board[row][col] = NO_SEED;  // all cells empty
         }
      }
      currentPlayer = CROSS;   // cross plays first
      currentState  = PLAYING; // ready to play
   }

   /** The currentPlayer makes one move (one step).
       Update board[selectedRow][selectedCol] and currentState. */
       public static void stepGame() {
         boolean validInput = false;
         do {
             int position = Integer.parseInt(JOptionPane.showInputDialog(getPlayerMessage() + "Enter your move (1-9): ")) - 1;
     
             int row = position / COLS;
             int col = position % COLS;
     
             if (position >= 0 && position < ROWS * COLS && board[row][col] == NO_SEED) {
                 currentState = stepGameUpdate(currentPlayer, row, col);
                 validInput = true;
             } else {
                 JOptionPane.showMessageDialog(null, "Invalid move. Try again...");
             }
         } while (!validInput); //request if input is invalid
     }

 public static String getPlayerMessage() {
   return (currentPlayer == CROSS) ? "Player 'X', " : "Player 'O', ";
}

 
   /**
    * Helper function of stepGame().
    * The given player makes a move at (selectedRow, selectedCol).
    * Update board[selectedRow][selectedCol]. Compute and return the
    * new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
    * @return new game state
    */
    public static int stepGameUpdate(int player, int selectedRow, int selectedCol) {
      board[selectedRow][selectedCol] = player;

      if (board[selectedRow][0] == player && board[selectedRow][1] == player && board[selectedRow][2] == player ||
          board[0][selectedCol] == player && board[1][selectedCol] == player && board[2][selectedCol] == player ||
          selectedRow == selectedCol && board[0][0] == player && board[1][1] == player && board[2][2] == player ||
          selectedRow + selectedCol == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player) {
          return (player == CROSS) ? CROSS_WON : NOUGHT_WON;
      } else {
          for (int row = 0; row < ROWS; ++row) {
              for (int col = 0; col < COLS; ++col) {
                  if (board[row][col] == NO_SEED) {
                      return PLAYING;
                  }
              }
          }
          return DRAW;
      }
  }

   /** Print the game board */
   public static void paintBoard() {
      for (int row = 0; row < ROWS; ++row) {
          for (int col = 0; col < COLS; ++col) {
              paintCell(board[row][col]);
              if (col != COLS - 1) {
                  System.out.print("|");
              }
          }
          System.out.println();
          if (row != ROWS - 1) {
              System.out.println("-----------");
          }
      }
      System.out.println();
  }

   /** Print a cell having the given content */
   public static void paintCell(int content) {
      switch (content) {
          case CROSS:   System.out.print(" X "); break;
          case NOUGHT:  System.out.print(" O "); break;
          case NO_SEED: System.out.print("   "); break;
      }
   }
}
