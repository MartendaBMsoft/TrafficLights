package handler;

import handler.observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import model.abstractfactory.AbstractField;
import model.Car;
import model.enums.CarType;
import model.Mesh;
import model.enums.FieldTypes;

public class MeshHandler implements Handler {

    private static MeshHandler instance;
    private final Mesh mesh = Mesh.getInstance();
    private List<Car> cars = new ArrayList();
    private AbstractField[][] fields;
    private List<Observer> observers = new ArrayList();
    private String simulationType;
    private final String filename = "malhas/malha-exemplo-3.txt";
    private boolean stopped = false;
    private boolean stopEveryone = false;

    public void setStopEveryoneTrue() { this.stopEveryone = true; };

    public boolean isStopEveryone() { return this.stopEveryone; };

    private MeshHandler() {
        try {
            this.mesh.print(filename);
            this.mesh.readEntriesAndExits();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        this.initRoadCells();
    }

    public static MeshHandler getInstance() {
        if (instance == null) {
            instance = new MeshHandler();
        }

        return instance;
    }

    public void attach(Observer obs) {
        this.observers.add(obs);
    }

    public void detach(Observer obs) {
        this.observers.remove(obs);
    }

    public void changeSimulationType(String opt) {
        this.simulationType = opt;
        try {
            mesh.changeMethodType(filename, simulationType);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        notifyStartButton(true);
        notifyStopButton(false);
    }

    public String getSimulationType(){
        return simulationType;
    }

    public void addCars(Car car) {
        this.cars.add(car);
    }

    public void start() {
        notifyStartButton(false);
        notifyStopButton(true);
        notifyExitButton(true);
        Car newCar = new Car(this);

        Integer[] pos = getFirstCell();
        newCar.setFirstPosition(pos[0], pos[1]);

        this.cars.add(newCar);
        notifyCounter();
        this.updateRoadView(newCar);
        newCar.start();
    }

    @Override
    public void stop() {
        this.stopped = true;
//        notifyStartButton(true);
        notifyStopButton(false);
        notifyExitButton(false);
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    private void initRoadCells() {
        this.fields = mesh.getMesh();
        List<Integer> stopCells = FieldTypes.getStopCells();

        int row = this.mesh.getRows();
        int col = this.mesh.getCols();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (setLastCell(new Integer[]{i, j})) {
                    this.fields[i][j].setLastField(true);
                }

                if (stopCells.contains(fields[i][j].getCarType())) {
                    fields[i][j].setStopField(true);
                }
            }
        }
    }

    private boolean setLastCell(Integer[] array) {
        for (Integer[] aValue :
                this.mesh.getExits()) {
            if (Arrays.equals(aValue, array)) {
                return true;
            }
        }
        return false;
    }

    public void setStopped(boolean status){
        this.stopped = status;
    }

    public boolean isStopped(){
        return stopped;
    }

    public void updateCarCount(Car c){
        this.cars.remove(c);
        notifyCounter();
    }

    public Icon renderField(int row, int col) {
        return this.fields[row][col].getIcon();
    }

    public Integer[] getFirstCell() {
        Collections.shuffle(this.mesh.getEntries());
        return this.mesh.getEntries().get(0);
    }

    public int getCars(){
        return this.cars.size();
    }

    public void updateRoadView(Car c) {
        int i = c.getRow();
        int j = c.getColumn();

        int moveType = this.mesh.getValueAtPosition(i, j);
        if(moveType >= 5){
            this.fields[i][j].setIcon(new ImageIcon(CarType.convertMoveType(moveType)));
        }else {
            this.fields[i][j].setIcon(new ImageIcon(CarType.getMoveType(moveType)));
        }

        updateFields();
    }

    public void updateFields() {
        for (Observer observer : observers) {
            observer.updateCarField();
        }
    }

    public void notifyStartButton(boolean status) {
        for (Observer observer : observers) {
            observer.updateStartButton(status);
        }
    }

    public void notifyStopButton(boolean status) {
        for (Observer observer : observers) {
            observer.updateStopButton(status);
        }
    }

    public void notifyExitButton(boolean status) {
        for (Observer observer : observers) {
            observer.updateEndButton(status);
        }
    }

    public void notifyCounter(){
        for (Observer observer : observers) {
            observer.updateCounter(this.getCars());
        }
    }

    public AbstractField getCellAtPosition(int row, int col) {
        return fields[row][col];
    }
}

 
