package game.topDownFighter.scripts;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3d;

import gameEngine.common.InputManager;
import gameEngine.common.Maths;
import gameEngine.common.UpdateHandler;
import gameEngine.components.scripts.Script;
import gameEngine.debug.Debug;

public class CameraMover extends Script {

	@Override
	public void update() {
		Vector3d movement = new Vector3d(0,0,0);
		Vector3d rotation = new Vector3d(0,0,0);
		// Forward
		if (InputManager.keyDown(GLFW_KEY_W))
			movement.z = -1;
		// Backward
		if (InputManager.keyDown(GLFW_KEY_S))
			movement.z = 1;
		// Left
		if (InputManager.keyDown(GLFW_KEY_A))
			movement.x = -1;
		// Right
		if (InputManager.keyDown(GLFW_KEY_D))
			movement.x = 1;
		// Look left
		if (InputManager.keyDown(GLFW_KEY_Q))
			rotation.y = -1;
		// Look right
		if (InputManager.keyDown(GLFW_KEY_E))
			rotation.y = 1;
		// Look up
		if (InputManager.keyDown(GLFW_KEY_Z))
			rotation.x = 1;
		// Look down
		if (InputManager.keyDown(GLFW_KEY_X))
			rotation.x = -1;
		// Down
		if (InputManager.keyDown(GLFW_KEY_LEFT_SHIFT))
			movement.y = -1;
		// Up
		if (InputManager.keyDown(GLFW_KEY_SPACE))
			movement.y = 1;
		
		gameObject.transform.rotation.add(rotation);
		movement.rotate(Maths.fromEulerAngle(gameObject.transform.rotation));
			
		gameObject.transform.position.add(movement.mul(12 * UpdateHandler.timeDelta));
	}

	@Override
	public void start() {
		
	}

	@Override
	public void lateUpdate() {
		
	}

	@Override
	public void onDestroy() {
		
	}
}
