package handler;

public class ThreadHandler extends Thread {

    MeshHandler meshHandler = MeshHandler.getInstance();
    private int carsQuantity;
    private int timer;

    public MeshHandler getMeshHandler() {
        return this.meshHandler;
    }

    public void setCarsQuantity(int carsQuantity) {
        this.carsQuantity = carsQuantity;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer * 1000;
    }

    @Override
    public void run() {
        for (int i = 0; i < carsQuantity; i++) {
            if (meshHandler.isStopped()) {
                stop();
                break;
            }
            meshHandler.start();
            try {
                Thread.currentThread().sleep(getTimer());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
