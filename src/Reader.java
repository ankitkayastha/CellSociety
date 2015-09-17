import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class Reader {
   public static void main(String[] args){

      try {	
         File inputFile = new File("game_of_life-5-5.xml");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();
         System.out.println("Root element: " 
            + doc.getDocumentElement().getNodeName());
        
         String rows = doc.getElementsByTagName("rows").item(0).getTextContent();
         String cols = doc.getElementsByTagName("cols").item(0).getTextContent();
         
         System.out.println("Rows: "+rows);
         System.out.println("Cols: "+cols);
         
         NodeList nList = doc.getElementsByTagName("square");
         for (int i=0; i<nList.getLength(); i++) {
        	 Node square = nList.item(i);
        	 Element eElement = (Element) square;
        	 Node row = eElement.getElementsByTagName("row").item(0);
        	 Node col = eElement.getElementsByTagName("col").item(0);
        	 NodeList charList = eElement.getElementsByTagName("characteristic");
        	 for (int j=0; j<charList.getLength(); j++) {
        		 Node charch = charList.item(j);
        		 Element charElement = (Element) charch;
        		 Node charName = charElement.getElementsByTagName("name").item(0);
        		 Node charValue = charElement.getElementsByTagName("value").item(0);
        		 System.out.println("CharName: "+charName.getTextContent());
        		 System.out.println("CharValue: "+charValue.getTextContent());
        	 }
        	 
        	 System.out.println("Row: "+row.getTextContent() +", Col: "+col.getTextContent());
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}