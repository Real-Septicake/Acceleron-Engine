package game.renderEngine;

import org.joml.Vector3d;

import gameEngine.components.Transform;

public class RenderData {
	public Vector3d pos;
	public Vector3d rot;
	public Vector3d scale;
	
	public RenderData(Vector3d position, Vector3d rotation, Vector3d sca) {
		this.pos = position;
		this.rot = rotation;
		this.scale = sca;
	}
	
	public RenderData(Transform transform) {
		this.pos = transform.position;
		this.rot = transform.rotation;
		this.scale = transform.scale;
	}
}
