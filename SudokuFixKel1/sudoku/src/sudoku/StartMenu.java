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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame {

    public StartMenu() {
        setTitle("Sudoku Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Sudoku");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SudokuMain().setVisible(true);
            }
        });

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(titleLabel, gbc);

        gbc.gridy++;
        panel.add(startGameButton, gbc);

        gbc.gridy++;
        panel.add(exitButton, gbc);

        add(panel);

        setPreferredSize(new Dimension(400, 300));
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StartMenu startmenu = new StartMenu();
            startmenu.setVisible(true);
        });
    }
}
