package model.abstractfactory;

import model.enums.FieldTypes;
import model.Car;

import javax.swing.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class ConcreteFieldSemaphore extends AbstractField {

    Semaphore mutex = new Semaphore(1);

    public ConcreteFieldSemaphore(int moveType, int row, int column) {
        this.stopCell = false;
        this.lastCell = false;
        this.row = row;
        this.column = column;
        this.moveType = moveType;
        this.icon = new ImageIcon(FieldTypes.getRoadType(moveType));
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCarType() {
        return moveType;
    }

    public Car getCar() {
        return car;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isStopField() {
        return stopCell;
    }

    public void setStopField(boolean stopCell) {
        this.stopCell = stopCell;
    }

    public boolean isLastField() {
        return lastCell;
    }

    public void setLastField(boolean lastCell) {
        this.lastCell = lastCell;
    }

    public int getMoveType() { return moveType; }

    public boolean setCarToGo(Car c) {
        try {
            if (mutex.tryAcquire(c.getSpeed(), TimeUnit.MILLISECONDS)) {
                this.car = c;
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void setCar(Car c) {
        try {
            mutex.acquire();
            this.car = c;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        this.setIcon(new ImageIcon(FieldTypes.getRoadType(moveType)));
        this.car = null;
        mutex.release();
    }
}
