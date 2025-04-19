import java.time.LocalDate;

public class Venda {
    private LocalDate data;
    private Produto produto;
    private int quantidade;

    public Venda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public LocalDate getData() { return data; }
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public double getTotal() {
        return produto.getValor() * quantidade;
    }
}
