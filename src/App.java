import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class App {
    static Scanner scanner = new Scanner(System.in);
    static List<Produto> produtos = new ArrayList<>();
    static List<Venda> vendas = new ArrayList<>();

    public static void main(String[] args) {
        cabecalho();
        int opcao;
        do {
            menu();
            opcao = Integer.parseInt(scanner.nextLine());
            switch (opcao) {
                case 1 -> incluirProduto();
                case 2 -> consultarProduto();
                case 3 -> listarProdutos();
                case 4 -> vendasPorPeriodo();
                case 5 -> realizarVenda();
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }

    static void cabecalho() {
        System.out.println("=======================================");
        System.out.println("       SISTEMA DE CONTROLE DE VENDAS");
        System.out.println("=======================================");
    }

    static void menu() {
        System.out.println("\nMenu:");
        System.out.println("1 - Incluir produto");
        System.out.println("2 - Consultar produto");
        System.out.println("3 - Listagem de produtos");
        System.out.println("4 - Vendas por período - detalhado");
        System.out.println("5 - Realizar venda");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    static void incluirProduto() {
        System.out.print("Código: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Valor: ");
        double valor = Double.parseDouble(scanner.nextLine());
        System.out.print("Quantidade em estoque: ");
        int quantidade = Integer.parseInt(scanner.nextLine());

        produtos.add(new Produto(codigo, nome, valor, quantidade));
        System.out.println("Produto incluído com sucesso!");
    }

    static void consultarProduto() {
        System.out.print("Informe o código do produto: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Produto p = buscarProduto(codigo);
        if (p != null) {
            System.out.println("Produto: " + p.getNome() + " | Valor: R$" + p.getValor() + " | Estoque: " + p.getQuantidade());
        } else {
            System.out.println("Produto não encontrado.");
        }
    }

    static void listarProdutos() {
        System.out.println("\nLISTAGEM DE PRODUTOS");
        double soma = 0, max = Double.MIN_VALUE, min = Double.MAX_VALUE;
        for (Produto p : produtos) {
            System.out.println("Código: " + p.getCodigo() + " | Nome: " + p.getNome() + " | Valor: R$" + p.getValor() + " | Estoque: " + p.getQuantidade());
            soma += p.getValor();
            if (p.getValor() > max) max = p.getValor();
            if (p.getValor() < min) min = p.getValor();
        }
        if (!produtos.isEmpty()) {
            double media = soma / produtos.size();
            System.out.printf("\nValor médio: R$%.2f | Máximo: R$%.2f | Mínimo: R$%.2f\n", media, max, min);
        }
    }

    static void realizarVenda() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Informe a data da venda (dd/MM/yyyy): ");
        LocalDate dataVenda = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.print("Informe o código do produto: ");
        int codigo = Integer.parseInt(scanner.nextLine());
        Produto p = buscarProduto(codigo);
        if (p == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        System.out.print("Quantidade para venda: ");
        int qtd = Integer.parseInt(scanner.nextLine());
        if (qtd > p.getQuantidade()) {
            System.out.println("Estoque insuficiente.");
            return;
        }

        p.setQuantidade(p.getQuantidade() - qtd);
        vendas.add(new Venda(dataVenda, p, qtd));
        System.out.println("Venda realizada com sucesso!");
    }

    static void vendasPorPeriodo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.print("Data inicial (dd/MM/yyyy): ");
        LocalDate inicio = LocalDate.parse(scanner.nextLine(), formatter);
        System.out.print("Data final (dd/MM/yyyy): ");
        LocalDate fim = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.println("\nRELATÓRIO DE VENDAS - DETALHADO");
        System.out.println("Período: " + inicio.format(formatter) + " a " + fim.format(formatter));
        double totalPeriodo = 0;
        int qtdVendas = 0;
        for (Venda v : vendas) {
            if (!v.getData().isBefore(inicio) && !v.getData().isAfter(fim)) {
                double total = v.getTotal();
                System.out.printf("Data: %s | Produto: %s | Quantidade: %d | Unitário: R$%.2f | Total: R$%.2f\n",
                        v.getData().format(formatter), v.getProduto().getNome(), v.getQuantidade(), v.getProduto().getValor(), total);
                totalPeriodo += total;
                qtdVendas++;
            }
        }
        if (qtdVendas > 0) {
            System.out.printf("\nMédia de vendas: R$%.2f\n", totalPeriodo / qtdVendas);
        } else {
            System.out.println("Nenhuma venda no período.");
        }
    }

    static Produto buscarProduto(int codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) return p;
        }
        return null;
    }
}
