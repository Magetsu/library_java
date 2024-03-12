package Library;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileFilter;
import java.util.Objects;
import java.util.Vector;

public class Utils implements ActionListener, DocumentListener, WindowListener {

    private final Sql sql;
    private final Xml xml;
    private final Ui ui;

    private JFrame mainFrame;

    private final Book book = new Book();
    private final Dvd dvd = new Dvd();

    /*
     * Utils
     *
     * Utils()
     * CreateLibrary()
     * CreateMainView()
     * Exit()
     * PrintLibrary()
     * ExportLibrary()
     * ImportLibrary()
     *
     * actionPerformed()
     *
     * windowOpened()
     * windowClosing()
     * windowClosed()
     * windowIconified()
     * windowDeiconified()
     * windowActivated()
     * windowDeactivated()
     *
     * insertUpdate()
     * removeUpdate()
     * changedUpdate()
     *
     */

    public Utils() {

        System.out.println("Utils.Utils()");

        sql = new Sql();
        xml = new Xml();
        ui = new Ui();

        ui.SetSql(sql);
        sql.GetConnection();
        sql.CreateDatabase();
    }

    /*
     * CreateLibrary
     *
     * Luodaan kirjastomalli
     *
     */
    @SuppressWarnings("unchecked")
    public void CreateLibrary() {

        System.out.println("Utils.CreateLibrary()");

        // Taulukko jossa on kirjaston kaikki kirjat ja DVD:t
        Object[] library = {new Vector<Book>(), new Vector<Dvd>()};

        // Haetaan kirjat tietokannasta
        library[0] = sql.GetBooks(Text.GET_BOOK+Text.ORDER_BOOK);
        book.SetBookModel((Vector<Book>) library[0]);
        System.out.println("CreateLibrary: Returned book count is : " + book.GetBookCount());

        // Otetaan talteen kirjojen määrä talteen UI:lle
        ui.SetBookCount(book.GetBookCount());

        // Haetaan DVD:t tietokannasta
        library[1] = sql.GetDvds(Text.GET_DVD+Text.ORDER_DVD);
        dvd.SetDvdModel((Vector<Dvd>) library[1]);
        System.out.println("CreateLibrary: Returned DVD count is : " + dvd.GetDvdCount());

        // Otetaan talteen DVD:n määrä talteen UI:lle
        ui.SetDvdCount(dvd.GetDvdCount());

        // Otetaan talteen kirjojen model ja DVD:n model talteen UI:lle
        ui.SetLibraryBookModel(book.GetBookModel());
        ui.SetLibraryDvdModel(dvd.GetDvdModel());

        // Luodaan taulukkoikkunat kirjoille ja DVD:lle
        ui.CreateBookScrollPane(ui.GetLibraryBookModel());
        ui.CreateDvdScrollPane(ui.GetLibraryDvdModel());

        System.out.println("Kirjasto tuotu tietokannasta");
    }

