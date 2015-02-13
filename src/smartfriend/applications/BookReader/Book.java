/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.Applications.BookReader;

import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author Keshani
 */
public class Book {

    private HashMap markerIDMap;// markerID and resource map of the book
    private String bkName;

    public Book() {
        markerIDMap = new HashMap();
    }

    public void setBkName(String bkName) {
        this.bkName = bkName;
    }

    public String getBkName() {
        return bkName;
    }

    public void setMarkerIDMap(HashMap markerIDMap) {
        this.markerIDMap = markerIDMap;
    }

    public HashMap getMarkerIDMap() {
        return markerIDMap;
    }

    /* Read the XML file and retrive marker details
     * and create HAshMap including those data
     */
    public void createBooKMarkerList(String pathToXML) {
        try {
            File xmlFile = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            //get the bookName
            String bookName = doc.getDocumentElement().getNodeName();

            //get the all nodes which are listed under the element called "ARMarker"
            Node node = doc.getElementsByTagName("ARMarkers").item(0);
            NodeList ARMarkers = node.getChildNodes();

            //loop through each and every child node
            for (int temp = 0; temp < ARMarkers.getLength(); temp++) {

                Node nNode = ARMarkers.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    // Create ARMarker by adding data from the XML
                    ARMarker marker = new ARMarker();
                    marker.setPageNumber(Integer.parseInt(eElement.getAttribute("pageNumber")));
                    marker.setMarkerID(Integer.parseInt(eElement.getAttribute("id")));
                    marker.setVideoPath(eElement.getAttribute("path"));
                    System.out.println("hi");
                    System.out.println(eElement.getAttribute("id"));

                    // add marker to HashMap
                    markerIDMap.put(marker.getMarkerID(), marker);

                }
            }

        } catch (Exception e) {
        }


    }
}
