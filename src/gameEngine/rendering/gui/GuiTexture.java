package gameEngine.rendering.gui;

import org.joml.Vector2d;
import org.joml.Vector3d;

import gameEngine.components.essentials.RectTransform;

public class GuiTexture {
	
	private int textureId;
	public RectTransform transform;
	
	public GuiTexture(int textureID) {
		this.textureId = textureID;
		this.transform = new RectTransform();
	}
	
	public GuiTexture(int textureID, RectTransform transform) {
		this.textureId = textureID;
		this.transform = transform;
	}
	
	public GuiTexture(int textureID, Vector3d position, Vector2d size, Vector3d rotation) {
		this.textureId = textureID;
		this.transform = new RectTransform(position, size, rotation);
	}
	
	public int getTexture() {
		return textureId;
	}
}
