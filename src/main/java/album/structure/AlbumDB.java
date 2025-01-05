package album.structure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class AlbumDB {

    private static String path = "./src/main/resources/albums/";

    public static void saveAlbum(Album album) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path+album.getId()))) {
            album.setModified(false);
            oos.writeObject(album);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Album loadAlbum(String albumId) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path+albumId))) {
            Album album = (Album) ois.readObject();
            return album;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Pair<String, Integer>> loadAlbums() {
        File albumDir = new File(path);
        File[] files = albumDir.listFiles(); 
        System.out.println(files);

        ObservableList<Pair<String, Integer>> albumNames = FXCollections.observableArrayList();
        if (files != null) {
            for (File file : files) {
                Album tmp = AlbumDB.loadAlbum(file.getName());
                albumNames.add( new Pair<>(tmp.getName(),tmp.getId()) ); 
            }
        }
        return albumNames;
    }

    public static void deleteAlbum(int albumId) {
        File albumFile = new File(path + albumId);

        if (albumFile.exists()) {
           albumFile.delete(); 
        }
    }
}