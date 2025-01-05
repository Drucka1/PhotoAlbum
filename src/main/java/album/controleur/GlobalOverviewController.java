package album.controleur;

import album.structure.*;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GlobalOverviewController {

    @FXML private FlowPane flowPane;
    
    private Album album;
    private Scene scene;

    private BrowseAlbumController ba;

    public GlobalOverviewController() {}

    public GlobalOverviewController(Album album,Scene scene, BrowseAlbumController ba){
        this.album = album;
        this.scene = scene;
        this.ba = ba;
    }

    public void refresh() {
        flowPane.getChildren().clear();

        for (Page page : album.getPages()){

            HBox hbox = new HBox();
            hbox.setSpacing(2);

            if (page.getLeft() != null){
                ImageView img = new ImageView(new Image(page.getLeft().getImagePath()));
                img.setPreserveRatio(true);
                img.setFitWidth(280);

                hbox.getChildren().add(img);
            }
            

            if (page.getRight() != null){
                ImageView img = new ImageView(new Image(page.getRight().getImagePath()));
                img.setPreserveRatio(true);
                img.setFitWidth(280);
                

                hbox.getChildren().add(img);
            }

            VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setAlignment(Pos.CENTER);
            vbox.setOnMouseClicked( new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if (event instanceof MouseEvent) {
                            MouseEvent mouseEvent = (MouseEvent) event;
                            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                                handleBack(album.getPages().indexOf(page));
                            }
                        }
                    }
                });

            vbox.getChildren().addAll(hbox, new Label("Page "+(album.getPages().indexOf(page)+1)));

            flowPane.getChildren().add(vbox);
            
        }        
    }

    public void handleBack(int pageNumber) {
        Stage stage = (Stage) flowPane.getScene().getWindow();  
        stage.setScene(scene); 
        album.setCurrentPage(pageNumber);
        ba.update();
    }
}

