package game.objectTypes;

import org.joml.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;
	
	public Light(Vector3f pos, Vector3f col) {
		position = pos;
		color = col;
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getColor() {
		return color;
	}
}
