package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class TextEntity extends Figure {
    private Point downX;
    private Point downY;
    private Point subY;
    private String text;
}
