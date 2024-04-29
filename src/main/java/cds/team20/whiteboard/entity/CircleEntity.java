package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class CircleEntity extends Figure {
    private Point centerX;
    private Point centerY;
    private double radiusX;
    private double radiusY;
}
