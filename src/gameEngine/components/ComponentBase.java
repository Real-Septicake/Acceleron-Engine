package gameEngine.components;

import gameEngine.components.essentials.GameObject;

public abstract class ComponentBase {
	
	public abstract void setup();
	public GameObject gameObject;
	public abstract void destroy();
}
