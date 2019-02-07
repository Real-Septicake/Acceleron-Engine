package game;

import org.joml.Vector3d;

import gameEngine.debug.Debug;


public class Bullet {
	
	public BulletDirection direction;
	public Vector3d position;
	public final double movementSpeed = .125f;
	public int teamId;
	
	public void update() {
		position = position.add(direction.getDirection().mul(movementSpeed));
	}
	
	public Bullet(BulletDirection direction, Vector3d pos, int teamId) {
		this.direction = direction;
		this.position = pos;
		this.teamId = teamId;
	}
}
