package view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

import handler.Handler;
import handler.MeshHandler;
import handler.observer.Observer;
import model.Field;

import java.awt.*;

public class Fields extends JPanel implements Observer {



    class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int col) {
            setIcon((ImageIcon) value);

            return this;
        }
    }

    private Handler c;

    private Field field;
    private JTable meshTable;


    public Fields() {
        super();

        c = MeshHandler.getInstance();
        c.attach(this);

        field = new Field(c);

        initComponent();

    }

    private void initComponent() {

        meshTable = new JTable();
        meshTable.setBackground(Color.black);
        meshTable.setModel(this.field);
        for (int x = 0; x < meshTable.getColumnModel().getColumnCount(); x++) {
            meshTable.getColumnModel().getColumn(x).setWidth(35);
            meshTable.getColumnModel().getColumn(x).setMaxWidth(45);
        }
        meshTable.setRowHeight(32);
        meshTable.setShowGrid(false);

        meshTable.setDefaultRenderer(Object.class, new CellRenderer());

        add(meshTable);
    }

    @Override
    public void updateCarField() {
        updateUI();
    }

    @Override
    public void updateStartButton(boolean status) {}

    @Override
    public void updateEndButton(boolean status) {}

    @Override
    public void updateCounter(int value) {}
}
