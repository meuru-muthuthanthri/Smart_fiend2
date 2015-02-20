/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.BookReader;

import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellChecker;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import smartfriend.util.general.MainConfiguration;
import com.swabunga.spell.engine.Word;

/**
 *
 * @author Keshani
 */


public class WordDictionary{

    private HashMap<String, WordObject> dictionaryMap;
    private static String filePath ;
    private SpellDictionaryHashMap dictionary = null;
    private  SpellChecker spellChecker = null;

    public WordDictionary() {
        dictionaryMap = new HashMap<>();        
        try {
             dictionary =new SpellDictionaryHashMap(new
                File(MainConfiguration.getCurrentDirectory()+"/resources/english.0"));
             
            spellChecker = new SpellChecker(dictionary);
            filePath = MainConfiguration.getCurrentDirectory()+"/resources/wordsMeaning.xml";
            
            File xmlFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc =  dBuilder.parse(xmlFile);

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
                    WordObject word = new WordObject();
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
   private  List getSuggestions(String word,
        int threshold) {

        return spellChecker.getSuggestions(word, threshold);
    }
    public HashMap<String, WordObject> getDictionary() {
        return dictionaryMap;
    }
    
    public static void main(String[] args) {
      //  new Dictionary();
    }
    
    public WordObject getWord(String word){
        return dictionaryMap.get(word);
    }

    public WordObject spellCorrectedWord(String term)
    {
        WordObject retunTerm=null;
        List<Word> list = getSuggestions(term, 3);
        for(Word word:list)
        {
            System.out.println(word.getWord());
           retunTerm= getWord(word.getWord());
           if(retunTerm!=null)
               break;
        }
        return retunTerm;   
    }
}
