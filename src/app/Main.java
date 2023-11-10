package app;

import data.ExtractData;
import data.RoutingData;
import graphics.WayPointRender;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import models.Place;
import models.Point;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import events.WayPointEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import services.RoutingService;

public class Main extends JFrame {

    private final ExtractData extractData;
    private final Set<Point> routePoints = new HashSet<>();
    private List<RoutingData> routingData = new ArrayList<>();
    private WayPointEvent event;

    public Main() {
        extractData = new ExtractData("src/content/data_knowledge.pl");
        initComponents();
        init();
        setTitle("RoutMap");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(new ImageIcon("src/assets/start.png").getImage());
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
    }
    
    private void init() {
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        jXMapViewer.setTileFactory(tileFactory);
        GeoPosition geo = new GeoPosition(13.667523251716391, -89.21138150483259);
        jXMapViewer.setAddressLocation(geo);
        jXMapViewer.setZoom(2);

        MouseInputListener mm = new PanMouseInputListener(jXMapViewer);
        jXMapViewer.addMouseListener(mm);
        jXMapViewer.addMouseMotionListener(mm);
        jXMapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(jXMapViewer));
        event = getEvent();

        DefaultComboBoxModel<String> originComboBoxModel = new DefaultComboBoxModel<>();
        DefaultComboBoxModel<String> destinationComboBoxModel = new DefaultComboBoxModel<>();

        List<String> placesList = extractData.getPlaces();
        for (String place : placesList) {
            originComboBoxModel.addElement(place.replace("_", " "));
            destinationComboBoxModel.addElement(place.replace("_", " "));
        }

        originBox.setModel(originComboBoxModel);
        destinationBox.setModel(destinationComboBoxModel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origin = (String) originBox.getSelectedItem();
                String destination = (String) destinationBox.getSelectedItem();

                List<String> route = extractData.getRoute(origin.replace(" ", "_"), destination.replace(" ", "_"));                
                
                if (origin.equals(destination)) {
                    clearWaypoint();
                    JOptionPane.showMessageDialog(Main.this, "El origen y el destino no pueden ser iguales.");
                } 
                else {
                    if (route.isEmpty()) {
                        clearWaypoint();
                        JOptionPane.showMessageDialog(Main.this, "No se encontró una ruta entre los lugares seleccionados.");
                    } else {  
                        clearWaypoint();
                        for (int i = 0; i < route.size(); i++) {
                            Place place = extractData.getPlace(route.get(i).replace(" ", "_"));
                            
                            Point.PointType pointType;
                            if (i == 0) {
                                pointType = Point.PointType.START;
                            } else if (i == route.size() - 1) {
                                pointType = Point.PointType.END;
                            } else {
                                pointType = Point.PointType.TRACE; 
                            }
                            
                            Point waypoint = new Point(
                                place.getName().replace("_", " "),
                                pointType,
                                event,
                                new GeoPosition(place.getLatitude(), place.getLongitude())
                            );
                            
                            addRoutePoint(waypoint);
                        }
                    }
                }
            }
        });
    }
      
    private void initWaypoint() {
        WaypointPainter<Point> wp = new WayPointRender();
        jXMapViewer.setOverlayPainter(wp);
        wp.setWaypoints(routePoints);
        
        for (Point routePoint : routePoints) {
            jXMapViewer.add(routePoint.getButton());
        }
       
        if (!routePoints.isEmpty()) {
            GeoPosition start = null;
            GeoPosition end = null;
            for (Point w : routePoints) {
                if (w.getPointType() == Point.PointType.START) {
                    start = w.getPosition();
                } else if (w.getPointType() == Point.PointType.END) {
                    end = w.getPosition();
                }
            }
            if (start != null && end != null) {
                routingData = RoutingService.getInstance().routing(start.getLatitude(), start.getLongitude(), end.getLatitude(), end.getLongitude());
            } else {
                routingData.clear();
            }
            jXMapViewer.setRoutingData(routingData);
        }
    }

    private void addRoutePoint(Point waypoint) {
        for (Point d : routePoints) {
            jXMapViewer.remove(d.getButton());
        }
        routePoints.add(waypoint);
        initWaypoint();
    }

    private void clearWaypoint() {
        for (Point d : routePoints) {
            jXMapViewer.remove(d.getButton());
        }
        routingData.clear();
        routePoints.clear();
        initWaypoint();
    }
    
    private WayPointEvent getEvent() {
        return new WayPointEvent() {
            @Override
            public void selected(Point waypoint) {
                JOptionPane.showMessageDialog(Main.this, waypoint.getName());
            }
        };
    }
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jXMapViewer = new graphics.MapViewer();
        jLabel1 = new javax.swing.JLabel();
        originBox = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        destinationBox = new javax.swing.JComboBox<>();
        searchButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("ORIGIN:");

        originBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        originBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                originBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("DESTINATION:");

        destinationBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        destinationBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                destinationBoxActionPerformed(evt);
            }
        });

        searchButton.setText("BUSCAR");

        javax.swing.GroupLayout jXMapViewerLayout = new javax.swing.GroupLayout(jXMapViewer);
        jXMapViewer.setLayout(jXMapViewerLayout);
        jXMapViewerLayout.setHorizontalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(originBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(destinationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 543, Short.MAX_VALUE)
                .addComponent(searchButton)
                .addGap(17, 17, 17))
        );
        jXMapViewerLayout.setVerticalGroup(
            jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jXMapViewerLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jXMapViewerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(originBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(destinationBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton))
                .addContainerGap(581, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jXMapViewer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void destinationBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_destinationBoxActionPerformed
    }//GEN-LAST:event_destinationBoxActionPerformed

    private void originBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_originBoxActionPerformed
    }//GEN-LAST:event_originBoxActionPerformed
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> destinationBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private graphics.MapViewer jXMapViewer;
    private javax.swing.JComboBox<String> originBox;
    private javax.swing.JButton searchButton;
    // End of variables declaration//GEN-END:variables
}