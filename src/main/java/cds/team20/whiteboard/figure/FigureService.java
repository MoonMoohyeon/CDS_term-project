package cds.team20.whiteboard.figure;

public interface FigureService {
    void createFigure();    //도형 생성해서 FigureRepository에 저장 후 모든 클라이언트에 도형 정보 전송
    Figure findFigure();
    void modifyFigure();    //기존 도형 삭제 후 다시 생성?
    void deleteFigure();
    //만들지 논의해봐야 함, 도형 수정을 삭제 후 다시 생성하도록 구현하려면 필요함, 삭제 시 FigureRepository에서 삭제 후 모든 클라이언트에 도형 정보 전송
}
