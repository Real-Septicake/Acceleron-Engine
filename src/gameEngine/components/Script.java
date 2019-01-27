package gameEngine.components;

import gameEngine.common.UpdateHandler;

public abstract class Script extends ComponentBase {
	
	public abstract void update();
	public abstract void start();
	
	@Override
	public void setup() {
		UpdateHandler.RegisterScript(this);
	}
	
	@Override
	public void destroy() {
		UpdateHandler.UnregisterScript(this);
	}
}
