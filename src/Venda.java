import java.time.LocalDate;

public class Venda {
    LocalDate data;
    Produto produto;
    int quantidade;

    public Venda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double getTotal() {
        return produto.valor * quantidade;
    }
}