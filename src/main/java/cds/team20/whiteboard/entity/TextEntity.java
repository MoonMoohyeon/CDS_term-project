package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class TextEntity extends Figure {
    private String id;
    private String msgColor;
    private String msgThickness;
    private Point startPoint;
    private Point endPoint;
    private String msg;
}
