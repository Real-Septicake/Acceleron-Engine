package game.gameData.blocks;

import game.gameData.BlockInfo;

public class Block_Air extends BlockInfo {
	
	private final static String name = "Air";
	private final static String internalName = "Base:Air";
	
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
		return true;
	}
	
	@Override
	public int[] getTextureIDs() {
		return new int[0];
	}
	
}
