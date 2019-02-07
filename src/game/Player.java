package game;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Vector3d;

import gameEngine.common.InputManager;
import gameEngine.common.UpdateHandler;
import gameEngine.debug.Debug;

public class Player {

	public Vector3d position;
	public Vector3d lastPosition;
	
	public final double movementSpeed = 6;
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
		}
		// Backward
		if (InputManager.keyDown(GLFW_KEY_S)) {
			movement.y = -1;
		}
		// Left
		if (InputManager.keyDown(GLFW_KEY_A)) {
			movement.x = -1;
		}
		// Right
		if (InputManager.keyDown(GLFW_KEY_D)) {
			movement.x = 1;
		}
		
		if(InputManager.keyDown(GLFW_KEY_UP) && hasStoppedFiring) {
			direction = BulletDirection.Up;
			fireBullet = true;
			hasStoppedFiring = false;
		}
		else if(InputManager.keyDown(GLFW_KEY_DOWN) && hasStoppedFiring) {
			direction = BulletDirection.Down;
			fireBullet = true;
			hasStoppedFiring = false;
		}
		else if(InputManager.keyDown(GLFW_KEY_LEFT) && hasStoppedFiring) {
			direction = BulletDirection.Left;
			fireBullet = true;
			hasStoppedFiring = false;
		}
		else if(InputManager.keyDown(GLFW_KEY_RIGHT) && hasStoppedFiring) {
			direction = BulletDirection.Right;
			fireBullet = true;
			hasStoppedFiring = false;
		}
		else if (!InputManager.keyDown(GLFW_KEY_UP) && !InputManager.keyDown(GLFW_KEY_DOWN) &&
				 !InputManager.keyDown(GLFW_KEY_LEFT) && !InputManager.keyDown(GLFW_KEY_RIGHT)){
			hasStoppedFiring = true;
		}
		
		if(movement.x != 0 || movement.y != 0)
			position.add(movement.normalize(1).mul(movementSpeed * UpdateHandler.timeDelta));
	}
}
