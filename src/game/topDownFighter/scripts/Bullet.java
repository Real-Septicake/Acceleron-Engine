package game.topDownFighter.scripts;

import org.joml.Vector3d;

import gameEngine.common.UpdateHandler;


public class Bullet {
	
	public BulletDirection direction;
	public Vector3d position;
	public final double movementSpeed = 7.5;
	public int teamId;
	public boolean fastShot;
	
	public void update() {
		position = position.add(direction.getDirection().mul(movementSpeed * UpdateHandler.timeDelta));
		if(fastShot)
			position = position.add(direction.getDirection().mul(movementSpeed * UpdateHandler.timeDelta));
	}
	
	public Bullet(BulletDirection direction, Vector3d pos, int teamId, boolean fastShot) {
		this.direction = direction;
		this.position = pos;
		this.teamId = teamId;
		this.fastShot = fastShot;
	}
}
