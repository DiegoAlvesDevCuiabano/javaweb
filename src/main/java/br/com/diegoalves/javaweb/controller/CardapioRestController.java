package br.com.diegoalves.javaweb.controller;

import br.com.diegoalves.javaweb.dto.ItemCardapioRespostaDTO;
import br.com.diegoalves.javaweb.model.ItemCardapio;
import br.com.diegoalves.javaweb.service.CardapioService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cardapio")
public class CardapioRestController {

    private final CardapioService cardapioService;

    public CardapioRestController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping
    public List<ItemCardapioRespostaDTO> listar() {
        List<ItemCardapio> itens = cardapioService.listarTodos();
        List<ItemCardapioRespostaDTO> resposta = new ArrayList<>();

        for (ItemCardapio item : itens) {
            resposta.add(new ItemCardapioRespostaDTO(item));
        }

        return resposta;
    }
}
