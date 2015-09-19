package model;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.Window;

import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class Reader {
	private static File inputFile;
	private static String sim;
	private static int globalIndex;
	private static Map<String, Integer> globalChars = new TreeMap<String, Integer>();
	private static List<Map<String, Integer>> data = new ArrayList<Map<String, Integer>>();

	public Reader() {
		openFile();
	}

	
	public void openFile() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		inputFile = fileChooser.showOpenDialog(null);
		if (inputFile == null) {
			return;
		}
		System.out.println(inputFile.getName());

		System.out.println(inputFile.getName().split("-")[0]);

		sim = inputFile.getName().split("-")[0];
		parseXML();
	}

	private void parseXML() {
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

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
				//System.out.println("GlobalName: " + stringGlobalCharName.getTextContent() + ", GlobalValue: "
						//+ stringGlobalCharValue.getTextContent());
			}

			NodeList nList = doc.getElementsByTagName("square");
			for (int i = 0; i < nList.getLength(); i++) {
				Node square = nList.item(i);
				Element eElement = (Element) square;
				Node stringIndexNode = eElement.getElementsByTagName("index").item(0);

				int ind = Integer.parseInt(stringIndexNode.getTextContent());

				Map<String, Integer> squareMap = new TreeMap<String, Integer>();

				squareMap.put("index", ind);

				//System.out.println("Row: " + stringRow.getTextContent() + ", Col: " + stringCol.getTextContent());

				NodeList charList = eElement.getElementsByTagName("characteristic");
				for (int j = 0; j < charList.getLength(); j++) {
					Node charch = charList.item(j);
					Element charElement = (Element) charch;
					Node charName = charElement.getElementsByTagName("name").item(0);
					Node charValue = charElement.getElementsByTagName("value").item(0);

					int charValueInt = Integer.parseInt(charValue.getTextContent());

					squareMap.put(charName.getTextContent(), charValueInt);

					//System.out.println("CharName: " + charName.getTextContent());
					//System.out.println("CharValue: " + charValue.getTextContent());
				}
				data.add(squareMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSim() {
		return sim;
	}
	
	public int getSimNum() {
		if (sim.equals("game_of_life")) {
			return 0;
		}
		else if (sim.equals("spreading_fire")) {
			return 1;
		}
		else if (sim.equals("segregation")) {
			return 2;
		}
		else if (sim.equals("wa_tor")) {
			return 3;
		}
		return -1;
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