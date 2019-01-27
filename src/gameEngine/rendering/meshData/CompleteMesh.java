package gameEngine.rendering.meshData;

public class CompleteMesh {

	private int atlasLocation = 0;
	private TexturedMeshLowLevel meshData;
	
	public CompleteMesh(TexturedMeshLowLevel texturedMesh) {
		meshData = texturedMesh;
	}
	
	public CompleteMesh(TexturedMeshLowLevel texturedMesh, int atlasLocation) {
		meshData = texturedMesh;
		this.atlasLocation = atlasLocation;
	}
	
	public TexturedMeshLowLevel getTexturedMesh() {
		return meshData;
	}
	
	public int getAtlasLocation() {
		return atlasLocation;
	}
}
