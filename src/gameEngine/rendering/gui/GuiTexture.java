package gameEngine.rendering.gui;

import org.joml.Vector2d;
import org.joml.Vector3d;

import gameEngine.components.essentials.RectTransform;

public class GuiTexture {
	
	private int textureId;
	public RectTransform transform;
	public boolean autoScale = true;
	public Vector2d baseScaleResolution = new Vector2d(1920, 1080);
	public double heightWidthRatio = 0.5;
	
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
	
	public Vector2d getScaledSize(double width, double height) {
		double scale = (1-heightWidthRatio) * (width / baseScaleResolution.x) + (heightWidthRatio) * (height / baseScaleResolution.y);
		return transform.size.mul(scale);
	}
	
	public Vector3d getScaledPosition(double width, double height) {
		double scale = (1-heightWidthRatio) * (width / baseScaleResolution.x) + (heightWidthRatio) * (height / baseScaleResolution.y);
		return transform.position.mul(scale);
	}
}
