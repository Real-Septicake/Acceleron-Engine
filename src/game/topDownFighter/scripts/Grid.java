package game.topDownFighter.scripts;

public class Grid {
	
	private TileState[] tileStates;
	private int width;
	private int height;
	public boolean isDirty = false;
	
	public Grid(int width, int height, TileState[] gridData) {
		this.tileStates = gridData;
		this.width = width;
		this.height = height;
	}
	
	public void modifyGrid(int location, TileState state) {
		tileStates[location] = state;
	}
	
	public TileState getTileAtLocation(int location) {
		return tileStates[location];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
