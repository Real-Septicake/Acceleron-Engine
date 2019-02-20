package game.scripts;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector2d;
import org.joml.Vector3d;

import game.scenes.*;
import gameEngine.common.*;
import gameEngine.components.essentials.*;
import gameEngine.components.scripts.*;
import gameEngine.debug.*;
import gameEngine.rendering.WindowManager;
import gameEngine.rendering.data.meshData.*;
import gameEngine.rendering.gui.*;

public class GameManager extends StaticScript {

	private StaticScript currentState;
	
	public static CompleteMesh sphereTextured;
	public static CompleteMesh floorTextured;
	public static CompleteMesh texturedTree;
	public static CompleteMesh carTextured;
	public static GuiTexture menuTexture;
	public static TexturedMeshLowLevel gridTile;
	public static TexturedMeshLowLevel bulletTextured;
	public static ModelTexture textureAtlas;
	
	@Override
	public void update() {
		if(InputManager.keyIsDown(GLFW_KEY_1)) {
			currentState.stop();
			currentState = new RandomScene();
			GameObject.clearScene();
		}
		else if (InputManager.keyIsDown(GLFW_KEY_2)) {
			currentState.stop();
			currentState = new Fighting2D();
			GameObject.clearScene();
		}
		else if (InputManager.keyIsDown(GLFW_KEY_3)) {
			currentState.stop();
			currentState = new MinecraftScene();
			GameObject.clearScene();
		}
		else if (InputManager.keyIsDown(GLFW_KEY_4)) {
			currentState.stop();
			currentState = new MainMenuScene();
			GameObject.clearScene();
		}
		
		GameManager.menuTexture.transform.size = new Vector2d(379, 74);
		GameManager.menuTexture.transform.position = new Vector3d(200, 1000, 0);
		
		GuiRendererHandler.addUIElement(GameManager.menuTexture);
	}

	@Override
	public void start() {
		
		Debug.log("Loading assets...");
		
		menuTexture = new GuiTexture(LowLevelLoader.loadTexture("UI/Acceleron"));
		
		//Sphere loading
		sphereTextured = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Models/Sphere"), 
						new ModelTexture(LowLevelLoader.loadTexture("Textures/Sphere Texture")))
				);
		
		//Tree Loading
		texturedTree = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Models/Tree"), 
						new ModelTexture(LowLevelLoader.loadTexture("Textures/Tree Texture")))
				);
		
		//Floor loading
		floorTextured = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Models/Floor"), 
						new ModelTexture(LowLevelLoader.loadTexture("Textures/Floor Texture")))
				);
		
		carTextured = new CompleteMesh(
				new TexturedMeshLowLevel(
						OBJLoader.loadObjModel("Models/Car"), 
						new ModelTexture(LowLevelLoader.loadTexture("Textures/Car Texture")), true)
				);
		
		gridTile = new TexturedMeshLowLevel(
			LowLevelLoader.loadToVAO(
				new double[] { 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0 }, 
				new int[] { 0, 1, 2, 2, 1, 3}, 
				new double[] {0, 0, 0, 1, 1, 0, 1, 1 }, 
				new double[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1}), 
			new ModelTexture(LowLevelLoader.loadTexture("Tile Sets/Original")), 2);
		
		bulletTextured = new TexturedMeshLowLevel(
				LowLevelLoader.loadToVAO(
					new double[] { 0, 1, 0, 0, 0, 0, 1, 1, 0, 1, 0, 0 }, 
					new int[] { 0, 1, 2, 2, 1, 3}, 
					new double[] {0, 0, 0, 1, 1, 0, 1, 1 }, 
					new double[] { 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1}), 
				new ModelTexture(LowLevelLoader.loadTexture("Textures/BulletAtlas")), 2);
		
		textureAtlas = new ModelTexture(LowLevelLoader.loadTexture("Textures/Minecraft Texture Atlas"));
		
		Debug.log("Finished loading assets! Initializing scene...");
		
		currentState = new MainMenuScene();
	}

	@Override
	public void lateUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		
	}

}
