package gameEngine.components.rendering;

import gameEngine.components.ComponentBase;
import gameEngine.rendering.MasterRenderer;
import gameEngine.rendering.data.meshData.CompleteMesh;

public class MeshRenderer extends ComponentBase {
	
	public CompleteMesh mesh;

	@Override
	public void destroy() {
		MasterRenderer.removeRenderer(this);
	}

	@Override
	public void setup() {
		MasterRenderer.addRenderer(this);
	}
}
