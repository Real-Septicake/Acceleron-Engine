package gameEngine.components.rendering;

import gameEngine.components.ComponentBase;
import gameEngine.rendering.MasterRenderer;

public class Camera extends ComponentBase {
	
	public double fov = 60.0f;
	public double orthographicSize = 5;
	public double nearPlane = 0.01f;
	public double farPlane = 10000f;
	public boolean orthographic = false;
	
	@Override
	public void setup() {
		MasterRenderer.setCamera(this);
	}

	@Override
	public void destroy() {
		MasterRenderer.removeCamera();
	}
}
