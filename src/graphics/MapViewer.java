package graphics;

import com.graphhopper.util.shapes.GHPoint3D;
import data.RoutingData;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.function.Consumer;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import java.awt.geom.Point2D;

public class MapViewer extends JXMapViewer {
    private List<RoutingData> routingData;
    
    public List<RoutingData> getRoutingData() {
        return routingData;
    }

    public void setRoutingData(List<RoutingData> routingData) {
        this.routingData = routingData;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (routingData != null && !routingData.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Path2D p2 = new Path2D.Double();
            first = true;
            for (RoutingData d : routingData) {
                draw(p2, d);
            }

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, new float[]{5.0f}, 0.0f));
            g2.draw(p2);
            g2.dispose();
        }
    }

    private boolean first = true;

    private void draw(Path2D p2, RoutingData d) {
        d.getPointList().forEach(new Consumer<GHPoint3D>() {
            @Override
            public void accept(GHPoint3D t) {
                Point2D point = convertGeoPositionToPoint(new GeoPosition(t.getLat(), t.getLon()));
                if (first) {
                    first = false;
                    p2.moveTo(point.getX(), point.getY());
                } else {
                    p2.lineTo(point.getX(), point.getY());
                }
            }
        });
    }
}
