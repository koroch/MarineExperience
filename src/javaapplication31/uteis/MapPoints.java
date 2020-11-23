/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication31.uteis;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author Dell
 */
public class MapPoints {
    public JXMapViewer RetornaPoints(){
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        //https://github.com/msteiger/jxmapviewer2/tree/master/examples/src
        JXMapViewer mapViewer = new JXMapViewer();
        
        // Create a TileFactoryInfo for OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        mapViewer.setTileFactory(tileFactory);
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
        
        // Use 8 threads in parallel to load the tiles
        tileFactory.setThreadPoolSize(8);

        // Set the focus
        GeoPosition litoral = new GeoPosition(-29.5,-49.4);
        GeoPosition frankfurt = new GeoPosition(-30.2,-49.9);
        GeoPosition wiesbaden = new GeoPosition(-29.7,-49.8);
        GeoPosition mainz     = new GeoPosition(-30,-49.62);
        GeoPosition darmstadt = new GeoPosition(-29.7,-48.8);
        GeoPosition offenbach   = new GeoPosition(-30,-49.65);

        // Set the focus
        mapViewer.setZoom(9);
        mapViewer.setAddressLocation(litoral);

        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
        
        Set<SwingWaypointYellow> waypoints = new HashSet<SwingWaypointYellow>(Arrays.asList(
                new SwingWaypointYellow("Litoral", 200.0, litoral),
                new SwingWaypointYellow("Frankfurt", 130.0, frankfurt),
                new SwingWaypointYellow("Wiesbaden", 15.0,wiesbaden),
                new SwingWaypointYellow("Mainz", 27.5, mainz),
                new SwingWaypointYellow("Darmstadt", 140.20, darmstadt),
                new SwingWaypointYellow("Offenbach", 201.0, offenbach)));

        // Set the overlay painter
        WaypointPainter<SwingWaypointYellow> swingWaypointPainter = new SwingWaypointOverlayPainter();
        swingWaypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(swingWaypointPainter);

        // Add the boats to the map viewer
        for (SwingWaypointYellow w : waypoints) {
            mapViewer.add(w.getLabel());
        }
        
        return mapViewer;
    }
}
