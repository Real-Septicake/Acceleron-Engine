package game.scenes;

import org.joml.Vector2i;
import org.joml.Vector3d;

import game.minecraft.data.Chunk;
import game.scripts.GameManager;
import game.topDownFighter.scripts.CameraMover;
import gameEngine.components.essentials.GameObject;
import gameEngine.components.rendering.Camera;
import gameEngine.components.rendering.Light;
import gameEngine.components.rendering.MeshRenderer;
import gameEngine.components.scripts.StaticScript;
import gameEngine.rendering.MasterRenderer;

public class MinecraftScene extends StaticScript {

	Chunk chunk;
	
	@Override
	public void update() {
		chunk.updateChunks();
		
		MasterRenderer.drawMesh(chunk.meshes[0], new Vector3d(0), new Vector3d(0), new Vector3d(.2));
		for (int i = 0; i < chunk.meshes.length; i++) {
			//MasterRenderer.drawMesh(chunk.meshes[i], new Vector3d(chunk.position.x, i * 16, chunk.position.y), new Vector3d(0), new Vector3d(0.2));
		}
	}

	@Override
	public void start() {
		chunk = new Chunk(new Vector2i(0));
		
		GameObject cameraGm = new GameObject(new Vector3d(0, 2, 0), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
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
	}

	@Override
	public void lateUpdate() {
		//ChunkRenderer.render(GameManager.textureAtlas);
	}

}
