package cds.team20.whiteboard.figure;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
@Getter
@Setter
public class Figure {
    private String id;
    private Type type;
    private String lineColor;
    private String lineThickness;
    private String fillColor;
    private Point startPoint;
    private Point endPoint;
    private String msg;
}
