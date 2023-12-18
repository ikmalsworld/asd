import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Main {
    public enum Seed {
        CROSS, NOUGHT, NO_SEED
    }


    public enum State {
        PLAYING, DRAW, CROSS_WON, NOUGHT_WON
    }


    public static class Cell {
        // Define named constants for drawing
        public static final int SIZE = 120; // cell width/height (square)
        // Symbols (cross/nought) are displayed inside a cell, with padding from border
        public static final int PADDING = SIZE / 5;
        public static final int SEED_SIZE = SIZE - PADDING * 2;
        public static final int SEED_STROKE_WIDTH = 8; // pen's stroke width


        // Define properties (package-visible)
        /**
         * Content of this cell (Seed.EMPTY, Seed.CROSS, or Seed.NOUGHT)
         */
        Seed content;
        /**
         * Row and column of this cell
         */
        int row, col;


        /**
         * Constructor to initialize this cell with the specified row and col
         */
        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            content = Seed.NO_SEED;
        }


        /**
         * Reset this cell's content to EMPTY, ready for new game
         */
        public void newGame() {
            content = Seed.NO_SEED;
        }


        /**
         * Paint itself on the graphics canvas, given the Graphics context
         */
        public void paint(Graphics g) {
            // Use Graphics2D which allows us to set the pen's stroke
            Graphics2D g2d = (Graphics2D) g; /** memanggil fitur graphic 2d**/
            g2d.setStroke(new BasicStroke(SEED_STROKE_WIDTH,
                    BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));/**setting tampilan**/
            // Draw the Seed if it is not empty
            int x1 = col * SIZE + PADDING;
            int y1 = row * SIZE + PADDING;
            /**menentukkan Koordinat awal untuk menggambar simbol dengan posisi kolom dan baris sel,
             * serta ukuran dan padding sel. **/


            if (content == Seed.CROSS) {
                g2d.setColor(GameMain.COLOR_CROSS);  // draw a 2-line cross
                int x2 = (col + 1) * SIZE - PADDING;
                int y2 = (row + 1) * SIZE - PADDING;
                g2d.drawLine(x1, y1, x2, y2);
                g2d.drawLine(x2, y1, x1, y2);
                /**g2d.setColor(GameMain.COLOR_CROSS); menentukan warna garis.
                 g2d.drawLine(x1, y1, x2, y2); dan g2d.drawLine(x2, y1, x1, y2);**/
            } else if (content == Seed.NOUGHT) {  // draw a circle
                g2d.setColor(GameMain.COLOR_NOUGHT);
                g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE);
                /** g2d.setColor(GameMain.COLOR_NOUGHT); menentukan warna garis untuk lingkaran.
                 g2d.drawOval(x1, y1, SEED_SIZE, SEED_SIZE); digunakan untuk menggambar lingkaran tersebut.
                 **/
            }
        }
    }


    public static class Board {
        public static final int ROWS = 3;  // ROWS x COLS cells
        public static final int COLS = 3;
        // Define named constants for drawing
        public static final int CANVAS_WIDTH = Cell.SIZE * COLS;  // the drawing canvas
        public static final int CANVAS_HEIGHT = Cell.SIZE * ROWS;
        public static final int GRID_WIDTH = 8;  // Grid-line's width
        public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2; // Grid-line's half-width
        public static final Color COLOR_GRID = Color.LIGHT_GRAY;  // grid lines
        public static final int Y_OFFSET = 1;  // Fine tune for better display


        // Define properties (package-visible)
        /**
         * Composes of 2D array of ROWS-by-COLS Cell instances
         */
        Cell[][] cells;


        /**
         * Constructor to initialize the game board
         */
        public Board() {
            initGame();
        }


        /**
         * Initialize the game objects (run once)
         */
        public void initGame() {
            cells = new Cell[ROWS][COLS]; // allocate the array
            for (int row = 0; row < ROWS; ++row) { /**Loop ini berjalan melalui setiap baris dari papan permainan.**/
                for (int col = 0; col < COLS; ++col) {/**Di dalam setiap baris, loop ini berjalan melalui setiap kolom.**/
                    // Allocate element of the array
                    cells[row][col] = new Cell(row, col);
                    // Cells are initialized in the constructor
                }
            }
        }


        /**
         * Reset the game board, ready for new game
         */
        public void newGame() {
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    cells[row][col].newGame(); // clear the cell content
                }
            }
        }




































        /**
         * The given player makes a move on (selectedRow, selectedCol).
         * Update cells[selectedRow][selectedCol]. Compute and return the
         * new game state (PLAYING, DRAW, CROSS_WON, NOUGHT_WON).
         */
        public State stepGame(Seed player, int selectedRow, int selectedCol) {
            // Update game board
            cells[selectedRow][selectedCol].content = player;


            // Compute and return the new game state
            if (cells[selectedRow][0].content == player  // 3-in-the-row
                    && cells[selectedRow][1].content == player
                    && cells[selectedRow][2].content == player
                    || cells[0][selectedCol].content == player // 3-in-the-column
                    && cells[1][selectedCol].content == player
                    && cells[2][selectedCol].content == player
                    || selectedRow == selectedCol     // 3-in-the-diagonal
                    && cells[0][0].content == player
                    && cells[1][1].content == player
                    && cells[2][2].content == player
                    || selectedRow + selectedCol == 2 // 3-in-the-opposite-diagonal
                    && cells[0][2].content == player
                    && cells[1][1].content == player
                    && cells[2][0].content == player) {
                return (player == Seed.CROSS) ? State.CROSS_WON : State.NOUGHT_WON;
                /**it means that the current player (player) has won the game. It returns State.CROSS_WON if the player is "X" (Seed.CROSS)
                 *  or State.NOUGHT_WON if the player is "O" (Seed.NOUGHT).**/
            } else {
                // Nobody win. Check for DRAW (all cells occupied) or PLAYING.
                for (int row = 0; row < ROWS; ++row) {
                    for (int col = 0; col < COLS; ++col) {
                        if (cells[row][col].content == Seed.NO_SEED) {
                            return State.PLAYING; // still have empty cells
                        }
                        /**t iterates through all the cells on the game board.
                         *  If it finds any cell that is still empty (content is Seed.NO_SEED),
                         *
                         *  it means the game is still ongoing (State.PLAYING).**/
                    }
                }
                return State.DRAW; // no empty cell, it's a draw
            }
        }


        /**
         * Paint itself on the graphics canvas, given the Graphics context
         */
        public void paint(Graphics g) {
            // Draw the grid-lines
            g.setColor(COLOR_GRID);
            for (int row = 1; row < ROWS; ++row) {
                /**0: Ini adalah koordinat x (horizontal) dari awal garis horizontal**/
                /** nilai Cell.SIZE, row (baris saat ini), dan GRID_WIDHT_HALF (setengah lebar garis grid).**/
                /**  Panjangnya adalah sepanjang papan permainan, yaitu CANVAS_WIDTH.
                 *  Dikurangi satu untuk menghindari tumpang tindih dengan tepi kanan papan.**/
                g.fillRoundRect(0, Cell.SIZE * row - GRID_WIDHT_HALF,
                        CANVAS_WIDTH - 1, GRID_WIDTH,
                        GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < COLS; ++col) {
                g.fillRoundRect(Cell.SIZE * col - GRID_WIDHT_HALF, 0 + Y_OFFSET,
                        GRID_WIDTH, CANVAS_HEIGHT - 1,
                        GRID_WIDTH, GRID_WIDTH);
            }


            // Draw all the cells
            for (int row = 0; row < ROWS; ++row) {
                for (int col = 0; col < COLS; ++col) {
                    cells[row][col].paint(g);  // ask the cell to paint itself
                }
            }
        }
    }


    public static class GameMain extends JPanel {
        private String player1Name;
        private String player2Name;
        // to prevent serializable warning


        // Define named constants for the drawing graphics
        public static final String TITLE = "Tic Tac Toe";
        public static final Color COLOR_BG = Color.white;
        public static final Color COLOR_BG_STATUS = new Color(216, 216, 216);
        public static final Color COLOR_CROSS = new Color(239, 105, 80);  // Red
        public static final Color COLOR_NOUGHT = new Color(64, 154, 225); // Blue
        public static final Font FONT_STATUS = new Font("OCR A Extended", Font.PLAIN, 14);


        // Define game objects
        private Board board;         // the game board
        private State currentState;  // the current state of the game
        private Seed currentPlayer;  // the current player
        private JLabel statusBar;    // for displaying status message
          // Reset button

        /**
         * Constructor to setup the UI and game components
         */
        public GameMain() {
            /**Kode ini menampilkan dialog input menggunakan JOptionPane yang meminta nama untuk
             * Pemain 1 (yang bermain sebagai Crosses). Hasil input dari **/
            player1Name = JOptionPane.showInputDialog("Enter name for Player 1 (Crosses):");
            player2Name = JOptionPane.showInputDialog("Enter name for Player 2 (Noughts):");


            // Ensure names are not empty
            // player1Name.trim().isEmpty(): Kondisi kedua memeriksa apakah player1Name setelah di-"trim" (menghilangkan spasi di awal dan akhir string) kosong (empty).
            // Ini berarti pemain tidak memasukkan karakter apa pun atau hanya memasukkan spasi saat mengisi nama.
            if (player1Name == null || player1Name.trim().isEmpty()) player1Name = "Player 1";
            if (player2Name == null || player2Name.trim().isEmpty()) player2Name = "Player 2";








            // This JPanel fires MouseEvent
            super.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {  // mouse-clicked handler
                    int mouseX = e.getX();
                    int mouseY = e.getY();
                    // Get the row and column clicked
                    int row = mouseY / Cell.SIZE;
                    int col = mouseX / Cell.SIZE;
                    //kondisi saat bermain
                    if (currentState == State.PLAYING) {
                        if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                                && board.cells[row][col].content == Seed.NO_SEED) {
                            // Update cells[][] and return the new game state after the move
                            currentState = board.stepGame(currentPlayer, row, col);


                            // Switch player
                            currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
                            /**Setelah pemain saat ini melakukan langkah,
                             * giliran akan beralih ke pemain berikutnya (Cross ke Nought atau sebaliknya).**/
                        }
                    } else {        // game over
                        newGame();  // restart the game
                    }
                    // Refresh the drawing canvas
                    repaint();  // Callback paintComponent().
                }
            });


            // Setup the status bar (JLabel) to display status message
            statusBar = new JLabel();
            statusBar.setFont(FONT_STATUS);
            statusBar.setBackground(COLOR_BG_STATUS);
            statusBar.setOpaque(true);
            statusBar.setPreferredSize(new Dimension(300, 30));
            statusBar.setHorizontalAlignment(JLabel.LEFT);
            statusBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 12));





            super.setLayout(new BorderLayout());
            super.add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
            super.setPreferredSize(new Dimension(Board.CANVAS_WIDTH, Board.CANVAS_HEIGHT + 30));
            // account for statusBar in height
            super.setBorder(BorderFactory.createLineBorder(COLOR_BG_STATUS, 2, false));


            // Set up Game
            initGame();
            newGame();
        }



        /**
         * Initialize the game (run once)
         */
        public void initGame() {
            board = new Board();  // allocate the game-board
        }


        /**
         * Reset the game-board contents and the current-state, ready for new game
         */
        public void newGame() {
            for (int row = 0; row < Board.ROWS; ++row) {
                for (int col = 0; col < Board.COLS; ++col) {
                    board.cells[row][col].content = Seed.NO_SEED; // all cells empty
                }
            }
            currentPlayer = Seed.CROSS;    // cross plays first
            currentState = State.PLAYING;  // ready to play
        }




        /**
         * Custom painting codes on this JPanel
         */
        @Override
        public void paintComponent(Graphics g) {  // Callback via repaint()
            super.paintComponent(g);
            setBackground(COLOR_BG); // set its background color


            board.paint(g);  // ask the game board to paint itself


            //status
            if (currentState == State.PLAYING) {
                statusBar.setForeground(Color.BLACK);
                statusBar.setText((currentPlayer == Seed.CROSS) ? player1Name + "'s Turn (X)" : player2Name + "'s Turn (O)");
            } else if (currentState == State.DRAW) {
                statusBar.setText("It's a Draw! Click to play again.");
            } else if (currentState == State.CROSS_WON) {
                statusBar.setText(player1Name + " (X) Won! Click to play again.");
            } else if (currentState == State.NOUGHT_WON) {
                statusBar.setText(player2Name + " (O) Won! Click to play again.");
            }
            final String TITLE = "Tic Tac Toe"; // Define TITLE here
        }


        public static void main(String[] args) {
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    JFrame frame = new JFrame(GameMain.TITLE);
                    frame.setContentPane(new GameMain());
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            });
        }
    }
}

