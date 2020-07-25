package query;

import filters.Filter;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;

import javax.swing.*;
import java.awt.*;

import java.util.Observable;
import java.util.Observer;

import twitter4j.Status;

import twitter4j.User;
import ui.PrettyMapMarker;
import util.Util;


/**
 * A query over the twitter stream.
 * TO DO: Task 4: you are to complete this class.
 */
public class Query implements Observer {
    // The map on which to display markers when the query matches
    private final JMapViewer map;
    // Each query has its own "layer" so they can be turned on and off all at once
    private Layer layer;
    // The color of the outside area of the marker
    private final Color color;
    // The string representing the filter for this query
    private final String queryString;
    // The filter parsed from the queryString
    private final Filter filter;
    // The checkBox in the UI corresponding to this query (so we can turn it on and off and delete it)
    private JCheckBox checkBox;
    // The marker on the map
    //private MapMarkerSimple mapMarkerSimple;
    private PrettyMapMarker prettyMapMarker;
    public Util util;


    public Color getColor() { return color; }
    public String getQueryString() {
        return queryString;
    }
    public Filter getFilter() {
        return filter;
    }
    public Layer getLayer() { return layer; }
    public JCheckBox getCheckBox() {
        return checkBox;
    }
    public void setCheckBox(JCheckBox checkBox) {
        this.checkBox = checkBox;
    }
    public void setVisible(boolean visible) { layer.setVisible(visible);}
    public boolean getVisible() { return layer.isVisible(); }

    public Query(String queryString, Color color, JMapViewer map) {
        this.queryString = queryString;
        this.filter = Filter.parse(queryString);
        this.color = color;
        this.layer = new Layer(queryString);
        this.map = map;
    }

    @Override
    public String toString() {
        return "Query: " + queryString;
    }

    /**
     * This query is no longer interesting, so terminate it and remove all traces of its existence.
     *
     * TO DO: Implement this method
     */
    public void terminate() {
       setVisible(false);
       map.removeMapMarker(prettyMapMarker);
    }

    @Override
    public void update(Observable o, Object arg) {
        // get object from observable
        Status s = (Status) arg;
        // check whether the query matches a tweet
        Boolean matches = filter.matches(s);
        if(matches) {
            // get coordinate from from object
            Coordinate coord = util.statusCoordinate(s);
            // create a marker
            //mapMarkerSimple = new MapMarkerSimple(getLayer(), coord);
            User user = s.getUser();
            String profileImageURL = user.getProfileImageURL();
            String tweet = s.getText();
            prettyMapMarker = new PrettyMapMarker(getLayer(), coord, getColor(), profileImageURL, tweet);
            // add the marker on the map
            // map.addMapMarker(mapMarkerSimple);
            map.addMapMarker(prettyMapMarker);
        }
    }
}
