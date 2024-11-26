package album.controleur;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

public class RepositoryController {

    private static final String[] EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".bmp", ".tiff"};
    
    @FXML private FlowPane flowPane;
    private String directoryPath;

    @FXML
    public void initialize() {
        if (directoryPath != null) refresh();
    }

    @FXML
    public void refresh(){
        flowPane.getChildren().clear();
        for (File image : getImagesFromDirectory()){
            flowPane.getChildren().add(new ImageView(new Image(image.getPath())));
        }
    }

    public void setDirectoryPath(String newDirectoryPath){
        directoryPath = newDirectoryPath;
        refresh();
    }

    private List<File> getImagesFromDirectory() {
        File directory = new File(directoryPath);
        List<File> imageFiles = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && isImage(file)) {
                        imageFiles.add(file);
                    }
                }
            }
        } else {
            System.out.println("Le répertoire n'existe pas ou n'est pas valide.");
        }

        return imageFiles;
    }

    private boolean isImage(File file){
        for (String ext : EXTENSIONS){
            if (file.getName().toLowerCase().endsWith(ext)){
                return true;
            }
        }
        return false;
    }
}
