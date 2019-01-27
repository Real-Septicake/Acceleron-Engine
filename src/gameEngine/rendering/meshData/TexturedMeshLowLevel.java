package gameEngine.rendering.meshData;

import game.textures.ModelTexture;

public class TexturedMeshLowLevel {
	private MeshLowLevel mesh;
	private ModelTexture texture;
	
	public TexturedMeshLowLevel(MeshLowLevel mesh, ModelTexture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}
	
	public MeshLowLevel getMesh() {
		return mesh;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
}
