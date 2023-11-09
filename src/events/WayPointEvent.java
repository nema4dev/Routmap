package events;

import models.Point;


public interface WayPointEvent {
    public void selected(Point waypoint);
}
