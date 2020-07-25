package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.*;

public class MapMarkerSimple extends MapMarkerCircle {
    public static final double DEFAULT_MARKER_SIZE = 5.0;
    public static final Color DEFAULT_COLOR = Color.red;

    public MapMarkerSimple(Layer layer, Coordinate coord) {
        super(layer, null, coord, DEFAULT_MARKER_SIZE, STYLE.FIXED, getDefaultStyle());
        setColor(Color.BLACK);
        setBackColor(DEFAULT_COLOR);
    }
}
