package game.models;

import game.textures.ModelTexture;

public class TexturedMesh {
	private Mesh mesh;
	private ModelTexture texture;
	
	public TexturedMesh(Mesh mesh, ModelTexture texture) {
		this.mesh = mesh;
		this.texture = texture;
	}
	
	public Mesh getMesh() {
		return mesh;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
}
