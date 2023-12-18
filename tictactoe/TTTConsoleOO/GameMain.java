import java.util.Scanner;
/**
 * The main class for the Tic-Tac-Toe (Console-OO, non-graphics version)
 * It acts as the overall controller of the game.
 */
public class GameMain {
    // Define properties
    /** The game board */
    private Board board;
    /** The current state of the game (of enum State) */
    private State currentState;
    /** The current player (of enum Seed) */
    private Seed  currentPlayer;
    private String playerXName; // Pastikan dideklarasikan di sini
    private String playerOName; // Pastikan dideklarasikan di sini

    private static Scanner in = new Scanner(System.in);

    /** Constructor to setup the game */
    //ini aku modif dia nanya mau main lagi apa engga
    public GameMain() {
        initGame();

        // aku modif nambahin nama
        System.out.print("Enter the name for player 'X': ");
        playerXName = in.nextLine();
        System.out.print("Enter the name for player 'O': ");
        playerOName = in.nextLine();

        do {
            newGame();
            do {
                stepGame();
                board.paint();
                if (currentState == State.CROSS_WON) {
                    System.out.println(playerXName + " won!");
                } else if (currentState == State.NOUGHT_WON) {
                    System.out.println(playerOName + " won!");
                } else if (currentState == State.DRAW) {
                    System.out.println("It's a Draw!");
                }
                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            } while (currentState == State.PLAYING);

            System.out.print("Do you want to play again? (yes/no): ");
        } while (in.next().equalsIgnoreCase("yes"));

        System.out.println("Bye!");
    }


    /** Perform one-time initialization tasks */
    public void initGame() {
        board = new Board();  // allocate game-board
    }

    /** Reset the game-board contents and the current states, ready for new game */
    public void newGame() {
        board.newGame();  // clear the board contents
        currentPlayer = Seed.CROSS;   // CROSS plays first
        currentState = State.PLAYING; // ready to play
    }

    /** The currentPlayer makes one move.
     Update cells[][] and currentState. */
    // ini aku ubah inputnya jadi 1-9
    public void stepGame() {
        boolean validInput = false;  // for validating input

        do {
            System.out.print((currentPlayer == Seed.CROSS ? playerXName : playerOName) + ", enter your move (1-9): ");


            int input = in.nextInt();

            // Map input to row and column indices
            int row = (input - 1) / Board.COLS;
            int col = (input - 1) % Board.COLS;

            if (input >= 1 && input <= Board.ROWS * Board.COLS
                    && board.cells[row][col].content == Seed.NO_SEED) {
                // Update cells[][] and return the new game state after the move
                currentState = board.stepGame(currentPlayer, row, col);
                validInput = true; // input okay, exit loop
            } else {
                System.out.println("Invalid move. Please enter a number between 1 and 9.");
            }
        } while (!validInput);   // repeat until input is valid
    }

    /** The entry main() method */
    public static void main(String[] args) {
        new GameMain();  // Let the constructor do the job
    }
}