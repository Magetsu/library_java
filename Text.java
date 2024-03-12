package Library;

public class Text {

    /**
     *
     */
    static final String mainCaptionText = "Kirjasto";
    static final String exportCommand = "export";
    static final String importCommand = "import";
    static final String addBookCommand = "addbook";
    static final String addBookDialogCommand = "addbookdialog";
    static final String addDvdCommand = "adddvd";
    static final String addDvdDialogCommand = "adddvddialog";
    static final String modifyBookCommand = "modifybook";
    static final String modifyBookDialogCommand = "modifybookdialog";
    static final String modifyDvdCommand = "modifydvd";
    static final String modifyDvdDialogCommand = "modifydvddialog";
    static final String removeBookCommand = "removebook";
    static final String removeDvdCommand = "removedvd";
    static final String printCommand = "print";
    static final String exitCommand = "exit";
    static final String exitDialogCommand = "exitdialog";
    static final String exportText = "Vie kirjasto";
    static final String importText = "Tuo kirjasto";
    static final String addBookText = "Lisää kirja";
    static final String modifyBookText = "Muuta kirjaa";
    static final String removeBookText = "Poista kirja";
    static final String addDvdText = "Lisää DVD";
    static final String modifyDvdText = "Muuta DVD:tä";
    static final String removeDvdText = "Poista DVD";
    static final String searchBookText = "Etsi kirja :";
    static final String searchDvdText = "Etsi DVD :";
    static final String printText = "Tulosta kirjasto";
    static final String printInfo = "Tätä ominaisuutta ei tueta vielä";
    static final String exitText = "Poistu";
    static final String elementLibrary = "library";
    static final String elementBook = "book";
    static final String elementDvd = "dvd";
    static final String exportSuccess = "Kirjaston vienti onnistui.";
    static final String importSuccess = "Kirjaston tuonti onnistui.";
    static final String exportFail = "Kirjaston vienti epäonnistui.";
    static final String importFail = "Kirjaston tuonti epäonnistui.";
    static final String exportCancel = "Kirjaston vienti keskeytettiin.";
    static final String importCancel = "Kirjaston tuonti keskeytettiin.";
    static final String addTitle = "Lisäys";
    static final String addBookSuccess = "Kirjan lisäys onnistui.";
    static final String addBookFail = "Kirjan lisäys epäonnistui.";
    static final String modifyBookSuccess = "Kirjan muutos onnistui.";
    static final String modifyBookFail = "Kirjan muutos epäonnistui.";
    static final String modifyDvdSuccess = "DVD:n muutos onnistui.";
    static final String modifyDvdFail = "DVD:n muutos epäonnistui.";
    static final String addDvdSuccess = "DVD:n lisäys onnistui.";
    static final String addDvdFail = "DVD:n lisäys epäonnistui.";
    static final String intFail = "Julkaisuvuosi sisältää kirjaimia.";
    static final String bookCount = "Kirjojen lukumäärä : ";
    static final String dvdCount = "DVD:tten lukumäärä : ";
    static final String fileoverwrite = "Tiedosto on jo olemassa. Haluatko tallentaa päälle?";
    static final String bookRemove1 = "Haluatko varmasti poistaa kirjan";
    static final String dvdRemove1 = "Haluatko varmasti poistaa DVD:n";
    static final String bookRemove2 = "kirjastosta?";
    static final String dvdRemove2 = "kirjastosta?";
    static final String bookTabPaneText = "Kirja";
    static final String dvdTabPaneText = "Dvd";

    /**
     *
     */
    static final String[] bookButtonText = { exportText, importText, addBookText, removeBookText, modifyBookText,
            printText, exitText };
    static final String[] bookButtonCommand = { exportCommand, importCommand, addBookCommand, removeBookCommand,
            modifyBookCommand, printCommand, exitCommand };
    static final String[] dvdButtonText = { exportText, importText, addDvdText, removeDvdText, modifyDvdText,
            printText, exitText };
    static final String[] dvdButtonCommand = { exportCommand, importCommand, addDvdCommand, removeDvdCommand,
            modifyDvdCommand, printCommand, exitCommand };
    static final Object[] buttonText = { bookButtonText, dvdButtonText };
    static final Object[] buttonCommand = { bookButtonCommand, dvdButtonCommand };
    static final String[] bookDataFieldNames = { "surname", "forename", "bookname", "pubyear", "isbn", "orgname", "orgyear", "other" };
    static final String[] bookTableFieldNames = { "Tekijän sukunimi :", "Tekijän etunimi :", "Kirjan nimi :", "Julkaisuvuosi :", "ISBN :", "Alkuteos :", "Julkaisuvuosi :", "Muuta :" };
    static final String[] dvdDataFieldNames = { "name", "year", "orgname", "barcode", "director", "music", "other" };
    static final String[] dvdTableFieldNames = { "Elokuvan nimi :", "Tekovuosi :", "Alkuteos :", "Tuotenumero :", "Ohjaaja :", "Musiikki :", "Muuta :" };
    static final int[] bookTableFieldLengths = { 110, 100, 300, 100, 100, 300, 100, 200 };
    static final int[] dvdTableFieldLengths = { 300, 100, 300, 100, 100, 100, 200 };
    static final int tableHeight = 600;

