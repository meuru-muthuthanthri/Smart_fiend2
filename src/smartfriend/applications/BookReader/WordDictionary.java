/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.BookReader;

import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import smartfriend.util.general.MainConfiguration;

/**
 *
 * @author Keshani
 */
public class WordDictionary {

    private HashMap<String, Word> dictionaryMap;
    private static String filePath;

    public WordDictionary() {
        dictionaryMap = new HashMap<>();
        try {
            filePath = MainConfiguration.getCurrentDirectory() + "/resources/wordsMeaning.xml";

            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            //get the bookName
            String dictionary = doc.getDocumentElement().getNodeName();

            Node node = doc.getElementsByTagName("English").item(0);
            NodeList englishWords = node.getChildNodes();

            //loop through each and every child node
            for (int temp = 0; temp < englishWords.getLength(); temp++) {

                Node nNode = englishWords.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    // Create words by adding data from the XML
                    Word word = new Word();
                    word.setName(eElement.getAttribute("name"));
                    word.setMeaning(eElement.getAttribute("meaning"));
                    word.setFilePath(eElement.getAttribute("filePath"));
                    System.out.println(word);

                    // add word to HashMap
                    dictionaryMap.put(word.getName(), word);

                }
            }

        } catch (Exception e) {
        }

    }

    public HashMap<String, Word> getDictionary() {
        return dictionaryMap;
    }

    public static void main(String[] args) {

        new WordDictionary();
    }

    public Word getWord(String word) {
        return dictionaryMap.get(word);
    }
}
