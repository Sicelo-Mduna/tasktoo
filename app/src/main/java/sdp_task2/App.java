package sdp_task2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
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

            // Get a list of all the child nodes of the root element
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            try (// Get user-selected fields to output
            Scanner scanner = new Scanner(System.in)) {
                String input = "";
                String[] fields = null;
                boolean validInput = false;
                while (!validInput) {
                    System.out.println("Enter field names to output (comma-separated):");
                    input = scanner.nextLine().trim();
                    fields = input.split(",");
                    validInput = true;
                    for (String field : fields) {
                        if (!nodeListContainsField(nodeList, field.trim())) {
                            System.out.println("Invalid field name: " + field);
                            validInput = false;
                            break;
                        }
                    }
                }

                // Create a map to store the selected fields and their values
                Map<String, String> fieldValues = new HashMap<String, String>();

                // Loop through the child nodes and add the selected fields to the map
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        String nodeName = node.getNodeName();
                        for (String field : fields) {
                            if (nodeName.equals(field.trim())) {
                                fieldValues.put(nodeName, node.getTextContent());
                                break;
                            }
                        }
                    }
                }

                // Convert the map to a JSONObject and output it in JSON format
                JSONObject jsonObject = new JSONObject(fieldValues);
                System.out.println(jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean nodeListContainsField(NodeList nodeList, String field) {
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(field)) {
                return true;
            }
        }
        return false;
    }

    public Object getGreeting() {
        return null;
    }
}




