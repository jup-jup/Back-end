package com.jupjup.www.jupjup.chat.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
    목적 및 사용 사례:
        • WebSocketMessageBrokerConfigurer 는 Spring 에서 STOMP(Simple Text Oriented Messaging Protocol) 프로토콜을 사용하여
            WebSocket 기반의 메시지 브로커를 구성하는 데 사용됩니다.
        • 이 방식은 채팅 애플리케이션, 실시간 알림, 다자간 메시지 전달 등과 같은 복잡한 메시징 패턴을 필요로 하는 경우에 적합합니다.
        • 메시지 브로커를 통해 클라이언트는 주제(/topic)나 대기열(queue)에 메시지를 보내거나 구독할 수 있습니다.
        • 주로 Pub/Sub 모델을 구현하거나 브로드캐스팅 메시지 전송을 필요로 할 때 사용됩니다.
    특징:
        • STOMP 프로토콜을 통해 클라이언트와 서버 간의 메시지 전송이 이루어집니다.
        • 메시지를 전송하기 위해 컨트롤러에서 @MessageMapping 어노테이션을 사용합니다.
        • 메시지 브로커는 /topic, /queue 등의 경로를 사용하여 메시지를 브로드캐스트하거나 특정 클라이언트에 전송합니다.

    https://dev-gorany.tistory.com/235
 */
@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final StompInterceptor stompInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 커넥션 경로
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 클라이언트가 메시지를 보낼 때 사용할 경로를 지정합니다.
        config.setApplicationDestinationPrefixes("/pub");

        // 클라이언트로 보낼 메시지의 경로를 지정합니다.
        config.enableSimpleBroker("/sub");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompInterceptor);
    }
}
