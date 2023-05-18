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
    private AbstractField nextCell = new ConcreteFieldSemaphore(0, 0, 0);

    public Car(MeshHandler meshHandler) {
        this.meshHandler = meshHandler;
        setSpeed();
    }

    @Override
    public void run() {
        super.run();

        // TODO

        meshHandler.updateFields();
    }

    private boolean checkLastCell() {
        return cell.isLastCell();
    }

    private void moveCar() {

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