    /*
     * CreateMainView
     *
     * Luodaan pääikkuna
     *
     */
    public void CreateMainView() {

        System.out.println("Utils.CreateMainView()");

        mainFrame = new JFrame(Text.mainCaptionText);
        mainFrame.setLayout(new BorderLayout());

        // Ikkunan kokoa ei voi muuttaa
        mainFrame.setResizable(false);

        // Lähetetään pääikkunan luokka Ui:lle käytettäväksi
        ui.SetFrame(mainFrame);

        // Luodaan paneeli jossa on painikkeet toiminnoille sekä haku-kenttä
        ui.CreateTopPanel(this);

        // Luodaan paneeli jossa on painikkeet joista voi valita näytetäänkö DVD:t vai kirjat
        ui.CreateCenterPanel(this);

        // Luodaan paneeli jossa näytetään kirjat tai DVD:t ja lisäksi kirjojen tai DVD:tten määrä
        ui.CreateBottomPanel();

        // Lisätään paneeli ikkunan yläosaan
        mainFrame.add(ui.GetTopPanel(), BorderLayout.NORTH);

        // Lisätään paneeli ikkunan keskiosaan
        mainFrame.add(ui.GetCenterPanel(), BorderLayout.CENTER);

        // Lisätään paneeli ikkunan alaosaan
        mainFrame.add(ui.GetBottomPanel(), BorderLayout.SOUTH);

        // Ikkunan sijainti
        mainFrame.setLocation(200, 200);
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    /*
     * Exit
     */
    public void Exit() {

        System.out.println("Utils.Exit()");

        sql.CloseConnection();
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }

    /*
     * Print
     *
     * Tulosta kirjasto
     *
     */
    public void PrintLibrary() {

        System.out.println("Utils.PrintLibrary()");

        switch(ui.GetPaneIndex()) {
            case 0:  {
                try {

                    boolean complete = ui.GetBookTable().print();
                    if (complete) {

                        System.out.println("Printing was success.");

                    } else {

                        System.out.println("Printing was ended.");

                    }
                } catch (PrinterException pe) {

                    System.out.println("Printing failed.");
                }
                break;
            }
            case 1: {
                try {

                    boolean complete = ui.GetDvdTable().print();
                    if (complete) {

                        System.out.println("Printing was success.");

                    } else {

                        System.out.println("Printing was ended.");

                    }
                } catch (PrinterException pe) {

                    System.out.println("Printing failed.");
                }
                break;
            }
        }
    }

    /*
     * Export
     *
     * Viedään kirjaston kirjat ja DVD:t XML-tiedostoon
     *
     */
    public void ExportLibrary() {

        System.out.println("Utils.ExportLibrary()");

        File xmlFile = null;

        boolean exportSuccess = false;

        JFileChooser exportFile = new JFileChooser();
        exportFile.setCurrentDirectory(new File("."));
        exportFile.setDialogTitle(Text.exportText);
        exportFile.setAcceptAllFileFilterUsed(false);
        exportFile.addChoosableFileFilter(new XmlFilter());
        int tulos = exportFile.showSaveDialog(mainFrame);

        if (tulos == JFileChooser.CANCEL_OPTION) {

            // Tallennuksessa valittu Cancel tiedoston tallennuksessa
            ui.CreateMessageDialog(ui.GetFrame(),Text.exportCancel,Text.exportText,JOptionPane.INFORMATION_MESSAGE);

        } else if (tulos == JFileChooser.APPROVE_OPTION) {

            // Tallennus hyväksyttiin
            xmlFile = exportFile.getSelectedFile();

            // Tarkistetetaan onko XML-tiedosto jo olemassa
            if(xmlFile.exists()) {

                // Tarkistetaan tallennetaanko aikaisemman yli
                int overwrite = ui.CreateOptionDialog(ui.GetFrame(), Text.fileoverwrite, Text.exportText);

                if(JOptionPane.NO_OPTION == overwrite) {

                    // Ei tallenneta yli
                    ui.CreateMessageDialog(ui.GetFrame(),Text.exportCancel,Text.exportText,JOptionPane.INFORMATION_MESSAGE);

                } else if(JOptionPane.YES_OPTION == overwrite) {

                    exportSuccess = true;
                }

            } else if (!xmlFile.exists()) {

            }
        }

        if (exportSuccess) {

            try {

                xml.Export(ui.GetLibraryBookModel(),ui.GetLibraryDvdModel(),xmlFile.toString());
                ui.CreateMessageDialog(ui.GetFrame(),Text.exportSuccess,Text.exportText,JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {

                ui.CreateMessageDialog(ui.GetFrame(),Text.exportFail,Text.exportText,JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    /*
     * Import
     *
     * Tuodaan kirjaston kirjat ja DVD:t XML-tiedostosta
     *
     */
    @SuppressWarnings("unchecked")
    public void ImportLibrary() {

        System.out.println("Utils.ImportLibrary()");

        JFileChooser importFile = new JFileChooser();
        importFile.setCurrentDirectory(new File("."));
        importFile.setDialogTitle(Text.exportText);
        importFile.setAcceptAllFileFilterUsed(false);
        importFile.addChoosableFileFilter(new XmlFilter());
        int tulos = importFile.showOpenDialog(ui.GetFrame());

        if (tulos == JFileChooser.CANCEL_OPTION) {

            ui.CreateMessageDialog(ui.GetFrame(),Text.importCancel,Text.importText,JOptionPane.INFORMATION_MESSAGE);

        } else if (tulos == JFileChooser.APPROVE_OPTION) {

            try {

                File xmlFile = importFile.getSelectedFile();
                sql.DeleteDatabase();
                sql.CreateDatabase();

                Object[] library = xml.Import(xmlFile.toString());

                book.SetBookModel((Vector<Book>) library[0]);
                dvd.SetDvdModel((Vector<Dvd>) library[1]);

                // Luodaan kirjaston kirja- ja DVD-mallit saadusta XML:stä
                LibraryBookModel bookModel = new LibraryBookModel((Vector<Book>)library[0]);
                LibraryDvdModel dvdModel = new LibraryDvdModel((Vector<Dvd>)library[1]);

                // Otetaan importin tuloksena talteen kirjojen model ja DVD:n model talteen UI:lle
                ui.SetLibraryBookModel(bookModel);
                ui.SetLibraryDvdModel(dvdModel);

                // Luodaan taulukkoikkunat kirjoille ja DVD:lle
                ui.CreateBookScrollPane(bookModel);
                ui.CreateDvdScrollPane(dvdModel);

                // Otetaan talteen kirjojen ja DVD:tten määrä talteen UI:lle
                ui.SetBookCount(book.GetBookCount());
                ui.SetDvdCount(dvd.GetDvdCount());

                // Lisätään kirjat tietokantaan
                for (int i=0; i < bookModel.getRowCount(); i++) {

                    sql.AddBook(bookModel.getRowAt(i));
                }

                // Lisätään Dvd:t tietokantaan
                for (int i=0; i < dvdModel.getRowCount(); i++) {

                    sql.AddDvd(dvdModel.getRowAt(i));
                }

                ui.UpdateBottomPanel();

                ui.CreateMessageDialog(ui.GetFrame(),Text.importSuccess,Text.importText,JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {

                ui.CreateMessageDialog(ui.GetFrame(),Text.importFail,Text.importText,JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent anEvent) {

        System.out.println("Utils.actionPerformed()");

        String command = anEvent.getActionCommand();

        if (Objects.equals(command, Text.exportCommand)) {

            System.out.println(Text.exportCommand);

            ExportLibrary();

        } else if (Objects.equals(command, Text.importCommand)) {

            System.out.println(Text.importCommand);

            ImportLibrary();

        } else if (Objects.equals(command, Text.addBookCommand)) {

            System.out.println(Text.addBookCommand);

            ui.CreateAddBookDialog();
            CreateLibrary();
            ui.UpdateBottomPanel();

        } else if (Objects.equals(command, Text.addDvdCommand)) {

            System.out.println(Text.addDvdCommand);

            ui.CreateAddDvdDialog();
            CreateLibrary();
            ui.UpdateBottomPanel();

        } else if (Objects.equals(command, Text.modifyBookCommand)) {

            System.out.println(Text.modifyBookCommand);

            Book book = ui.GetLibraryBookModel().getRowAt(ui.GetCurRowIndex());
            ui.CreateModifyBookDialog(book);
            CreateLibrary();
            ui.UpdateBottomPanel();

        } else if (Objects.equals(command, Text.modifyDvdCommand)) {

            System.out.println(Text.modifyDvdCommand);

            Dvd dvd = ui.GetLibraryDvdModel().getRowAt(ui.GetCurRowIndex());
            ui.CreateModifyDvdDialog(dvd);
            CreateLibrary();
            ui.UpdateBottomPanel();

        } else if (Objects.equals(command, Text.removeBookCommand)) {

            System.out.println(Text.removeBookCommand);

            Book book = ui.GetLibraryBookModel().getRowAt(ui.GetCurRowIndex());

            boolean choice = ui.CreateRemoveBookDialog(book);

            if (choice) {

                sql.RemoveBook(book);

                CreateLibrary();
                ui.UpdateBottomPanel();

            }

        } else if (Objects.equals(command, Text.removeDvdCommand)) {

            System.out.println(Text.removeDvdCommand);

            Dvd dvd = ui.GetLibraryDvdModel().getRowAt(ui.GetCurRowIndex());

            boolean choice = ui.CreateRemoveDvdDialog(dvd);

            if (choice) {

                sql.RemoveDvd(dvd);

                CreateLibrary();
                ui.UpdateBottomPanel();

            }

        } else if (Objects.equals(command, Text.printCommand)) {

            System.out.println(Text.printCommand);

            PrintLibrary();

        } else if (Objects.equals(command, Text.exitCommand)) {

            System.out.println(Text.exitCommand);

            Exit();

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void insertUpdate(DocumentEvent e) {

        System.out.println("Utils.insertUpdate()");

        ui.SearchLibrary();
        ui.UpdateBottomPanel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

        System.out.println("Utils.removeUpdate()");

        ui.SearchLibrary();
        ui.UpdateBottomPanel();

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}

/**
* @author fiinmani
*
*/
final class XmlFilter extends javax.swing.filechooser.FileFilter implements FileFilter {

    /*
     * accept
     */
    public boolean accept(File f) {

        if (f.isDirectory()) {
            return true;
        }

        String extension = FileUtils.getExtension(f);
        if (extension != null) {

            if (extension.equals(FileUtils.xml)) {

                return true;

            } else {

                return false;
            }
        }

        return false;
    }

    /*
     * getDescription
     */
    public String getDescription() {
        return "XML files";
    }
}

/**
 * @author fiinmani
 *
 */
final class FileUtils {
    public final static String xml = "xml";

    /*
     * getExtension
     */
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }
}
