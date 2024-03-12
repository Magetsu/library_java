package Library;


import java.util.Vector;

public class Dvd {

    private String name;
    private int year;
    private String orgname;
    private String barcode;
    private String director;
    private String music;
    private String other;

    private LibraryDvdModel dvdModel;

    /**
     * @author fiinmani
     *
     *	SetBook()
     *	GetBookModel()
     *	GetName()
     *	GetYear()
     *	GetOrgname()
     *	GetBarcode()
     *	GetDirector()
     *	GetMusic()
     *	GetOther()
     *	GetSelectedDvd()
     *	GetDvdCount()
     *
     *	SetDvdModel()
     *
     */
    public Dvd() {

    }

    public void SetDvd(String aName, int aYear, String aOrgname, String aBarcode, String aDirector, String aMusic, String aOther) {

        name = aName;
        year = aYear;
        orgname = aOrgname;
        barcode = aBarcode;
        director = aDirector;
        music = aMusic;
        other = aOther;
    }

    public LibraryDvdModel GetDvdModel() {

        return dvdModel;
    }

    public String GetName() {

        return name;
    }

    public int GetYear() {

        return year;
    }

    public String GetOrgname() {

        return orgname;
    }

    public String GetBarcode() {

        return barcode;
    }

    public String GetDirector() {

        return director;
    }

    public String GetMusic() {

        return music;
    }

    public String GetOther() {

        return other;
    }

    /*
     * GetDvdCount
     *
     * Palautetaan DVD:n määrä
     *
     */
    public int GetDvdCount() {

        System.out.println("Dvd.GetDvdCount()");

        return dvdModel.getRowCount();
    }

    /*
     * CreateDvdModel
     */
    public void SetDvdModel(Vector<Dvd> aModel) {

        System.out.println("Dvd.SetDvdModel()");

        dvdModel = new LibraryDvdModel(aModel);
    }
}
