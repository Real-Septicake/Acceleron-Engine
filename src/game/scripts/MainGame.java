package game.scripts;

import gameEngine.common.*;

public class MainGame extends Game {

	public static EngineCore gameEngine;
	
	public static void main(String[] args) {
		new MainGame().initializeGame();
	}
	
	@Override
	public void initializeGame() {
		gameEngine = new EngineCore();
		gameEngine.initializeEngine(this);
	}
	
	@Override
	public void startGame() {
		new GameManager();
	}
}
