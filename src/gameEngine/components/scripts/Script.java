package gameEngine.components.scripts;

import gameEngine.common.UpdateHandler;
import gameEngine.components.ComponentBase;

public abstract class Script extends ComponentBase {
	
	public abstract void update();
	public abstract void start();
	public abstract void lateUpdate();
	
	@Override
	public void setup() {
		UpdateHandler.RegisterScript(this);
	}
	
	@Override
	public void destroy() {
		UpdateHandler.UnregisterScript(this);
	}
}
