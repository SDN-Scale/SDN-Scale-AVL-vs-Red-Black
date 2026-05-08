import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TestesDeCarga {

    private static final int VOLUME_DADOS = 10000; 
    private static final long SEED_FIXA = 42L;

    public static void main(String[] args) {
        System.out.println("=== Iniciando Bateria de Testes (" + VOLUME_DADOS + " regras) ===");
        
        // --- 1. TESTE DA AVL ---
        System.out.println("\n[1] Estrutura: AVL Tree");
        Random geradorAvl = new Random(SEED_FIXA);
        AVL_Router_Tree avl = new AVL_Router_Tree(); 
        List<Integer> idsAvl = new ArrayList<>();

        long inicioInsercaoAvl = System.nanoTime();
        for (int i = 1; i <= VOLUME_DADOS; i++) {
            avl.insert(new PacketRule(i, "192.168.0.1", "10.0.0.1", geradorAvl.nextInt(100))); 
            idsAvl.add(i);
        }
        System.out.println("Tempo de Inserção (AVL): " + (System.nanoTime() - inicioInsercaoAvl) + " ns");

        Collections.shuffle(idsAvl, new Random(SEED_FIXA));
        long inicioDelecaoAvl = System.nanoTime();
        for (int i = 0; i < (VOLUME_DADOS * 0.20); i++) {
            avl.delete(idsAvl.get(i)); 
        }
        System.out.println("Tempo de Deleção (AVL - 20%): " + (System.nanoTime() - inicioDelecaoAvl) + " ns");


        // --- 2. TESTE DA RED-BLACK ---
        System.out.println("\n[2] Estrutura: Red-Black Tree");
        Random geradorRbt = new Random(SEED_FIXA); 
        RedBlack_Router_Tree rbt = new RedBlack_Router_Tree(); 
        List<Integer> idsRbt = new ArrayList<>();

        long inicioInsercaoRbt = System.nanoTime();
        for (int i = 1; i <= VOLUME_DADOS; i++) {
            rbt.insert(new PacketRule(i, "192.168.0.1", "10.0.0.1", geradorRbt.nextInt(100))); 
            idsRbt.add(i);
        }
        System.out.println("Tempo de Inserção (RBT): " + (System.nanoTime() - inicioInsercaoRbt) + " ns");

        Collections.shuffle(idsRbt, new Random(SEED_FIXA));
        long inicioDelecaoRbt = System.nanoTime();
        for (int i = 0; i < (VOLUME_DADOS * 0.20); i++) {
            rbt.delete(idsRbt.get(i)); 
        }
        System.out.println("Tempo de Deleção (RBT - 20%): " + (System.nanoTime() - inicioDelecaoRbt) + " ns");
    }
}