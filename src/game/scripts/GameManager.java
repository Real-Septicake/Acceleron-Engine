package game.scripts;

import static org.lwjgl.glfw.GLFW.*;

import game.textures.ModelTexture;
import gameEngine.common.*;
import gameEngine.components.*;
import gameEngine.rendering.meshData.*;

public class GameManager extends StaticScript {

	private StaticScript currentState;
	
	public static CompleteMesh sphereTextured;
	public static CompleteMesh floorTextured;
	public static CompleteMesh texturedTree;
	public static CompleteMesh carTextured;
	
	@Override
	public void update() {
		if(InputManager.keyDown(GLFW_KEY_1)) {
			GameObject.clearScene();
			currentState.stop();
			currentState = new RandomScene();
		}
		else if (InputManager.keyDown(GLFW_KEY_2)) {
			GameObject.clearScene();
			currentState.stop();
			currentState = new RacingScene();
		}
		else if (InputManager.keyDown(GLFW_KEY_3)) {
			GameObject.clearScene();
			currentState.stop();
			currentState = new MainMenuScene();
		}
	}

	@Override
	public void start() {
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
		
		currentState = new MainMenuScene();
	}

}
