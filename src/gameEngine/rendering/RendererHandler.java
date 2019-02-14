package gameEngine.rendering;

import static org.lwjgl.opengl.GL11.*;

import java.util.*;

import org.joml.*;
import org.joml.Math;
import org.lwjgl.opengl.*;

import gameEngine.rendering.data.RenderObjectInfo;
import gameEngine.rendering.data.meshData.*;
import gameEngine.rendering.shaders.*;
import gameEngine.common.Maths;
import gameEngine.components.rendering.Camera;
import gameEngine.debug.Debug;

public class RendererHandler {
	
	private Matrix4f projectionMatrix;
	private StaticShader shader;
	
	public RendererHandler(StaticShader shader) {
		this.shader = shader;
	}
	
	public void clear() {
		GL11.glClearColor(.25f, 0, 0.5f, 1);
		GL11.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void render(Map<TexturedMeshLowLevel, List<RenderObjectInfo>> entities, Camera camera) {
		
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		createProjectionMatrix(camera);
		
		shader.loadProjectionMatrix(projectionMatrix);
		
		for(TexturedMeshLowLevel mesh : entities.keySet()) {
			
			//if(mesh != null) {
				
				if(mesh.isDoubleSided()) {
					GL11.glDisable(GL11.GL_CULL_FACE);
				}
				
				prepareTexturedMesh(mesh);
				
				List<RenderObjectInfo> batch = entities.get(mesh);
				
				for(RenderObjectInfo entity : batch) {
					
					prepareInstance(entity, mesh.getTextureAtlasRows());
					
					GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getMesh().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
				}
				
				unbindTexturedModel();
				
				if(mesh.isDoubleSided()) {
					GL11.glEnable(GL11.GL_CULL_FACE);
				}
			//}
		}
	}
	
	private void prepareTexturedMesh(TexturedMeshLowLevel mesh) {
		GL30.glBindVertexArray(mesh.getMesh().getVaoID());
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		shader.loadAtlasRows(mesh.getTextureAtlasRows());
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, mesh.getTexture().getID());
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}
	
	private void prepareInstance(RenderObjectInfo entity, int textureAtlasRows) {
		
		shader.loadTransformationMatrix(Maths.createTransformationMatrix(entity.pos, entity.rot, entity.scale));
		
		shader.loadAtlasOffset(getAtlasOffset(textureAtlasRows, entity.textureAtlasLocation));
	}
	
	private Vector2d getAtlasOffset(int rows, int location) {
		int row = location / rows, collumn = location % rows;
		
		return new Vector2d(collumn / (double)rows, row / (double)rows);
	}
	
	private void createProjectionMatrix(Camera cam) {
		float width = (float) WindowManager.manager.getCurrentWidth(), height = (float) WindowManager.manager.getCurrentHeight();
		if(cam.orthographic == false) {
			projectionMatrix = new Matrix4f().perspective((float) Math.toRadians((float)cam.fov), width/height, (float)cam.nearPlane, (float)cam.farPlane);
		}
		else {
			projectionMatrix = new Matrix4f().ortho((float)-cam.orthographicSize, (float)cam.orthographicSize, (float)-cam.orthographicSize * height / width, (float) cam.orthographicSize * height / width, (float) cam.nearPlane, (float) cam.farPlane);
		}
	}
}
