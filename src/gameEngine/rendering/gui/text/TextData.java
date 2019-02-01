package gameEngine.rendering.gui.text;

import gameEngine.components.essentials.RectTransform;

public class TextData {
	
	public int meshId;
	
	public int vertexCount;
	
	public RectTransform transform;
	
	public TextData(int meshId, RectTransform transform, int vertexCount) {
		this.meshId = meshId;
		this.transform = transform;
		this.vertexCount = vertexCount;
	}
}
