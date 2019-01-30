package gameEngine.rendering.gui;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.io.*;
import java.util.*;

import org.joml.*;

import gameEngine.debug.Debug;

public class FontHandler {
	
	public Font loadedFont;
	
	public FontHandler(String filePath) {
		InputStream myStream;
		
		try {
			myStream = new BufferedInputStream(new FileInputStream("font.ttf"));
		} catch (FileNotFoundException e1) {
			Debug.logError(e1.toString());
			return;
		}
		
		try {
			loadedFont = Font.createFont(Font.TRUETYPE_FONT, myStream);
		} catch (FontFormatException e) {
			Debug.logError(e.toString());
		} catch (IOException e) {
			Debug.logError(e.toString());
		}
	}
	
	public Vector3d[] getCharacterMesh(char character) {
		
		GlyphVector vector = loadedFont.createGlyphVector(new FontRenderContext(null, null, null), "a");
		
		PathIterator iterator = vector.getGlyphOutline(0).getPathIterator(null);
		
		Queue<Vector3d> pointsQueue = new LinkedList<Vector3d>();
		
		
		while (!iterator.isDone()) {
			double[] coords = new double[6];
			int type = iterator.currentSegment(coords);
			if(type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO) {
				pointsQueue.add(new Vector3d(coords[0], coords[1], 0));
			}
			
			iterator.next();
		}
		
		return (Vector3d[]) pointsQueue.toArray();
	}
}
