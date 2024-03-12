package Library;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class Ui {

    private final Font font;

    private int bookFieldTotalLength = 0;
    private int dvdFieldTotalLength = 0;
    private int bookCount = 0;
    private int dvdCount = 0;
    private int paneIndex = 0;
    private int curRowIndex = 0;

    private LibraryBookModel bookModel;
    private LibraryDvdModel dvdModel;

    private JFrame mainFrame;
    private JDialog addDialog, modifyDialog;
    private JPanel topPanel, bottomPanel;

    JTabbedPane centerPanel;
    private JTextField searchField;
    private JTable bookTable, dvdTable;
    private JScrollPane dvdTableScrollPane,bookTableScrollPane;
    private JTextField[] fields;
    private JTextArea textArea;

    private Book selectedBook;
    private Dvd selectedDvd;

    private Sql sqlHandler;

    private JPopupMenu bookPopupMenu;
    private JPopupMenu dvdPopupMenu;

    /* UI luokka
     *
     * UI()
     *  SetBookCount()
     *  SetDvdCount()
     *  SetLibraryBookModel()
     *  SetLibraryDvdModel()
     *  SetFrame()
     *  SetSql()
     *
     *  GetBookTable()
     *  GetDvdTable()
     *  GetPaneIndex()
     *  GetLibraryBookModel()
     *  GetLibraryDvdModel()
     *  GetTopPanel()
     *  GetCenterPanel()
     *  GetBottomPanel()
     *  GetFrame()
     *  GetBookAddFields()
     *  GetDvdAddFields()
     *  GetOtherField()
     *  GetSearchText()
     *
     *  CreateTopPanel()
     *  CreateCenterPanel()
     *  CreateBookScrollPane()
     *  CreateDvdScrollPane()
     *
     *  CreateAddBookDialog()
     *  CreateAddDvdDialog()
     *  CreateModifyBookDialog()
     *  CreateModifyDvdDialog()
     *  CreateRemoveBookDialog()
     *  CreateRemoveDvdDialog()
     *  CreateMessageDialog()
     *  CreateOptionDialog()
     *
     *  UpdateTopPanel()
     *  UpdateBottomPanel()
     *  UpdateBookLibrary()
     *  UpdateDvdLibrary()
     *
     *  AddBookSql()
     *  AddDvdSql()
     *  SearchLibrary()
     *  ModifyBookGetValues()
     *  ModifyDvdGetValues()
     *  showPopup()
     *
     */

    public Ui() {

        System.out.println("	Ui.Ui()");

        font = new Font("Dialog", Font.PLAIN, 12);

        for (int j = 0; j < Text.bookTableFieldNames.length; j++) {

            bookFieldTotalLength += Text.bookTableFieldLengths[j];
        }

        for (int j = 0; j < Text.dvdTableFieldNames.length; j++) {

            dvdFieldTotalLength += Text.dvdTableFieldLengths[j];
        }
    }

    public void SetBookCount(int aBookCount) {

        System.out.println("	Ui.SetBookCount()");

        bookCount = aBookCount;
    }

    public void SetDvdCount(int aDvdCount) {

        System.out.println("	Ui.SetDvdCount()");

        dvdCount = aDvdCount;
    }

    public void SetLibraryBookModel(LibraryBookModel aBookModel) {

        System.out.println("	Ui.SetLibraryBookModel()");

        bookModel = aBookModel;
    }

    public void SetLibraryDvdModel(LibraryDvdModel aDvdModel) {

        System.out.println("	Ui.SetLibraryDvdModel()");

        dvdModel = aDvdModel;
    }

    public void SetFrame(JFrame aFrame) {

        System.out.println("	Ui.SetFrame()");
        mainFrame = aFrame;
    }

    public void SetSql(Sql aSqlHandler) {

        System.out.println("	Ui.SetSql()");

        sqlHandler = aSqlHandler;
    }

    public JTable GetBookTable() {

        System.out.println("	Ui.GetBookTable()");

        return bookTable;
    }

    public JTable GetDvdTable() {

        System.out.println("	Ui.GetDvdTable()");

        return dvdTable;
    }

    public int GetPaneIndex() {

        System.out.println("	Ui.GetPaneIndex()");

        return paneIndex;
    }

    public LibraryBookModel GetLibraryBookModel() {

        System.out.println("	Ui.GetLibraryBookModel()");
        return bookModel;
    }

    public LibraryDvdModel GetLibraryDvdModel() {

        System.out.println("	Ui.GetLibraryDvdModel()");
        return dvdModel;
    }

    public JPanel GetTopPanel() {

        System.out.println("	Ui.GetTopPanel()");
        return topPanel;
    }

    public JTabbedPane GetCenterPanel() {

        System.out.println("	Ui.GetCenterPanel()");
        return centerPanel;
    }

    public JPanel GetBottomPanel() {

        System.out.println("	Ui.GetBottomPanel()");
        return bottomPanel;
    }

    public JFrame GetFrame() {

        System.out.println("	Ui.GetFrame()");
        return mainFrame;
    }

    /*
     * GetBookAddFields
     *
     *
     *
     */
    public String[] GetBookAddFields() {

        System.out.println("	Ui.GetBookAddFields()");

        String[] text;

        text = new String[Text.bookTableFieldNames.length-1];
        for (int k = 0; k < Text.bookTableFieldNames.length-1; k++) {

            text[k] = fields[k].getText();

        }

        return text;
    }

    /*
     * GetDvdAddFields
     */
    public String[] GetDvdAddFields() {
        String[] text;

        text = new String[Text.dvdTableFieldNames.length-1];
        for (int k = 0; k < Text.dvdTableFieldNames.length-1; k++) {
            text[k] = fields[k].getText();
        }

        return text;
    }

    /*
     * GetOtherField
     */
    public String GetOtherField() {

        return textArea.getText();
    }

    int GetCurRowIndex() {

        System.out.println("curRowIndex : "+curRowIndex);
        return curRowIndex;
    }

    /*
     * GetSearchText
     */
    public String GetSearchText() {

        return searchField.getText();
    }

    /*
     * CreateTopPanel
     *
     * Luodaan yläpaneeli jossa on painikkeet toiminnoille sekä haku-kenttä
     *
     */
    public void CreateTopPanel(Utils anUtils) {

        System.out.println("	Ui.CreateTopPanel()");

        JLabel searchLabel = null;

        topPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        JPanel labelPanel = new JPanel(new GridLayout(1, 1));
        JPanel fieldPanel = new JPanel(new GridLayout(1, 1));

        searchPanel.add(labelPanel, BorderLayout.WEST);
        searchPanel.add(fieldPanel, BorderLayout.CENTER);

        searchField = new JTextField();
        searchField.addActionListener(anUtils);
        searchField.getDocument().addDocumentListener(anUtils);
        searchField.setColumns(30);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(searchField);
        fieldPanel.add(panel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        switch(paneIndex) {
            case 0:  {

                JButton[] button = new JButton[Text.bookButtonText.length];
                for (int i = 0; i < Text.bookButtonText.length; i++) {
                    button[i] = new JButton(Text.bookButtonText[i]);
                    button[i].setActionCommand(Text.bookButtonCommand[i]);
                    button[i].addActionListener(anUtils);
                    buttonPanel.add(button[i]);
                }

                searchLabel = new JLabel(Text.searchBookText, JLabel.RIGHT);

                break;
            }
            case 1: {

                JButton[] button = new JButton[Text.dvdButtonText.length];
                for (int i = 0; i < Text.dvdButtonText.length; i++) {
                    button[i] = new JButton(Text.dvdButtonText[i]);
                    button[i].setActionCommand(Text.dvdButtonCommand[i]);
                    button[i].addActionListener(anUtils);
                    buttonPanel.add(button[i]);
                }

                searchLabel = new JLabel(Text.searchDvdText, JLabel.RIGHT);

                break;
            }
        }
        assert searchLabel != null;
        searchLabel.setFont(font);
        searchLabel.setLabelFor(searchField);
        labelPanel.add(searchLabel);

        topPanel.add(buttonPanel, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);
        topPanel.setBorder(BorderFactory.createEmptyBorder());
    }

    /*
     * CreateCenterPanel
     *
     * Luodaan paneeli jossa on painikkeet joista voi valita näytetäänkö DVD:t vai kirjat
     *
     */
    public void CreateCenterPanel(Utils anUtils) {

        System.out.println("	Ui.CreateCenterPanel()");

        centerPanel = new JTabbedPane();

        centerPanel.addTab(Text.bookTabPaneText, null, null, null);
        centerPanel.addTab(Text.dvdTabPaneText, null, null, null);

        ChangeListener tabListener = new ChangeListener() {
            public void stateChanged(ChangeEvent changeEvent) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
                paneIndex = sourceTabbedPane.getSelectedIndex();
                UpdateTopPanel(anUtils);
                UpdateBottomPanel();
            }
        };

        centerPanel.addChangeListener(tabListener);
    }

    /*
     * CreateBottomPanel
     *
     * Luodaan paneeli jossa näytetään kirjat tai DVD:t ja lisäksi kirjojen tai DVD:tten määrä
     *
     */
    public void CreateBottomPanel() {

        System.out.println("	Ui.CreateBottomPanel()");

        String countString;
        JLabel label;

        bottomPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel(new GridLayout(1, 1));

        switch(paneIndex) {
            case 0:  {
                bottomPanel.add(bookTableScrollPane, BorderLayout.NORTH);
                countString = Text.bookCount + bookCount;
                label = new JLabel(countString, JLabel.LEFT);
                label.setFont(font);
                textPanel.add(label);
                break;
            }
            case 1: {
                bottomPanel.add(dvdTableScrollPane, BorderLayout.NORTH);
                countString = Text.dvdCount + dvdCount;
                label = new JLabel(countString, JLabel.LEFT);
                label.setFont(font);
                textPanel.add(label);
                break;
            }
        }
        bottomPanel.add(textPanel, BorderLayout.SOUTH);
    }

    /*
     * CreateBookScrollPane
     *
     * Luodaan paneeli jossa näytetään kirjat
     *
     */
    public void CreateBookScrollPane(LibraryBookModel aLibraryBookModel) {

        System.out.println("	Ui.CreateBookScrollPane()");

        bookTable = new JTable();
        bookTable.setPreferredScrollableViewportSize(new Dimension(bookFieldTotalLength,Text.tableHeight));
        bookTable.setFillsViewportHeight(true);
        bookTable.setAutoCreateRowSorter(true);
        bookTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        bookTable.setAutoCreateColumnsFromModel(false);
        bookTable.setModel(aLibraryBookModel);
        for (int k = 0; k < LibraryBookModel.columns.length; k++) {

            TableRenderer renderer = new TableRenderer();
            renderer.setHorizontalAlignment(LibraryBookModel.columns[k].alignment);
            TableColumn column = new TableColumn(k, LibraryBookModel.columns[k].width, renderer, null);
            column.setHeaderValue(LibraryBookModel.columns[k].title);
            bookTable.addColumn(column);
        }
        bookTable.setDefaultRenderer(Book.class, new TableRenderer());
        ListSelectionModel listSelectionModel = bookTable.getSelectionModel();

        ListSelectionListener selectionListener = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {

                // Valitaan kirja taulukosta
                curRowIndex = bookTable.getSelectedRow();
                selectedBook = aLibraryBookModel.getRowAt(curRowIndex);

                // Luodaan popupmenu kirjalle
                bookPopupMenu = CreateBookPopupMenu(selectedBook);

                System.out.println("	Selected book : "+selectedBook.GetBookname());
            }
        };
        listSelectionModel.addListSelectionListener(selectionListener);
        bookTable.setSelectionModel(listSelectionModel);
        bookTableScrollPane = new JScrollPane(bookTable);

        bookTable.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount()==2) {

                    // Valitaan kirja taulukosta
                    curRowIndex = bookTable.getSelectedRow();
                    selectedBook = aLibraryBookModel.getRowAt(curRowIndex);

                    System.out.println("Doubleclicked book : "+selectedBook.GetBookname());

                    // Tuplaklikkauksella luodaan ikkuna jolla voidaan muokata kirjaa
                    CreateModifyBookDialog(selectedBook);

                    Book book = new Book();

                    // Haetaan kirjat tietokannasta
                    book.SetBookModel(sqlHandler.GetBooks(Text.GET_BOOK+Text.ORDER_BOOK));
                    System.out.println("CreateLibrary: Returned book count is : " + book.GetBookCount());

                    // Otetaan talteen kirjojen määrä talteen UI:lle
                    SetBookCount(book.GetBookCount());
                    SetLibraryBookModel(book.GetBookModel());
                    CreateBookScrollPane(GetLibraryBookModel());

                    UpdateBottomPanel();

                }}
            public void mousePressed(MouseEvent mouseEvent) {

                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {

                    // Valitaan kirja taulukosta
                    curRowIndex = bookTable.getSelectedRow();
                    selectedBook = aLibraryBookModel.getRowAt(curRowIndex);

                    System.out.println("Right clicked book : "+selectedBook.GetBookname());

                    // Näytetään popup-menu kirjalle
                    showPopup(mouseEvent, bookPopupMenu);

                }}

            public void mouseReleased(MouseEvent mouseEvent) {

                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {

                    // Valitaan kirja taulukosta
                    curRowIndex = bookTable.getSelectedRow();
                    selectedBook = aLibraryBookModel.getRowAt(curRowIndex);

                    System.out.println("Right clicked book : "+selectedBook.GetBookname());


                    // Näytetään popup-menu kirjalle
                    showPopup(mouseEvent, bookPopupMenu);

                }}
        });
    }

    /*
     * CreateDvdScrollPane
     *
     * Luodaan paneeli jossa näytetään DVD:t
     *
     */
    public void CreateDvdScrollPane(LibraryDvdModel aLibraryDvdModel) {

        System.out.println("	Ui.CreateDvdScrollPane()");

        dvdTable = new JTable();
        dvdTable.setPreferredScrollableViewportSize(new Dimension(dvdFieldTotalLength,Text.tableHeight));
        dvdTable.setFillsViewportHeight(true);
        dvdTable.setAutoCreateRowSorter(true);
        dvdTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        dvdTable.setAutoCreateColumnsFromModel(false);
        dvdTable.setModel(aLibraryDvdModel);
        for (int k = 0; k < LibraryDvdModel.columns.length; k++) {

            TableRenderer renderer = new TableRenderer();
            renderer.setHorizontalAlignment(LibraryDvdModel.columns[k].alignment);
            TableColumn column = new TableColumn(k, LibraryDvdModel.columns[k].width, renderer, null);
            column.setHeaderValue(LibraryDvdModel.columns[k].title);
            dvdTable.addColumn(column);
        }
        dvdTable.setDefaultRenderer(Dvd.class, new TableRenderer());
        ListSelectionModel listSelectionModel = dvdTable.getSelectionModel();
        ListSelectionListener selectionListener = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent event) {

                // Valitaan kirja taulukosta
                curRowIndex = dvdTable.getSelectedRow();
                selectedDvd = aLibraryDvdModel.getRowAt(curRowIndex);

                // Luodaan popupmenu kirjalle
                dvdPopupMenu = CreateDvdPopupMenu(selectedDvd);

                System.out.println("Selected book : "+selectedDvd.GetName());
            }
        };
        listSelectionModel.addListSelectionListener(selectionListener);
        dvdTable.setSelectionModel(listSelectionModel);
        dvdTableScrollPane = new JScrollPane(dvdTable);

        dvdTable.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent mouseEvent) {

                if (mouseEvent.getClickCount()==2) {

                    // Valitaan dvd taulukosta
                    curRowIndex = dvdTable.getSelectedRow();
                    selectedDvd = aLibraryDvdModel.getRowAt(curRowIndex);

                    System.out.println("Doubleclicked dvd : "+selectedDvd.GetName());

                    // Tuplaklikkauksella luodaan ikkuna jolla voidaan muokata kirjaa
                    CreateModifyDvdDialog(selectedDvd);

                    /*------------------------------------------------------------------*/
                    Dvd dvd = new Dvd();

                    // Haetaan kirjat tietokannasta
                    dvd.SetDvdModel(sqlHandler.GetDvds(Text.GET_DVD+Text.ORDER_DVD));

                    // Otetaan talteen kirjojen määrä talteen UI:lle
                    SetDvdCount(dvd.GetDvdCount());
                    SetLibraryDvdModel(dvd.GetDvdModel());
                    CreateDvdScrollPane(GetLibraryDvdModel());

                    UpdateBottomPanel();
                    /*------------------------------------------------------------------*/


                }}
            public void mousePressed(MouseEvent mouseEvent) {

                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {

                    // Valitaan kirja taulukosta
                    curRowIndex = dvdTable.getSelectedRow();
                    selectedDvd = aLibraryDvdModel.getRowAt(curRowIndex);

                    System.out.println("Right clicked dvd : "+selectedDvd.GetName());

                    // Näytetään popup-menu DVD:lle
                    showPopup(mouseEvent, dvdPopupMenu);

                }}

            public void mouseReleased(MouseEvent mouseEvent) {

                if (mouseEvent.getButton() == MouseEvent.BUTTON3) {

                    // Valitaan kirja taulukosta
                    curRowIndex = dvdTable.getSelectedRow();
                    selectedDvd = aLibraryDvdModel.getRowAt(curRowIndex);

                    System.out.println("Right clicked dvd : "+selectedDvd.GetName());

                    // Näytetään popup-menu DVD:lle
                    showPopup(mouseEvent, dvdPopupMenu);

                }}
        });
    }
    /*
     * CreateAddBookDialog
     *
     * Luodaan kirjan lisäys -ikkuna
     *
     */
    public void CreateAddBookDialog() {

        System.out.println("	Ui.CreateAddBookDialog()");

        JPanel formPanel, labelPanel, fieldPanel,buttonPanel;
        JScrollPane areaScrollPane;
        JButton add_button,exit_button;

        addDialog = new JDialog(mainFrame, Text.addBookText, true);
        addDialog.setLayout(new BorderLayout());

        // Luodaan kirjan tietojen syöttökentät ja otsakkeet
        formPanel = new JPanel(new BorderLayout());
        labelPanel = new JPanel(new GridLayout(Text.bookTableFieldNames.length-1, 1));
        fieldPanel = new JPanel(new GridLayout(Text.bookTableFieldNames.length-1, 1));

        formPanel.add(labelPanel, BorderLayout.WEST);
        formPanel.add(fieldPanel, BorderLayout.CENTER);

        fields = new JTextField[Text.bookTableFieldNames.length-1];

        for (int i = 0; i < Text.bookTableFieldNames.length-1; i++) {
            JLabel lab;
            JPanel p;

            fields[i] = new JTextField();
            //if (i < Text.bookTablefieldNames.length-1)
                fields[i].setColumns(30);

            lab = new JLabel(Text.bookTableFieldNames[i], JLabel.RIGHT);
            lab.setLabelFor(fields[i]);

            labelPanel.add(lab);
            p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.add(fields[i]);
            fieldPanel.add(p);
        }

        // Luodaan muuta tietoa tekstikenttä
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 100));
        areaScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory
                        .createTitledBorder(Text.bookTableFieldNames[7]), BorderFactory
                        .createEmptyBorder(5, 5, 5, 5)), areaScrollPane
                        .getBorder()));

        addDialog.add(formPanel, BorderLayout.NORTH);
        addDialog.add(areaScrollPane, BorderLayout.CENTER);

        // Luodaan painonapit
        buttonPanel = new JPanel();

        add_button = new JButton(Text.addBookText);
        add_button.setActionCommand(Text.addBookDialogCommand);
        add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                AddBookSql();
                addDialog.setVisible(false);
                addDialog.dispose();
            }
        });

        buttonPanel.add(add_button);
        exit_button = new JButton(Text.exitText);
        exit_button.setActionCommand(Text.exitDialogCommand);
        exit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                addDialog.setVisible(false);
                addDialog.dispose();
            }
        });

        buttonPanel.add(exit_button);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        addDialog.setLocation(200, 200);
        addDialog.pack();
        addDialog.setVisible(true);
    }

    /*
     * CreateAddDvdDialog
     *
     * Luodaan kirjan lisäys -ikkuna
     *
     */
    public void CreateAddDvdDialog() {

        System.out.println("	Ui.CreateAddDvdDialog()");

        JPanel formPanel, labelPanel, fieldPanel,buttonPanel;
        JScrollPane areaScrollPane;
        JButton add_button,exit_button;

        addDialog = new JDialog(mainFrame, Text.addDvdText, true);
        addDialog.setLayout(new BorderLayout());

        // Luodaan kirjan tietojen syöttökentät ja otsakkeet
        formPanel = new JPanel(new BorderLayout());
        labelPanel = new JPanel(new GridLayout(Text.dvdTableFieldNames.length-1, 1));
        fieldPanel = new JPanel(new GridLayout(Text.dvdTableFieldNames.length-1, 1));

        formPanel.add(labelPanel, BorderLayout.WEST);
        formPanel.add(fieldPanel, BorderLayout.CENTER);

        fields = new JTextField[Text.dvdTableFieldNames.length-1];

        for (int i = 0; i < Text.dvdTableFieldNames.length-1; i++) {
            JLabel lab;
            JPanel p;

            fields[i] = new JTextField();
            //if (i < Text.dvdTablefieldNames.length-1)

                fields[i].setColumns(30);

            lab = new JLabel(Text.dvdTableFieldNames[i], JLabel.RIGHT);
            lab.setLabelFor(fields[i]);

            labelPanel.add(lab);
            p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.add(fields[i]);
            fieldPanel.add(p);
        }

        // Luodaan muuta tietoa tekstikenttä
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 100));
        areaScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory
                        .createTitledBorder(Text.dvdTableFieldNames[6]), BorderFactory
                        .createEmptyBorder(5, 5, 5, 5)), areaScrollPane
                        .getBorder()));

        addDialog.add(formPanel, BorderLayout.NORTH);
        addDialog.add(areaScrollPane, BorderLayout.CENTER);

        // Luodaan painonapit
        buttonPanel = new JPanel();

        add_button = new JButton(Text.addDvdText);
        add_button.setActionCommand(Text.addDvdDialogCommand);
        add_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                AddDvdSql();
                addDialog.setVisible(false);
                addDialog.dispose();
            }
        });

        buttonPanel.add(add_button);
        exit_button = new JButton(Text.exitText);
        exit_button.setActionCommand(Text.exitDialogCommand);
        exit_button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                addDialog.setVisible(false);
                addDialog.dispose();
            }
        });

        buttonPanel.add(exit_button);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        addDialog.setLocation(200, 200);
        addDialog.pack();
        addDialog.setVisible(true);
    }

    /*
     * CreateModifyBookForm
     *
     *
     * Luodaan kirjan muokkaus -ikkuna
     *
     */
    public void CreateModifyBookDialog(Book aBook) {

        System.out.println("	Ui.CreateModifyBookDialog()");

        JPanel formPanel, labelPanel, fieldPanel,buttonPanel;
        JScrollPane areaScrollPane;
        JButton add_button,exit_button;

        modifyDialog = new JDialog(mainFrame, Text.modifyBookText, true);
        modifyDialog.setLayout(new BorderLayout());

        // Luodaan kirjan tietojen syöttökentät ja otsakkeet
        formPanel = new JPanel(new BorderLayout());
        labelPanel = new JPanel(new GridLayout(Text.bookTableFieldNames.length-1, 1));
        fieldPanel = new JPanel(new GridLayout(Text.bookTableFieldNames.length-1, 1));

        formPanel.add(labelPanel, BorderLayout.WEST);
        formPanel.add(fieldPanel, BorderLayout.CENTER);

        fields = new JTextField[Text.bookTableFieldNames.length-1];

        for (int i = 0; i < Text.bookTableFieldNames.length-1; i++) {
            JLabel lab;
            JPanel p;

            fields[i] = new JTextField();
            //if (i < Text.bookTablefieldNames.length-1)

                fields[i].setColumns(30);

            switch (i) {

                case 0:
                    fields[i].setText(aBook.GetSurname());
                    break;
                case 1:
                    fields[i].setText(aBook.GetForename());
                    break;
                case 2:
                    fields[i].setText(aBook.GetBookname());
                    break;
                case 3:
                    fields[i].setText(String.valueOf(aBook.GetPubyear()));
                    break;
                case 4:
                    fields[i].setText(aBook.GetIsbn());
                    break;
                case 5:
                    fields[i].setText(aBook.GetOrgname());
                    break;
                case 6:
                    fields[i].setText(String.valueOf(aBook.GetOrgyear()));
                    break;
            }

            lab = new JLabel(Text.bookTableFieldNames[i], JLabel.RIGHT);
            lab.setLabelFor(fields[i]);

            labelPanel.add(lab);
            p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.add(fields[i]);
            fieldPanel.add(p);
        }

        // Luodaan muuta tietoa tekstikenttä
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 100));
        areaScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory
                        .createTitledBorder(Text.bookTableFieldNames[7]), BorderFactory
                        .createEmptyBorder(5, 5, 5, 5)), areaScrollPane
                        .getBorder()));
        textArea.setText(aBook.GetOther());

        modifyDialog.add(formPanel, BorderLayout.NORTH);
        modifyDialog.add(areaScrollPane, BorderLayout.CENTER);

        // Luodaan painonapit
        buttonPanel = new JPanel();

        add_button = new JButton(Text.modifyBookText);
        add_button.setActionCommand(Text.modifyBookDialogCommand);
        add_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ModifyBookGetValues(aBook);
                modifyDialog.setVisible(false);
                modifyDialog.dispose();			 }
        });
        buttonPanel.add(add_button);
        exit_button = new JButton(Text.exitText);
        exit_button.setActionCommand(Text.exitDialogCommand);
        exit_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                modifyDialog.setVisible(false);
                modifyDialog.dispose();
            }
        });
        buttonPanel.add(exit_button);
        modifyDialog.add(buttonPanel, BorderLayout.SOUTH);

        modifyDialog.setLocation(200, 200);
        modifyDialog.pack();
        modifyDialog.setVisible(true);
    }

    /*
     * CreateModifyDvdDialog
     *
     *
     * Luodaan kirjan muokkaus -ikkuna
     *
     */
    public void CreateModifyDvdDialog(Dvd aDvd) {

        System.out.println("	Ui.CreateModifyDvdDialog()");

        JPanel formPanel, labelPanel, fieldPanel,buttonPanel;
        JScrollPane areaScrollPane;
        JButton add_button,exit_button;

        modifyDialog = new JDialog(mainFrame, Text.modifyDvdText, true);
        modifyDialog.setLayout(new BorderLayout());

        // Luodaan kirjan tietojen syöttökentät ja otsakkeet
        formPanel = new JPanel(new BorderLayout());
        labelPanel = new JPanel(new GridLayout(Text.dvdTableFieldNames.length-1, 1));
        fieldPanel = new JPanel(new GridLayout(Text.dvdTableFieldNames.length-1, 1));

        formPanel.add(labelPanel, BorderLayout.WEST);
        formPanel.add(fieldPanel, BorderLayout.CENTER);

        fields = new JTextField[Text.dvdTableFieldNames.length-1];

        for (int i = 0; i < Text.dvdTableFieldNames.length-1; i++) {
            JLabel lab;
            JPanel p;

            fields[i] = new JTextField();
            //if (i < Text.dvdTablefieldNames.length-1)

                fields[i].setColumns(30);

            switch (i) {

                case 0:
                    fields[i].setText(aDvd.GetName());
                    break;
                case 1:
                    fields[i].setText(String.valueOf(aDvd.GetYear()));
                    break;
                case 2:
                    fields[i].setText(aDvd.GetOrgname());
                    break;
                case 3:
                    fields[i].setText(aDvd.GetBarcode());
                    break;
                case 4:
                    fields[i].setText(aDvd.GetDirector());
                    break;
                case 5:
                    fields[i].setText(aDvd.GetMusic());
                    break;
            }

            lab = new JLabel(Text.dvdTableFieldNames[i], JLabel.RIGHT);
            lab.setLabelFor(fields[i]);

            labelPanel.add(lab);
            p = new JPanel(new FlowLayout(FlowLayout.LEFT));
            p.add(fields[i]);
            fieldPanel.add(p);
        }

        // Luodaan muuta tietoa tekstikenttä
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        areaScrollPane = new JScrollPane(textArea);
        areaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        areaScrollPane.setPreferredSize(new Dimension(250, 100));
        areaScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory
                        .createTitledBorder(Text.dvdTableFieldNames[6]), BorderFactory
                        .createEmptyBorder(5, 5, 5, 5)), areaScrollPane
                        .getBorder()));
        textArea.setText(aDvd.GetOther());

        modifyDialog.add(formPanel, BorderLayout.NORTH);
        modifyDialog.add(areaScrollPane, BorderLayout.CENTER);

        // Luodaan painonapit
        buttonPanel = new JPanel();

        add_button = new JButton(Text.modifyDvdText);
        add_button.setActionCommand(Text.modifyDvdDialogCommand);
        add_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                ModifyDvdGetValues(aDvd);
                modifyDialog.setVisible(false);
                modifyDialog.dispose();			 }
        });
        buttonPanel.add(add_button);
        exit_button = new JButton(Text.exitText);
        exit_button.setActionCommand(Text.exitDialogCommand);
        exit_button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                modifyDialog.setVisible(false);
                modifyDialog.dispose();
            }
        });
        buttonPanel.add(exit_button);
        modifyDialog.add(buttonPanel, BorderLayout.SOUTH);

        modifyDialog.setLocation(200, 200);
        modifyDialog.pack();
        modifyDialog.setVisible(true);
    }

    /*
     * RemoveBook
     *
     * Luodaan kirjan poisto -ikkuna
     *
     */
    public boolean CreateRemoveBookDialog(Book aBook) {

        System.out.println("	Ui.RemoveBookDialog()");

        boolean result=false;

        int remove = CreateOptionDialog(mainFrame, Text.bookRemove1+" '"+aBook.GetBookname()+"' "+Text.bookRemove2, Text.removeBookText);

        if (JOptionPane.NO_OPTION == remove) {

            return result;

        } else if(JOptionPane.YES_OPTION == remove) {

            result = sqlHandler.RemoveBook(aBook);
        }
        return result;
    }

    /*
     * RemoveDvd
     *
     * Luodaan DVD:n poisto -ikkuna
     *
     */
    public boolean CreateRemoveDvdDialog(Dvd aDvd) {

        System.out.println("	Ui.RemoveDvdDialog()");

        boolean result=false;

        int remove = CreateOptionDialog(mainFrame, Text.dvdRemove1+" '"+aDvd.GetName()+"' "+Text.dvdRemove2, Text.removeDvdText);

        if (JOptionPane.NO_OPTION == remove) {

            return result;

        } else if(JOptionPane.YES_OPTION == remove) {

            result = sqlHandler.RemoveDvd(aDvd);
        }
        return result;
    }

    /*
     * CreateMessageDialog
     */
    public void CreateMessageDialog(JFrame aFrame, String aMessage, String aTitle, int aType) {

        JOptionPane.showMessageDialog(aFrame, aMessage, aTitle, aType);
    }

    /*
     * CreateOptionDialog
     */
    public int CreateOptionDialog(JFrame aFrame, String aMessage, String aTitle) {

        return JOptionPane.showOptionDialog(aFrame,
                aMessage,
                aTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                Text.options,
                Text.options[0]);
    }

    /*
     * UpdateTopPanel
     */
    public void UpdateTopPanel(final Utils anUtils) {

        System.out.println("	Ui.UpdateTopPanel()");

        mainFrame.remove(GetTopPanel());
        CreateTopPanel(anUtils);
        mainFrame.add(GetTopPanel(), BorderLayout.NORTH);
        mainFrame.validate();
        mainFrame.repaint();
    }

    /*
     * UpdateBottomPanel
     */
    public void UpdateBottomPanel() {

        System.out.println("	Ui.UpdateBottomPanel()");

        mainFrame.remove(GetBottomPanel());
        CreateBottomPanel();
        mainFrame.add(GetBottomPanel(), BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.validate();
        mainFrame.repaint();
    }

    /*
     * UpdateBookLibrary
     */
    public void UpdateBookLibrarySearch(String aQueryString) {

        System.out.println("	Ui.UpdateBookLibrarySearch()");

        Vector<Book> result = new Vector<Book>();

        result = sqlHandler.GetBooks(aQueryString);

        Book book = new Book();

        book.SetBookModel(result);
        LibraryBookModel bookModel = new LibraryBookModel(result);
        SetLibraryBookModel(bookModel);
        CreateBookScrollPane(bookModel);
        SetBookCount(book.GetBookCount());
    }

    /*
     * UpdateDvdLibrary
     */
    public void UpdateDvdLibrarySearch(String aQueryString) {

        System.out.println("	Ui.UpdateDvdLibrarySearch()");

        Vector<Dvd> result = new Vector<Dvd>();
        result = sqlHandler.GetDvds(aQueryString);

        Dvd dvd = new Dvd();

        dvd.SetDvdModel(result);
        LibraryDvdModel dvdModel = new LibraryDvdModel(result);
        SetLibraryDvdModel(dvdModel);
        CreateDvdScrollPane(dvdModel);
        SetBookCount(dvd.GetDvdCount());
    }

    /*
     * AddBook
     *
     * Luetaan kirjan tiedot lomakkeesta ja lisätään kirja SQL-kantaan
     *
     */
    public void AddBookSql() {

        System.out.println("	Ui.AddBookSql()");

        int pubyear,orgyear;
        boolean addResult = false;
        String[] fieldValue;
        String otherValue;

        try {

            fieldValue = new String[LibraryBookModel.columns.length];
            fieldValue = GetBookAddFields();
            otherValue = GetOtherField();

            if (fieldValue[3].isEmpty()) {
                pubyear = 0;
            } else {
                pubyear = Integer.parseInt(fieldValue[3]);
            }
            if (fieldValue[6].isEmpty()) {
                orgyear = 0;
            } else {
                orgyear = Integer.parseInt(fieldValue[6]);
            }

            Book book = new Book();

            book.SetBook(fieldValue[0],
                    fieldValue[1],
                    fieldValue[2],
                    pubyear,
                    fieldValue[4],
                    fieldValue[5],
                    orgyear,
                    otherValue);

            addResult  = sqlHandler.AddBook(book);

            if(addResult) {

                JOptionPane.showMessageDialog(addDialog, Text.addBookSuccess, Text.addTitle, JOptionPane.INFORMATION_MESSAGE);

            } else {

                JOptionPane.showMessageDialog(addDialog, Text.addBookFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
            }

        } catch(NumberFormatException e) {

            JOptionPane.showMessageDialog(addDialog, Text.intFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * AddDvd
     */
    public void AddDvdSql() {

        System.out.println("	Ui.AddDvdSql()");

        int pubdate;
        boolean addResult = false;
        String[] fieldValue;
        String otherValue;

        try {

            fieldValue = new String[LibraryDvdModel.columns.length];
            fieldValue = GetDvdAddFields();
            otherValue = GetOtherField();

            if (fieldValue[1].isEmpty()) {

                pubdate = 0;

            } else {

                pubdate = Integer.parseInt(fieldValue[1]);
            }

            Dvd dvd = new Dvd();

            dvd.SetDvd(fieldValue[0],
                    pubdate,
                    fieldValue[2],
                    fieldValue[3],
                    fieldValue[4],
                    fieldValue[5],
                    otherValue);

            addResult  = sqlHandler.AddDvd(dvd);

            if(addResult) {

                JOptionPane.showMessageDialog(addDialog, Text.addDvdSuccess, Text.addTitle, JOptionPane.INFORMATION_MESSAGE);

            } else {

                JOptionPane.showMessageDialog(addDialog, Text.addDvdFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
            }

        } catch(NumberFormatException e) {

            JOptionPane.showMessageDialog(addDialog, Text.intFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);

        }
    }

    /*
     * SearchLibrary
     */
    public void SearchLibrary() {

        System.out.println("	Ui.SearchLibrary()");

        StringBuilder search_string = null;

        switch(paneIndex) {
            case 0:  {
                search_string = new StringBuilder(Text.GET_BOOK);
                search_string.append(Text.WHERE);
                search_string.append(Text.bookDataFieldNames[0]).append(" LIKE '%").append(GetSearchText()).append("%'");

                for (int i = 1; i < Text.bookDataFieldNames.length; i += 1) {
                    search_string.append(" OR ");
                    search_string.append(Text.bookDataFieldNames[i]).append(" LIKE '%").append(GetSearchText()).append("%'");
                }

                UpdateBookLibrarySearch(search_string.toString());
                break;
            }
            case 1: {
                search_string = new StringBuilder(Text.GET_DVD);
                search_string.append(Text.WHERE);
                search_string.append(Text.dvdDataFieldNames[0]).append(" LIKE '%").append(GetSearchText()).append("%'");

                for (int i = 1; i < Text.dvdDataFieldNames.length; i += 1) {
                    search_string.append(" OR ");
                    search_string.append(Text.dvdDataFieldNames[i]).append(" LIKE '%").append(GetSearchText()).append("%'");
                }

                UpdateDvdLibrarySearch(search_string.toString());

                break;
            }
        }

        UpdateBottomPanel();
    }

    /*
     * ModifyBookGetValues
     */
    public void ModifyBookGetValues(Book orgbook) {

        System.out.println("	Ui.ModifyBookGetValues()");

        int pubdate,orgyear;
        boolean addResult = false;
        String[] fieldValue;
        String otherValue;

        try {

            fieldValue = new String[LibraryBookModel.columns.length];
            fieldValue = GetBookAddFields();
            otherValue = GetOtherField();

            if (fieldValue[3].isEmpty()) {
                pubdate = 0;
            } else {
                pubdate = Integer.parseInt(fieldValue[3]);
            }
            if (fieldValue[6].isEmpty()) {
                orgyear = 0;
            } else {
                orgyear = Integer.parseInt(fieldValue[6]);
            }

            Book book = new Book();

            book.SetBook(fieldValue[0],
                    fieldValue[1],
                    fieldValue[2],
                    pubdate,
                    fieldValue[4],
                    fieldValue[5],
                    orgyear,
                    otherValue);

            addResult  = sqlHandler.ModifyBook(book, orgbook);

            if(addResult) {

                JOptionPane.showMessageDialog(addDialog, Text.modifyBookSuccess, Text.addTitle, JOptionPane.INFORMATION_MESSAGE);

            } else {

                JOptionPane.showMessageDialog(addDialog, Text.modifyBookFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
            }

        } catch(NumberFormatException e) {

            JOptionPane.showMessageDialog(addDialog, Text.intFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * ModifyDvdGetValues
     */
    public void ModifyDvdGetValues(Dvd orgdvd) {

        System.out.println("	Ui.ModifyDvdGetValues()");

        int pubdate;
        boolean addResult = false;
        String[] fieldValue;
        String otherValue;

        try {

            fieldValue = new String[LibraryDvdModel.columns.length];
            fieldValue = GetDvdAddFields();
            otherValue = GetOtherField();

            if (fieldValue[1].isEmpty()) {
                pubdate = 0;
            } else {
                pubdate = Integer.parseInt(fieldValue[1]);
            }

            Dvd dvd = new Dvd();

            dvd.SetDvd(fieldValue[0],
                    pubdate,
                    fieldValue[2],
                    fieldValue[3],
                    fieldValue[4],
                    fieldValue[5],
                    otherValue);

            addResult  = sqlHandler.ModifyDvd(dvd, orgdvd);

            if(addResult) {

                JOptionPane.showMessageDialog(addDialog, Text.modifyBookSuccess, Text.addTitle, JOptionPane.INFORMATION_MESSAGE);

            } else {

                JOptionPane.showMessageDialog(addDialog, Text.modifyBookFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
            }

        } catch(NumberFormatException e) {

            JOptionPane.showMessageDialog(addDialog, Text.intFail, Text.addTitle, JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * CreatePopupMenu
     *
     * Luodaan popup-menu
     *
     */
    private JPopupMenu CreateBookPopupMenu(Book aBook) {

        JPopupMenu popupMenu = new JPopupMenu();

        // Luodaan kirjan poisto valinta pop-up:in
        JMenuItem choice4 = new JMenuItem(Text.removeBookText+" "+aBook.GetBookname());
        popupMenu.add(choice4);
        choice4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                boolean result = CreateRemoveBookDialog(aBook);
                if(result) {

                    Book book = new Book();

                    // Haetaan kirjat tietokannasta
                    book.SetBookModel(sqlHandler.GetBooks(Text.GET_BOOK+Text.ORDER_BOOK));

                    // Otetaan talteen kirjojen määrä talteen UI:lle
                    SetBookCount(book.GetBookCount());
                    SetLibraryBookModel(book.GetBookModel());
                    CreateBookScrollPane(GetLibraryBookModel());

                    UpdateBottomPanel();
                }
            }
        });
        choice4.setActionCommand(Text.removeBookCommand);

        // Luodaan kirjan muokkaus valinta pop-up:in
        JMenuItem choice5 = new JMenuItem(Text.modifyBookText+" "+aBook.GetBookname());
        popupMenu.add(choice5);
        choice5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                CreateModifyBookDialog(aBook);

                Book book = new Book();

                // Haetaan kirjat tietokannasta
                book.SetBookModel(sqlHandler.GetBooks(Text.GET_BOOK+Text.ORDER_BOOK));

                // Otetaan talteen kirjojen määrä talteen UI:lle
                SetBookCount(book.GetBookCount());
                SetLibraryBookModel(book.GetBookModel());
                CreateBookScrollPane(GetLibraryBookModel());

                UpdateBottomPanel();
            }
        });
        choice5.setActionCommand(Text.modifyBookDialogCommand);

        return popupMenu;
    }

    /*
     * CreatePopupMenu
     *
     * Luodaan popup-menu
     *
     */
    private JPopupMenu CreateDvdPopupMenu(Dvd aDvd) {

        JPopupMenu popupMenu = new JPopupMenu();

        // Luodaan kirjan poisto valinta pop-up:in
        JMenuItem choice4 = new JMenuItem(Text.removeDvdText+" "+aDvd.GetName());
        popupMenu.add(choice4);
        choice4.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                boolean result = CreateRemoveDvdDialog(aDvd);

                if(result) {

                    Dvd dvd = new Dvd();

                    // Haetaan kirjat tietokannasta
                    dvd.SetDvdModel(sqlHandler.GetDvds(Text.GET_DVD+Text.ORDER_DVD));

                    // Otetaan talteen kirjojen määrä talteen UI:lle
                    SetDvdCount(dvd.GetDvdCount());
                    SetLibraryDvdModel(dvd.GetDvdModel());
                    CreateDvdScrollPane(GetLibraryDvdModel());

                    UpdateBottomPanel();
                }
            }
        });
        choice4.setActionCommand(Text.removeDvdCommand);

        // Luodaan kirjan muokkaus valinta pop-up:in
        JMenuItem choice5 = new JMenuItem(Text.modifyDvdText+" "+aDvd.GetName());
        popupMenu.add(choice5);
        choice5.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                CreateModifyDvdDialog(aDvd);

                Dvd dvd = new Dvd();

                // Haetaan kirjat tietokannasta
                dvd.SetDvdModel(sqlHandler.GetDvds(Text.GET_DVD+Text.ORDER_DVD));

                // Otetaan talteen kirjojen määrä talteen UI:lle
                SetDvdCount(dvd.GetDvdCount());
                SetLibraryDvdModel(dvd.GetDvdModel());
                CreateDvdScrollPane(GetLibraryDvdModel());

                UpdateBottomPanel();
            }
        });
        choice5.setActionCommand(Text.modifyDvdDialogCommand);

        return popupMenu;
    }

    private void showPopup(MouseEvent e, JPopupMenu aPopupMenu) {

        if (e.isPopupTrigger()) {
            aPopupMenu.show(e.getComponent(),
                    e.getX(), e.getY());
        }
    }
}

/**
 * @author fiinmani
 *
 */
final class TableRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    /*
     * setValue
     */
    public void setValue(Object aValue) {

        if ( aValue != null ) {

            setToolTipText( aValue.toString() );
        }

        super.setValue(aValue);
    }
}

