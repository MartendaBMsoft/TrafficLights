package view;

import javax.swing.*;
import javax.swing.border.Border;

import handler.Handler;
import handler.MeshHandler;
import handler.observer.Observer;
import handler.ThreadHandler;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class SettingsFrame extends JFrame implements Observer {

    private Handler handler;
    private ThreadHandler threadHandler = new ThreadHandler();
    private Fields fields;
    Container buttonsContainer;
    JLabel lbCount;
    Container options;
    JSpinner numeroVeiculos;
    JSpinner timer;
    JButton buttonStart;
    JButton buttonStop;
    JButton buttonEnd;
    String[] simulationType = {"Semaforo", "Monitor"};

    public SettingsFrame() throws IOException {
        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
        Border insetBorder = BorderFactory.createEmptyBorder(0, 10, 0, 10);

        handler = MeshHandler.getInstance();
        handler.attach(this);

        this.setSize(1200, 960);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        fields = new Fields();

        options = new Container();
        buttonsContainer = new Container();

        createButtons();

        JComboBox simulationTypeComboBox = new JComboBox(simulationType);
        handler.changeSimulationType((String) simulationTypeComboBox.getSelectedItem());
        simulationTypeComboBox.addActionListener((ActionEvent e) -> {
            handler.changeSimulationType((String) simulationTypeComboBox.getSelectedItem());
        });

        JLabel lbVeiculos = new JLabel("Numero de veículos: ");
        numeroVeiculos = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        numeroVeiculos.setFont(Font.getFont(Font.SANS_SERIF));

        JLabel timerLabel = new JLabel("Tempo: ");
        timer = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));


        timer.setBorder(BorderFactory.createCompoundBorder(lineBorder,
                insetBorder));

        JLabel lbNumCars = new JLabel("Veículos: ");
        lbCount = new JLabel("0");

        options.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));


        options.add(simulationTypeComboBox);
        options.add(lbVeiculos);
        options.add(numeroVeiculos);
        options.add(timerLabel);
        options.add(timer);
        options.add(lbNumCars);
        options.add(lbCount);

        buttonsContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

        buttonsContainer.add(buttonStart);
        buttonsContainer.add(buttonStop);
        buttonsContainer.add(buttonEnd);

        this.add(fields, BorderLayout.NORTH);
        this.add(options, BorderLayout.CENTER);
        this.add(buttonsContainer, BorderLayout.SOUTH);



        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

//        this.pack();
    }

    private void createButtons() {
        buttonStart = new JButton("Start");
        buttonStart.addActionListener((ActionEvent e) -> {
            threadHandler.setCarsQuantity(Integer.parseInt(numeroVeiculos.getValue() + ""));
            threadHandler.setTimer(Integer.parseInt(timer.getValue() + ""));
            threadHandler.start();
        });
        buttonStart.setEnabled(false);

        buttonStop = new JButton("Stop Creating");
        buttonStop.addActionListener((ActionEvent e) -> {
            handler.stop();
        });
        buttonStop.setEnabled(false);

        buttonEnd = new JButton("Stop Everyone");
        buttonEnd.addActionListener((ActionEvent e) -> {
            handler.stop();
            threadHandler.getMeshHandler().setStopEveryoneTrue();
//            System.exit(1);
        });
        buttonEnd.setEnabled(false);
    }

    @Override
    public void updateCarField() {}

    @Override
    public void updateStartButton(boolean status){
        this.buttonStart.setEnabled(status);
    }

    @Override
    public void updateStopButton(boolean status){
        this.buttonStop.setEnabled(status);
    }

    @Override
    public void updateEndButton(boolean status) {
        this.buttonEnd.setEnabled(status);
    }

    @Override
    public void updateCounter(int value) {
        this.lbCount.setText(value+"");
    }
}
