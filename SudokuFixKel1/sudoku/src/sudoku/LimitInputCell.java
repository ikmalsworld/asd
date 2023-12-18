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
import javax.swing.text.*;
import java.text.NumberFormat;

class LimitInputCell extends PlainDocument {
    private JFormattedTextField textField;
    private int limit;

    LimitInputCell(int limit) {
        super();
        this.limit = limit;
        this.textField = new JFormattedTextField(NumberFormat.getIntegerInstance());
        textField.setDocument(this); // Set document for the text field
    }

    @Override
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {

        if (str == null) {
            return;
        }

        // Only allow digits
        if (str.matches("\\d+")) {
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}