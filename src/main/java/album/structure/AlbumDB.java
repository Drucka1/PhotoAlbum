package album.structure;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public static ObservableList<String> loadAlbums() {
        File albumDir = new File("./src/main/resources/albums/");
        File[] files = albumDir.listFiles(); 
        System.out.println(files);

        ObservableList<String> albumNames = FXCollections.observableArrayList();
        if (files != null) {
            for (File file : files) {
                albumNames.add(file.getName()); 
            }
        }
        return albumNames;
    }

    public static boolean deleteAlbum(String albumName) {
        File albumFile = new File(path + albumName);

        // Vérifier si le fichier existe
        if (albumFile.exists()) {
            boolean deleted = albumFile.delete(); // Supprimer le fichier
            if (deleted) {
                System.out.println("L'album a été supprimé : " + albumName);
            } else {
                System.out.println("Erreur lors de la suppression de l'album : " + albumName);
            }
            return deleted;
        } else {
            System.out.println("L'album n'existe pas : " + albumName);
            return false;
        }
    }
}