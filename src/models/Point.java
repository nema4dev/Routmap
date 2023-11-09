package models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import components.ButtonWaypoint;
import events.WayPointEvent;


public class Point extends DefaultWaypoint{
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PointType getPointType() {
        return pointType;
    }

    public void setPointType(PointType pointType) {
        this.pointType = pointType;
    }
    
    public JButton getButton() {
        return button;
    }

    public void setButton(JButton button) {
        this.button = button;
    }

    public Point(String name, PointType pointType, WayPointEvent event, GeoPosition coord) {
        super(coord);
        this.name = name;
        this.pointType = pointType;
        initButton(event);
    }

    public Point() {
    }

    private String name;
    private JButton button;
    private PointType pointType;

    private void initButton(WayPointEvent event) {
        button = new ButtonWaypoint();
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                event.selected(Point.this);
            }
        });
    }

    public static enum PointType {
        START,TRACE, END
    }
    
}
