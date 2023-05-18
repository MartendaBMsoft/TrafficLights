package model;

import model.abstractfactory.AbstractField;
import model.abstractfactory.ConcreteFieldMonitor;
import model.abstractfactory.ConcreteFieldSemaphore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mesh {

    private static Mesh instance;

    private final String[] size = new String[2];
    private AbstractField[][] mesh;

    private static List<Integer[]> entries = new ArrayList<>();
    private static List<Integer[]> exits = new ArrayList<>();


    private Mesh() {
    }

    public static Mesh getInstance() {
        if (instance == null) {
            instance = new Mesh();
        }
        return instance;
    }

    public int getCols() {
        return Integer.parseInt(size[1]);
    }

    public int getRows() {
        return Integer.parseInt(size[0]);
    }

    public int getValueAtPosition(int row, int col) {
        return mesh[row][col].getCarType();
    }

    public void print(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        try {
            String line = br.readLine();
            size[0] = line.split("\t")[0];
            line = br.readLine();
            size[1] = line.split("\t")[0];

            createSemaphoreType(br);

        } finally {
            br.close();
        }
    }

    public void changeMethodType(String file, String methodType) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = bufferedReader.readLine();
        size[0] = line.split("\t")[0];
        line = bufferedReader.readLine();
        size[1] = line.split("\t")[0];

        if (methodType.equals("Semaforo")) {
            createSemaphoreType(bufferedReader);
        } else {
            createMonitorType(bufferedReader);
        }
    }

    private void createMonitorType(BufferedReader br) throws IOException {
        String line;
        mesh = new ConcreteFieldMonitor[getRows()][getCols()];

        for (int i = 0; i < getRows(); i++) {
            line = br.readLine();
            String[] colunas = line.split("\t");

            for (int j = 0; j < getCols(); j++) {
                mesh[i][j] = new ConcreteFieldMonitor(Integer.parseInt(colunas[j]), i, j);
            }
        }
    }

    private void createSemaphoreType(BufferedReader br) throws IOException {
        String line;
        mesh = new ConcreteFieldSemaphore[getRows()][getCols()];

        for (int i = 0; i < getRows(); i++) {
            line = br.readLine();
            String[] colunas = line.split("\t");

            for (int j = 0; j < getCols(); j++) {
                mesh[i][j] = new ConcreteFieldSemaphore(Integer.parseInt(colunas[j]), i, j);
            }
        }
    }


    public void readEntriesAndExits() {
        findRowsEntriesAndExits();
        findColumnsEntriesAndExits();
    }

    public void findRowsEntriesAndExits() {
        for (int i = 0; i < this.getRows() - 1; ++i) {

            if (getValueAtPosition(i, 0) == 2) {
                entries.add(new Integer[]{i, 0});
            } else if (getValueAtPosition(i, 0) == 4) {
                exits.add(new Integer[]{i, 0});
            }

            if (getValueAtPosition(i, this.getCols() - 1) == 4) {
                entries.add(new Integer[]{i, this.getCols() - 1});
            } else if (getValueAtPosition(i, this.getCols() - 1) == 2) {
                exits.add(new Integer[]{i, this.getCols() - 1});
            }
        }

    }

    public void findColumnsEntriesAndExits() {
        for (int i = 0; i < this.getCols(); ++i) {

            if (getValueAtPosition(0, i) == 3) {
                entries.add(new Integer[]{0, i});
            } else if (getValueAtPosition(0, i) == 1) {
                exits.add(new Integer[]{0, i});
            }

            if (getValueAtPosition(this.getRows() - 1, i) == 1) {
                entries.add(new Integer[]{this.getRows() - 1, i});
            } else if (getValueAtPosition(this.getRows() - 1, i) == 3) {
                exits.add(new Integer[]{this.getRows() - 1, i});
            }
        }
    }


    public AbstractField[][] getMesh() {
        return mesh;
    }

    public List<Integer[]> getEntries() {
        return entries;
    }

    public List<Integer[]> getExits() {
        return exits;
    }
}
