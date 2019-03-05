package game.topDownFighter.scripts;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Random;

import org.joml.Vector3d;

import gameEngine.common.InputManager;
import gameEngine.common.UpdateHandler;

public class Player {

	public Vector3d position;
	public Vector3d lastPosition;
	
	public final double movementSpeed = 6;
	public int teamId;
	public boolean shotIsFastShot = false;
	
	public BulletDirection direction = BulletDirection.Right;
	public boolean fireBullet = false;
	private boolean hasStoppedFiring = true;
	
	public Player (Vector3d pos, int teamId) {
		this.position = pos;
		this.teamId = teamId;
	}

	public void update() {
		
		lastPosition = position;
		boolean isFirstPlayer = (teamId == 0);
		int up = (isFirstPlayer) ? GLFW_KEY_W : GLFW_KEY_UP;
		int down = (isFirstPlayer) ? GLFW_KEY_S : GLFW_KEY_DOWN;
		int left = (isFirstPlayer) ? GLFW_KEY_A : GLFW_KEY_LEFT; 
		int right = (isFirstPlayer) ? GLFW_KEY_D : GLFW_KEY_RIGHT;
		
		int fireUp = (isFirstPlayer) ? GLFW_KEY_I : GLFW_KEY_KP_5;
		int fireDown = (isFirstPlayer) ? GLFW_KEY_K : GLFW_KEY_KP_2;
		int fireLeft = (isFirstPlayer) ? GLFW_KEY_J : GLFW_KEY_KP_1; 
		int fireRight = (isFirstPlayer) ? GLFW_KEY_L : GLFW_KEY_KP_3;
		
		Vector3d movement = new Vector3d(0,0,0);
		// Forward
		if (InputManager.keyDown(up)) {
			movement.y = 1;
		}
		// Backward
		if (InputManager.keyDown(down)) {
			movement.y = -1;
		}
		// Left
		if (InputManager.keyDown(left)) {
			movement.x = -1;
		}
		// Right
		if (InputManager.keyDown(right)) {
			movement.x = 1;
		}
		
		if(InputManager.keyIsDown(fireUp)) {
			direction = BulletDirection.Up;
			fireBullet = true;
		}
		
		if(InputManager.keyIsDown(fireDown)) {
			direction = BulletDirection.Down;
			fireBullet = true;
		}
		
		if(InputManager.keyIsDown(fireLeft)) {
			direction = BulletDirection.Left;
			fireBullet = true;
		}
		
		if(InputManager.keyIsDown(fireRight)) {
			direction = BulletDirection.Right;
			fireBullet = true;
		}
		
		shotIsFastShot = new Random().nextBoolean();
		
		if(movement.x != 0 || movement.y != 0)
			position.add(movement.normalize(1).mul(movementSpeed * UpdateHandler.timeDelta));
	}
}
