import java.util.Objects;

public class PacketRule implements Comparable<PacketRule> {
    
    // Atributos privados para garantir o encapsulamento
    private int id;
    private String ipOrigem;
    private String ipDestino;
    private int prioridade;

    /**
     * Construtor da classe PacketRule.
     */
    public PacketRule(int id, String ipOrigem, String ipDestino, int prioridade) {
        this.id = id;
        this.ipOrigem = ipOrigem;
        this.ipDestino = ipDestino;
        this.prioridade = prioridade;
    }

    // --- Getters e Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIpOrigem() {
        return ipOrigem;
    }

    public void setIpOrigem(String ipOrigem) {
        this.ipOrigem = ipOrigem;
    }

    public String getIpDestino() {
        return ipDestino;
    }

    public void setIpDestino(String ipDestino) {
        this.ipDestino = ipDestino;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int prioridade) {
        this.prioridade = prioridade;
    }

    // --- Métodos Sobrescritos ---

    /**
     * Define a regra de ordenação nas Árvores Binárias de Busca.
     * Neste caso, a árvore usará o ID do pacote para definir quem vai para a esquerda ou direita.
     */
    @Override
    public int compareTo(PacketRule outroPacote) {
        return Integer.compare(this.id, outroPacote.getId());
    }

    /**
     * Garante que dois pacotes com o mesmo ID sejam considerados o mesmo objeto 
     * nas comparações lógicas do Java.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PacketRule that = (PacketRule) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Facilita a visualização dos dados do pacote no console, útil para o Integrante 3 e 2 nos testes.
     */
    @Override
    public String toString() {
        return "PacketRule{" +
                "id=" + id +
                ", ipOrigem='" + ipOrigem + '\'' +
                ", ipDestino='" + ipDestino + '\'' +
                ", prioridade=" + prioridade +
                '}';
    }
}