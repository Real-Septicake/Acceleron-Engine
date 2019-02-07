package game;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3d;

import gameEngine.common.InputManager;
import gameEngine.debug.Debug;

public class Player {

	public Vector3d position;
	public Vector3d lastPosition;
	
	public final double movementSpeed = .1f;
	public int teamId;
	
	public BulletDirection direction = BulletDirection.Right;
	public boolean fireBullet = false;
	private boolean hasStoppedFiring = true;
	
	public Player (Vector3d pos, int teamId) {
		this.position = pos;
		this.teamId = teamId;
	}

	public void update() {
		
		lastPosition = position;
		
		Vector3d movement = new Vector3d(0,0,0);
		// Forward
		if (InputManager.keyDown(GLFW_KEY_W)) {
			movement.y = 1;
			direction = BulletDirection.Up;
		}
		// Backward
		if (InputManager.keyDown(GLFW_KEY_S)) {
			movement.y = -1;
			direction = BulletDirection.Down;
		}
		// Left
		if (InputManager.keyDown(GLFW_KEY_A)) {
			movement.x = -1;
			direction = BulletDirection.Left;
		}
		// Right
		if (InputManager.keyDown(GLFW_KEY_D)) {
			movement.x = 1;
			direction = BulletDirection.Right;
		}
		
		if(InputManager.keyDown(GLFW_KEY_SPACE) && hasStoppedFiring) {
			fireBullet = true;
			hasStoppedFiring = false;
		}
		else if (!InputManager.keyDown(GLFW_KEY_SPACE)) {
			hasStoppedFiring = true;
		}
		
		position.add(movement.mul(movementSpeed));
	}
}
