package handler;

public class ThreadHandler extends Thread {

    MeshHandler meshHandler = MeshHandler.getInstance();
    private int qtdCarros;
    private int timer;

    public void setCarsQuantity(int carsQuantity) {
        this.qtdCarros = carsQuantity;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer * 1000;
    }

    @Override
    public void run() {
        for (int i = 0; i < qtdCarros; i++) {
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
