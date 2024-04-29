package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class LineEntity extends Figure {
    private Point upX;
    private Point upY;
    private Point downX;
    private Point downY;
}
