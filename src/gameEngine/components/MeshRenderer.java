package gameEngine.components;

import gameEngine.rendering.MasterRenderer;
import gameEngine.rendering.meshData.TexturedMeshLowLevel;

public class MeshRenderer extends ComponentBase {
	
	public TexturedMeshLowLevel mesh;
	
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
