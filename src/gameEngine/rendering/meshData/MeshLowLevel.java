package gameEngine.rendering.meshData;

public class MeshLowLevel {
	private int vaoID;
	private int vertexCount;
	
	public MeshLowLevel(int vaoID, int vertexCount) {
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID() {
		return vaoID;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
}
