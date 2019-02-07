package gameEngine.common;

import java.util.HashSet;

import gameEngine.components.scripts.*;
import gameEngine.debug.Debug;

public class UpdateHandler {
	private static HashSet<Script> scripts = new HashSet<Script>();
	private static HashSet<Script> scriptsToStart = new HashSet<Script>();
	private static HashSet<Script> scriptsToRemove = new HashSet<Script>();
	private static HashSet<Script> scriptsToAdd = new HashSet<Script>();
	
	private static HashSet<StaticScript> staticScripts = new HashSet<StaticScript>();
	private static HashSet<StaticScript> staticScriptsToStart = new HashSet<StaticScript>();
	private static HashSet<StaticScript> staticScriptsToRemove = new HashSet<StaticScript>();
	private static HashSet<StaticScript> staticScriptsToAdd = new HashSet<StaticScript>();
	
	public static double timeDelta;
	
	public static void RunUpdate(double timeD) {
		
		timeDelta = timeD;
		
		//Run start on component scripts
		for (Script script : scriptsToStart) {
			try {
				script.start();
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		
		scriptsToStart.clear();
		
		
		//Run start on static scripts
		for (StaticScript script : staticScriptsToStart) {
			try {
				script.start();
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		
		staticScriptsToStart.clear();
		
		
		//Run update on component scripts
		for (Script script : scripts) {
			try {
				script.update();
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		
		
		//Run update on static scripts
		for (StaticScript script : staticScripts) {
			try {
				script.update();	
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		
		//Run late update on component scripts
		for (Script script : scripts) {
			try {
				script.lateUpdate();
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		
		
		//Run update on static scripts
		for (StaticScript script : staticScripts) {
			try {
				script.lateUpdate();	
			} catch (Exception e) {
				Debug.logError(e);
			}
		}
		
		//Remove component scripts
		for (Script script : scriptsToRemove) {
			scripts.remove(script);
			scriptsToStart.remove(script);
		}
		
		
		//Remove static scripts
		for (StaticScript script : staticScriptsToRemove) {
			staticScripts.remove(script);
			staticScriptsToStart.remove(script);
		}
		
		
		//Add component scripts
		for (Script script : scriptsToAdd) {
			scripts.add(script);
			scriptsToStart.add(script);
		}
		
		scriptsToAdd.clear();
		
		
		//Add static scripts
		for (StaticScript script : staticScriptsToAdd) {
			staticScripts.add(script);
			staticScriptsToStart.add(script);
		}
		
		staticScriptsToAdd.clear();
	}
	
	public static void RegisterScript(Script script) {
		scriptsToAdd.add(script);
	}
	
	public static void UnregisterScript(Script script) {
		scriptsToRemove.add(script);
	}
	
	public static void RegisterScript(StaticScript script) {
		staticScriptsToAdd.add(script);
	}
	
	public static void UnregisterScript(StaticScript script) {
		staticScriptsToRemove.add(script);
	}
}
