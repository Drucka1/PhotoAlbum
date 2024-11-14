package album;

import java.util.List;
import java.util.ArrayList;

public class Album {

    private String name; 
    private List<Photo> photos;
    private int currentPageIndex;

    public Album(String name) {
        this.name = name;
        this.photos = new ArrayList<>();
        this.currentPageIndex = 0;
    }

    public void addPhoto(String name,String img) {
        photos.add(new Photo(name,img));
    }

    public Photo getCurrentPhoto() {
        return photos.get(currentPageIndex);
    }

    public List<Photo> getPhotos() {
        return this.photos;
    }

    public void nextPage() {
        if (currentPageIndex < photos.size() - 1) {
            currentPageIndex++;
        }
    }

    public void previousPage() {
        if (currentPageIndex > 0) {
            currentPageIndex--;
        }
    }

    public void firstPage() {
        currentPageIndex = 0;
    }

    public void lastPage() {
        currentPageIndex = photos.size() - 1;
    }

    public String getName() {
        return name;
    }

    public int getPagesCount() {
        return photos.size();
    }
}
