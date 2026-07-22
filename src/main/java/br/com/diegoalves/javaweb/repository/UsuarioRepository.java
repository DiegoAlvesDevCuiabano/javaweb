package br.com.diegoalves.javaweb.repository;

import br.com.diegoalves.javaweb.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

    Usuario findUsuarioById(Long id);
}
