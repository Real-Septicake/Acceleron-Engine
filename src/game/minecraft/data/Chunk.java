package game.minecraft.data;

import java.util.*;

import org.joml.Vector2i;

import game.minecraft.scripts.*;
import game.scripts.GameManager;
import gameEngine.common.LowLevelLoader;
import gameEngine.debug.Debug;
import gameEngine.rendering.data.meshData.TexturedMeshLowLevel;

public class Chunk {
	
	private Blocks[] chunkBlocks = new Blocks[16 * 16 * 256];
	private boolean[] segmentDirty = new boolean[16];
	
	public TexturedMeshLowLevel[] meshes = new TexturedMeshLowLevel[16];
	
	public final int textureAtlasRows = 16;
	
	public Vector2i position;
	public static FastNoise noise = new FastNoise(32941);
	
	public Chunk(Vector2i pos) {
		
		this.position = pos;
		ChunkHandler.registerChunk(this);
		
		Vector2i worldChunkOffset = PosConverter.chunkToWorld(pos);
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				double output = (noise.GetPerlin((x + worldChunkOffset.x) * 4, (-z + worldChunkOffset.y) * 4) + 1) / 2;
				//Debug.log(Math.round(output * 5));
				for (int y = 0; y < 256; y++) {
					if (chunkBlocks[x + z * 16 + y * 256] == null) {
						if(y < 65 + Math.round(output * 15)) {
							//(x % 4 < 2 && z % 4 < 2 && y % 4 < 2) || (x % 4 > 1 && z % 4 > 1)
							chunkBlocks[x + z * 16 + y * 256] = Blocks.Grass;//(((x % 2 == z % 2) && y % 2 == 0) || (x % 2 != z % 2) && y % 2 != 0) ? Blocks.Grass : Blocks.Stone;
						}
						else {
							
							if (65 + Math.round(output * 15) + 4 >= y && (noise.GetPerlin((x + worldChunkOffset.x) * 16, (-z + worldChunkOffset.y) * 16) + 1) / 2 > 0.825) {
								chunkBlocks[x + z * 16 + y * 256] = Blocks.OakLog;
							}
							else {
								chunkBlocks[x + z * 16 + y * 256] = Blocks.Air;
							}
						}
					}
				}		
			}
		}
		
		for (int i = 0; i < segmentDirty.length; i++) {
			segmentDirty[i] = true;
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x - 1, position.y), i);
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x + 1, position.y), i);
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x, position.y - 1), i);
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x, position.y + 1), i);
		}
	}
	
	public void modifyChunk(int x, int y, int z, Blocks block) {
		chunkBlocks[x + z * 16 + y * 256] = block;
		
		segmentDirty[y / 16] = true;
		
		if (x == 0) {
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x - 1, position.y), y / 16);
		}
		else if(x == 15) {
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x + 1, position.y), y / 16);
		}
		
		if (z == 0) {
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x, position.y - 1), y / 16);
		}
		else if(z == 15) {
			ChunkHandler.refreshChunkSegment(new Vector2i(position.x, position.y + 1), y / 16);
		}
	}
	
	public Blocks getBlock(int x, int y, int z) {
		return chunkBlocks[x + z * 16 + y * 256];
	}
	
	public void updateChunks() {
		
		for (int i = 0; i < segmentDirty.length; i++) {
			
			if(segmentDirty[i]) {
				regenerateMesh(i);
				
				segmentDirty[i] = false;
			}
			
		}
	}
	
	public void refreshSegment(int i) {
		
		segmentDirty[i] = true;
		
	}
	
	private void regenerateMesh(int mesh) {
		ArrayList<Double> positions = new ArrayList<Double>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Double> textureCoords = new ArrayList<Double>();
		ArrayList<Double> normals = new ArrayList<Double>();
		
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = mesh * 16; y < mesh * 16 + 16; y++) {
					generateFaces(x, y, z, positions, indices, textureCoords, normals);
				}
			}
		}
		
		double[] posArray = new double[positions.size()];
		for (int i = 0; i < positions.size(); i++) {
			posArray[i] = positions.get(i);
		}
		positions.clear();
		
		int[] intArray = new int[indices.size()];
		for (int i = 0; i < indices.size(); i++) {
			intArray[i] = indices.get(i);
		}
		indices.clear();
		
		double[] textureArray = new double[textureCoords.size()];
		for (int i = 0; i < textureCoords.size(); i++) {
			textureArray[i] = textureCoords.get(i);
		}
		textureCoords.clear();
		
		double[] normalsArray = new double[normals.size()];
		for (int i = 0; i < normals.size(); i++) {
			normalsArray[i] = normals.get(i);
		}
		normals.clear();
		
		if(meshes[mesh] == null) {
			meshes[mesh] = new TexturedMeshLowLevel(LowLevelLoader.loadToVAO(posArray, intArray, textureArray, normalsArray), GameManager.textureAtlas);
		}
		else {
			meshes[mesh].setMesh(LowLevelLoader.reloadToVAO(posArray, intArray, textureArray, normalsArray, meshes[mesh].getMesh().getVaoID()));
		}
	}
	
	private void generateFaces(int x, int y, int z, ArrayList<Double> positions, ArrayList<Integer> indices, ArrayList<Double> textureCoords, ArrayList<Double> normals) {
		
		Blocks block = getBlock(x, y, z);
		BlockType type = block.getBlockType();
		if(type == BlockType.Empty)
			return;

		if(type == BlockType.Custom) {
			Debug.log("Custom block type rendering not implemented, but is trying to be used!");
		}
		else {
			Blocks otherChunkBlock;
			if(x == 0) {
				//Generate west face
				otherChunkBlock = ChunkHandler.getBlockInChunk(15, y, z, new Vector2i(position.x - 1, position.y));
				if (otherChunkBlock != null && otherChunkBlock.isTransparent()) {
					generateFace(x, y, -z, 3, block.getTextureAtlasLocation(3), positions, indices, textureCoords, normals);
				}
				
				//Generate east face
				if(chunkBlocks[(x + 1) + z * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 2, block.getTextureAtlasLocation(2), positions, indices, textureCoords, normals);
				}
			}
			else if (x == 15) {
				//Generate east face
				otherChunkBlock = ChunkHandler.getBlockInChunk(0, y, z, new Vector2i(position.x + 1, position.y));
				if (otherChunkBlock != null && otherChunkBlock.isTransparent()) {
					generateFace(x, y, -z, 2, block.getTextureAtlasLocation(2), positions, indices, textureCoords, normals);
				}
				
				//Generate west face
				if(chunkBlocks[(x - 1) + z * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 3, block.getTextureAtlasLocation(3), positions, indices, textureCoords, normals);
				}
			}
			else {
				//Generate west face
				if(chunkBlocks[(x - 1) + z * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 3, block.getTextureAtlasLocation(3), positions, indices, textureCoords, normals);
				}
				
				//Generate east face
				if(chunkBlocks[(x + 1) + z * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 2, block.getTextureAtlasLocation(2), positions, indices, textureCoords, normals);
				}
			}
			
			if(z == 0) {
				//Generate south face
				otherChunkBlock = ChunkHandler.getBlockInChunk(x, y, 15, new Vector2i(position.x, position.y + 1));
				if (otherChunkBlock != null && otherChunkBlock.isTransparent()) {
					generateFace(x, y, -z, 1, block.getTextureAtlasLocation(1), positions, indices, textureCoords, normals);
				}
				
				//Generate north face
				if(chunkBlocks[x + (z + 1) * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 0, block.getTextureAtlasLocation(0), positions, indices, textureCoords, normals);
				}
			}
			else if (z == 15) {
				//Generate north face
				otherChunkBlock = ChunkHandler.getBlockInChunk(x, y, 0, new Vector2i(position.x, position.y - 1));
				if (otherChunkBlock != null && otherChunkBlock.isTransparent()) {
					generateFace(x, y, -z, 0, block.getTextureAtlasLocation(0), positions, indices, textureCoords, normals);
				}
				
				//Generate south face
				if(chunkBlocks[x + (z - 1) * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 1, block.getTextureAtlasLocation(1), positions, indices, textureCoords, normals);
				}
			}
			else {
				//Generate south face
				if(chunkBlocks[x + (z - 1) * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 1, block.getTextureAtlasLocation(1), positions, indices, textureCoords, normals);
				}
				
				//Generate north face
				if(chunkBlocks[x + (z + 1) * 16 + y * 256].isTransparent()) {
					generateFace(x, y, -z, 0, block.getTextureAtlasLocation(0), positions, indices, textureCoords, normals);
				}
			}
			
			if(y == 0) {
				generateFace(x, y, -z, 4, block.getTextureAtlasLocation(4), positions, indices, textureCoords, normals);
			}
			else if(y == 255) {
				generateFace(x, y, -z, 5, block.getTextureAtlasLocation(5), positions, indices, textureCoords, normals);
			}
			else {
				if(chunkBlocks[x + z * 16 + (y - 1) * 256].isTransparent()) {
					generateFace(x, y, -z, 4, block.getTextureAtlasLocation(4), positions, indices, textureCoords, normals);
				}
				if(chunkBlocks[x + z * 16 + (y + 1) * 256].isTransparent()) {
					generateFace(x, y, -z, 5, block.getTextureAtlasLocation(5), positions, indices, textureCoords, normals);
				}
			}
		}
	}
	
	private void generateFace(int x, int y, int z, int face, int textureAtlasLocation, ArrayList<Double> positions, ArrayList<Integer> indices, ArrayList<Double> textureCoords, ArrayList<Double> normals) {
		int offset = positions.size();
		
		indices.add(offset / 3);
		indices.add(offset / 3 + 1);
		indices.add(offset / 3 + 2);
		indices.add(offset / 3 + 2);
		indices.add(offset / 3 + 3);
		indices.add(offset / 3);
		
		int row = textureAtlasLocation / textureAtlasRows, collumn = textureAtlasLocation % textureAtlasRows;
		
		double xOffset = collumn / (double)textureAtlasRows, yOffset = row / (double)textureAtlasRows;
		
		textureCoords.add(xOffset);
		textureCoords.add(yOffset + 1d / textureAtlasRows);
		
		textureCoords.add(xOffset + 1d / textureAtlasRows);
		textureCoords.add(yOffset + 1d / textureAtlasRows);
		
		textureCoords.add(xOffset + 1d / textureAtlasRows);
		textureCoords.add(yOffset);
		
		textureCoords.add(xOffset);
		textureCoords.add(yOffset);
		
		//North
		if(face == 0) {
			
			positions.add((double)x + 1);
			positions.add((double)y);
			positions.add((double)z - 1);
			
			positions.add((double)x);
			positions.add((double)y);
			positions.add((double)z - 1);
			
			positions.add((double)x);
			positions.add((double)y + 1);
			positions.add((double)z - 1);
			
			positions.add((double)x + 1);
			positions.add((double)y + 1);
			positions.add((double)z - 1);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(-1d);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(-1d);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(-1d);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(-1d);
		}
		//South
		else if(face == 1) {
			
			positions.add((double)x);
			positions.add((double)y);
			positions.add((double)z);
			
			positions.add((double)x + 1);
			positions.add((double)y);
			positions.add((double)z);
			
			positions.add((double)x + 1);
			positions.add((double)y + 1);
			positions.add((double)z);
			
			positions.add((double)x);
			positions.add((double)y + 1);
			positions.add((double)z);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(1d);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(1d);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(1d);
			
			normals.add(0d);
			normals.add(0d);
			normals.add(1d);
		}
		//East
		else if(face == 2) {
			
			positions.add((double)x + 1);
			positions.add((double)y);
			positions.add((double)z);
			
			positions.add((double)x + 1);
			positions.add((double)y);
			positions.add((double)z - 1);
			
			positions.add((double)x + 1);
			positions.add((double)y + 1);
			positions.add((double)z - 1);
			
			positions.add((double)x + 1);
			positions.add((double)y + 1);
			positions.add((double)z);
			
			normals.add(1d);
			normals.add(0d);
			normals.add(0d);
			
			normals.add(1d);
			normals.add(0d);
			normals.add(0d);

			normals.add(1d);
			normals.add(0d);
			normals.add(0d);
			
			normals.add(1d);
			normals.add(0d);
			normals.add(0d);
		}
		//West
		else if(face == 3) {
			
			positions.add((double)x);
			positions.add((double)y);
			positions.add((double)z - 1);
			
			positions.add((double)x);
			positions.add((double)y);
			positions.add((double)z);
			
			positions.add((double)x);
			positions.add((double)y + 1);
			positions.add((double)z);
			
			positions.add((double)x);
			positions.add((double)y + 1);
			positions.add((double)z - 1);
			
			normals.add(-1d);
			normals.add(0d);
			normals.add(0d);
			
			normals.add(-1d);
			normals.add(0d);
			normals.add(0d);

			normals.add(-1d);
			normals.add(0d);
			normals.add(0d);
			
			normals.add(-1d);
			normals.add(0d);
			normals.add(0d);
		}
		//Bottom
		else if(face == 4) {
			positions.add((double)x);
			positions.add((double)y);
			positions.add((double)z - 1);
			
			positions.add((double)x + 1);
			positions.add((double)y);
			positions.add((double)z - 1);
			
			positions.add((double)x + 1);
			positions.add((double)y);
			positions.add((double)z);
			
			positions.add((double)x);
			positions.add((double)y);
			positions.add((double)z);
			
			normals.add(0d);
			normals.add(-1d);
			normals.add(0d);
			
			normals.add(0d);
			normals.add(-1d);
			normals.add(0d);
			
			normals.add(0d);
			normals.add(-1d);
			normals.add(0d);
			
			normals.add(0d);
			normals.add(-1d);
			normals.add(0d);
		}
		//Top
		else if(face == 5) {
			positions.add((double)x);
			positions.add((double)y + 1);
			positions.add((double)z - 1);
			
			positions.add((double)x);
			positions.add((double)y + 1);
			positions.add((double)z);
			
			positions.add((double)x + 1);
			positions.add((double)y + 1);
			positions.add((double)z);
			
			positions.add((double)x + 1);
			positions.add((double)y + 1);
			positions.add((double)z - 1);
			
			normals.add(0d);
			normals.add(1d);
			normals.add(0d);
			
			normals.add(0d);
			normals.add(1d);
			normals.add(0d);
			
			normals.add(0d);
			normals.add(1d);
			normals.add(0d);
			
			normals.add(0d);
			normals.add(1d);
			normals.add(0d);
		}
	}
	
	public void clean() {
		for (int i = 0; i < meshes.length; i++) {
			LowLevelLoader.removeMesh(meshes[i].getMesh().getVaoID());
		}
	}
}
