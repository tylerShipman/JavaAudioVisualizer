/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package audioviz;

import static java.lang.Integer.min;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 *
 * @author TylerShipman
 */
public class TwswwdFireworks implements Visualizer{
    
    private final String name = "Twswwd";
    
    private Integer numBands;
    private AnchorPane vizPane;
    
    private final Double bandHeightPercentage = 1.3;
    private final Double minEllipseRadius = 10.0;  // 10.0
    
    private Double width = 0.0;
    private Double height = 0.0;
    
    private Double bandWidth = 0.0;
    private Double bandHeight = 0.0;
    private Double halfBandHeight = 0.0;
    
    private final Double startHue = 260.0;
    
    private Line[] lines;

    @Override
    public void start(Integer numBands, AnchorPane vizPane) {
        end();
        this.numBands = numBands;
        this.vizPane = vizPane;
        
        height = vizPane.getHeight();
        width = vizPane.getWidth();
        
        bandWidth = width / numBands;
        bandHeight = height * bandHeightPercentage;
        halfBandHeight = bandHeight / 2;
        lines = new Line[numBands];
        
        for (int i = 0; i < numBands; i++) {
            Line line = new Line();
            //line.setLayoutX(width/2);
            //line.setLayoutY(height);
            //line.setStartX(width/2);
            //line.setStartY(height/1.2);
            
            line.setLayoutX(width/2 - line.getLayoutBounds().getMinX());
            line.setLayoutY(height/1.2 - line.getLayoutBounds().getMinX());
            
            line.setRotate(5*i);
            
            if(numBands == 1|| numBands == 2){
              line.setStrokeWidth(80);  
            }
            else{
                line.setStrokeWidth(bandWidth/2);
            }
            
            //line.setStrokeWidth(bandWidth/2);
            line.setStartY(minEllipseRadius);
            vizPane.getChildren().add(line);
            lines[i] = line;
        
    }
    }

    @Override
    public void end() {
if (lines != null) {
             for (Line line : lines) {
                 vizPane.getChildren().remove(line);
             }
            lines = null;
        }     }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void update(double timestamp, double duration, float[] magnitudes, float[] phases) {
        if (lines == null) {
            return;
        }
        
        Integer num = min(lines.length, magnitudes.length);
        
        for (int i = 0; i < num; i++) {
            lines[i].setEndY( -((60.0 + magnitudes[i])/60.0) * halfBandHeight*2 + minEllipseRadius);
            lines[i].setStroke(Color.hsb(startHue - (magnitudes[i] * -6.0), 1.0, 1.0, 1.0));
            lines[i].setRotate(25*i);
        }
    }       
    
}
