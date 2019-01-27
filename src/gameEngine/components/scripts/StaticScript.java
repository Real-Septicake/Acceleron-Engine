package gameEngine.components.scripts;

import gameEngine.common.UpdateHandler;

public abstract class StaticScript {
	
	public StaticScript() {
		setup();
	}
	
	public abstract void update();
	public abstract void start();
	
	private void setup() {
		UpdateHandler.RegisterScript(this);
	}
	
	public void stop() {
		UpdateHandler.UnregisterScript(this);
	}
}
