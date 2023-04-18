package sdp_task2;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

public class App {
    public static void main(String[] args) {
        try {
            // Specify the file path of the XML file to read
            File inputFile = new File("data.xml");

            // Create a DocumentBuilderFactory object
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

            // Create a DocumentBuilder object
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Use the DocumentBuilder to parse the input file and create a Document object
            Document doc = dBuilder.parse(inputFile);

            // Normalize the Document object to ensure that it is in a consistent state
            doc.getDocumentElement().normalize();

            // Get the root element of the XML document
            String rootElement = doc.getDocumentElement().getNodeName();
            System.out.println("Root element: " + rootElement);

            // Get a list of all the child nodes of the root element
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            // Loop through the child nodes and print out the field values
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    System.out.println(node.getNodeName() + ": " + node.getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

