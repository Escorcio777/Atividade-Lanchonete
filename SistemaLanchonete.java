// Autor: Arthur Escorcio - Uc24100406
// Disciplina: Programacao Orientada a Objetos
// Tema: Aplicacao de principios GRASP (Creator e Controller)
// Descricao: Sistema simples para gerenciamento de pedidos de uma lanchonete.

import java.util.ArrayList;
import java.util.List;

// -------------------------
// Classe que representa um produto do cardapio
// -------------------------
class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }
}

// -------------------------
// Classe que representa um item dentro de um pedido
// -------------------------
class ItemPedido {
    private Produto produto;
    private int quantidade;

    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double calcularSubtotal() {
        return produto.getPreco() * quantidade;
    }

    public String toString() {
        return produto.getNome() + " (x" + quantidade + ") - R$ " + calcularSubtotal();
    }
}

// -------------------------
// Classe Pedido
// Padrão GRASP: CREATOR
// Justificativa: Segundo o princípio Creator, a classe que contém ou agrega instâncias
// de outra deve ser responsável por criá-las. Aqui, Pedido cria seus próprios ItemPedido.
// -------------------------
class Pedido {
    private List<ItemPedido> itens = new ArrayList<>();

    // Método que cria e adiciona itens ao pedido (aplicando o padrão Creator)
    public void adicionarItem(Produto produto, int quantidade) {
        ItemPedido item = new ItemPedido(produto, quantidade);
        itens.add(item);
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    public void exibirResumo() {
        System.out.println("\nResumo do Pedido:");
        for (ItemPedido item : itens) {
            System.out.println(item);
        }
        System.out.println("Total: R$ " + calcularTotal());
    }
}

// -------------------------
// Classe Controladora do sistema
// Padrao GRASP: CONTROLLER
// Justificativa: O Controller e responsavel por receber as requisicoes do "usuario"
// e delegar o trabalho para as classes do dominio (Pedido, Produto, etc).
// -------------------------
class LanchoneteController {
    private Pedido pedidoAtual;

    public LanchoneteController() {
        pedidoAtual = new Pedido();
    }

    public void adicionarItemAoPedido(String nomeProduto, double preco, int quantidade) {
        Produto produto = new Produto(nomeProduto, preco);
        pedidoAtual.adicionarItem(produto, quantidade);
        System.out.println("Item adicionado: " + nomeProduto + " x" + quantidade);
    }

    public void finalizarPedido() {
        pedidoAtual.exibirResumo();
        System.out.println("Pedido finalizado com sucesso!");
    }
}

// -------------------------
// Classe principal (main)
// Aqui simulamos o uso do sistema
// -------------------------
public class SistemaLanchonete {
    public static void main(String[] args) {
        // Criando o controller, que centraliza as acoes do sistema (padrao Controller)
        LanchoneteController controller = new LanchoneteController();

        // Adicionando alguns produtos ao pedido
        controller.adicionarItemAoPedido("Hamburguer", 15.0, 2);
        controller.adicionarItemAoPedido("Refrigerante", 6.0, 1);
        controller.adicionarItemAoPedido("Batata Frita", 8.0, 1);

        // Finalizando e exibindo o pedido
        controller.finalizarPedido();
    }
}
