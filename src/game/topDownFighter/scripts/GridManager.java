package game.topDownFighter.scripts;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;

import org.joml.Vector2d;
import org.joml.Vector3d;

import game.scripts.GameManager;
import gameEngine.common.LowLevelLoader;
import gameEngine.debug.Debug;
import gameEngine.rendering.MasterRenderer;

public class GridManager {

	private Grid grid;
	private GridPhysicsEngine physicsEngine;
	private HashSet<Vector2d> whiteSpawns = new HashSet<Vector2d>();
	private HashSet<Vector2d> blackSpawns = new HashSet<Vector2d>();
	
	public void update() {
		int width = grid.getWidth(), height = grid.getHeight();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				MasterRenderer.drawMesh(GameManager.gridTile, new Vector3d(x - width / 2f, y - height / 2f, 0), new Vector3d(0), new Vector3d(1.01), grid.getTileAtLocation(x + y * width).getTextureAtlasLocation());
			}
		}
	}

	public void lateUpdate() {
		
		physicsEngine.runUpdate();
		
		for (Bullet bullet : physicsEngine.bullets) {
			MasterRenderer.drawMesh(GameManager.bulletTextured, new Vector3d(0.4 + bullet.position.x, 0.4 + bullet.position.y, bullet.position.z), new Vector3d(0), new Vector3d(.25), bullet.fastShot ? 1 : 0);	
		}
		
		for (Player player : physicsEngine.players) {
			MasterRenderer.drawMesh(GameManager.gridTile, new Vector3d(0.25 + player.position.x, 0.25 + player.position.y, player.position.z), new Vector3d(0), new Vector3d(.5), player.teamId == 0 ? 0 : 1);
		}
	}
	
	public void setup(String mapLocation) {
		
		BufferedImage mapData = LowLevelLoader.getImageContents(mapLocation);
		int width = mapData.getWidth(), height = mapData.getHeight();
		TileState[] tileData = new TileState[width * height];
		
		int colorDat;
		int r, g, b;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				
				colorDat = mapData.getRGB(x, height - 1 - y);
				r = (colorDat & 0x00ff0000) >> 16;
				g = (colorDat & 0x0000ff00) >> 8;
				b = colorDat & 0x000000ff;
				
				TileState state;
				
				if(b == 255 || (r == 255 && g == 255 && b == 255)) {
					if(g == 0 && r == 0) {
						whiteSpawns.add(new Vector2d(x - width / 2f, y - height / 2f));
					}
					state = TileState.White;
				}
				else if(r == 255 || (r == 0 && g == 0 && b == 0)) {
					if(r == 255) {
						blackSpawns.add(new Vector2d(x - width / 2f, y - height / 2f));
					}
					state = TileState.Black;
				}
				else if(g == 255) {
					state = TileState.Wall;
				}
				else {
					state = TileState.Empty;
				}
				
				tileData[x + y * width] = state;
			}
		}
		
		grid = new Grid(width, height, tileData);
		
		physicsEngine = new GridPhysicsEngine();
		physicsEngine.setup(grid);
		
		Vector2d spawn = getWhiteSpawn();
		physicsEngine.addPlayer(new Player(new Vector3d(0, 0, .5).add(spawn.x, spawn.y, 0), 1));
		
		spawn = getBlackSpawn();
		physicsEngine.addPlayer(new Player(new Vector3d(0, 0, .5).add(spawn.x, spawn.y, 0), 0));
	}
	
	public int largestDimension() {
		return (grid.getHeight() > grid.getWidth()) ? grid.getHeight() : grid.getWidth();
	}
	
	public Vector2d getWhiteSpawn() {
		return (Vector2d) whiteSpawns.toArray()[new Random(1).nextInt(whiteSpawns.size())];
	}
	
	public Vector2d getBlackSpawn() {
		return (Vector2d) blackSpawns.toArray()[new Random(1).nextInt(blackSpawns.size())];
	}
	
	public void updateTile(int index, TileState state) {
		if(index >= grid.getWidth() * grid.getHeight()) {
			Debug.logError("INDEX OUT OF RANGE!");
		}
		else {
			grid.modifyGrid(index, state);
		}
	}
}
