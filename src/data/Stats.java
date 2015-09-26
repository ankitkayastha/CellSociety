package data;

import java.util.List;
import java.util.Map;

public class Stats {
	private Map<String, Integer> globalChars;
	private List<Map<String, Integer>> data;
	
	public Stats(Reader myReader) {
		this.globalChars = myReader.getGlobalChars();
		this.data = myReader.getData();
	}
	
	public int getSize() {
		return data.size();
	}

	public Map<String, Integer> getGlobalChars() {
		return globalChars;
	}

	public Map<String, Integer> getCellData(int index) {
		return data.get(index);
	}

	public List<Map<String, Integer>> getData() {
		return data;
	}
	
	private void updateStats(String key, int value) {
		globalChars.put(key, value);
	}
	
	public void flipType() {
		globalChars.put("type", 1- globalChars.get("type"));
	}
	
	public void putDimensions(int height, int width) {
		globalChars.put("height", height);
		globalChars.put("width",  width);
	}
}
