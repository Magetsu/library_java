package Library;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class LibraryDvdModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;
    private final Vector<Dvd> result;

    static final public ColumnData columns[] = {
            new ColumnData( Text.dvdTableFieldNames[0], Text.dvdTableFieldLengths[0], JLabel.LEFT ),
            new ColumnData( Text.dvdTableFieldNames[1], Text.dvdTableFieldLengths[1], JLabel.LEFT ),
            new ColumnData( Text.dvdTableFieldNames[2], Text.dvdTableFieldLengths[2], JLabel.LEFT ),
            new ColumnData( Text.dvdTableFieldNames[3], Text.dvdTableFieldLengths[3], JLabel.LEFT ),
            new ColumnData( Text.dvdTableFieldNames[4], Text.dvdTableFieldLengths[4], JLabel.LEFT ),
            new ColumnData( Text.dvdTableFieldNames[5], Text.dvdTableFieldLengths[5], JLabel.LEFT ),
            new ColumnData( Text.dvdTableFieldNames[6], Text.dvdTableFieldLengths[6], JLabel.LEFT )
    };

    /**
     *
     * LibraryDvdModel()
     * getColumnCount()
     * getRowCount()
     * getValueAt()
     * getRowAt()
     * isCellEditable()
     */
	public LibraryDvdModel(Vector<Dvd> aResult) {

            System.out.println("LibraryDvdModel.LibraryDvdModel()");

            result = aResult;
    }

        @Override
        public int getColumnCount() {

            return columns.length;
    }

        @Override
        public int getRowCount() {

            return result==null ? 0 : result.size();
    }

        @Override
        public Object getValueAt(int nRow, int nCol) {

            if (nRow < 0 || nRow>=getRowCount())
                return "";
            Dvd row = result.elementAt(nRow);
            switch (nCol) {
                case 0: return row.GetName();
                case 1: return row.GetYear();
                case 2: return row.GetOrgname();
                case 3: return row.GetBarcode();
                case 4: return row.GetDirector();
                case 5: return row.GetMusic();
                case 6: return row.GetOther();
            }
            return "";
    }

        /**
         *
         */
        public Dvd getRowAt(int nRow) {

            return result.elementAt(nRow);
        }
}
