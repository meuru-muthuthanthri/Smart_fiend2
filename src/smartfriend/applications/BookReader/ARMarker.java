/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smartfriend.applications.BookReader;

import smartfriend.applications.BookReader.aruco.Marker;

/**
 * ARMarker class
 * @author Keshani
 */

public class ARMarker extends BookMarkers{
    
    private Marker marker;
    private String videoPath;
    private int markerID;
    
    public void setMarkerID(int id)
    {
        markerID=id;
    }
    public void setVideoPath(String path)
    {
        videoPath=path;
    }
    
    public void setMarker(Marker ARMarker)
    {
        marker=ARMarker;
    }
    
    public int getMarkerID()
    {
        return markerID;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Marker getMarker() {
        return marker;
    }
   
}
