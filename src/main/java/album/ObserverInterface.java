package album;

import album.structure.Album;

public interface ObserverInterface {
    void update();
    void changeAlbum(Album album);
} 
