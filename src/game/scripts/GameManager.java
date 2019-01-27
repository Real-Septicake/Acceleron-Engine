package game.scripts;

import static org.lwjgl.glfw.GLFW.*;

import game.textures.ModelTexture;
import gameEngine.common.*;
import gameEngine.components.*;
import gameEngine.rendering.meshData.MeshLowLevel;
import gameEngine.rendering.meshData.TexturedMeshLowLevel;

public class GameManager extends StaticScript {

	private StaticScript currentState;
	
	public static TexturedMeshLowLevel sphereTextured;
	public static TexturedMeshLowLevel floorTextured;
	public static TexturedMeshLowLevel texturedTree;
	public static TexturedMeshLowLevel carTextured;
	
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
		MeshLowLevel sphereMesh = OBJLoader.loadObjModel("Sphere", loader);
		ModelTexture sphereTexture = new ModelTexture(loader.loadTexture("sphere_UV"));
		sphereTextured = new TexturedMeshLowLevel(sphereMesh, sphereTexture, false);
		
		//Tree Loading
		MeshLowLevel mesh = OBJLoader.loadObjModel("Tree", loader);
		ModelTexture treeTexture = new ModelTexture(loader.loadTexture("Tree"));
		texturedTree = new TexturedMeshLowLevel(mesh, treeTexture, false);
		
		//Floor loading
		MeshLowLevel floorMesh = OBJLoader.loadObjModel("Floor", loader);
		ModelTexture floorTexture = new ModelTexture(loader.loadTexture("floor UV"));
		floorTextured = new TexturedMeshLowLevel(floorMesh, floorTexture, false);
		
		carTextured = new TexturedMeshLowLevel(OBJLoader.loadObjModel("Car", loader), new ModelTexture(loader.loadTexture("Car UV")), true);
		
		currentState = new MainMenuScene();
	}

}
