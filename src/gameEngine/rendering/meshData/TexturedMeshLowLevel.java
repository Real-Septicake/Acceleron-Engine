package gameEngine.rendering.meshData;

import game.textures.ModelTexture;

public class TexturedMeshLowLevel {
	private MeshLowLevel mesh;
	private ModelTexture texture;
	private boolean doubleSided;
	
	public TexturedMeshLowLevel(MeshLowLevel mesh, ModelTexture texture, boolean doubleSided) {
		this.mesh = mesh;
		this.texture = texture;
		this.doubleSided = doubleSided;
	}
	
	public MeshLowLevel getMesh() {
		return mesh;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
	
	public boolean isDoubleSided() {
		return doubleSided;
	}
}
