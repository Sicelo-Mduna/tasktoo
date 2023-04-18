package sdp_task2;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class App {
    public static void main(String[] args) {
        try {
            // Specify the file path of the XML file to read
            File inputFile = new File("data.xml");

            // Create a SAXParserFactory object
            SAXParserFactory factory = SAXParserFactory.newInstance();

            // Create a SAXParser object
            SAXParser saxParser = factory.newSAXParser();

            // Create a ContentHandler object to handle SAX events
            CustomContentHandler handler = new CustomContentHandler();

            // Set user-selected fields to output
            Scanner scanner = new Scanner(System.in);
            String input = "";
            String[] fields = null;
            boolean validInput = false;
            while (!validInput) {
                System.out.println("Enter field names to output (comma-separated):");
                input = scanner.nextLine().trim();
                fields = input.split(",");
                validInput = true;
                for (String field : fields) {
                    if (field.isEmpty()) {
                        System.out.println("Invalid field name: " + field);
                        validInput = false;
                        break;
                    }
                }
            }
            handler.setFields(fields);

            // Parse the input file and handle SAX events
            saxParser.parse(inputFile, handler);

            // Get the map of selected fields and their values from the ContentHandler object
            Map<String, String> fieldValues = handler.getFieldValues();

            // Convert the map to a JSONObject and output it in JSON format
            JSONObject jsonObject = new JSONObject(fieldValues);
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class CustomContentHandler extends DefaultHandler {
        private Map<String, String> fieldValues;
        private String[] fields;
        private String currentField;
        private boolean isFieldSelected;

        public CustomContentHandler() {
            fieldValues = new HashMap<String, String>();
            fields = null;
            currentField = null;
            isFieldSelected = false;
        }

        public void setFields(String[] fields) {
            this.fields = fields;
        }

        public Map<String, String> getFieldValues() {
            return fieldValues;
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            currentField = qName;
            isFieldSelected = containsField(currentField);
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            if (isFieldSelected) {
                String value = new String(ch, start, length).trim();
                if (value.length() > 0) {
                    fieldValues.put(currentField, value);
                }
            }
        }

        private boolean containsField(String field) {
            if (fields == null) {
                return true;
            }
            for (String selectedField : fields) {
                if (selectedField.equals(field)) {
                    return true;
                }
            }
            return false;
        }
    }

    public Object getGreeting() {
        return null;
    }
}





