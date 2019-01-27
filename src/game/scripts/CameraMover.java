package game.scripts;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_SHIFT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_X;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Z;

import org.joml.Vector3d;

import gameEngine.common.InputManager;
import gameEngine.common.Maths;
import gameEngine.components.Script;

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
			
		gameObject.transform.position.add(movement.mul(0.2));
	}

	@Override
	public void start() {
		
	}
}
