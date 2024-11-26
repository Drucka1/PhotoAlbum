package album;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import album.controleur.BrowseAlbumController;
import album.controleur.MainWindowController;
import album.controleur.OverviewController;
import album.controleur.RepositoryController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Album album = new Album("My Album");
        FXMLLoader loader = new FXMLLoader();

        URL fxmlURL = getClass().getResource("/view/MainWindow.fxml");
        if (fxmlURL == null) {
            System.err.println("Could not find .fxml");
            System.exit(1);
        }
        
        MainWindowController mw = new MainWindowController();
        OverviewController oc = new OverviewController(album);
        BrowseAlbumController ba = new BrowseAlbumController(album);
        RepositoryController rc = new RepositoryController("/home/clem/TN/2A/PCD/lab2-e4206u/src/main/resources/images/");
        ba.addObserver(oc);

        loader.setControllerFactory(controllerClass -> {
            if (controllerClass.equals(MainWindowController.class)) return mw;
            else if (controllerClass.equals(OverviewController.class)) return oc;
            else if (controllerClass.equals(BrowseAlbumController.class)) return ba;
            else if (controllerClass.equals(RepositoryController.class)) return rc;
            return null; 
        });

        
        loader.setLocation(fxmlURL);
        Parent root = loader.load();
    
        

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Bootstrap Project using FXML");
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
