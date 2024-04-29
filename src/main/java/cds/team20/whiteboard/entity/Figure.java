package cds.team20.whiteboard.entity;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
@Getter
@Setter
public abstract class Figure {
    private String id;
    private String lineWidth;
    private String strokeColor;
    private String fillColor;
}
