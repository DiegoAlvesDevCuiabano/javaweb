# Guia da aula final

## Objetivo

Mostrar que uma aplicação web precisa proteger todas as camadas:

1. JavaScript melhora a experiência do usuário.
2. O backend protege as regras de negócio.
3. Spring Security controla autenticação, sessão, CSRF e acesso às rotas.
4. DTO separa os dados da tela das entidades do banco.
5. Exceções são transformadas em mensagens amigáveis.
6. MVC devolve HTML e REST devolve dados JSON.

## Preparação

1. Inicie o MySQL na porta `3306`.
2. Confira usuário e senha em `application.properties`.
3. Inicie a aplicação.
4. Entre com `admin` e `1234`.

## Parte 1 — Validação no navegador

Abra a Home, deixe o nome vazio ou informe preço `0` e clique em **Salvar**.

O arquivo `validacao-cardapio.js` intercepta o evento `submit`:

```javascript
if (preco <= 0 || Number.isNaN(preco)) {
    evento.preventDefault();
    mostrarAlerta("O preço deve ser maior que zero.");
}
```

`preventDefault()` impede o envio. O modal apenas apresenta a mensagem de forma mais amigável.

## Parte 2 — Demonstrar que JavaScript pode ser contornado

Esta demonstração é chamada de **adulteração ou manipulação da requisição**. Não é SQL Injection.

Opção A: comente esta linha em `home.html` e atualize a página:

```html
<script th:src="@{/js/validacao-cardapio.js}"></script>
```

Informe preço negativo e envie.

Opção B: sem alterar arquivos, abra o console do navegador e execute:

```javascript
document.querySelector("#preco").value = "-10";
document.querySelector("#form-cardapio").submit();
```

O método `submit()` envia o formulário diretamente sem disparar o listener JavaScript. O token CSRF que o Thymeleaf colocou no formulário continua sendo enviado.

Resultado esperado: o JavaScript é ignorado, mas o backend rejeita o preço e renderiza `erro.html`.

Conclusão para a turma:

> Nunca confie nos dados recebidos do navegador. Validação JavaScript é experiência; validação Java é segurança da regra de negócio.

## Parte 3 — DTO e validação backend

`ItemCardapioDTO` representa somente os dados recebidos da tela:

```java
@NotBlank(message = "O nome é obrigatório.")
private String nome;

@NotNull(message = "O preço é obrigatório.")
@DecimalMin(value = "0.01", message = "O preço deve ser maior que zero.")
private BigDecimal preco;
```

O controller recebe um objeto em vez de vários parâmetros:

```java
public String salvar(
        @Valid @ModelAttribute("item") ItemCardapioDTO dto,
        BindingResult resultado,
        Authentication authentication)
```

`@Valid` executa as anotações. `BindingResult` informa se algum campo falhou.

O service também valida explicitamente. Essa segunda proteção garante a regra mesmo se o service for chamado por outro controller, um teste ou uma futura integração.

## Parte 4 — Exceções e página de erro

Hierarquia usada:

```text
RuntimeException
└── AplicacaoException
    ├── ValidacaoException
    ├── ItemNaoEncontradoException
    └── LoginException
```

Exceções da aplicação podem apresentar mensagens controladas:

```java
throw new ValidacaoException("O preço deve ser maior que zero.");
```

`TratadorDeExcecoes` captura a exceção:

```java
@ExceptionHandler(AplicacaoException.class)
public String tratarErroDaAplicacao(
        AplicacaoException erro,
        Model model) {

    model.addAttribute("mensagem", erro.getMessage());
    return "erro";
}
```

Erros de banco e erros inesperados recebem mensagens genéricas. Nunca envie SQL, stack trace ou detalhes internos para a tela do usuário.

## Parte 5 — Spring Security

Antes, cada controller verificava manualmente `usuarioId` na sessão. Agora o filtro do Spring Security atua antes do controller:

```text
Requisição
    ↓
Spring Security
    ↓
Autenticado?
├── não: redireciona para o login
└── sim: chama o controller
```

`SecurityConfig` define:

- `/`, `/login`, `/css/**` e `/js/**` são públicos.
- As demais rotas exigem autenticação.
- Login bem-sucedido vai para `/home`.
- Logout invalida a sessão e remove `JSESSIONID`.

`LoginService` implementa `UserDetailsService`. O Spring Security chama `loadUserByUsername`, busca o usuário no banco e compara a senha usando o `PasswordEncoder` BCrypt.

O controller recebe o usuário autenticado assim:

```java
Authentication authentication
String login = authentication.getName();
```

Ao incluir um item, o service busca esse login no banco e salva o usuário em `incluido_por`. Não existe mais ID de usuário enviado pelo formulário ou controlado manualmente no controller.

## Parte 6 — CSRF

Operações POST usam `th:action`:

```html
<form method="post" th:action="@{/cardapio}">
```

Com Spring Security, o Thymeleaf acrescenta um campo oculto com o token CSRF. O servidor rejeita POSTs que não apresentam o token correto.

CSRF protege contra requisições forjadas por outro site. Ele não substitui validação de campos nem autorização.

## Parte 7 — MVC e REST

`CardapioController` usa `@Controller` e devolve páginas:

```java
return "home";
```

`CardapioRestController` usa `@RestController` e devolve JSON:

```text
GET /api/cardapio
```

Abra essa URL depois de autenticar. O navegador deve mostrar uma lista JSON.

Os dois controllers compartilham o mesmo `CardapioService`. A regra de negócio não é duplicada.

O REST devolve `ItemCardapioRespostaDTO`, não a entidade. Isso evita expor por acidente relacionamentos, senha do usuário ou detalhes internos do banco.

## Sequência sugerida

1. Execute o CRUD normalmente.
2. Mostre o modal JavaScript.
3. Contorne o JavaScript e mostre a validação backend.
4. Explique o DTO.
5. Force um ID inexistente e mostre a exceção/tela de erro.
6. Abra `/home` em janela anônima e mostre o redirecionamento do Security.
7. Faça login e logout, observando o cookie `JSESSIONID`.
8. Abra `/api/cardapio` e compare JSON com HTML.

## Perguntas para fechar

- Por que não confiamos no JavaScript?
- Qual a diferença entre DTO e entidade?
- Quem deve validar a regra de preço: controller ou service?
- Por que o erro de banco não mostra a mensagem original?
- Por que MVC e REST podem compartilhar o mesmo service?
- Qual a diferença entre autenticação e autorização?
