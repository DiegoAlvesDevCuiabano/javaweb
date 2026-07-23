# Roteiro do apresentador — Aula final

## Como usar

- Apresentação: `AULA_FINAL_SPRING.pptx`
- Duração sugerida: 2h a 2h30.
- Regra de ritmo: no máximo dois slides seguidos sem abrir o projeto.

## Slide 1 — Do formulário à aplicação segura

Abra dizendo que a aula não adiciona apenas funcionalidades: ela transforma o CRUD em uma aplicação capaz de rejeitar entradas maliciosas ou incorretas.

## Slide 2 — A jornada

Recapitule rapidamente MVC, banco, CRUD e login. Pergunte aos alunos onde eles acham que ainda existe fragilidade.

## Slide 3 — Podemos confiar no navegador?

Mostre que `required`, `min` e JavaScript estão na máquina do usuário. Ele controla o navegador; nós controlamos somente o servidor.

## Slide 4 — Demonstração

1. Tente salvar preço `-10` normalmente: mostre o modal.
2. Abra o console do navegador.
3. Execute:

```javascript
document.querySelector("#preco").value = "-10";
document.querySelector("#form-cardapio").submit();
```

4. Mostre `erro.html` vindo do backend.
5. Reforce: isso é manipulação da requisição, não SQL Injection.

## Slide 5 — Defesa em camadas

Explique de fora para dentro. JavaScript melhora UX; DTO valida contrato; service garante negócio; banco mantém integridade.

## Slide 6 — DTO

Abra `ItemCardapioDTO`. Compare com `ItemCardapio`: o DTO não contém `incluidoPor`. Isso impede que o cliente escolha quem criou o item.

## Slide 7 — Exceções

Abra `TratadorDeExcecoes`. Mostre por que validação pode exibir mensagem controlada, mas banco e erro inesperado recebem texto genérico.

## Slide 8 — Security limpa controllers

Compare mentalmente o antigo `if (session...)` repetido com os controllers atuais. A proteção ocorre antes do controller.

## Slide 9 — Login

Abra `SecurityConfig` e `LoginService`. Siga a sequência do slide. Mostre `authentication.getName()` no controller.

## Slide 10 — CSRF

Inspecione o HTML renderizado e procure o campo `_csrf`. Explique que `th:action` permite a inclusão automática do token nos formulários POST.

## Slide 11 — MVC x REST

Depois de autenticar, abra `http://localhost:8080/api/cardapio`. Compare JSON com `home.html`. Mostre que os dois controllers usam o mesmo service.

## Slide 12 — Arquitetura final

Faça os alunos narrarem o caminho de uma inclusão: browser, Security, controller, DTO, service, repository e MySQL.

## Slide 13 — Condução

Use como checkpoint de tempo. Se estiver atrasado, reduza a parte REST, mas preserve a demonstração de validação e o Security.

## Slide 14 — O que levar

Peça um exemplo concreto para cada frase. Exemplo: “Como alguém contorna o JavaScript?”

## Slide 15 — Encerramento

Feche com a frase: “Uma aplicação segura não confia — verifica”. Abra para perguntas.
