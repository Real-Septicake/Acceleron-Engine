package game.scenes;

import org.joml.*;

import game.scripts.GameManager;
import game.scripts.MainGame;
import gameEngine.common.*;
import gameEngine.components.*;
import gameEngine.components.essentials.GameObject;
import gameEngine.components.rendering.Camera;
import gameEngine.components.rendering.Light;
import gameEngine.components.scripts.StaticScript;
import gameEngine.debug.Debug;
import gameEngine.rendering.MasterRenderer;
import gameEngine.rendering.WindowManager;
import gameEngine.rendering.gui.GuiRendererHandler;
import gameEngine.rendering.gui.GuiTexture;

public class MainMenuScene extends StaticScript {

	private double currentRotation;
	private static GuiTexture menuTexture;
	
	@Override
	public void update() {
		currentRotation += 0.25;
		
		Quaterniond rot = Maths.fromEulerAngle(new Vector3d(0,currentRotation,0));
		
		for (float x = -10; x <= 10; x+= 0.25f) {
			for (float y = -10; y <= 10; y+= 0.25f) {
				for (float z = -10; z <= 10; z+= 0.25f) {
					float distance = x * x + y * y + z * z;
					if((distance <= 100 && distance > 98) || (distance <= 5 && distance > 3)) {
						//count++;
						MasterRenderer.drawMesh(GameManager.sphereTextured, new Vector3d(x, y, z).rotate(rot).add(new Vector3d(0, 10, -15)), new Vector3d(0, 0, 0), new Vector3d(2, 2, 2));
					}
				}
			}
		}
		
		int width = WindowManager.manager.getCurrentWidth(), height = WindowManager.manager.getCurrentHeight();
		double aspectRatio = (double)width / height;
		menuTexture.transform.size = new Vector2d(300f * aspectRatio / width, 150f * aspectRatio / height);
		
		GuiRendererHandler.addUIElement(menuTexture);
	}

	@Override
	public void start() {
		
		Debug.log("Main menu started!");
		
		if(menuTexture == null) {
			menuTexture = new GuiTexture(MainGame.gameEngine.getLoader().loadTexture("PoorMansFPS"));
		}
		
		menuTexture.transform.position = new Vector3d(-.75, 0, 0);
		
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

}
