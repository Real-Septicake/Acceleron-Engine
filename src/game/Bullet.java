package game;

import org.joml.Vector3d;


public class Bullet {
	
	public BulletDirection direction;
	public Vector3d position;
	public final double movementSpeed = 3f;
	public int teamId;
	
	public Bullet(BulletDirection direction, Vector3d pos, int teamId) {
		this.direction = direction;
		this.position = pos;
		this.teamId = teamId;
	}
}
