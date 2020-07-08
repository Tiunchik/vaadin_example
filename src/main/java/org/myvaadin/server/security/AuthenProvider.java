package org.myvaadin.server.security;

import org.myvaadin.server.player.ChessPlayer;
import org.myvaadin.server.player.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;


@Component
public class AuthenProvider implements AuthenticationProvider {

    private final PlayerRepository playerRepository;

    public AuthenProvider(@Autowired PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        ChessPlayer chessPlayer = playerRepository.findByLoginAndPassword(name, password);
        if (chessPlayer != null) {
            return new UsernamePasswordAuthenticationToken(
                    name,
                    password,
                    chessPlayer.getRoles().stream()
                            .map(e -> (GrantedAuthority) () -> e.toString())
                            .collect(Collectors.toList()));
        } else {
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
