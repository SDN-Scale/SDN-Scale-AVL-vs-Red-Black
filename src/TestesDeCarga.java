import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TestesDeCarga {

    private static final int VOLUME_DADOS = 100000; 
    private static final long SEED_FIXA = 42L;

    public static void main(String[] args) {
        System.out.println("--- Iniciando Teste Isolado: RED-BLACK (" + VOLUME_DADOS + " regras) ---");
        
        Random gerador = new Random(SEED_FIXA);
        RedBlack_Router_Tree rbt = new RedBlack_Router_Tree(); 
        List<Integer> idsInseridos = new ArrayList<>();

        long inicioInsercao = System.nanoTime();
        for (int i = 1; i <= VOLUME_DADOS; i++) {
            int prioridadeGerada = gerador.nextInt(100);
            PacketRule regra = new PacketRule(i, "192.168.0.1", "10.0.0.1", prioridadeGerada);
            rbt.insert(regra); 
            idsInseridos.add(i);
        }
        long tempoTotalInsercaoNs = (System.nanoTime() - inicioInsercao);
        System.out.println("Tempo de Inserção (Red-Black): " + tempoTotalInsercaoNs + " ns");

        int quantidadeParaDeletar = (int) (VOLUME_DADOS * 0.20);
        Collections.shuffle(idsInseridos, gerador);
        
        long inicioDelecao = System.nanoTime();
        for (int i = 0; i < quantidadeParaDeletar; i++) {
            rbt.delete(idsInseridos.get(i)); 
        }
        long tempoTotalDelecaoNs = (System.nanoTime() - inicioDelecao);
        System.out.println("Tempo de Deleção (Red-Black - 20%): " + tempoTotalDelecaoNs + " ns");
    }
}