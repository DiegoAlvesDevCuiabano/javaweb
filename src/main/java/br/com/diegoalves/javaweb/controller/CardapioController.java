package br.com.diegoalves.javaweb.controller;

import br.com.diegoalves.javaweb.model.ItemCardapio;
import br.com.diegoalves.javaweb.service.CardapioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class CardapioController {

    private final CardapioService cardapioService;

    public CardapioController(CardapioService cardapioService) {
        this.cardapioService = cardapioService;
    }

    @GetMapping("/home")
    public String mostrarHome(HttpSession session,
                              Model model) {
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/";
        }

        model.addAttribute("itens", cardapioService.listarTodos());

        return "home";
    }

    @PostMapping("/cardapio")
    public String salvar(@RequestParam(required = false) Long id,
                          @RequestParam String nome,
                          @RequestParam String descricao,
                          @RequestParam BigDecimal preco,
                          HttpSession session) {
        Long usuarioId = (Long) session.getAttribute("usuarioId");
        if (usuarioId == null) {
            return "redirect:/";
        }

        if (id == null) {
            cardapioService.incluir(nome, descricao, preco, usuarioId);
        } else {
            cardapioService.alterar(id, nome, descricao, preco);
        }

        return "redirect:/home";
    }

    @GetMapping("/cardapio/{id}/editar")
    public String editar(@PathVariable Long id,
                         HttpSession session,
                         Model model) {
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/";
        }

        ItemCardapio item = cardapioService.buscarPorId(id);

        if (item == null) {
            return "redirect:/home";
        }

        model.addAttribute("itemEdicao", item);
        model.addAttribute("itens", cardapioService.listarTodos());

        return "home";
    }

    @PostMapping("/cardapio/{id}/excluir")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("usuarioId") == null) {
            return "redirect:/";
        }

        cardapioService.excluir(id);
        return "redirect:/home";
    }
}
