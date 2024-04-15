package cds.team20.whiteboard.figure;

public interface FigureRepository {
    void save(Figure figure);
    Figure findById(String figureId);
    void delete(Figure figure);
}
