package album;

import javafx.scene.image.Image;

public class Photo {

    private String name;
    private Image image;

    public Photo(String name, String imagePath) {
        this.name = name;
        this.image = new Image(getClass().getResource(imagePath).toExternalForm());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


}