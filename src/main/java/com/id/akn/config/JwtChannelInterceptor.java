package com.id.akn.config;

import com.id.akn.serviceimpl.CustomUserDetail;
import lombok.AllArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;
    private final CustomUserDetail customUserDetail; 

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            
            List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");

            if (authorizationHeaders == null || authorizationHeaders.isEmpty()) {
                throw new BadCredentialsException("Missing Authorization header");
            }

            String authHeader = authorizationHeaders.get(0);
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new BadCredentialsException("Invalid Authorization header format");
            }

            String jwt = authHeader.substring(7);

            try {
                String email = jwtProvider.getEmailFromToken(jwt);
                if (email != null) {
                    UserDetails userDetails = customUserDetail.loadUserByUsername(email);
                    
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    
                    accessor.setUser(authentication);
                }
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid token: " + e.getMessage());
            }
        }
        return message;
    }
}