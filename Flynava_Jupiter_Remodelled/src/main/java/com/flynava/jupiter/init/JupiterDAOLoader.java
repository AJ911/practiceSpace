package com.flynava.jupiter.init;

import java.util.List;
import java.util.Map;

import com.mongodb.DBObject;

public class JupiterDAOLoader {

	private static Map<String, List<DBObject>> manualTrgrMap = null;
	private static boolean manualTrgrExists = false;

	private static Map<String, List<DBObject>> manualTrgrLYRMap = null;
	private static boolean manualTrgrLYRExists = false;

	public static Map<String, List<DBObject>> getManualTrgrMap() {
		return manualTrgrMap;
	}

	public static void setManualTrgrMap(Map<String, List<DBObject>> manualTrgrMap) {
		JupiterDAOLoader.manualTrgrMap = manualTrgrMap;
	}

	public static boolean isManualTrgrExists() {
		return manualTrgrExists;
	}

	public static void setManualTrgrExists(boolean manualTrgrExists) {
		JupiterDAOLoader.manualTrgrExists = manualTrgrExists;
	}

	public static Map<String, List<DBObject>> getManualTrgrLYRMap() {
		return manualTrgrLYRMap;
	}

	public static void setManualTrgrLYRMap(Map<String, List<DBObject>> manualTrgrLYRMap) {
		JupiterDAOLoader.manualTrgrLYRMap = manualTrgrLYRMap;
	}

	public static boolean isManualTrgrLYRExists() {
		return manualTrgrLYRExists;
	}

	public static void setManualTrgrLYRExists(boolean manualTrgrLYRExists) {
		JupiterDAOLoader.manualTrgrLYRExists = manualTrgrLYRExists;
	}

}
