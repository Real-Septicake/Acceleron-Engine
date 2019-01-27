package gameEngine.components.rendering;

import gameEngine.components.ComponentBase;
import gameEngine.rendering.MasterRenderer;

public class Camera extends ComponentBase {
	
	public float fov = 60.0f;
	public float nearPlane = 0.01f;
	public float farPlane = 1000f;
	
	@Override
	public void setup() {
		MasterRenderer.setCamera(this);
	}

	@Override
	public void destroy() {
		MasterRenderer.removeCamera();
	}
}
