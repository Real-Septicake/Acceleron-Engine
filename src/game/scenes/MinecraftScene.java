package game.scenes;

import org.joml.Vector2i;
import org.joml.Vector3d;

import game.minecraft.data.Chunk;
import game.minecraft.scripts.ChunkHandler;
import game.scripts.GameManager;
import game.topDownFighter.scripts.CameraMover;
import gameEngine.components.essentials.GameObject;
import gameEngine.components.rendering.Camera;
import gameEngine.components.rendering.Light;
import gameEngine.components.rendering.MeshRenderer;
import gameEngine.components.scripts.StaticScript;
import gameEngine.debug.Debug;
import gameEngine.rendering.MasterRenderer;

public class MinecraftScene extends StaticScript {

	Chunk[] chunks;
	
	@Override
	public void update() {
		for (int i = 0; i < chunks.length; i++) {
			chunks[i].updateChunks();
		}
		
		ChunkHandler.renderVisibleChunks();
	}

	@Override
	public void start() {
		
		chunks = new Chunk[9];
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 3; y++) {
				chunks[x + y * 3] = new Chunk(new Vector2i(x - 1, y - 1));
			}
		}
		
		GameObject cameraGm = new GameObject(new Vector3d(0, 70, 5), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		cameraGm.name = "Camera / Player Object";
		Camera camera = (Camera) cameraGm.addComponent(Camera.class);
		camera.orthographic = false;
		cameraGm.addComponent(CameraMover.class);
		
		GameObject lightGm1 = new GameObject(new Vector3d(-100, 100, 100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		Light light = (Light) lightGm1.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm1.name = "Light Object #1";
		
		GameObject lightGm2 = new GameObject(new Vector3d(-100, 100, -100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm2.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm2.name = "Light Object #2";
		
		GameObject lightGm3 = new GameObject(new Vector3d(100, 100, 100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm3.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm3.name = "Light Object #3";
		
		GameObject lightGm4 = new GameObject(new Vector3d(100, 100, -100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm4.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm4.name = "Light Object #4";
		
		GameObject floor = new GameObject(new Vector3d(0), new Vector3d(0), new Vector3d(100, 1, 100));
		MeshRenderer floorRend = (MeshRenderer) floor.addComponent(MeshRenderer.class);
		floorRend.mesh = GameManager.floorTextured;
		
		Debug.log("Minecraft area loaded!");
	}

	@Override
	public void lateUpdate() {
		//ChunkRenderer.render(GameManager.textureAtlas);
	}

	@Override
	public void onDestroy() {
		for (int i = 0; i < chunks.length; i++) {
			chunks[i].clean();
		}
	}

}
