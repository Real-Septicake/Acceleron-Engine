package gameEngine.rendering.gui.text;

import java.awt.Window;
import java.rmi.server.LoaderHandler;
import java.util.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gameEngine.common.LowLevelLoader;
import gameEngine.common.Maths;
import gameEngine.components.rendering.MeshRenderer;
import gameEngine.rendering.WindowManager;
import gameEngine.rendering.data.RenderObjectInfo;
import gameEngine.rendering.data.meshData.TexturedMeshLowLevel;
import gameEngine.rendering.gui.GuiTexture;
import gameEngine.rendering.gui.text.data.*;

public class FontRenderer {

	private FontShader shader;
	private LowLevelLoader loader;
	
	private static Map<FontType, List<TextData>> textElements = new HashMap<FontType, List<TextData>>();

	public FontRenderer(LowLevelLoader loader) {
		shader = new FontShader();
		this.loader = loader;
	}
	
	public void renderText() {
	
		shader.Start();
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		double width = WindowManager.manager.getCurrentWidth(), height = WindowManager.manager.getCurrentHeight();
		
		for(FontType font : textElements.keySet()) {
			
			if(font != null) {
				
				prepareFont(font);
				
				List<TextData> batch = textElements.get(font);
				
				for(TextData text : batch) {
					
					prepareTextInstance(text, width, height);
					
					GL11.glDrawElements(GL11.GL_TRIANGLES, text.vertexCount, GL11.GL_UNSIGNED_INT, 0);
				}
				
				unbindTexturedModel();
				
			}
		}
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		textElements.clear();
		shader.Stop();
		
	}
	
	private void prepareFont(FontType font) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
	}
	
	private void prepareTextInstance(TextData text, double width, double height) {
		GL30.glBindVertexArray(text.meshId);
		
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		
		shader.loadTransformationMatrix(Maths.createTransformationMatrixGui(text.transform.position, text.transform.rotation, text.transform.size, width, height));
	}
	
	private void unbindTexturedModel() {
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		
		GL30.glBindVertexArray(0);
	}

	
	public static void addUIElement(GUIText toRender) {
		
		FontType fontType = toRender.getFont();
		
		List<TextData> batch = textElements.get(fontType);
		
		if (batch == null) {
			
			batch = new ArrayList<TextData>();
		}
		
		batch.add(new TextData(toRender.getMesh(), toRender.transform, toRender.getVertexCount()));
		
		textElements.put(fontType, batch);
	}
	
	public void cleanUp() {
		shader.clean();
	}
}
