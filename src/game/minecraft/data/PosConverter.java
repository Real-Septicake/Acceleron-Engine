package game.minecraft.data;

import org.joml.Vector2i;
import org.joml.Vector3d;

public class PosConverter {
	
	public static Vector2i coordinatesToChunk(Vector3d position) {
		return new Vector2i((int) ((position.x >= 0) ? (int)(position.x) / 16 : (int)(position.x) / 16 - 1),
				(int) ((position.z <= 0) ? (int)(position.z) / 16 : (int)(position.z) / 16 + 1));
	}
	
	public static Vector2i chunkToWorld(Vector2i position) {
		return new Vector2i((int) ((position.x >= 0) ? (int)(position.x) * 16 : (int)(position.x) * 16 - 1),
				(int) ((position.y <= 0) ? (int)(position.y) * 16 : (int)(position.y) * 16 + 1));
	}
}
