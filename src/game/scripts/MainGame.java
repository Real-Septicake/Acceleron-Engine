package game.scripts;

import gameEngine.common.*;

public class MainGame extends Game {

	private static EngineCore gameEngine;
	private static boolean reloadOnClose = false;
	
	public static void main(String[] args) {
		new MainGame().initializeGame();
	}
	
	@Override
	public void initializeGame() {
		gameEngine = new EngineCore();
		gameEngine.initializeEngine(this);
		
		if(reloadOnClose) {
			reloadOnClose = false;
			main(new String[0]);
		}
	}
	
	@Override
	public void startGame() {
		new GameManager();
	}
	
	public static void reloadGame() {
		reloadOnClose = true;
	}
}