    /**
     *  SQL
     */
    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/KIRJASTO";
    static final String username = "root";
    static final String password = "root";
    static final String CREATE_DATABASE = "CREATE DATABASE IF NOT EXISTS library";
    static final String CREATE_BOOK_TABLE = "CREATE TABLE IF NOT EXISTS library.book ( " +
            bookDataFieldNames[0] + " VARCHAR(15) NOT NULL, " +
            bookDataFieldNames[1] + " VARCHAR(20) NOT NULL, " +
            bookDataFieldNames[2] + " VARCHAR(100) NOT NULL, " +
            bookDataFieldNames[3] + " INT NOT NULL, " +
            bookDataFieldNames[4] + " VARCHAR(20) NOT NULL, " +
            bookDataFieldNames[5] + " VARCHAR(100) NOT NULL, " +
            bookDataFieldNames[6] + " INT NOT NULL, " +
            bookDataFieldNames[7] + " VARCHAR(1000) NOT NULL )";
    static final String CREATE_DVD_TABLE = "CREATE TABLE IF NOT EXISTS library.dvd ( " +
            dvdDataFieldNames[0] + " VARCHAR(100) NOT NULL, " +
            dvdDataFieldNames[1] + " INT NOT NULL, " +
            dvdDataFieldNames[2] + " VARCHAR(100) NOT NULL, " +
            dvdDataFieldNames[3] + " VARCHAR(20) NOT NULL, " +
            dvdDataFieldNames[4] + " VARCHAR(100) NOT NULL, " +
            dvdDataFieldNames[5] + " VARCHAR(100) NOT NULL, " +
            dvdDataFieldNames[6] + " VARCHAR(1000) NOT NULL)";
    static final String DELETE = "DROP DATABASE IF EXISTS library";
    static final String ADD_BOOK = "INSERT INTO library.book ( " +
            bookDataFieldNames[0] + ", " +
            bookDataFieldNames[1] + ", " +
            bookDataFieldNames[2] + ", " +
            bookDataFieldNames[3] + ", " +
            bookDataFieldNames[4] + ", " +
            bookDataFieldNames[5] + ", " +
            bookDataFieldNames[6] + ", " +
            bookDataFieldNames[7] + " ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ? )";
    static final String ADD_DVD = "INSERT INTO library.dvd ( " +
            dvdDataFieldNames[0] + ", " +
            dvdDataFieldNames[1] + ", " +
            dvdDataFieldNames[2] + ", " +
            dvdDataFieldNames[3] + ", " +
            dvdDataFieldNames[4] + ", " +
            dvdDataFieldNames[5] + ", " +
            dvdDataFieldNames[6] + " ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
    static final String GET_BOOK = "SELECT * FROM library.book";
    static final String GET_DVD = "SELECT * FROM library.dvd";
    static final String ORDER_BOOK = " ORDER BY surname ";
    static final String ORDER_DVD = " ORDER BY name ";
    static final String WHERE = " WHERE ";
    static final String UPDATE_BOOK = "UPDATE library.book SET surname=?, forename=?, bookname=?, pubyear=?, isbn=?, orgname=?, orgyear=?, other=? "
            + "							WHERE surname=? AND forename=? AND bookname=? AND pubyear=? AND isbn=? AND orgname=? AND orgyear=? AND other=?";
    static final String UPDATE_DVD = "UPDATE library.dvd SET name=?, year=?, orgname=?, barcode=?, director=?, music=?, other=? "
            + "							WHERE name=? AND year=? AND orgname=? AND barcode=? AND director=? AND music=? AND other=?";
    static final String REMOVE_BOOK = "DELETE FROM library.book WHERE (surname=? AND forename=? AND bookname=? AND pubyear=? AND isbn=? AND orgname=? AND orgyear=? AND other=?)";
    static final String REMOVE_DVD = "DELETE FROM library.dvd WHERE (name=? AND year=? AND orgname=? AND barcode=? AND director=? AND music=? AND other=?)";

    static final Object[] options = {"Kyllä","Ei"};
}
