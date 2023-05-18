package model;

import handler.Handler;

import javax.swing.table.AbstractTableModel;

public class Field extends AbstractTableModel {

    private Handler c;

    public Field(Handler c) {
        this.c = c;
    }

    @Override
    public int getRowCount() {
        return c.getMesh().getRows();
    }

    @Override
    public int getColumnCount() {
        return c.getMesh().getCols();
    }

    @Override
    public Object getValueAt(int row, int col) {
        return c.renderField(row, col);
    }

}
