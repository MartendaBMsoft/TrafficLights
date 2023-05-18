package handler.observer;

public interface Observer {
    
    public void updateCarField();
    public void updateStartButton(boolean status);
    public void updateEndButton(boolean status);
    public void updateCounter(int value);
}
