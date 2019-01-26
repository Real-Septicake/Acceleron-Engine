package gameEngine.components;

import org.joml.Vector3d;

import game.renderEngine.MasterRenderer;

public class Light extends ComponentBase {
	public Vector3d color;

	@Override
	public void setup() {
		MasterRenderer.addLight(this);
	}

	@Override
	public void destroy() {
		MasterRenderer.removeLight(this);
	}
}
