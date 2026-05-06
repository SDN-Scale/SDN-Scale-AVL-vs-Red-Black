import java.util.ArrayList;
import java.util.List;

/**
 * TreeValidator — Integrante 3 (QA & Analytics)
 *
 * Verifica invariantes estruturais das árvores AVL_Router_Tree e RedBlack_Router_Tree.
 * Uso: chame validateAVL() após cada inserção/deleção no code review ou nos testes.
 */
public class TreeValidator {

    // =========================================================================
    // Resultado de validação
    // =========================================================================

    public static class ValidationReport {
        private final List<String> errors = new ArrayList<>();

        void addError(String msg) { errors.add(msg); }

        public boolean isValid()          { return errors.isEmpty(); }
        public List<String> getErrors()   { return errors; }

        @Override
        public String toString() {
            if (isValid()) return "[OK] Todas as invariantes satisfeitas.";
            StringBuilder sb = new StringBuilder("[FALHA] " + errors.size() + " violação(ões) encontrada(s):\n");
            for (String e : errors) sb.append("  - ").append(e).append("\n");
            return sb.toString();
        }
    }

    // =========================================================================
    // AVL — verificação de invariantes
    // =========================================================================

    /**
     * Valida todas as invariantes da AVL_Router_Tree:
     *   1. Altura armazenada em cada nó é consistente com a altura real.
     *   2. Fator de Equilíbrio (FB) satisfaz |FB| <= 1 em todos os nós.
     *   3. Propriedade BST: valores da subárvore esquerda < nó < subárvore direita.
     */
    public static ValidationReport validateAVL(AVL_Router_Tree tree) {
        ValidationReport report = new ValidationReport();
        checkAVLNode(tree.getRoot(), null, null, report);
        return report;
    }

    /**
     * Percorre recursivamente a árvore verificando altura, FB e BST.
     *
     * @param node  nó atual
     * @param min   limite inferior exclusivo (todo nó nessa subárvore deve ser > min)
     * @param max   limite superior exclusivo (todo nó nessa subárvore deve ser < max)
     * @return      altura real calculada do nó
     */
    private static int checkAVLNode(AVL_Router_Tree.AVLNode node,
                                    PacketRule min, PacketRule max,
                                    ValidationReport report) {
        if (node == null) return 0;

        // --- Invariante BST ---
        if (min != null && node.rule.compareTo(min) <= 0) {
            report.addError(String.format(
                "BST violada: nó %d deveria ser > %d (limite inferior)",
                node.rule.getId(), min.getId()));
        }
        if (max != null && node.rule.compareTo(max) >= 0) {
            report.addError(String.format(
                "BST violada: nó %d deveria ser < %d (limite superior)",
                node.rule.getId(), max.getId()));
        }

        // --- Percorre filhos ---
        int realHeightLeft  = checkAVLNode(node.left,  min,       node.rule, report);
        int realHeightRight = checkAVLNode(node.right, node.rule, max,       report);

        // --- Invariante: altura armazenada é consistente ---
        int expectedHeight = 1 + Math.max(realHeightLeft, realHeightRight);
        if (node.height != expectedHeight) {
            report.addError(String.format(
                "Altura inconsistente no nó %d: armazenada=%d, real=%d",
                node.rule.getId(), node.height, expectedHeight));
        }

        // --- Invariante: |FB| <= 1 ---
        int fb = realHeightLeft - realHeightRight;
        if (Math.abs(fb) > 1) {
            report.addError(String.format(
                "AVL violada no nó %d: FB=%d (|FB| deve ser <= 1). " +
                "Altura esquerda=%d, Altura direita=%d",
                node.rule.getId(), fb, realHeightLeft, realHeightRight));
        }

        return expectedHeight;
    }

    // =========================================================================
    // Utilitário — relatório de rotações (AVL)
    // =========================================================================

    public static void printRotationReport(AVL_Router_Tree avl) {
        System.out.println("=== Relatório de Rotações ===");
        System.out.println("AVL : " + avl.getRotationCount() + " rotações");
        // RBT será adicionado após merge de RedBlack_Router_Tree
    }

    // =========================================================================
    // main — smoke test rápido da AVL
    // =========================================================================

    public static void main(String[] args) {
        System.out.println("=== Smoke Test — AVL_Router_Tree ===\n");

        AVL_Router_Tree avl = new AVL_Router_Tree();

        // Inserção em ordem crescente: caso mais agressivo para balanceamento
        int[] ids = {10, 20, 30, 40, 50, 25};
        for (int id : ids) {
            avl.insert(new PacketRule(id, "192.168.0." + id, "10.0.0." + id, id % 5));
        }

        ValidationReport reportInsert = validateAVL(avl);
        System.out.println("Após 6 inserções: " + reportInsert);

        // Deleção de 20% dos nós (2 de 6)
        avl.delete(10);
        avl.delete(20);

        ValidationReport reportDelete = validateAVL(avl);
        System.out.println("Após deleção de 20% dos nós: " + reportDelete);

        System.out.println("Rotações realizadas: " + avl.getRotationCount());
    }
}
