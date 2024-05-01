package cds.team20.whiteboard.controller;

import cds.team20.whiteboard.entity.Figure;
import cds.team20.whiteboard.service.FigureService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);
    private List<WebSocketSession> sessionList = new ArrayList<>();
    private final FigureService figureService;
    @Autowired
    public WebSocketHandler(FigureService figureService) {
        this.figureService = figureService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("세션 [ {} ]  연결됨", session.getId());
        /*세션 저장*/
        sessionList.add(session);
//        super.afterConnectionEstablished(session);
//        String name = session.getHandshakeHeaders().get("name").get(0);
//        sessionList.add(session);
        session.sendMessage(new TextMessage("Hello World"));
        sessionList.forEach(s-> {
            try {
                s.sendMessage(new TextMessage(session.getId()+"님께서 입장하셨습니다."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("handleTextMessage is called...");
        log.info("payload {}", payload);
//        super.handleTextMessage(session, message);
//        Gson gson = new Gson();
//        Figure figure = gson.fromJson(message.getPayload(), Figure.class);
//        figureService.createFigure(figure);
//        session.sendMessage(new TextMessage("Hello World"));
        sessionList.forEach(s-> {
            try {
                s.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("{} 연결 끊김", session.getId());
        sessionList.remove(session);
//        super.afterConnectionClosed(session, status);
//        sessionList.remove(session);
//        String name = session.getHandshakeHeaders().get("name").get(0);
        sessionList.forEach(s-> {
            try {
                s.sendMessage(new TextMessage(session.getId()+"님께서 퇴장하셨습니다."));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
