package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class LineEntity extends Figure {
    private String id;
    private String lineColor;
    private String lineThickness;
    private Point startPoint;
    private Point endPoint;
}
