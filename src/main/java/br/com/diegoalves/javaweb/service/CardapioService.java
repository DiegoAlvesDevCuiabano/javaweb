package br.com.diegoalves.javaweb.service;

import br.com.diegoalves.javaweb.model.ItemCardapio;
import br.com.diegoalves.javaweb.model.Usuario;
import br.com.diegoalves.javaweb.repository.ItemCardapioRepository;
import br.com.diegoalves.javaweb.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CardapioService {

    private final ItemCardapioRepository itemRepository;
    private final UsuarioRepository usuarioRepository;

    public CardapioService(ItemCardapioRepository itemRepository,
                           UsuarioRepository usuarioRepository) {
        this.itemRepository = itemRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<ItemCardapio> listarTodos() {
        return itemRepository.findAll();
    }

    public ItemCardapio buscarPorId(Long id) {
        return itemRepository.findItemCardapioById(id);
    }

    public void incluir(String nome, String descricao, BigDecimal preco, Long usuarioId) {
        Usuario usuario = usuarioRepository.findUsuarioById(usuarioId);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        ItemCardapio novoItem = new ItemCardapio(nome, descricao, preco, usuario);
        itemRepository.save(novoItem);
    }

    public void alterar(Long id, String nome, String descricao, BigDecimal preco) {
        ItemCardapio item = itemRepository.findItemCardapioById(id);

        if (item == null) {
            throw new IllegalArgumentException("Item não encontrado");
        }

        item.alterar(nome, descricao, preco);
        itemRepository.save(item);
    }

    public void excluir(Long id) {
        itemRepository.deleteById(id);
    }
}
