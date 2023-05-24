package model;

import handler.MeshHandler;
import model.abstractfactory.AbstractField;
import model.abstractfactory.ConcreteFieldSemaphore;

public class Car extends Thread {

    private int row;
    private int column;
    private long speed;
    private boolean outOfRoad = false;
    private final MeshHandler meshHandler;
    private AbstractField cell;
    private AbstractField nextCell;// = new ConcreteFieldSemaphore(0, 0, 0);
    boolean stopRunning = false;

    public Car(MeshHandler meshHandler) {
        this.meshHandler = meshHandler;
        setSpeed();
    }

    @Override
    public void run() {
        super.run();

        while (!stopRunning) {
            try {
                sleep(1500 - this.getSpeed());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            this.moveCar();
        }

        this.meshHandler.updateFields();
//        this.meshHandler.updateRoadView(this);
        this.meshHandler.updateCarCount(this);
    }

    private boolean checkLastCell() {
        return cell.isLastCell();
    }

    private void moveCar() {
        this.setCell(meshHandler.getCellAtPosition(this.getRow(), this.getColumn()));

        if (this.getCell().isLastCell()) {
            stopRunning = true;
            return ;
        }

        int newRow = this.getRow();
        int newCol = this.getColumn();

        switch (this.getCell().getMoveType()) {
            case 1: //Up
                newRow --;
                break;
            case 2: //Right
                newCol ++;
                break;
            case 3: //Down
                newRow ++;
                break;
            case 4: //Left
                newCol --;
                break;
        }

        this.setNextCell(meshHandler.getCellAtPosition(newRow, newCol));

        if (this.getNextCell().isStopCell()) {
//            this.moveCarToIntersectionExit(null);
            stopRunning = true;
            return ;
        }

        if (this.getNextCell().getMoveType() != 0 && this.getNextCell().getCar() == null) {
            this.setRow(this.getNextCell().getRow());
            this.setColumn(this.getNextCell().getColumn());
            this.setCell(meshHandler.getCellAtPosition(this.getRow(), this.getColumn()));
        }

        updateFront();
    }

    private void moveCarToIntersectionExit(AbstractField c) {


        updateFront();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setSpeed() {
        this.speed = Double.valueOf((Math.random() * (1500 - 500)) + 500).longValue();
    }

    public long getSpeed() { //timesleep para a thread
        return speed;
    }

    public AbstractField getCell() {
        return cell;
    }

    public void setCell(AbstractField cell) {
        this.cell = cell;
    }

    public AbstractField getNextCell() {
        return nextCell;
    }

    public void setNextCell(AbstractField nextCell) {
        this.nextCell = nextCell;
    }

    public void setOutOfRoad(boolean outOfRoad) {
        this.outOfRoad = outOfRoad;
    }

    public void updateFront() {
        meshHandler.updateRoadView(this);
    }

    public boolean setFirstPosition(Integer row, Integer col) {
        AbstractField cell = meshHandler.getCellAtPosition(row, col);
        cell.setCar(this);
        this.setCell(cell);

        setRow(row);
        setColumn(col);
        return true;
    }


}
