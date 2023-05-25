package model;

import handler.MeshHandler;
import model.abstractfactory.AbstractField;

import java.util.ArrayList;
import java.util.List;

public class Car extends Thread {

    private int row;
    private int column;
    private long speed;
    private boolean outOfRoad = false;
    private final MeshHandler meshHandler;
    private AbstractField field;
    private AbstractField nextField;
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
        return field.isLastField();
    }

    private void moveCar() {
        if (this.getField().isLastField()) {
            stopRunning = true;
            meshHandler.updateCarCount(this);
            return;
       }

        if (nextField.isStopField()) {
            handleCrossing();
            stopRunning = true;
            return ;
        }


        AbstractField c = getNextField(this.field);
        c.setCar(this);
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        if (!c.isLastField())
            this.nextField = getNextField(c);

        field.reset();
        field = c;

//        this.setField(meshHandler.getCellAtPosition(this.getRow(), this.getColumn()));
//

//        int newRow = this.getRow();
//        int newCol = this.getColumn();
//
//        switch (this.getField().getMoveType()) {
//            case 1: //Up
//                newRow --;
//                break;
//            case 2: //Right
//                newCol ++;
//                break;
//            case 3: //Down
//                newRow ++;
//                break;
//            case 4: //Left
//                newCol --;
//                break;
//        }
//
//        this.setNextField(meshHandler.getCellAtPosition(newRow, newCol));
//
//        if (this.getNextField().isStopCell()) {
////            this.moveCarToIntersectionExit(null);
//            stopRunning = true;
//            return ;
//        }
//
//        if (this.getNextField().getMoveType() != 0 && this.getNextField().getCar() == null) {
//            this.setRow(this.getNextField().getRow());
//            this.setColumn(this.getNextField().getColumn());
//            this.setField(meshHandler.getCellAtPosition(this.getRow(), this.getColumn()));
//        }
//        this.field.reset();

        updateFront();
    }

    private void handleCrossing() {
        List<AbstractField> intersectionExits = new ArrayList<>();
        List<List<AbstractField>> pathToAllExits = new ArrayList<>();
        List<AbstractField> currentPath = new ArrayList<>();

        AbstractField field = nextField;

        for (int i = 0; i < 4; i++) {
            int moveType = field.getMoveType();
            currentPath.add(field);

            switch (moveType) {
                case 9:
                    intersectionExits.add(meshHandler.getCellAtPosition(field.getRow(), field.getColumn() + 1));
                    pathToAllExits.add(new ArrayList<>(currentPath));
                    break;
                case 10:
                    intersectionExits.add(meshHandler.getCellAtPosition(field.getRow() - 1, field.getColumn()));
                    pathToAllExits.add(new ArrayList<>(currentPath));
                    break;
                case 11:
                    intersectionExits.add(meshHandler.getCellAtPosition(field.getRow() + 1, field.getColumn()));
                    pathToAllExits.add(new ArrayList<>(currentPath));
                    break;
                case 12:
                    intersectionExits.add(meshHandler.getCellAtPosition(field.getRow(), field.getColumn() - 1));
                    pathToAllExits.add(new ArrayList<>(currentPath));
                    break;
            }
            field = getNextField(field);
        }

        checkNextFieldAndMove(pathToAllExits, intersectionExits);
    }

    private void checkNextFieldAndMove(List<List<AbstractField>> pathToAllExits, List<AbstractField> intersectionExits) {

    }

    private AbstractField getNextField(AbstractField cell) {
        int moveType;

        moveType = getMoveTypeFromFieldNumber(cell);

        switch (moveType) {
            case 1:
                this.nextField = meshHandler.getCellAtPosition(cell.getRow() - 1, cell.getColumn());
                break;
            case 2:
                this.nextField = meshHandler.getCellAtPosition(cell.getRow(), cell.getColumn() + 1);
                break;
            case 3:
                this.nextField = meshHandler.getCellAtPosition(cell.getRow() + 1, cell.getColumn());
                break;
            case 4:
                this.nextField = meshHandler.getCellAtPosition(cell.getRow(), cell.getColumn() - 1);
                break;
            default:
                break;
        }

        return nextField;
    }

    private int getMoveTypeFromFieldNumber(AbstractField cell) {
        int moveType;
        if (cell.getMoveType() > 4 && cell.getMoveType() <= 8) {
            moveType = cell.getMoveType() - 4;
        } else if (cell.getMoveType() > 8) {
            moveType = switch (cell.getMoveType()) {
                case 9 -> 1;
                case 10 -> 4;
                case 11 -> 2;
                case 12 -> 3;
                default -> 0;
            };
        } else {
            moveType = cell.getMoveType();
        }
        return moveType;
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

    public AbstractField getField() {
        return field;
    }

    public void setField(AbstractField field) {
        this.field = field;
    }

    public AbstractField getNextField() {
        return nextField;
    }

    public void setNextField(AbstractField nextField) {
        this.nextField = nextField;
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
        this.setField(cell);

        setRow(row);
        setColumn(col);
        return true;
    }


}
