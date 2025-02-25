package album.structure;

import java.util.List;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.Serializable;
import java.util.LinkedList;

public class Album implements Serializable {

    private int id;
    private String name; 
    private LinkedList<Page> pages;
    private int currentPageIndex;
    private String directoryPath;
    private Boolean modified;

    public Album(String name,String path) {
        this.name = name;
        this.pages = new LinkedList<Page>();
        pages.add(new Page(null));
        this.currentPageIndex = 0;
        directoryPath = path;
        modified = false;
        id = this.hashCode();
    }

    public Boolean isModified(){
        return modified;
    }

    public int getId(){
        return id;
    }

    public void addPage(Page page) {
        pages.addLast(page);
        modified = true;
    }

    public void setLeft(Photo newLeft){
        getCurrentPage().setLeft(newLeft);
        modified = true;
    }

    public void setRight(Photo newRight){
        getCurrentPage().setRight(newRight);
        modified = true;
    }

    public void setModified(Boolean bool){
        modified = bool;
    }

    public void removePage() {
        if (pages.size() > 1){
            
            pages.remove(currentPageIndex);
            modified = true;

            if (currentPageIndex == pages.size()) currentPageIndex -= 1;
           
        } 
        else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText("Suppression impossible");
            alert.setContentText("Il doit y avoir au moins une page dans l'");
            alert.showAndWait();
        }
    }

    public Page getCurrentPage() {
        return pages.get(currentPageIndex);
    }

    public void setCurrentPage(int newPageIndex) {
        currentPageIndex = newPageIndex;
    }

    public void setName(String name){
        this.name = name;
        modified = true;
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
        modified = true;
    }
}
