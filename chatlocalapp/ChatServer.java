package com.cmc.chatlocalapp;

import java.util.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import org.glassfish.tyrus.server.Server;

@ServerEndpoint("/chat")
public class ChatServer {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session s) { sessions.add(s); }

    @OnMessage
    public void onMessage(String msg, Session s) throws Exception {
        for (Session session : sessions) {
            if (session.isOpen()) session.getBasicRemote().sendText(msg);
        }
    }

    @OnClose
    public void onClose(Session s) { sessions.remove(s); }

    public static void main(String[] args) {
        // CÁCH KHỞI TẠO CHUẨN: 
        // 1. Host, 2. Port, 3. ContextPath, 4. Cấu hình Map (để trống), 5. Các class Endpoint
        Map<String, Object> properties = new HashMap<>();
        Server server = new Server("localhost", 8081, "/ws", properties, ChatServer.class);

        try {
            server.start();
            System.out.println("--- SERVER CHAT ĐANG CHẠY TẠI: ws://localhost:8081/ws/chat ---");
            System.out.println("Vui lòng không tắt cửa sổ này!");
            new Scanner(System.in).nextLine(); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}