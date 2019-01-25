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
	private static Renderer renderer = new Renderer(shader);

	private static Map<TexturedMesh, List<RenderData>> entities = new HashMap<TexturedMesh, List<RenderData>>();
	private static List<MeshRenderer> meshes = new LinkedList<MeshRenderer>();
	
	public static void render(Light sun, Camera cam) {
		renderer.clear();
		shader.Start();
		shader.LoadLight(sun);
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
		List<RenderData> batch = entities.get(entityMesh);
		if (batch != null) {
			batch.add(new RenderData(rend.gameObject.transform));
		}
		else {
			batch = new ArrayList<RenderData>();
			batch.add(new RenderData(rend.gameObject.transform));
		}
		
		entities.put(entityMesh, batch);
	}
	
	public static void addRenderer(MeshRenderer rend) {
		meshes.add(rend);
	}
	
	public static void removeRenderer(MeshRenderer rend) {
		meshes.remove(rend);
	}
	
	public static void drawMesh(TexturedMesh entityMesh, Vector3d position, Vector3d rotation, Vector3d scale) {
		List<RenderData> batch = entities.get(entityMesh);
		if (batch != null) {
			batch.add(new RenderData(position, rotation, scale));
		}
		else {
			batch = new ArrayList<RenderData>();
			batch.add(new RenderData(position, rotation, scale));
		}
		
		entities.put(entityMesh, batch);
	}

	public static void clean() {
		entities.clear();
		shader.Clean();
	}

}
