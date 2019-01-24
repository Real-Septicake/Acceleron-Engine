package game.gameData.blocks;

import game.gameData.BlockInfo;

public class Block_Stone extends BlockInfo {
	
	private final static String name = "Stone";
	private final static String internalName = "Base:Stone";
	//private final static String[] textureLocations = new String[]{};
	private final static int[] textureIDs = new int[] { 0, 0, 0, 0, 0, 0};
	
	
	public Block_Stone() {
		//textureIDs = ContentDatabase.LoadTextures(textureLocations);
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getInternalName() {
		return internalName;
	}
	
	@Override
	public boolean getTransparent() {
		return false;
	}

	@Override
	public int[] getTextureIDs() {
		return textureIDs;
	}
}
