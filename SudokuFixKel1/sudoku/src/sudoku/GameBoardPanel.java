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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GameBoardPanel extends JPanel {
    public static final int GRID_SIZE = 9;
    public static final int SUBGRID_SIZE = 3;
    private static final long serialVersionUID = 1L;  // to prevent serial warning

    static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static int screenHeight = screenSize.height;
    int screenWidth = screenSize.width;
    // Define named constants for UI sizes
    public static int CELL_SIZE = screenHeight/14;   // Cell width/height in pixels
    public static int BOARD_WIDTH  = CELL_SIZE * GRID_SIZE;
    public static final int BOARD_HEIGHT = CELL_SIZE * GRID_SIZE;
    // Board width/height in pixels

    // Define properties
    /** The game board composes of 9x9 Cells (customized JTextFields) */
    private Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];
    /** It also contains a Puzzle with array numbers and isGiven */
    private Puzzle puzzle = new Puzzle();
    private String diff="Easy";
    private int mistake=0;
    JPanel sudokugrid = new JPanel();
    JLabel Difficultylabel = new JLabel("Difficulty: "+diff);
    JLabel mistakeslabel = new JLabel("Mistakes: "+mistake+"/9");
    JPanel scorebox = new JPanel();
    /** Constructor */
    public GameBoardPanel() {
        super.setLayout(new BorderLayout());
        setOpaque(false);
        scorebox.setOpaque(false);
        scorebox.setLayout(new FlowLayout(FlowLayout.CENTER,20,0));
        scorebox.add(Difficultylabel);
        scorebox.add(mistakeslabel);
        scorebox.setPreferredSize(new Dimension(BOARD_WIDTH,50));
        super.add(sudokugrid, BorderLayout.CENTER);
        super.add(scorebox, BorderLayout.NORTH);
        Difficultylabel.setFont(new Font("Arial",Font.PLAIN,25));
        mistakeslabel.setFont(new Font("Arial",Font.PLAIN,25));
        sudokugrid.setPreferredSize(new Dimension(BOARD_WIDTH,BOARD_HEIGHT));
        sudokugrid.setBorder(new LineBorder(Color.black,4));
        sudokugrid.setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));  // JPanel

        // Allocate the 2D array of Cell, and added into JPanel.
        for (int row = 0; row < SUBGRID_SIZE; row++) {
            for (int col = 0; col < SUBGRID_SIZE; col++) {
                JPanel subgridpanel = new JPanel();
                subgridpanel.setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));
                subgridpanel.setBorder(new LineBorder(Color.black,2));
                for (int i=0; i < SUBGRID_SIZE; i++) {
                    for (int j=0; j < SUBGRID_SIZE; j++) {
                        cells[row*3+i][col*3+j] = new Cell(row*3+i, col*3+j);
                        subgridpanel.add(cells[row*3+i][col*3+j]);
                    }
                }
                sudokugrid.add(subgridpanel);
            }
        }

        // [TODO 3] Allocate a common listener as the ActionEvent listener for all the
        //  Cells (JTextFields)


        CellInputListener listener = new CellInputListener();



        // [TODO 4] Adds this common listener to all editable cells
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].isEditable()) {
                    cells[row][col].addActionListener(listener);   // For all editable rows and cols
                }
            }
        }

        super.setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
    }

    /**
     * Generate a new puzzle; and reset the gameboard of cells based on the puzzle.
     * You can call this method to start a new game.
     */
    public void newGame(int levelGame) {

        // Generate a new puzzle
        puzzle.newPuzzle(levelGame);
        if (levelGame==1){
            diff="Easy";
        }
        else if (levelGame==2){
            diff="Medium";
        }
        else if (levelGame==3){
            diff="Hard";
        }
        Difficultylabel.setText("Difficulty: "+diff);
        mistake=0;
        mistakeslabel.setText("Mistakes: "+mistake+"/9");

        // Initialize all the 9x9 cells, based on the puzzle.
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], puzzle.isGiven[row][col]);
            }
        }
    }


    /**
     * Return true if the puzzle is solved
     * i.e., none of the cell have status of TO_GUESS or WRONG_GUESS
     */
    public boolean isSolved() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (cells[row][col].status == false) {
                    return false;
                }
            }
        }
        return true;
    }


    private class CellInputListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Get a reference of the JTextField that triggers this action event
            Cell sourceCell = (Cell)e.getSource();


            // Retrieve the int entered
            int numberIn = Integer.parseInt(sourceCell.getText());
            // For debugging
            System.out.println("You entered " + numberIn);

            /*
             * [TODO 5] (later - after TODO 3 and 4)
             * Check the numberIn against sourceCell.number.
             * Update the cell status sourceCell.status,
             * and re-paint the cell via sourceCell.paint().
             */
            // re-paint this cell based on its status
            if (numberIn == sourceCell.number) {
                sourceCell.status = true;
                sourceCell.painttrue();
            } else {
                sourceCell.paintfalse();
                mistake+=1;
            }
            mistakeslabel.setText("Mistakes: "+mistake+"/9");
            /*
             * [TODO 6] (later)
             * Check if the player has solved the puzzle after this move,
             *   by calling isSolved(). Put up a congratulation JOptionPane, if so.
             */
            if(isSolved()) {

                JOptionPane.showMessageDialog(null, "Congratulation");
            }
            if (mistake>=9) {

                JOptionPane.showMessageDialog(null, "You Failed");
                solve();
            }
        }

    }
    public void solve(){

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                cells[row][col].newGame(puzzle.numbers[row][col], true);
            }
        }
    }
}