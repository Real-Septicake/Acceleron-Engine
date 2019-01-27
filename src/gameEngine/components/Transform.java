package gameEngine.components;

import org.joml.Vector3d;

public class Transform {
	
	public Vector3d position;
	public Vector3d rotation;
	public Vector3d scale;
	
	public Transform() {
		position = new Vector3d(0,0,0);
		rotation = new Vector3d(0,0,0);
		scale = new Vector3d(1,1,1);
	}
	
	public Transform(Vector3d pos, Vector3d rot, Vector3d scal) {
		this.position = pos;
		this.rotation = rot;
		this.scale = scal;
	}
	

	public void move(Vector3d pos) {
		position.x += pos.x;
		position.y += pos.y;
		position.z += pos.z;
	}
	
	public void rotate(Vector3d rot) {
		rotation.x += rot.x;
		rotation.y += rot.y;
		rotation.z += rot.z;
	}
	
	public void scale(Vector3d sca) {
		scale.x *= sca.x;
		scale.y *= sca.y;
		scale.z *= sca.z;
	}
}
