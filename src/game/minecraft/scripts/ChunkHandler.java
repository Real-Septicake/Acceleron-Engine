package game.minecraft.scripts;

import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

import java.util.*;

import org.joml.Vector2i;
import org.joml.Vector3d;

import game.minecraft.data.Blocks;
import game.minecraft.data.Chunk;
import gameEngine.debug.Debug;
import gameEngine.rendering.MasterRenderer;

public class ChunkHandler {
	
	public static Hashtable<Vector2i, Chunk> chunkStorage = new Hashtable<Vector2i, Chunk>();
	
	public static Blocks getBlockInChunk(int x, int y, int z, Vector2i chunkLocation) {
		
		Chunk chunk = chunkStorage.get(chunkLocation);
		if(chunk == null) {
			return null;
		}
		
		return chunk.getBlock(x, y, z);
	}
	
	public static void registerChunk(Chunk chunk) {
		chunkStorage.put(chunk.position, chunk);
	}
	
	public static void refreshChunkSegment(Vector2i location, int segment) {
		Chunk chunk = chunkStorage.get(location);
		if (chunk != null) {
			chunk.refreshSegment(segment);
		}
	}
	
	public static void renderVisibleChunks() {
		for (Chunk chunk : chunkStorage.values()) {
			chunk.updateChunks();
			for (int i = 0; i < chunk.meshes.length; i++) {
				if (chunk.meshes[i].getMesh().getVertexCount() > 0) {
					MasterRenderer.drawMesh(chunk.meshes[i], new Vector3d(chunk.position.x * 16, 0, chunk.position.y * 16), new Vector3d(0), new Vector3d(1));
				}
			}
		}
	}
	
	public static void loadChunk(Vector2i location) {
		if(!chunkStorage.containsKey(location)) {
			new Chunk(location);
		}
	}
	
	public static void clean() {
		for (Chunk chunk : chunkStorage.values()) {
			chunk.clean();
		}
		
		chunkStorage.clear();
	}
}
