package gameEngine.rendering;

import org.joml.Vector3d;

import gameEngine.components.Transform;

public class RenderObjectInfo {
	public Vector3d pos;
	public Vector3d rot;
	public Vector3d scale;
	
	public RenderObjectInfo(Vector3d position, Vector3d rotation, Vector3d sca) {
		this.pos = position;
		this.rot = rotation;
		this.scale = sca;
	}
	
	public RenderObjectInfo(Transform transform) {
		this.pos = transform.position;
		this.rot = transform.rotation;
		this.scale = transform.scale;
	}
}
