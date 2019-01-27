package gameEngine.components.essentials;

import org.joml.*;

public class RectTransform {
	
	public Vector3d position;
	public Vector2d size;
	public Vector3d rotation;
	
	public RectTransform() {
		this.position = new Vector3d(0.5, 0.5, 0);
		this.size = new Vector2d(.25, .25);
		this.rotation = new Vector3d(0);
	}
	
	public RectTransform(Vector3d position, Vector2d size, Vector3d rotation) {
		this.position = position;
		this.size = size;
		this.rotation = rotation;
	}
}
