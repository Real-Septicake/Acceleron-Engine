package game.scripts;

import static org.lwjgl.glfw.GLFW.*;

import game.scenes.*;
import game.textures.*;
import gameEngine.common.*;
import gameEngine.components.essentials.*;
import gameEngine.components.scripts.*;
import gameEngine.debug.*;
import gameEngine.rendering.data.meshData.*;

public class GameManager extends StaticScript {

	private StaticScript currentState;
	
	public static CompleteMesh sphereTextured;
	public static CompleteMesh floorTextured;
	public static CompleteMesh texturedTree;
	public static CompleteMesh carTextured;
	
	private int currentScene = 0;
	
	@Override
	public void update() {
		if(currentScene != 1 && InputManager.keyDown(GLFW_KEY_1)) {
			GameObject.clearScene();
			currentState.stop();
			currentState = new RandomScene();
			currentScene = 1;
		}
		else if (currentScene != 2 && InputManager.keyDown(GLFW_KEY_2)) {
			GameObject.clearScene();
			currentState.stop();
			currentState = new RacingScene();
			currentScene = 2;
		}
		else if (currentScene != 0 && InputManager.keyDown(GLFW_KEY_3)) {
			GameObject.clearScene();
			currentState.stop();
			currentState = new MainMenuScene();
			currentScene = 0;
		}
	}

	@Override
	public void start() {
		
		Debug.log("Loading assets...");
		
		LowLevelLoader loader = MainGame.gameEngine.getLoader();
		
		//Sphere loading
		sphereTextured = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Sphere", loader), 
						new ModelTexture(loader.loadTexture("sphere_UV")))
				);
		
		//Tree Loading
		texturedTree = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Tree", loader), 
						new ModelTexture(loader.loadTexture("Tree")))
				);
		
		//Floor loading
		floorTextured = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Floor", loader), 
						new ModelTexture(loader.loadTexture("floor UV")))
				);
		
		carTextured = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Car", loader), 
						new ModelTexture(loader.loadTexture("Car UV")
								), true)
				);
		
		Debug.log("Finished loading assets! Initializing scene...");
		
		currentState = new MainMenuScene();
	}

}
