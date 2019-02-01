package gameEngine.components.essentials;

import java.util.*;

import org.joml.Vector3d;

import gameEngine.components.ComponentBase;
import gameEngine.debug.Debug;

public class GameObject {
	
	private static HashSet<GameObject> gameObjects = new HashSet<GameObject>();
	private static HashSet<GameObject> gmsToRemove = new HashSet<GameObject>();
	private static int nextID = 0;
	
	public Transform transform;
	public String name = "New GameObject";
	private int id;
	
	public int getId() { return id; }
	
	public Boolean equal(GameObject other) {
		return (id == other.getId());
	}
	
	public static GameObject find(int id) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject.id == id)
				return gameObject;
		}
		return null;
	}
	
	public static GameObject find(String name) {
		for (GameObject gameObject : gameObjects) {
			if(gameObject.name.equals(name))
				return gameObject;
		}
		
		return null;
	}
	
	public static void clearScene() {
		Collection<GameObject> gms = gameObjects;
		for (GameObject gameObject : gms) {
			gameObject.destroy();
		}
		clearOld();
	}
	
	@SuppressWarnings("rawtypes")
	private ComponentArray components = new ComponentArray<>();
	
	public GameObject(Vector3d pos, Vector3d rot, Vector3d sca) {
		transform = new Transform(pos, rot, sca);
		id = nextID;
		nextID++;
		gameObjects.add(this);
	}
	
	public <T extends ComponentBase> ComponentBase getComponent(Class<T> type) {
		return (ComponentBase) components.components.get(type);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends ComponentBase> ComponentBase addComponent(Class<T> type) {
		
		if(components.components.get(type) == null) {
			
			try {
				ComponentBase comp = (ComponentBase) type.newInstance();
				comp.gameObject = this;
				comp.setup();
				components.components.put(type, comp);
				return getComponent(type);
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		System.out.println("Tried to add component to GameObject which already had one! CHECK!");
		return null;
	}
	
	private class ComponentArray <T extends ComponentBase>{
		public HashMap<Class<T>, ComponentBase> components = new HashMap<Class<T>, ComponentBase>();;
	}
	
	public void destroy() {
		@SuppressWarnings("unchecked")
		Collection<ComponentBase> comp = components.components.values();
		for (ComponentBase component : comp) {
			component.destroy();
		}
		gmsToRemove.add(this);
	}
	
	public static void clearOld() {
		for (GameObject gameObject : gmsToRemove) {
			gameObjects.remove(gameObject);
			
		}
		gmsToRemove.clear();
	}
}
