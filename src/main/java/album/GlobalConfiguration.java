package album;

public class GlobalConfiguration {
    
    private static final GlobalConfiguration config = new GlobalConfiguration();
    private static Album currentAlbum= null;
    private static String currentRepositoryPath = "";
    
    private GlobalConfiguration() {
    }
    
    public static GlobalConfiguration getInstance() {
        return config;
    }

    public Album getCurrentAlbum() {
        return currentAlbum;
    }

    public void setCurrentAlbum(Album album) {
        currentAlbum = album;
    }

    public String getCurrentRepositoryPath() {
        return currentRepositoryPath;
    }

    public void setCurrentRepositoryPath(String path) {
        currentRepositoryPath = path;
    }
}
