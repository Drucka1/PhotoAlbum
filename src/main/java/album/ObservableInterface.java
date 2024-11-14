package album;

public interface ObservableInterface {
    void addObserver(ObserverInterface observer);
    void notifyObserver();
}
