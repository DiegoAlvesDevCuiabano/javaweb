package br.com.diegoalves.javaweb.service;

import br.com.diegoalves.javaweb.model.Usuario;
import br.com.diegoalves.javaweb.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario autenticar(String login, String senha) {
        Usuario usuario = usuarioRepository.findByLogin(login);

        if (usuario == null) {
            return null;
        }

        boolean senhaCorreta = passwordEncoder.matches(senha, usuario.getSenha());

        if (senhaCorreta) {
            return usuario;
        }

        return null;
    }
}
