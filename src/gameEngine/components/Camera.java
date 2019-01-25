package gameEngine.components;

public class Camera extends ComponentBase {
	
	public float fov = 60.0f;
	public float nearPlane = 0.01f;
	public float farPlane = 1000f;
	
	@Override
	public void setup() {
		
	}

	@Override
	public void destroy() {
		
	}
}
