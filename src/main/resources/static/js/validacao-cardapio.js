const formulario = document.querySelector("#form-cardapio");
const alerta = document.querySelector("#alerta");
const mensagemAlerta = document.querySelector("#mensagem-alerta");
const botaoFechar = document.querySelector("#fechar-alerta");

function mostrarAlerta(mensagem) {
    mensagemAlerta.textContent = mensagem;
    alerta.classList.remove("escondido");
}

botaoFechar.addEventListener("click", function () {
    alerta.classList.add("escondido");
});

formulario.addEventListener("submit", function (evento) {
    const nome = document.querySelector("#nome").value.trim();
    const descricao = document.querySelector("#descricao").value;
    const preco = Number(document.querySelector("#preco").value);

    if (nome === "") {
        evento.preventDefault();
        mostrarAlerta("Informe o nome do item.");
        return;
    }

    if (descricao.length > 255) {
        evento.preventDefault();
        mostrarAlerta("A descrição deve ter no máximo 255 caracteres.");
        return;
    }

    if (preco <= 0 || Number.isNaN(preco)) {
        evento.preventDefault();
        mostrarAlerta("O preço deve ser maior que zero.");
    }
});
