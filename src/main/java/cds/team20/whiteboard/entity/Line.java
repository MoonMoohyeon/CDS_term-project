package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Line extends Figure {
    private String id;
    private Type type;
    private String lineColor;
    private String lineThickness;
    private String fillColor;
    private Point startPoint;
    private Point endPoint;
    private String msg;
}
