package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;

public class MainController  implements EventHandler<Event> {
	
	private Scene scene = null;
	
	public MainController(Scene scene) {
		this.scene = scene;
	}

	@Override
	public void handle(Event event) {
		
	}
	
	
}
