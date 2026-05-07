import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TestesDeCarga {

    private static final int VOLUME_DADOS = 100000;
    private static final long SEED_FIXA = 42L;

    public static void main(String[] args) {
        System.out.println("--- Iniciando Bateria de Testes de Carga (SDN-Scale) ---");
        
        Random gerador = new Random(SEED_FIXA);
        
        AVL_Router_Tree avl = new AVL_Router_Tree(); 
        
        List<Integer> idsInseridos = new ArrayList<>();

        System.out.println("\n1. Iniciando teste de INSERÇÃO de " + VOLUME_DADOS + " regras...");
        
        long inicioInsercao = System.nanoTime();

        for (int i = 1; i <= VOLUME_DADOS; i++) {
            int prioridadeGerada = gerador.nextInt(100);
            
            PacketRule regra = new PacketRule(i, "192.168.0.1", "10.0.0.1", prioridadeGerada);
            
            avl.insert(regra); 
            idsInseridos.add(i);
        }

        long fimInsercao = System.nanoTime();
        long tempoTotalInsercaoNs = (fimInsercao - inicioInsercao);
        System.out.println("Tempo total de inserção (AVL): " + tempoTotalInsercaoNs + " ns");

        int quantidadeParaDeletar = (int) (VOLUME_DADOS * 0.20);
        System.out.println("\n2. Iniciando teste de DELEÇÃO de " + quantidadeParaDeletar + " regras (20%)...");
        
        Collections.shuffle(idsInseridos, gerador);
        
        long inicioDelecao = System.nanoTime();

        for (int i = 0; i < quantidadeParaDeletar; i++) {
            int idParaRemover = idsInseridos.get(i);
  
            avl.delete(idParaRemover); 
        }

        long fimDelecao = System.nanoTime();
        long tempoTotalDelecaoNs = (fimDelecao - inicioDelecao);
        System.out.println("Tempo total de deleção (AVL): " + tempoTotalDelecaoNs + " ns");
        
        System.out.println("\n--- Testes da AVL finalizados! ---");
    }
}