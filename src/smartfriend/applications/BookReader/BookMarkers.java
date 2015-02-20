/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.BookReader;

import java.util.HashMap;

/**
 *
 * @author Keshani
 */
public class BookMarkers {
 
   public enum markerType{
    
    ARMarker,
    QRMarker
    
    }
 
    private markerType type;
    private int pageNumber;

    public int getPageNumber() {
        return pageNumber;
    }

    public markerType getType() {
        return type;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setType(markerType type) {
        this.type = type;
    }
    
    
    
    
    
}
