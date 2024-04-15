package cds.team20.whiteboard.figure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FigureServiceImpl implements FigureService{

    private final FigureRepository figureRepository;

    @Autowired
    public FigureServiceImpl(FigureRepository figureRepository) {
        this.figureRepository = figureRepository;
    }

    @Override
    public void createFigure(Figure figure) {
        figureRepository.save(figure);
    }

    @Override
    public Figure findFigure(String id) {
        return figureRepository.findById(id);
    }

    @Override
    public void modifyFigure(String id) {
        //미구현
    }

    @Override
    public void deleteFigure(String id) {
        figureRepository.delete(figureRepository.findById(id));
    }
}
