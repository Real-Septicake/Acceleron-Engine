package gameEngine.components;

import org.joml.*;

import game.models.TexturedMesh;

public class Entity extends GameObject {

	private TexturedMesh model;
		
	public Entity(TexturedMesh model, Vector3d pos, Vector3d rot, Vector3d sca) {
		super(pos, rot, sca);
		this.model = model;
		transform = new Transform(pos, rot, sca);
	}
	
	public TexturedMesh getMesh() {
		return model;
	}
}
