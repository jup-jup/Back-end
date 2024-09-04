package com.jupjup.www.jupjup.chat.config;

import com.jupjup.www.jupjup.config.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final JWTUtil jwtUtil;
    private static final String BEARER_PREFIX = "Bearer ";

    // websocket 을 통해 들어온 요청이 처리 되기전 실행 됨
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // websocket 연결시 헤더의 jwt token 유효성 검증
        if (StompCommand.CONNECT == accessor.getCommand()) {
            String authorization = accessor.getFirstNativeHeader("Authorization");
            String token = authorization.substring(BEARER_PREFIX.length());
            if (!jwtUtil.validateAccessToken(token)) {
                // TODO: 커넥션 제한
            }
        }

        return message;
    }

}
