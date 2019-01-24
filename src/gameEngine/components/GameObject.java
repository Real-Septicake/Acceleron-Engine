package gameEngine.components;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import org.joml.Vector3d;

public class GameObject {
	public Transform transform;
	private HashMap<ComponentBase, ComponentBase> components;
	
	public GameObject(Vector3d pos, Vector3d rot, Vector3d sca) {
		transform = new Transform(pos, rot, sca);
		components = new HashMap<ComponentBase, ComponentBase>();
	}
	
	public <T> ComponentBase getComponent(T type) {
		/*
		if(type instanceof ComponentBase == false) {
			throw new Exception("When getting a component, it must extend ComponentBase!");
		}*/
		return components.get(type);
	}
	
	public <T> ComponentBase addComponent(T type) {
		/*
		if(type instanceof ComponentBase == false) {
			throw new Exception("When adding a component, it must extend ComponentBase!");
		}*/
		
		if(components.get(type) == null) {
			
			try {
				return components.put((ComponentBase) type.getClass().cast(ComponentBase.class), (ComponentBase)type.getClass().newInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Tried to add component to GameObject which already had one! CHECK!");
		return null;
	}
}
