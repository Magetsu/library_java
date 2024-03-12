package Library;

import java.sql.*;
import java.util.Vector;

/**
 * @author fiinmani
 *
 * Sql()
 * GetConnection()
 * CloseConnection()
 * CreateDatabase()
 * DeleteDatabase()
 * GetBooks()
 * GetDvds()
 * AddBook()
 * AddDvd()
 * RemoveBook()
 * RemoveDvd()
 * ModifyBook()
 * ModifyDvd()
 */
public class Sql {

    Connection connection;

    /*
     *
     */
    public Sql() {

        System.out.println("	Sql.Sql()");
    }

    /*
     *
     */
    public void GetConnection() {

        System.out.println("	Sql.GetConnection()");

        try {

            Class.forName(Text.driver);
            connection = DriverManager.getConnection(Text.url, Text.username, Text.password);

        } catch (ClassNotFoundException e) {

            System.out.println("SQL Driver was not found. SQL is not installed?");

        } catch (SQLException e) {

            System.out.println("Could not open the connection. Database not found.");
        }

        System.out.println("	Connection created");
    }

    /*
     *
     */
    public void CloseConnection() {

        System.out.println("	Sql.CloseConnection()");

        if (connection != null) {
            try {

                connection.close();

            } catch (SQLException e) {

                System.out.println("Could not close the connection.");
            }
        }
        System.out.println("	Connection closed");
    }

    /*
     *
     */
    public void CreateDatabase() {

        System.out.println("	Sql.CreateDatabase()");

        Statement statement = null;

        try {

            statement = connection.createStatement();
            statement.executeUpdate(Text.CREATE_DATABASE);

        } catch (SQLException e) {

            System.out.println("Could not create the database.");

        } finally {

            if (statement != null) {

                try {

                    statement.executeUpdate(Text.CREATE_BOOK_TABLE);
                    statement.executeUpdate(Text.CREATE_DVD_TABLE);
                    statement.close();

                } catch (SQLException e) {

                    System.out.println("Could not create the table or close the statement.");
                }
            }
        }
        System.out.println("	Database created");
    }

    /*
     *
     */
    public void DeleteDatabase() {

        System.out.println("	Sql.DeleteDatabase()");

        Statement statement = null;

        try {

            statement = connection.createStatement();
            statement.executeUpdate(Text.DELETE);

        } catch (Exception e) {

            System.out.println("Could not delete the database.");
            e.printStackTrace();

        } finally {
            if (statement != null) {

                try {

                    statement.close();

                } catch (SQLException e) {

                    System.out.println("Could not close the statement.");
                }
            }
        }
    }

