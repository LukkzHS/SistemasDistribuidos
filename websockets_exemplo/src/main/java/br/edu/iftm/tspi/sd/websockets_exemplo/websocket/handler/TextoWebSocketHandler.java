package br.edu.iftm.tspi.sd.websockets_exemplo.websocket.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class TextoWebSocketHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private Thread messageThread;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Conexão estabelecida: " + session.getId());
        startMessageRoutine();
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Mensagem recebida: " + payload);
        session.sendMessage(new TextMessage("Mensagem recebida no servidor: " + payload));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("Conexão fechada: " + session.getId());
    }

    private void startMessageRoutine() {
        messageThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Thread.sleep(5000);
                    for (WebSocketSession session : sessions) {
                        session.sendMessage(new TextMessage("Alerta! (Mensagem automática a cada 5 segundos)"));
                    }
                }
            } catch (InterruptedException e) {
               
                Thread.currentThread().interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        messageThread.start(); 
    }
    
}