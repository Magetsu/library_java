package Library;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

/**
 * @author fiinmani
 *
 */
public class LibraryBookModel extends AbstractTableModel {

    /**
     *
     * LibraryBookModel()
     * getColumnCount()
     * getRowCount()
     * getValueAt()
     * getRowAt()
     * isCellEditable()
     *
     */
    private static final long serialVersionUID = 1L;
    private final Vector<Book> bookContainer;

    static final public ColumnData columns[] = {
            new ColumnData( Text.bookTableFieldNames[0], Text.bookTableFieldLengths[0], JLabel.LEFT ),
            new ColumnData( Text.bookTableFieldNames[1], Text.bookTableFieldLengths[1], JLabel.LEFT ),
            new ColumnData( Text.bookTableFieldNames[2], Text.bookTableFieldLengths[2], JLabel.LEFT ),
            new ColumnData( Text.bookTableFieldNames[3], Text.bookTableFieldLengths[3], JLabel.CENTER ),
            new ColumnData( Text.bookTableFieldNames[4], Text.bookTableFieldLengths[4], JLabel.CENTER ),
            new ColumnData( Text.bookTableFieldNames[5], Text.bookTableFieldLengths[5], JLabel.LEFT ),
            new ColumnData( Text.bookTableFieldNames[6], Text.bookTableFieldLengths[6], JLabel.CENTER ),
            new ColumnData( Text.bookTableFieldNames[7], Text.bookTableFieldLengths[7], JLabel.LEFT )
    };

    public LibraryBookModel(Vector<Book> aBookContainer) {

        System.out.println("LibraryBookModel.LibraryBookModel()");

        bookContainer = aBookContainer;
    }

    /**
     *
     */
    public int getColumnCount() {

        return columns.length;
    }

    /**
     *
     */
    public int getRowCount() {

        return bookContainer==null ? 0 : bookContainer.size();
    }

    /**
     *
     */
    public Object getValueAt(int nRow, int nCol) {

        if (nRow < 0 || nRow>=getRowCount())
            return "";
        Book row = bookContainer.elementAt(nRow);
        switch (nCol) {
            case 0: return row.GetSurname();
            case 1: return row.GetForename();
            case 2: return row.GetBookname();
            case 3: return row.GetPubyear();
            case 4: return row.GetIsbn();
            case 5: return row.GetOrgname();
            case 6: return row.GetOrgyear();
            case 7: return row.GetOther();
        }
        return "";
    }

    /**
     *
     */
    public Book getRowAt(int nRow) {

        return bookContainer.elementAt(nRow);
    }

    /**
     *
     */
    public boolean isCellEditable(int nRow, int nCol) {

        return false;
    }
}

/**
 * @author fiinmani
 *
 */
class ColumnData
{
    public String  title;
    public int     width;
    public int     alignment;

    public ColumnData(String aTitle, int aWidth, int aAlignment) {

        title = aTitle;
        width = aWidth;
        alignment = aAlignment;
    }
}
