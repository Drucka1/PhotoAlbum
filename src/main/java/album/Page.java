package album;

public class Page {
    
    private Photo left;
    private Photo right;

    public Page(Photo left, Photo right){
        this.right = right;
        this.left = left;
    }

    public Page(Photo left){
        this.left = left;
        this.right = null;
    }

    public Photo getLeft(){
        return left;
    }

    public Photo getRight(){
        return right;
    }

    public void setLeft(Photo newLeft){
        this.left = newLeft;
    }

    public void setRight(Photo newRight){
        this.right = newRight;
    }
}
