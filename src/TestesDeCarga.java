import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TestesDeCarga {

    private static final String SEPARADOR = "=".repeat(50);
    private static final int[] CENARIOS = {10_000, 100_000, 1_000_000};
    private static final long SEED_FIXA = 42L;

    public static void main(String[] args) {
        for (int quantidade : CENARIOS) {
            imprimirCabecalho(quantidade);
            testarEstruturas(quantidade);
            System.out.println();
        }
    }

    private static void imprimirCabecalho(int quantidade) {
        System.out.println(SEPARADOR);
        System.out.println("Iniciando Bateria de Testes (" + quantidade + " regras)");
        System.out.println(SEPARADOR);
    }

    private static void testarEstruturas(int volume) {
        
        Random gerador = new Random(SEED_FIXA);
        List<PacketRule> pacotes = new ArrayList<>();
        List<Integer> idsParaDeletar = new ArrayList<>();

        for (int i = 1; i <= volume; i++) {
            pacotes.add(new PacketRule(i, "192.168.0.1", "10.0.0.1", gerador.nextInt(100)));
            idsParaDeletar.add(i);
        }
        
        Collections.shuffle(idsParaDeletar, new Random(SEED_FIXA));
        int limiteDelecao = (int) (volume * 0.20); 

        System.out.println("\n--- Árvore AVL ---");
        AVL_Router_Tree avl = new AVL_Router_Tree();

        long tempoInsercaoAvl = medirTempo(() -> {
            for (PacketRule pacote : pacotes) avl.insert(pacote);
        });
        System.out.println("Inserção:  " + tempoInsercaoAvl + " ns");

        long tempoBuscaAvl = medirTempo(() -> avl.search(volume));
        System.out.println("Busca:     " + tempoBuscaAvl + " ns");

        long tempoDelecaoAvl = medirTempo(() -> {
            for (int i = 0; i < limiteDelecao; i++) avl.delete(idsParaDeletar.get(i));
        });
        System.out.println("Deleção:   " + tempoDelecaoAvl + " ns");
        System.out.println("Rotações:  " + avl.getRotationCount());


        System.out.println("\n--- Árvore Red-Black ---");
        RedBlack_Router_Tree rbt = new RedBlack_Router_Tree();

        long tempoInsercaoRbt = medirTempo(() -> {
            for (PacketRule pacote : pacotes) rbt.insert(pacote);
        });
        System.out.println("Inserção:  " + tempoInsercaoRbt + " ns");

        long tempoBuscaRbt = medirTempo(() -> rbt.search(volume));
        System.out.println("Busca:     " + tempoBuscaRbt + " ns");

        long tempoDelecaoRbt = medirTempo(() -> {
            for (int i = 0; i < limiteDelecao; i++) rbt.delete(idsParaDeletar.get(i));
        });
        System.out.println("Deleção:   " + tempoDelecaoRbt + " ns");
        System.out.println("Rotações:  " + rbt.getRotationCount());
    }

    private static long medirTempo(Runnable acao) {
        long inicio = System.nanoTime();
        acao.run();
        return System.nanoTime() - inicio;
    }
}