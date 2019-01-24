package gameEngine.components;

import java.util.Dictionary;
import java.util.Hashtable;

import org.joml.Vector3d;

public class GameObject {
	public Transform transform;
	private Dictionary<Class<ComponentBase>, Object> components;
	
	public GameObject(Vector3d pos, Vector3d rot, Vector3d sca) {
		transform = new Transform(pos, rot, sca);
		components = new Hashtable<Class<ComponentBase>, Object>();
	}
	
	public Object getComponent(Class<ComponentBase> component) {
		return components.get(component);
	}
	
	public Boolean addComponent(Class<ComponentBase> componentType, Object obj) {
		if(components.get(componentType) == null) {
			
			components.put(componentType, obj);
			return true;
		}
		
		return false;
	}
}
