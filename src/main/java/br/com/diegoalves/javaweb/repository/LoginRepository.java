package br.com.diegoalves.javaweb.repository;

import br.com.diegoalves.javaweb.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<Login,Long> {
}
