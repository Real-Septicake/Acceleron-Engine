package gameEngine.components;

import game.models.TexturedMesh;
import game.renderEngine.MasterRenderer;

public class MeshRenderer extends ComponentBase {
	
	public TexturedMesh mesh;
	
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
