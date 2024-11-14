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
        loader.setLocation(fxmlURL);
        MainWindowController main = new MainWindowController(album);
        OverviewController overview = new OverviewController(album);
        BrowseAlbumController browse = new BrowseAlbumController(album);

        browse.addObserver(overview);

        loader.setControllerFactory(ic -> {
            if (ic.equals(album.controleur.MainWindowController.class)) return main;
            if (ic.equals(album.controleur.OverviewController.class)) return overview;
            if (ic.equals(album.controleur.BrowseAlbumController.class)) return browse;
            return null ;
            });


        Parent root = loader.load();

        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Bootstrap Project using FXML");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
