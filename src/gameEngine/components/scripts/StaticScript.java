package gameEngine.components.scripts;

import gameEngine.common.UpdateHandler;

public abstract class StaticScript {
	
	public StaticScript() {
		setup();
	}
	
	public abstract void update();
	public abstract void start();
	public abstract void lateUpdate();
	public abstract void onDestroy();
	
	private void setup() {
		UpdateHandler.RegisterScript(this);
	}
	
	public void stop() {
		UpdateHandler.UnregisterScript(this);
		onDestroy();
	}
}
