package game.minecraft.scripts;

import java.util.*;

import org.joml.Vector2i;

import game.minecraft.data.Blocks;
import game.minecraft.data.Chunk;

public class ChunkHandler {
	
	public static HashMap<Vector2i, Chunk> chunkStorage = new HashMap<Vector2i, Chunk>();
	
	public static Blocks getBlockInChunk(int x, int y, int z, Vector2i chunkLocation) {
		
		return Blocks.Air;
		/*
		Chunk chunk = chunkStorage.get(chunkLocation);
		if(chunk == null) {
			return null;
		}
		
		return chunk.getBlock(x, y, z);*/
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
}
