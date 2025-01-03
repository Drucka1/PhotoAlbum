package album.structure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AlbumDB {

    private static String path = "./src/main/resources/albums/";

    public static void saveAlbum(Album album) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path+album.getName()))) {
            oos.writeObject(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Album loadAlbum(String name) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path+name))) {
            Album album = (Album) ois.readObject();
            return album;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}