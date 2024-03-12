package Library;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Vector;

public class Xml {

    /*
     *	Xml()
     *  Export()
     *  Import()
     *
     */
    public Xml() {

        System.out.println("	Xml.Xml()");
    }

    /*
     *   Export()
     */
    public void Export(LibraryBookModel bookModel, LibraryDvdModel dvdModel, String aFile) throws Exception {

        System.out.println("	Xml.Export()");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation impl = builder.getDOMImplementation();
        Document doc = impl.createDocument(null, null, null);

        Element library = doc.createElement(Text.elementLibrary);
        doc.appendChild(library);

        for (int i =0; i < bookModel.getRowCount(); i++) {

            Element bookElement = doc.createElement(Text.elementBook);

            for (int j = 0; j < bookModel.getColumnCount(); j++) {

                Element element;
                Node node;

                element = doc.createElement(Text.bookDataFieldNames[j]);

                if (bookModel.getValueAt(i, j) != null) {

                    node = doc.createTextNode(bookModel.getValueAt(i, j).toString());
                    element.appendChild(node);
                }
                bookElement.appendChild(element);
            }
            library.appendChild(bookElement);
        }

        for (int i =0; i < dvdModel.getRowCount(); i++) {

            Element dvdElement = doc.createElement(Text.elementDvd);

            for (int j = 0; j < dvdModel.getColumnCount(); j++) {

                Element element;
                Node node;

                element = doc.createElement(Text.dvdDataFieldNames[j]);
                if (dvdModel.getValueAt(i, j) != null) {

                    node = doc.createTextNode(dvdModel.getValueAt(i, j).toString());
                    element.appendChild(node);
                }
                dvdElement.appendChild(element);
            }
            library.appendChild(dvdElement);
        }

        DOMSource domSource = new DOMSource(doc);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        Result result = new StreamResult(new File(aFile));
        transformer.transform(domSource, result);
    }

    /*
     *  Import()
     */
    public Object[] Import(String aFilename) {

        System.out.println("	Xml.Import()");

        Vector<Book> bookVector = new Vector<>();
        Vector<Dvd> dvdVector = new Vector<>();

        // Taulukko jossa on kirjaston kaikki kirjat ja DVD:t
        Object[] library = {new Vector<Book>(), new Vector<Dvd>()};

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(aFilename);
            doc.getDocumentElement().normalize();

            if (doc.getDocumentElement().getNodeName().compareTo(Text.elementLibrary) == 0) {

                NodeList bookList = doc.getElementsByTagName(Text.elementBook);

                for (int s = 0; s < bookList.getLength(); s++) {

                    Node bookNode = bookList.item(s);

                    if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                        int length = Text.bookDataFieldNames.length;
                        String[] data = new String[length];

                        for (int i = 0; i < length; i++) {

                            NodeList fstNmElmntLst = ((Element) bookNode).getElementsByTagName(Text.bookDataFieldNames[i]);
                            Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                            NodeList fstNm = fstNmElmnt.getChildNodes();
                            try {
                                Node node = fstNm.item(0);
                                if (node != null) {
                                    data[i] = node.getNodeValue();
                                } else {
                                    data[i] = "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Book newBook = new Book();

                        newBook.SetBook(data[0],
                                data[1],
                                data[2],
                                Integer.parseInt(data[3]),
                                data[4],
                                data[5],
                                Integer.parseInt(data[6]),
                                data[7]);

                        bookVector.addElement(newBook);
                    }
                }

                library[0] = bookVector;

                NodeList dvdList = doc.getElementsByTagName(Text.elementDvd);

                for (int s = 0; s < dvdList.getLength(); s++) {

                    Node dvdNode = dvdList.item(s);

                    if (dvdNode.getNodeType() == Node.ELEMENT_NODE) {
                        int length = Text.dvdDataFieldNames.length;
                        String[] data = new String[length];

                        for (int i = 0; i < length; i++) {

                            NodeList fstNmElmntLst = ((Element) dvdNode).getElementsByTagName(Text.dvdDataFieldNames[i]);
                            Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
                            NodeList fstNm = fstNmElmnt.getChildNodes();
                            try {
                                Node node = fstNm.item(0);
                                if (node != null) {
                                    data[i] = node.getNodeValue();
                                } else {
                                    data[i] = "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Dvd newDvd = new Dvd();

                        newDvd.SetDvd(data[0],
                                Integer.parseInt(data[1]),
                                data[2],
                                data[3],
                                data[4],
                                data[5],
                                data[6]);

                        dvdVector.addElement(newDvd);

                    }
                }

                library[1] = dvdVector;

            } else {

                System.out.println("Import failed.");
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return library;
    }
}
