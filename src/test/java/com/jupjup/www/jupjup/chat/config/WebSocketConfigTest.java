//package com.jupjup.www.jupjup.chat.config;
//
//import com.jupjup.www.jupjup.JupJupApplication;
//import com.jupjup.www.jupjup.chat.entity.Chat;
//import com.jupjup.www.jupjup.config.security.JWTUtil;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;#
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.messaging.converter.MappingJackson2MessageConverter;
//import org.springframework.messaging.simp.stomp.StompFrameHandler;
//import org.springframework.messaging.simp.stomp.StompHeaders;
//import org.springframework.messaging.simp.stomp.StompSession;
//import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.web.socket.WebSocketHttpHeaders;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.messaging.WebSocketStompClient;
//
//import java.lang.reflect.Type;
//import java.util.concurrent.*;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@TestPropertySource(locations = "classpath:application.yml")
//@ContextConfiguration(classes = JupJupApplication.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class WebSocketConfigTest {
//
//    @LocalServerPort
//    private int port;
//
//    private static final String WEBSOCKET_URL = "ws://localhost:%d/ws";
//    private static final String SUBSCRIBE_CHAT_TEMPLATE = "/sub/room/%d";
//    private static final String PUBLISH_MESSAGE_ENDPOINT_TEMPLATE = "/pub/room/%d/chat";
//
//    private final JWTUtil jwtUtil;
//
//    @Autowired
//    public WebSocketConfigTest(JWTUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    private static final Long userId = 1L;
//    private static final String userName = "name";
//    private static final String userEmail = "email@email.com";
//
//    private static final String token = JWTUtil.generateAccessToken(userId, userName, userEmail);
//
//    private static StompSession stompSession;
//
//
//    @BeforeEach
//    public void connectStompSession() throws ExecutionException, InterruptedException, TimeoutException {
//        WebSocketStompClient stompClient = new WebSocketStompClient(new StandardWebSocketClient());
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
//
//        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
//        handshakeHeaders.add("Authorization", "Bearer " + token);
//
//        StompHeaders connectHeaders = new StompHeaders();
//        connectHeaders.add("Authorization", "Bearer " + token);
//
//        stompSession = stompClient.connectAsync(
//                        String.format(WEBSOCKET_URL, port),
//                        handshakeHeaders, // http 핸드쉐이킹용
//                        connectHeaders, // websocket interceptor 용
//                        new StompSessionHandlerAdapter() {
//                        })
//                .get(180, TimeUnit.SECONDS);
//
//        assertThat(stompSession.isConnected()).isTrue();
//    }
//
//    @AfterEach
//    public void disconnectStompSession() {
//        if (stompSession.isConnected()) {
//            stompSession.disconnect();
//        }
//    }
//
//    @Test
//    public void subscribeTest() {
//        Long roomId = 1L;
//        BlockingQueue<Chat> blockingQueue = new LinkedBlockingQueue<>();
//
//        StompHeaders subHeaders = new StompHeaders();
//        subHeaders.setDestination(String.format(SUBSCRIBE_CHAT_TEMPLATE, roomId));
//        subHeaders.add("Authorization", "Bearer " + token);
//        stompSession.subscribe(subHeaders, new StompFrameHandler() {
//            @Override
//            public Type getPayloadType(StompHeaders headers) {
//                return Chat.class;
//            }
//
//            @Override
//            public void handleFrame(StompHeaders headers, Object payload) {
//                blockingQueue.offer((Chat) payload);
//            }
//        });
//    }
//
//    @Transactional
//    @Test
//    public void sendTest() {
//        Long roomId = 1L;
//        String content = "채팅 메시지입니다.";
//
//        Chat messageToSend = Chat.builder()
//                .content(content)
//                .build();
//
//        StompHeaders sendHeaders = new StompHeaders();
//        sendHeaders.add("Authorization", "Bearer " + token);
//        sendHeaders.setDestination(String.format(PUBLISH_MESSAGE_ENDPOINT_TEMPLATE, roomId));
//
//        stompSession.send(sendHeaders, messageToSend);
//    }
//
////    @DisplayName("websocket CONNECTION 이후 SUBSCRIBE 및 SEND, " +
////            "그리고 polling 을 통해 메시지가 정상적으로 왔는지 확인하는 채팅 연결의 전반적인 테스트입니다.")
////    @Test
////    public void webSocketChatFlowTest() throws Exception {
////        Long roomId = 1L;
////        String content = "채팅 메시지입니다.";
////        BlockingQueue<ChatDTO> blockingQueue = new LinkedBlockingQueue<>();
////
////        StompHeaders subHeaders = new StompHeaders();
////        subHeaders.setDestination(String.format(SUBSCRIBE_CHAT_TEMPLATE, roomId));
////        subHeaders.add("Authorization", "Bearer " + token);
////        stompSession.subscribe(subHeaders, new StompFrameHandler() {
////            @Override
////            public Type getPayloadType(StompHeaders headers) {
////                return ChatDTO.class;
////            }
////
////            @Override
////            public void handleFrame(StompHeaders headers, Object payload) {
////                System.out.println("Received message: " + payload); // 디버그 로그 추가
////                blockingQueue.offer((ChatDTO) payload);
////            }
////        });
////
////        // 구독 후 약간의 대기 시간 추가
////        Thread.sleep(3000);
////
////        ChatDTO messageToSend = ChatDTO.builder()
////                .content(content)
////                .build();
////
////        StompHeaders sendHeaders = new StompHeaders();
////        sendHeaders.add("Authorization", "Bearer " + token);
////        sendHeaders.setDestination(String.format(PUBLISH_MESSAGE_ENDPOINT_TEMPLATE, roomId));
////        stompSession.send(sendHeaders, messageToSend);
////
////        // TODO: 왜 안받아질까..
////        ChatDTO receivedMessage = blockingQueue.poll(5, TimeUnit.SECONDS);
////        assertThat(receivedMessage).isNotNull();
////        assertThat(receivedMessage.getContent()).isEqualTo(content);
////    }
//
//}