    /*
     *
     */
    public Vector<Book> GetBooks(String aQueryText) {

        System.out.println("	Sql.GetBooks()");

        Statement statement = null;
        ResultSet res;
        Vector<Book> dataVector = new Vector<>();

        try {

            statement = connection.createStatement();

            res = statement.executeQuery(aQueryText);

            while (res.next()) {

                Book newBook = new Book();

                newBook.SetBook(res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getInt(4),
                        res.getString(5),
                        res.getString(6),
                        res.getInt(7),
                        res.getString(8));

                dataVector.addElement(newBook);

            }
            res.close();
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return dataVector;
    }

    /*
     *
     */
    public Vector<Dvd> GetDvds(String aQueryText) {

        System.out.println("	Sql.GetDvds()");

        Statement statement = null;
        ResultSet res;
        Vector<Dvd> dataVector = new Vector<>();

        try {

            statement = connection.createStatement();

            res = statement.executeQuery(aQueryText);

            while (res.next()) {

                Dvd newDvd = new Dvd();

                newDvd.SetDvd(res.getString(1),
                        res.getInt(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7));

                dataVector.addElement(newDvd);

            }
            res.close();
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return dataVector;
    }

    /*
     *
     */
    public boolean AddBook(Book aBook) {

        System.out.println("	Sql.AddBook()");

        PreparedStatement statement = null;
        boolean returnValue = false;
        boolean addfail = false;

        try {

            statement  = connection.prepareStatement(Text.ADD_BOOK);
            statement.setString(1,aBook.GetSurname());
            statement.setString(2,aBook.GetForename());
            statement.setString(3,aBook.GetBookname());
            statement.setInt(4,aBook.GetPubyear());
            statement.setString(5,aBook.GetIsbn());
            statement.setString(6,aBook.GetOrgname());
            statement.setInt(7,aBook.GetOrgyear());
            statement.setString(8,aBook.GetOther());
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Could not add book to the database.");
            addfail = true;

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                    if (!addfail) {

                        returnValue = true;

                    }
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return returnValue;
    }

    /*
     *
     */
    public boolean AddDvd(Dvd aDvd) {

        System.out.println("	Sql.AddDvd()");

        PreparedStatement statement = null;
        boolean returnValue = false;
        boolean addfail = false;

        try {

            statement  = connection.prepareStatement(Text.ADD_DVD);
            statement.setString(1,aDvd.GetName());
            statement.setInt(2,aDvd.GetYear());
            statement.setString(3,aDvd.GetOrgname());
            statement.setString(4,aDvd.GetBarcode());
            statement.setString(5,aDvd.GetDirector());
            statement.setString(6,aDvd.GetMusic());
            statement.setString(7,aDvd.GetOther());
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Could not add book to the database.");
            addfail = true;

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                    if (!addfail) {

                        returnValue = true;

                    }
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return returnValue;
    }

    /*
     *
     */
    public boolean RemoveBook(Book aBook) {

        System.out.println("	Sql.RemoveBook()");

        PreparedStatement statement = null;
        boolean returnValue = false;
        boolean removeFail = false;

        try {

            statement  = connection.prepareStatement(Text.REMOVE_BOOK);
            statement.setString(1,aBook.GetSurname());
            statement.setString(2,aBook.GetForename());
            statement.setString(3,aBook.GetBookname());
            statement.setInt(4,aBook.GetPubyear());
            statement.setString(5,aBook.GetIsbn());
            statement.setString(6,aBook.GetOrgname());
            statement.setInt(7,aBook.GetOrgyear());
            statement.setString(8,aBook.GetOther());
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Could not remove book from the database.");
            removeFail = true;

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                    if (!removeFail) {

                        returnValue = true;

                    }
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return returnValue;
    }

    /*
     *
     */
    public boolean RemoveDvd(Dvd aDvd) {

        System.out.println("	Sql.RemoveDvd()");

        PreparedStatement statement = null;
        boolean returnValue = false;
        boolean removeFail = false;

        try {

            statement  = connection.prepareStatement(Text.REMOVE_DVD);
            statement.setString(1,aDvd.GetName());
            statement.setInt(2,aDvd.GetYear());
            statement.setString(3,aDvd.GetOrgname());
            statement.setString(4,aDvd.GetBarcode());
            statement.setString(5,aDvd.GetDirector());
            statement.setString(6,aDvd.GetMusic());
            statement.setString(7,aDvd.GetOther());
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Could not remove dvd from the database.");
            removeFail = true;

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                    if (!removeFail) {

                        returnValue = true;

                    }
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return returnValue;
    }

    /*
     *
     */
    public boolean ModifyBook(Book aBook, Book aOrgbook) {

        System.out.println("	Sql.ModifyBook()");

        PreparedStatement statement = null;
        boolean returnValue = false;
        boolean modifyFail = false;

        try {

            statement  = connection.prepareStatement(Text.UPDATE_BOOK);
            statement.setString(1,aBook.GetSurname());
            statement.setString(2,aBook.GetForename());
            statement.setString(3,aBook.GetBookname());
            statement.setInt(4,aBook.GetPubyear());
            statement.setString(5,aBook.GetIsbn());
            statement.setString(6,aBook.GetOrgname());
            statement.setInt(7,aBook.GetOrgyear());
            statement.setString(8,aBook.GetOther());
            statement.setString(9,aOrgbook.GetSurname());
            statement.setString(10,aOrgbook.GetForename());
            statement.setString(11,aOrgbook.GetBookname());
            statement.setInt(12,aOrgbook.GetPubyear());
            statement.setString(13,aOrgbook.GetIsbn());
            statement.setString(14,aOrgbook.GetOrgname());
            statement.setInt(15,aOrgbook.GetOrgyear());
            statement.setString(16,aOrgbook.GetOther());
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Could not update book to the database.");
            modifyFail = true;

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                    if (!modifyFail) {

                        returnValue = true;
                    }
                } catch (SQLException e) {

                    e.printStackTrace();
                }
            }
        }
        return returnValue;
    }

    /*
     *
     */
    public boolean ModifyDvd(Dvd aDvd, Dvd aOrgDvd) {

        System.out.println("	Sql.ModifyDvd()");

        PreparedStatement statement = null;
        boolean returnValue = false;
        boolean modifyFail = false;

        try {

            statement  = connection.prepareStatement(Text.UPDATE_DVD);
            statement.setString(1,aDvd.GetName());
            statement.setInt(2,aDvd.GetYear());
            statement.setString(3,aDvd.GetOrgname());
            statement.setString(4,aDvd.GetBarcode());
            statement.setString(5,aDvd.GetDirector());
            statement.setString(6,aDvd.GetMusic());
            statement.setString(7,aDvd.GetOther());
            statement.setString(8,aOrgDvd.GetName());
            statement.setInt(9,aOrgDvd.GetYear());
            statement.setString(10,aOrgDvd.GetOrgname());
            statement.setString(11,aOrgDvd.GetBarcode());
            statement.setString(12,aOrgDvd.GetDirector());
            statement.setString(13,aOrgDvd.GetMusic());
            statement.setString(14,aOrgDvd.GetOther());
            statement.executeUpdate();

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Could not update dvd to the database.");
            modifyFail = true;

        } finally {

            if (statement != null) {

                try {

                    statement.close();

                    if (!modifyFail) {

                        returnValue = true;
                    }
                } catch (SQLException e) {

                    e.printStackTrace();
                } // nothing we can do
            }
        }
        return returnValue;
    }
}
