package Library;

public class MainWindow {

    private final Utils utils;


    /*
     *  MainWindow
     */
    public MainWindow() {

        System.out.println("MainWindow.MainWindow()");

        utils = new Utils();
    }

    /*
     *  Show()
     */
    public void Show() {

        System.out.println("MainWindow.Show()");

        utils.CreateLibrary();
        utils.CreateMainView();
    }
}
