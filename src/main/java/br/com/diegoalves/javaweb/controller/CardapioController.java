package br.com.diegoalves.javaweb.controller;

import br.com.diegoalves.javaweb.dto.ItemCardapioDTO;
import br.com.diegoalves.javaweb.exception.ItemNaoEncontradoException;
import br.com.diegoalves.javaweb.exception.ValidacaoException;
import br.com.diegoalves.javaweb.model.ItemCardapio;
import br.com.diegoalves.javaweb.service.CardapioService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping("/home")
    public String mostrarHome(Authentication authentication,
                              Model model) {
        model.addAttribute("usuarioLogin", authentication.getName());
        model.addAttribute("item", new ItemCardapioDTO());
        model.addAttribute("itens", cardapioService.listarTodos());

        return "home";
    }

    @PostMapping("/cardapio")
    public String salvar(@Valid @ModelAttribute("item") ItemCardapioDTO dto,
                          BindingResult resultado,
                          Authentication authentication) {
        if (resultado.hasErrors()) {
            FieldError erro = resultado.getFieldError();
            String mensagem = "Os dados informados são inválidos.";

            if (erro != null) {
                mensagem = erro.getDefaultMessage();
            }

            throw new ValidacaoException(mensagem);
        }

        if (dto.getId() == null) {
            cardapioService.incluir(dto, authentication.getName());
        } else {
            cardapioService.alterar(dto);
        }

        return "redirect:/home";
    }

    @GetMapping("/cardapio/{id}/editar")
    public String editar(@PathVariable Long id,
                         Authentication authentication,
                         Model model) {
        ItemCardapio item = cardapioService.buscarPorId(id);

        if (item == null) {
            throw new ItemNaoEncontradoException();
        }

        ItemCardapioDTO dto = new ItemCardapioDTO();
        dto.setId(item.getId());
        dto.setNome(item.getNome());
        dto.setDescricao(item.getDescricao());
        dto.setPreco(item.getPreco());

        model.addAttribute("usuarioLogin", authentication.getName());
        model.addAttribute("item", dto);
        model.addAttribute("itens", cardapioService.listarTodos());

        return "home";
    }

    @PostMapping("/cardapio/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        cardapioService.excluir(id);
        return "redirect:/home";
    }
}
