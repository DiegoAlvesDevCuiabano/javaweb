package br.com.diegoalves.javaweb.repository;

import br.com.diegoalves.javaweb.model.ItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Long> {

    ItemCardapio findItemCardapioById(Long id);
}
