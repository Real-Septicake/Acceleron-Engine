package gameEngine.rendering.data;

import org.joml.Vector3d;

import gameEngine.components.essentials.Transform;

public class RenderObjectInfo {
	
	public Vector3d pos;
	public Vector3d rot;
	public Vector3d scale;
	public int textureAtlasLocation;
	
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
	
	public RenderObjectInfo(Vector3d position, Vector3d rotation, Vector3d sca, int textureAtlasLocation) {
		this.pos = position;
		this.rot = rotation;
		this.scale = sca;
		this.textureAtlasLocation = textureAtlasLocation;
	}
	
	public RenderObjectInfo(Transform transform, int textureAtlasLocation) {
		this.pos = transform.position;
		this.rot = transform.rotation;
		this.scale = transform.scale;
		this.textureAtlasLocation = textureAtlasLocation;
	}
}
