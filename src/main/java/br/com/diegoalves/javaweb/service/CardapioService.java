package br.com.diegoalves.javaweb.service;

import br.com.diegoalves.javaweb.dto.ItemCardapioDTO;
import br.com.diegoalves.javaweb.exception.ItemNaoEncontradoException;
import br.com.diegoalves.javaweb.exception.LoginException;
import br.com.diegoalves.javaweb.exception.ValidacaoException;
import br.com.diegoalves.javaweb.model.ItemCardapio;
import br.com.diegoalves.javaweb.model.Usuario;
import br.com.diegoalves.javaweb.repository.ItemCardapioRepository;
import br.com.diegoalves.javaweb.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

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

    public void incluir(ItemCardapioDTO dto, String login) {
        validar(dto);

        Usuario usuario = usuarioRepository.findByLogin(login);

        if (usuario == null) {
            throw new LoginException();
        }

        ItemCardapio novoItem = new ItemCardapio(
                dto.getNome(),
                dto.getDescricao(),
                dto.getPreco(),
                usuario
        );
        itemRepository.save(novoItem);
    }

    public void alterar(ItemCardapioDTO dto) {
        validar(dto);

        ItemCardapio item = itemRepository.findItemCardapioById(dto.getId());

        if (item == null) {
            throw new ItemNaoEncontradoException();
        }

        item.alterar(dto.getNome(), dto.getDescricao(), dto.getPreco());
        itemRepository.save(item);
    }

    public void excluir(Long id) {
        ItemCardapio item = itemRepository.findItemCardapioById(id);

        if (item == null) {
            throw new ItemNaoEncontradoException();
        }

        itemRepository.deleteById(id);
    }

    private void validar(ItemCardapioDTO dto) {
        if (dto.getNome() == null || dto.getNome().isBlank()) {
            throw new ValidacaoException("O nome é obrigatório.");
        }

        if (dto.getNome().length() > 100) {
            throw new ValidacaoException("O nome deve ter no máximo 100 caracteres.");
        }

        if (dto.getDescricao() != null && dto.getDescricao().length() > 255) {
            throw new ValidacaoException("A descrição deve ter no máximo 255 caracteres.");
        }

        if (dto.getPreco() == null || dto.getPreco().signum() <= 0) {
            throw new ValidacaoException("O preço deve ser maior que zero.");
        }
    }
}
