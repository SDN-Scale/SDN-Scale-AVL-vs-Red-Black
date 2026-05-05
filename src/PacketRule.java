import java.util.Objects;

public class PacketRule implements Comparable<PacketRule> {
   
    private int id;
    private String ipOrigem;
    private String ipDestino;
    private int prioridade;

    public PacketRule(int id, String ipOrigem, String ipDestino, int prioridade) {
        this.id = id;
        this.ipOrigem = ipOrigem;
        this.ipDestino = ipDestino;
        this.prioridade = prioridade;
    }

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

    @Override
    public int compareTo(PacketRule outroPacote) {
        return Integer.compare(this.id, outroPacote.getId());
    }

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