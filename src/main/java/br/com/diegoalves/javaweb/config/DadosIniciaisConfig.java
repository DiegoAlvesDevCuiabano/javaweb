package br.com.diegoalves.javaweb.config;

import br.com.diegoalves.javaweb.model.Usuario;
import br.com.diegoalves.javaweb.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DadosIniciaisConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner cadastrarUsuarioInicial(UsuarioRepository usuarioRepository,
                                               PasswordEncoder passwordEncoder) {
        return args -> prepararUsuarioAdmin(usuarioRepository, passwordEncoder);
    }

    private void prepararUsuarioAdmin(UsuarioRepository usuarioRepository,
                                      PasswordEncoder passwordEncoder) {
        Usuario usuario = usuarioRepository.findByLogin("admin");

        if (usuario == null) {
            String senhaCriptografada = passwordEncoder.encode("1234");
            Usuario novoUsuario = new Usuario("admin", senhaCriptografada);
            usuarioRepository.save(novoUsuario);
            return;
        }

        boolean senhaJaCriptografada = usuario.getSenha().startsWith("$2");

        if (!senhaJaCriptografada) {
            String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
            usuario.alterarSenha(senhaCriptografada);
            usuarioRepository.save(usuario);
        }
    }
}
