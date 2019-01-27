package gameEngine.components;

import gameEngine.rendering.MasterRenderer;
import gameEngine.rendering.meshData.CompleteMesh;

public class MeshRenderer extends ComponentBase {
	
	public CompleteMesh mesh;
	
	public MeshRenderer() {
		
	}

	@Override
	public void destroy() {
		MasterRenderer.removeRenderer(this);
	}

	@Override
	public void setup() {
		MasterRenderer.addRenderer(this);
	}
}
