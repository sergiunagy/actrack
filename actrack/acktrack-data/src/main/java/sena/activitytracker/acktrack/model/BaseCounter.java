package sena.activitytracker.acktrack.model;

public class BaseCounter {

    public Integer counter;

    public BaseCounter() {
        this.counter = 0;
    }

    public void inc(){
        this.counter++;
    }
}
