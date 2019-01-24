package game.gameData.blocks;

import game.gameData.BlockInfo;

public class Block_Dirt extends BlockInfo {

	private final static String name = "Dirt";
	private final static String internalName = "Base:Dirt";
	//private final static String[] textureLocations = new String[]{};
	private final static int[] textureIDs = new int[] { 3, 3, 3, 3, 3, 3};
	
	
	public Block_Dirt() {
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
