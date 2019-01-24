package game.objectTypes;

import org.joml.*;

import game.models.TexturedMesh;

public class Entity {

	private TexturedMesh model;
	private Vector3d position;
	private Vector3d rotation;
	private Vector3d scale;
	
	public Entity(TexturedMesh model, Vector3d pos, Vector3d rot, Vector3d sca) {
		this.model = model;
		this.position = pos;
		this.rotation = rot;
		this.scale = sca;
	}
	
	public void move(Vector3d pos) {
		position.x += pos.x;
		position.y += pos.y;
		position.z += pos.z;
	}
	
	public void rotate(Vector3d rot) {
		rotation.x += rot.x;// * 0.0174533f;
		rotation.y += rot.y;// * 0.0174533f;
		rotation.z += rot.z;// * 0.0174533f;
	}
	
	public void scale(Vector3d sca) {
		scale.x += sca.x;
		scale.y += sca.y;
		scale.z += sca.z;
	}
	
	public void SetPosition(Vector3d pos) {
		position = pos;
	}
	
	public void setRotation(Vector3f rot) {
		rotation.x = rot.x;// * 0.0174533f;
		rotation.y = rot.y;// * 0.0174533f;
		rotation.z = rot.z;// * 0.0174533f;
	}
	
	public void setScale(Vector3d sca) {
		scale = sca;
	}
	
	public TexturedMesh getMesh() {
		return model;
	}
	
	public Vector3d getPosition() {
		return position;
	}
	
	public Vector3d getRotation() {
		return rotation;
	}
	
	public Vector3d getScale() {
		return scale;
	}
}
