package model.abstractfactory;

import model.Car;

import javax.swing.*;

public abstract class AbstractField {

    protected int row;
    protected int column;
    protected boolean stopCell;
    protected boolean lastCell;
    protected int moveType;
    protected Icon icon;
    protected Car car;

    public abstract int getCarType();

    public abstract Car getCar();

    public abstract boolean setCarToGo(Car c);

    public abstract void setCar(Car c);

    public abstract void reset();

    public abstract int getRow();

    public abstract void setRow(int row);

    public abstract int getColumn();

    public abstract void setColumn(int column);

    public abstract Icon getIcon();

    public abstract boolean isLastField();

    public abstract void setLastField(boolean lastCell);

    public abstract int getMoveType();

    public abstract void setIcon(ImageIcon icon);

    public abstract boolean isStopField();

    public abstract void setStopField(boolean stopCell);



}
