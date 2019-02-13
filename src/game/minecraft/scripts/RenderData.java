package game.minecraft.scripts;

import org.joml.Vector3d;

import gameEngine.rendering.data.meshData.MeshLowLevel;

public class RenderData {
	public MeshLowLevel mesh;
	public Vector3d renderLocation;
	
	public RenderData(MeshLowLevel mesh, Vector3d renderLoc) {
		this.mesh = mesh;
		this.renderLocation = renderLoc;
	}
}
