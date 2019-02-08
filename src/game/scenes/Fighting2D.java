package game.scenes;

import org.joml.Vector3d;

import game.GridManager;
import gameEngine.components.essentials.GameObject;
import gameEngine.components.rendering.*;
import gameEngine.components.scripts.StaticScript;
import gameEngine.debug.Debug;
import gameEngine.rendering.WindowManager;

public class Fighting2D extends StaticScript {

	private Camera camera;
	private GridManager manager;
	
	@Override
	public void update() {
		//MasterRenderer.drawMesh(GameManager.sphereTextured, new Vector3d(0,0,0), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
		
		float width = WindowManager.manager.getCurrentWidth(), height = WindowManager.manager.getCurrentHeight();
		
		float scaler;
		if(width > height) {
			scaler = width / height;
		}
		else {
			scaler = (height * (width / height)) / width;
		}
		
		camera.orthographicSize = (manager.largestDimension() / 2.0) * scaler;
		
		manager.update();
	}

	@Override
	public void start() {
		
		GameObject cameraGm = new GameObject(new Vector3d(0, 0, 2), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		cameraGm.name = "Camera / Player Object";
		camera = (Camera) cameraGm.addComponent(Camera.class);
		camera.orthographic = true;
		
		GameObject lightGm1 = new GameObject(new Vector3d(-100, -50, 100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		Light light = (Light) lightGm1.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm1.name = "Light Object #1";
		
		GameObject lightGm2 = new GameObject(new Vector3d(100, 50, 100), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		light = (Light) lightGm2.addComponent(Light.class);
		light.color = new Vector3d(.5, .5, .5);
		lightGm2.name = "Light Object #2";
		
		manager = new GridManager();
		manager.setup("Tile Maps/Deception");
		
		Debug.log("Fighting area started!");
	}

	@Override
	public void lateUpdate() {
		manager.lateUpdate();
	}

}
