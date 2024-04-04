package cds.team20.whiteboard.figure;

import cds.team20.whiteboard.client.Client;

public interface FigureRepository {
    void save(Figure figure);
    Figure findById(String figureId);
    void delete(Figure figure);
}
