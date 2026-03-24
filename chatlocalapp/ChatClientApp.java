package com.cmc.chatlocalapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*; // Sử dụng Control của JavaFX (TextArea, TextField)
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.websocket.*;
import java.net.URI;

@ClientEndpoint
public class ChatClientApp extends Application {
    private Session session;
    // Đảm bảo dùng TextArea và TextField của javafx.scene.control
    private TextArea txtChat = new TextArea();
    private TextField txtInput = new TextField();

    @Override
    public void start(Stage stage) throws Exception {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            // Kết nối tới Server (Phải chạy ChatServer trước khi mở cái này)
            container.connectToServer(this, new URI("ws://localhost:8081/ws/chat"));
        } catch (Exception e) {
            txtChat.appendText("Lỗi: Không thể kết nối Server! (Bạn đã chạy ChatServer chưa?)\n");
        }

        txtChat.setEditable(false);
        txtInput.setPromptText("Nhập tin nhắn và Enter...");
        
        // Sự kiện khi nhấn Enter trên bàn phím
        txtInput.setOnAction(e -> {
            if (session != null && session.isOpen()) {
                try {
                    String message = txtInput.getText();
                    if (!message.isEmpty()) {
                        session.getBasicRemote().sendText("Bạn: " + message);
                        txtInput.clear();
                    }
                } catch (Exception ex) { ex.printStackTrace(); }
            }
        });

        // Sắp xếp giao diện
        VBox root = new VBox(10, new Label("Cửa sổ Chat Local:"), txtChat, txtInput);
        root.setStyle("-fx-padding: 10;");
        
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Chat Client");
        stage.show();
    }

    @OnOpen 
    public void onOpen(Session s) { 
        this.session = s; 
    }

    @OnMessage 
    public void onMessage(String msg) {
        // Platform.runLater giúp cập nhật giao diện từ luồng WebSocket
        Platform.runLater(() -> txtChat.appendText(msg + "\n"));
    }

    public static void main(String[] args) { 
        launch(args); 
    }
}