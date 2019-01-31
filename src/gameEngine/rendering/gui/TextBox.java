package gameEngine.rendering.gui;

import java.util.LinkedList;
import java.util.Queue;

import org.joml.Vector2d;
import org.joml.Vector3d;

import gameEngine.common.LowLevelLoader;
import gameEngine.components.essentials.RectTransform;
import gameEngine.rendering.data.meshData.MeshLowLevel;

public class TextBox {
	
	private String textBoxText;
	private boolean dirty;
	
	public RectTransform transform;
	
	private MeshLowLevel lowLevel;
	
	public boolean autoScale = true;
	public Vector2d baseScaleResolution = new Vector2d(1920, 1080);
	public double heightWidthRatio = 0.5;
	
	public TextBox() {
		this.transform = new RectTransform();
	}
	
	public TextBox(RectTransform transform) {
		this.transform = transform;
	}
	
	public TextBox(Vector3d position, Vector2d size, Vector3d rotation) {
		this.transform = new RectTransform(position, size, rotation);
	}
	
	public void setText(String text) {
		this.textBoxText = text;
		dirty = true;
	}
	
	public String getText(String text) {
		return textBoxText;
	}
	
	public int getMesh(FontHandler handler, LowLevelLoader loader) {
		if(!dirty) {
			return lowLevel.getVaoID();
		}
		else {
			Queue<Vector3d> characters = new LinkedList<Vector3d>();
			for (char character : textBoxText.toCharArray()) {
				Vector3d[] vectors = handler.getCharacterMesh("" + character);
				for (Vector3d vector3d : vectors) {
					characters.add(vector3d);
				}
			}
			
			double[] positions = new double[characters.size() * 3];
			int index = 0;
			for (Vector3d pos : characters) {
				
				positions[index] = pos.x;
				positions[index + 1] = pos.y;
				positions[index + 2] = pos.z;
				index += 3;
				
			}
			
			if(lowLevel == null) {
				lowLevel = loader.loadToVAO(positions);
			}
			else {
				loader.reloadToVAO(positions, lowLevel.getVaoID());
			}
			return lowLevel.getVaoID();
		}
	}
	
	public int vertexCount() {
		return lowLevel.getVertexCount();
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
