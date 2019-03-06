package game.topDownFighter.scripts;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;

import org.joml.Vector3d;

import gameEngine.debug.Debug;

public class GridPhysicsEngine {

    public HashSet<Player> players = new HashSet<Player>();
    private Grid grid;
    public HashSet<Bullet> bullets = new HashSet<Bullet>();
    
    /* TODO LIST:
     * Remove above variables, replace with arrays
     * Do checks for possible tile collisions (9 tiles for player, 2 for bullets (current tile, next tile)) instead of checking all tiles
     * Allow physics rewinding by saving states
     * Have all players store a grid location to simplify checks if it is within a tile for things like fast collision checking
     * ? Ignore grid entirely and do collidable to collidable collisions?
foreach bullet
	Get surrounding tiles
		If you can collide with them (Black tile, white tile, grey tile)
			Check if they are able to collide with you (Overlapping)
				Resolve Collision
			
foreach player
	if the tile you are on was changed by a bullet this frame die
	else Get surrounding tiles
		If you can collide with them (Black tile, white tile, grey tile)
			Check if they are able to collide with you (Overlapping)
				Resolve Collision
     */
	
    private Player[] playerArray;
    private Bullet[] bulletArray;
    public void runUpdate() {
    	

    	for (Player player : players) {
    		player.update();
    		if(player.fireBullet) {
    			player.fireBullet = false;
    			addBullet(new Bullet(player.direction, new Vector3d(player.position.x, player.position.y, player.position.z), player.teamId, player.shotIsFastShot));
    			if(player.direction == BulletDirection.Left || player.direction == BulletDirection.Right) {
    				addBullet(new Bullet(player.direction, new Vector3d(player.position.x, player.position.y + 1, player.position.z), player.teamId, player.shotIsFastShot));
    				addBullet(new Bullet(player.direction, new Vector3d(player.position.x, player.position.y - 1, player.position.z), player.teamId, player.shotIsFastShot));
    			}
    			else if(player.direction == BulletDirection.Up || player.direction == BulletDirection.Down) {
    				addBullet(new Bullet(player.direction, new Vector3d(player.position.x + 1, player.position.y, player.position.z), player.teamId, player.shotIsFastShot));
    				addBullet(new Bullet(player.direction, new Vector3d(player.position.x - 1, player.position.y, player.position.z), player.teamId, player.shotIsFastShot));
    			}
    		}
    	}
    	
    	playerArray = new Player[players.size()];
    	bulletArray = new Bullet[bullets.size()];
    	
    	int index = 0;
    	for (Player player : players) {
    		playerArray[index] = player;
			index++;
		}
    	
    	index = 0;
    	for (Bullet bullet : bullets) {
    		bullet.update();
			bulletArray[index] = bullet;
			index++;
		}
    	
    	TileState currentTile;
        Player currentPlayer;
        Bullet currentBullet;
        for (int x = 0; x < grid.getHeight() * grid.getWidth(); x++)
        {
            currentTile = grid.getTileAtLocation(x);
            Vector3d tilePos = new Vector3d(x % grid.getWidth() - grid.getWidth() / 2f, x / grid.getHeight() - grid.getHeight() / 2f, 0);
            for (int y = 0; y < playerArray.length; y++)
            {
                currentPlayer = playerArray[y];
                if (currentTile == TileState.Wall || currentTile == TileState.Empty || ((currentPlayer.teamId == 0 && currentTile == TileState.White) || currentPlayer.teamId == 1 && currentTile == TileState.Black))
                {
                    if (overlapPlayerTile(currentPlayer, tilePos))
                    {
                        double currentX = currentPlayer.position.x;
                        double currentY = currentPlayer.position.y;

                        currentPlayer.position.x = currentX;
                        currentPlayer.position.y = currentPlayer.lastPosition.y;
                        double xPos = resolveX(currentPlayer, tilePos);
                        currentPlayer.position.x -= xPos;

                        currentPlayer.position.y = currentY;
                        double yPos = resolveY(currentPlayer, tilePos);
                        currentPlayer.position.y -= yPos;
                        playerArray[y].position = currentPlayer.position;
                        playerArray[y].lastPosition = currentPlayer.position;
                    }
                }
            }
        }
        
        Vector3d tilePos;
        TileState tileState;
        Map<Vector3d, Bullet> bulletFilledTile = new HashMap<Vector3d, Bullet>();
        
        for (int y = 0; y < bulletArray.length; y++)
        {
            currentBullet = bulletArray[y];
            for (Vector3d tile : bulletFilledTile.keySet()) {
				if (overlapTileBullet(tile, currentBullet.position)) {
					Bullet otherBullet = bulletFilledTile.get(tile);
					if (otherBullet != currentBullet && currentBullet.teamId != otherBullet.teamId) {
						removeBullet(bulletFilledTile.get(tile));
						removeBullet(currentBullet);
						break;
					}
				}
            }
            
            bulletFilledTile.put(new Vector3d(Math.round(currentBullet.position.x), Math.round(currentBullet.position.y), 0), currentBullet);
            
            tilePos = new Vector3d(currentBullet.position.x + grid.getWidth() / 2f, currentBullet.position.y + grid.getHeight() / 2f, 0);
            
            tilePos.x = Math.round(tilePos.x);
            tilePos.y = Math.round(tilePos.y);
            
            
            if(tilePos.x < 0 || tilePos.x >= grid.getWidth() ||
            		tilePos.y < 0 || tilePos.y >= grid.getHeight()) {
            }
            else {
            	int location = (int)tilePos.x + (int)tilePos.y * grid.getWidth();
            	tileState = grid.getTileAtLocation(location);
            	tilePos = new Vector3d(location % grid.getWidth() - grid.getWidth() / 2f, location / grid.getHeight() - grid.getHeight() / 2f, 0);
            	
	            if ( ((currentBullet.teamId == 0 && tileState == TileState.White) || currentBullet.teamId == 1 && tileState == TileState.Black))
	            {
	                if (overlapTileBullet(tilePos, currentBullet.position))
	                {
	                	grid.modifyGrid(location, (tileState == TileState.Black) ? TileState.White : TileState.Black);
	                	grid.isDirty = true;
	                	
	                	for (Player player : playerArray) {
							if (player.teamId != currentBullet.teamId && overlapPlayerTile(player, new Vector3d(Math.round(currentBullet.position.x), Math.round(currentBullet.position.y), 0))) {
								removePlayer(player);
							}
						}
	                }
	            }
	            else if (tileState == TileState.Empty || tileState == TileState.Wall)
	            {
	                if (overlapTileBullet(tilePos, currentBullet.position))
	                {
	                    removeBullet(currentBullet);
	                }
	            }
	            
            }
        }
    }
	
