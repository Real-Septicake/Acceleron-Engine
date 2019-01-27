package game.scenes;

import gameEngine.components.scripts.StaticScript;
import gameEngine.debug.Debug;

public class RacingScene extends StaticScript {

	@Override
	public void update() {
		Debug.logWarning("Racing area is empty! TODO: Fix this!");
	}

	@Override
	public void start() {
		Debug.log("Racing area started!");
	}

}
