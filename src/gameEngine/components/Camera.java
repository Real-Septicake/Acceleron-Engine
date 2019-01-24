package gameEngine.components;

import org.joml.*;

public class Camera {
	private Vector3d position = new Vector3d(0, 0, 0);
	private Vector3d rotation = new Vector3d(0, 0, 0);
	
	public Camera() {}
	
	public void move(Vector3d pos) {
		position.add(pos);
	}
	
	public void rotate(Vector3d rot) {
		rotation.add(rot);
	}
	
	public void setPosition(Vector3d pos) {
		position = pos;
	}
	
	public void setRotation(Vector3d rot) {
		rotation = rot;
	}
	
	public Vector3d getPosition() {
		return position;
	}
	
	public Vector3d getRotation() {
		return rotation;
	}
	
}
