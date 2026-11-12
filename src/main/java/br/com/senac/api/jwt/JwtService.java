package br.com.senac.api.jwt;


import br.com.senac.api.entidades.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class JwtService {
    private String segredo = "teste";

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(segredo);

            String token = JWT.create()
                    .withIssuer("pedido-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(this.geraDataExpiracao())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e) {
            throw e;
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(segredo);

            return JWT.require(algorithm)
                    .withIssuer("pedido-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            return null;
        }
    }

    private Instant geraDataExpiracao(){
        return LocalDateTime
                .now() // pega a data e hora atual
                .plusHours(1) // soma uma hora a data atual
                .toInstant(ZoneOffset.of("-03:00")); // define o fusohorário

    }
}
