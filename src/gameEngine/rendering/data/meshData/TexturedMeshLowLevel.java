package gameEngine.rendering.data.meshData;

public class TexturedMeshLowLevel {
	private MeshLowLevel mesh;
	private ModelTexture texture;
	
	private boolean doubleSided;
	private int textureAtlasRows = 1;
	
	public TexturedMeshLowLevel(MeshLowLevel mesh, ModelTexture texture) {
		this.mesh = mesh;
		this.texture = texture;
		this.doubleSided = false;
	}
	
	public TexturedMeshLowLevel(MeshLowLevel mesh, ModelTexture texture, boolean doubleSided) {
		this.mesh = mesh;
		this.texture = texture;
		this.doubleSided = doubleSided;
	}
	
	public TexturedMeshLowLevel(MeshLowLevel mesh, ModelTexture texture, int textureAtlasRows) {
		this.mesh = mesh;
		this.texture = texture;
		this.doubleSided = false;
		this.textureAtlasRows = textureAtlasRows;
	}
	
	public TexturedMeshLowLevel(MeshLowLevel mesh, ModelTexture texture, boolean doubleSided, int textureAtlasRows) {
		this.mesh = mesh;
		this.texture = texture;
		this.doubleSided = doubleSided;
		this.textureAtlasRows = textureAtlasRows;
	}
	
	public MeshLowLevel getMesh() {
		return mesh;
	}
	
	public void setMesh(MeshLowLevel mesh) {
		this.mesh = mesh;
	}
	
	public ModelTexture getTexture() {
		return texture;
	}
	
	public boolean isDoubleSided() {
		return doubleSided;
	}
	
	public int getTextureAtlasRows() {
		return textureAtlasRows;
	}
}
