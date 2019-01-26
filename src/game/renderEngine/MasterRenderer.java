package game.renderEngine;

import java.util.*;

import org.joml.Vector3d;

import game.models.*;
import gameEngine.rendering.shaders.*;
import gameEngine.components.Camera;
import gameEngine.components.Light;
import gameEngine.components.MeshRenderer;

public class MasterRenderer {
	private static StaticShader shader = new StaticShader();
	private static RendererHandler renderer = new RendererHandler(shader);

	private static Map<TexturedMesh, List<RenderObjectInfo>> entities = new HashMap<TexturedMesh, List<RenderObjectInfo>>();
	private static List<MeshRenderer> meshes = new LinkedList<MeshRenderer>();
	
	private static List<Light> lights = new LinkedList<Light>();
	
	public static void render(Camera cam) {
		renderer.clear();
		shader.Start();
		shader.LoadLight(lights);
		shader.LoadViewMatrix(cam);
		for (MeshRenderer meshRenderer : meshes) {
			addEntity(meshRenderer);
		}
		renderer.render(entities, cam);
		shader.Stop();
		entities.clear();
	}
	
	private static void addEntity(MeshRenderer rend) {
		TexturedMesh entityMesh = rend.mesh;
		List<RenderObjectInfo> batch = entities.get(entityMesh);
		if (batch != null) {
			batch.add(new RenderObjectInfo(rend.gameObject.transform));
		}
		else {
			batch = new ArrayList<RenderObjectInfo>();
			batch.add(new RenderObjectInfo(rend.gameObject.transform));
		}
		
		entities.put(entityMesh, batch);
	}
	
	public static void addRenderer(MeshRenderer rend) {
		meshes.add(rend);
	}
	
	public static void removeRenderer(MeshRenderer rend) {
		meshes.remove(rend);
	}
	
	public static void addLight(Light light) {
		lights.add(light);
	}
	
	public static void removeLight(Light light) {
		lights.remove(light);
	}
	
	public static void drawMesh(TexturedMesh entityMesh, Vector3d position, Vector3d rotation, Vector3d scale) {
		List<RenderObjectInfo> batch = entities.get(entityMesh);
		if (batch != null) {
			batch.add(new RenderObjectInfo(position, rotation, scale));
		}
		else {
			batch = new ArrayList<RenderObjectInfo>();
			batch.add(new RenderObjectInfo(position, rotation, scale));
		}
		
		entities.put(entityMesh, batch);
	}

	public static void clean() {
		entities.clear();
		shader.Clean();
	}

}
