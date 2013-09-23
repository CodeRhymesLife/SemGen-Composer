package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ObjectActionListener<T> implements ActionListener {
	// Object to hold
	private T _object;
	
	public ObjectActionListener(T object){
		_object = object;
	}
	
	/*
	 * Get object
	 */
	public T getObject(){
		return _object;
	}
}
