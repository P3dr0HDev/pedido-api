package br.com.senac.api.services;

import br.com.senac.api.dtos.TokenResponseDto;
import br.com.senac.api.dtos.UsuarioRequestDto;
import br.com.senac.api.entidades.Usuario;
import br.com.senac.api.jwt.JwtService;
import br.com.senac.api.respositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listar() {
        return usuarioRepositorio.findAll();
    }

    public Usuario criar(UsuarioRequestDto usuario) {
        Usuario usuarioSaida = this.usuarioRequestDtoToUsuario(usuario);
        return usuarioRepositorio.save(usuarioSaida);
    }

    public Usuario atualizar(Long id, UsuarioRequestDto usuario) {
        if (usuarioRepositorio.existsById(id)) {
            Usuario usuarioSaida = this.usuarioRequestDtoToUsuario(usuario);
            usuarioSaida.setId(id);
            return usuarioRepositorio.save(usuarioSaida);
        }

        throw new RuntimeException("Usuario nao encontrado!");
    }

    public void deletar(Long id) {
        if (usuarioRepositorio.existsById(id)) {
            usuarioRepositorio.deleteById(id);
            return;
        }

        throw new RuntimeException("Usuario nao encontrado!");
    }

    public Usuario listarPorId(Long id) {
        Optional<Usuario> usuarioRetorno = usuarioRepositorio.findById(id);
        if (usuarioRetorno.isPresent()) {
            return usuarioRetorno.get();
        }

        throw new RuntimeException("Usuario nao encontrado!");
    }

    public TokenResponseDto login(UsuarioRequestDto usuario) {
        Optional<Usuario> usuariosRetorno =
                usuarioRepositorio.findByEmail(usuario.getEmail());
        if(usuariosRetorno.isPresent()) {
            Usuario usuarioDb = usuariosRetorno.get();

            if(passwordEncoder.matches(usuario.getSenha(), usuarioDb.getSenha())) {
                String token = jwtService.gerarToken(usuarioDb);

                return new TokenResponseDto(token);
            }

            throw new RuntimeException("Senha invalda!");
        }

        throw new RuntimeException("Usuario não encotrado!");
    }

    private Usuario usuarioRequestDtoToUsuario(UsuarioRequestDto entrada) {
        Usuario saida = new Usuario();
        saida.setNome(entrada.getNome());
        saida.setEmail(entrada.getEmail());
        saida.setSenha(passwordEncoder.encode(entrada.getSenha()));
        return saida;
    }
}
