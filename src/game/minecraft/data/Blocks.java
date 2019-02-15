package game.minecraft.data;

public enum Blocks {
	Air("Air", "Base", true, BlockType.Empty, null),
	Grass("Grass", "Base", false, BlockType.Cube, new int[] {2, 2, 2, 2, 3, 1} ),
	Dirt("Dirt", "Base", false, BlockType.Cube, new int[] {3, 3, 3, 3, 3, 3}),
	Stone("Stone", "Base", false, BlockType.Cube, new int[] {0, 0, 0, 0, 0, 0}),
	OakLog("Oak Log", "Base", false, BlockType.Cube, new int[] {20, 20, 20, 20, 20, 19});
	
	private String name;
	private String mod;
	private boolean transparent;
	private BlockType blockType;
	private int[] textureAtlasLocation;
	
	public String getName() {
		return name;
	}
	
	public String getMod() {
		return mod;
	}
	
	public boolean isTransparent() {
		return transparent;
	}
	
	public BlockType getBlockType() {
		return blockType;
	}
	
	public int getTextureAtlasLocation(int side) {
		return textureAtlasLocation[side];
	}
	
	private Blocks(String name, String mod, boolean transparent, BlockType blockType, int[] textureAtlasLocation) {
		this.name = name;
		this.mod = mod;
		this.transparent = transparent;
		this.blockType = blockType;
		this.textureAtlasLocation = textureAtlasLocation;
	}
}
