package game.scenes;

import org.joml.*;

import game.scripts.*;
import gameEngine.common.*;
import gameEngine.components.essentials.*;
import gameEngine.components.rendering.*;
import gameEngine.components.scripts.*;
import gameEngine.debug.*;
import gameEngine.rendering.*;

public class MainMenuScene extends StaticScript {

	private double currentRotation;
	
	@Override
	public void update() {
		currentRotation += 15 * UpdateHandler.timeDelta;
		
		SpinningSphere.render(currentRotation);
	}

	@Override
	public void start() {
		
		Debug.log("Main menu started!");
		
		GameObject cameraGm = new GameObject(new Vector3d(0, 10, 10), new Vector3d(0, 0, 0), new Vector3d(1, 1, 1));
		cameraGm.name = "Camera / Player Object";
		cameraGm.addComponent(Camera.class);
		
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
	}

	@Override
	public void lateUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDestroy() {
		
	}

}