	//Is a player and a tile overlapping?
    private static boolean overlapPlayerTile(Player player, Vector3d tile)
    {
        if (player.position.x + 0.25 < tile.x - .5) return false;
        if (player.position.x - 0.25 > tile.x + .5) return false;
        if (player.position.y + 0.25 < tile.y - .5) return false;
        if (player.position.y - 0.25 > tile.y + .5) return false;

        return true;
    }

    //Is a tile and a bullet overlapping?
	private static boolean overlapTileBullet(Vector3d tile, Vector3d bullet)
    {
        if (bullet.x > tile.x + .5) return false;
        if (bullet.x < tile.x - .5) return false;
        if (bullet.y > tile.y + .5) return false;
        if (bullet.y < tile.y - .5) return false;

        return true;
    }

    private static double resolveX(Player player, Vector3d tile)
    {
        double xOverlap1 = (player.position.x - 0.25) - (tile.x + 0.5);
        double xOverlap2 = (player.position.x + 0.25) - (tile.x - 0.5);

        double xOverlap = Math.abs(xOverlap1) < Math.abs(xOverlap2) ? xOverlap1 : xOverlap2;

        double yOverlap1 = (player.position.y - 0.25) - (tile.y + 0.5);
        double yOverlap2 = (player.position.y + 0.25) - (tile.y - 0.5);

        double yOverlap = Math.abs(yOverlap1) < Math.abs(yOverlap2) ? yOverlap1 : yOverlap2;

        return Math.abs(xOverlap) < Math.abs(yOverlap) ? xOverlap : 0;
    }

    private static double resolveY(Player player, Vector3d tile)
    {
        double xOverlap1 = (player.position.x - 0.25) - (tile.x + 0.5);
        double xOverlap2 = (player.position.x + 0.25) - (tile.x - 0.5);

        double xOverlap = Math.abs(xOverlap1) < Math.abs(xOverlap2) ? xOverlap1 : xOverlap2;

        double yOverlap1 = (player.position.y - 0.25) - (tile.y + 0.5);
        double yOverlap2 = (player.position.y + 0.25) - (tile.y - 0.5);

        double yOverlap = Math.abs(yOverlap1) < Math.abs(yOverlap2) ? yOverlap1 : yOverlap2;

        return Math.abs(xOverlap) > Math.abs(yOverlap) ? yOverlap : 0;
    }
    
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public void addBullet(Bullet bullet) {
		bullets.add(bullet);
	}
	
	public void removeBullet(Bullet bullet) {
		bullets.remove(bullet);
	}
	
	public void setup(Grid grid) {
		this.grid = grid;
	}
}
