package gameEngine.rendering.gui;

import java.util.LinkedList;
import java.util.Queue;

import org.joml.Vector3d;

import gameEngine.common.LowLevelLoader;
import gameEngine.components.essentials.RectTransform;
import gameEngine.rendering.data.meshData.MeshLowLevel;

public class TextBox {
	
	private String textBoxText;
	private boolean dirty;
	
	public RectTransform transform;
	
	private MeshLowLevel lowLevel;
	
	public void setText(String text) {
		this.textBoxText = text;
		dirty = true;
	}
	
	public String getText(String text) {
		return textBoxText;
	}
	
	public int getMesh(FontHandler handler, LowLevelLoader loader) {
		if(!dirty) {
			return meshId;
		}
		else {
			Queue<Vector3d> characters = new LinkedList<Vector3d>();
			for (char character : textBoxText.toCharArray()) {
				Vector3d[] vectors = handler.getCharacterMesh(character);
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
}
