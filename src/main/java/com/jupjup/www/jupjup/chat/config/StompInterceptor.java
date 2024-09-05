package com.jupjup.www.jupjup.chat.config;

import com.jupjup.www.jupjup.config.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Order(Ordered.HIGHEST_PRECEDENCE + 99) // spring security 설정보다 앞서야 함.
@Slf4j
@RequiredArgsConstructor
@Component
public class StompInterceptor implements ChannelInterceptor {

    private static final String BEARER_PREFIX = "Bearer ";

    // websocket 을 통해 들어온 요청이 처리 되기전 실행 됨
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // websocket 연결시 헤더의 jwt token 유효성 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authorization = accessor.getFirstNativeHeader("Authorization");
            if (authorization == null || authorization.isEmpty()) {
                // TODO: exception 발생
                log.error("인증 정보가 없습니다.");
                return null;
            }

            try{
                String token = authorization.substring(BEARER_PREFIX.length());
                if (!JWTUtil.validateAccessToken(token)) {
                    // TODO: exception 발생
                    log.error("invalid access token");
                    return null;
                }
            } catch (ExpiredJwtException e) {
                log.error("expired access token");
                return null;
            }
        }

        return message;
    }

}
