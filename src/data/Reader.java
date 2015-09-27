package data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javafx.stage.FileChooser;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Reader {
	private static File inputFile;
	private static String sim;
	private static int globalIndex;
	private static Map<String, Integer> globalChars;
	private static List<Map<String, Integer>> data;

	public Reader() throws IOException {
		globalChars = new TreeMap<String, Integer>();
		data = new ArrayList<Map<String, Integer>>();
		openFile();
	}

	private boolean openFile() throws IOException {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		inputFile = fileChooser.showOpenDialog(null);
		if (inputFile == null) {
			return false;
		}
		System.out.println(inputFile.getName());
		parseXML();
		checkData();
		return true;
	}

	private void parseXML() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			String stringIndex = doc.getElementsByTagName("index").item(0).getTextContent();
			globalIndex = Integer.parseInt(stringIndex);

			NodeList globalCharList = doc.getElementsByTagName("chars");
			for (int i = 0; i < globalCharList.getLength(); i++) {
				Node globalCharNode = globalCharList.item(i);
				if (globalCharNode.getTextContent().length() == 0) {
					break;
				}
				Element globalCharElement = (Element) globalCharNode;
				Node stringGlobalCharName = globalCharElement.getElementsByTagName("name").item(0);
				Node stringGlobalCharValue = globalCharElement.getElementsByTagName("value").item(0);
				int globalCharValue = Integer.parseInt(stringGlobalCharValue.getTextContent());
				globalChars.put(stringGlobalCharName.getTextContent(), globalCharValue);
			}

			NodeList nList = doc.getElementsByTagName("square");
			for (int i = 0; i < nList.getLength(); i++) {
				Node square = nList.item(i);
				Element eElement = (Element) square;
				Node stringIndexNode = eElement.getElementsByTagName("index").item(0);
				int ind = Integer.parseInt(stringIndexNode.getTextContent());
				Map<String, Integer> squareMap = new TreeMap<String, Integer>();
				squareMap.put("index", ind);
				NodeList charList = eElement.getElementsByTagName("characteristic");
				for (int j = 0; j < charList.getLength(); j++) {
					Node charch = charList.item(j);
					putMap(squareMap, charch);
				}
				data.add(squareMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void putMap(Map<String, Integer> squareMap, Node charch) {
		Element charElement = (Element) charch;
		Node charName = charElement.getElementsByTagName("name").item(0);
		Node charValue = charElement.getElementsByTagName("value").item(0);
		int charValueInt = Integer.parseInt(charValue.getTextContent());
		squareMap.put(charName.getTextContent(), charValueInt);
	}

	private void checkData() throws IOException {
		if (globalChars.get("sim") < 0 || globalChars.get("sim") > 4) {
			throw new IOException();
		}
		if (globalChars.get("type") < 0 || globalChars.get("type") > 1) {
			throw new IOException();
		}
		if (globalChars.get("rows") * globalChars.get("cols") != data.size()) {
			throw new IOException();
		}
		return;
	}

	public String getSim() {
		return sim;
	}

	public int getSimNum() {
		return globalChars.get("sim");
	}

	public int getSize() {
		return globalIndex;
	}

	public Map<String, Integer> getGlobalChars() {
		return globalChars;
	}

	public Map<String, Integer> getCell(int index) {
		return data.get(index);
	}

	public List<Map<String, Integer>> getData() {
		return data;
	}
}