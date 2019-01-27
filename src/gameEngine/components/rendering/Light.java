package gameEngine.components.rendering;

import org.joml.Vector3d;

import gameEngine.components.ComponentBase;
import gameEngine.rendering.MasterRenderer;

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
