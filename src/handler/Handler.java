package handler;

import handler.observer.Observer;
import model.Mesh;

import javax.swing.*;

public interface Handler {

	public void attach(Observer obs);
	public void start() ;

	public void stop();

	public void updateFields();

	public Mesh getMesh();

	public Icon renderField(int row, int col);

	public void changeSimulationType(String var1);
}
