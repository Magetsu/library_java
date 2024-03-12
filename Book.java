package Library;

import java.util.Vector;

/**
 * @author fiinmani
 *
 *	Book()
 *	GetBookModel()
 *	GetSurname()
 *	GetForename()
 *	GetBookname()
 *	GetPubyear()
 *	GetIsbn()
 *	GetOrgname()
 *	GetOrgyear()
 *	GetOther()
 *	GetSelectedBook()
 *	GetBookCount()
 *
 *	SetBookModel()
 *
 */
public class Book {

    private String surname;
    private String forename;
    private String bookname;
    private int pubyear;
    private String isbn;
    private String orgname;
    private int orgyear;
    private String other;

    private LibraryBookModel bookModel;

    public Book() {

    }

    public void SetBook(String aSurname, String aForename, String aBookname, int aPubyear, String aIsbn, String aOrgname, int aOrgyear, String aOther) {

        surname = aSurname;
        forename = aForename;
        bookname = aBookname;
        pubyear = aPubyear;
        isbn = aIsbn;
        orgname = aOrgname;
        orgyear = aOrgyear;
        other = aOther;
    }

    public LibraryBookModel GetBookModel() {

        return bookModel;
    }

    public String GetSurname() {

        return surname;
    }

    public String GetForename() {

        return forename;
    }

    public String GetBookname() {

        return bookname;
    }

    public int GetPubyear() {

        return pubyear;
    }

    public String GetIsbn() {

        return isbn;
    }

    public String GetOrgname() {

        return orgname;
    }

    public int GetOrgyear() {

        return orgyear;
    }

    public String GetOther() {

        return other;
    }

    /*
     * GetBookCount
     *
     * Palautetaan kirjojen määrä
     *
     */
    public int GetBookCount() {

        System.out.println("Book.GetBookCount()");

        return bookModel.getRowCount();
    }

    /*
     * CreateBookModel
     *
     * Luodaan kirjamalli
     *
     */
    public void SetBookModel(Vector<Book> aModel) {

        System.out.println("Book.SetBookModel()");

        bookModel = new LibraryBookModel(aModel);
    }
}
