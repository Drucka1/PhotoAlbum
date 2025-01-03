package album.structure;

import java.util.List;
import java.io.Serializable;
import java.util.LinkedList;

public class Album implements Serializable {

    private String name; 
    private LinkedList<Page> pages;
    private int currentPageIndex;
    private String directoryPath;

    public Album(String name,String path) {
        this.name = name;
        this.pages = new LinkedList<Page>();
        pages.add(new Page(null));
        this.currentPageIndex = 0;
        directoryPath = path;
    }

    public void addPhoto(Photo photo) {
        if (pages.size() > 0 && pages.getLast().getRight() == null){
            pages.getLast().setRight(photo);
        }
        else pages.addLast(new Page(photo));
    }

    public void addPages(Page page) {
        pages.addLast(page);
    }

    public Page getCurrentPage() {
        return pages.get(currentPageIndex);
    }

    public List<Page> getPages() {
        return this.pages;
    }

    public void nextPage() {
        if (currentPageIndex < pages.size() - 1) {
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
        currentPageIndex = pages.size() - 1;
    }

    public String getName() {
        return name;
    }
    
    public int size(){
        return pages.size();
    }

    public int currentIndex(){
        return currentPageIndex;
    }

    public String getDirectoryPath(){
        return directoryPath;
    }

    public void setDirectoryPath(String newPath){
        directoryPath = newPath;
    }

}
