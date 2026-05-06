/**
 * RBTValidator — Integrante 3 (QA & Analytics)
 *
 * Verifica as 5 propriedades da RedBlack_Router_Tree.
 * Este arquivo compila após o merge de RedBlack_Router_Tree.
 *
 * DEPENDÊNCIA: RedBlack_Router_Tree deve expor:
 *   - RBTNode getRoot()
 *   - long getRotationCount()
 *   E RBTNode deve ter campos: PacketRule rule, RBTNode left, RBTNode right, boolean isRed()
 */
public class RBTValidator {

    /**
     * Valida as 5 propriedades fundamentais da Red-Black Tree:
     *
     *   P1. Todo nó é VERMELHO ou PRETO.
     *       → Garantida pelo tipo (boolean/enum na implementação).
     *
     *   P2. A raiz é PRETA.
     *       → Verificado explicitamente abaixo.
     *
     *   P3. Todo nó NIL (null) é PRETO.
     *       → Tratado implicitamente: null = preto na contagem.
     *
     *   P4. Se um nó é VERMELHO, ambos os filhos são PRETOS.
     *       → Não pode haver dois nós vermelhos consecutivos no caminho.
     *
     *   P5. Para todo nó, todos os caminhos até folhas NIL contêm
     *       o mesmo número de nós PRETOS (black-height uniforme).
     */
    public static TreeValidator.ValidationReport validateRBT(RedBlack_Router_Tree tree) {
        TreeValidator.ValidationReport report = new TreeValidator.ValidationReport();

        RedBlack_Router_Tree.RBTNode root = tree.getRoot();

        // P2
        if (root != null && root.isRed()) {
            report.addError("P2 violada: a raiz deve ser PRETA.");
        }

        checkNode(root, report);
        return report;
    }

    /**
     * Percorre recursivamente a RBT verificando P4 e P5.
     *
     * @return black-height do caminho a partir deste nó (excluindo o próprio nó).
     *         Retorna -1 quando uma violação de P5 já foi registrada, para evitar
     *         cascata de erros duplicados nos ancestrais.
     */
    private static int checkNode(RedBlack_Router_Tree.RBTNode node,
                                  TreeValidator.ValidationReport report) {
        // P3 — NIL conta como 0 nós pretos a partir daqui
        if (node == null) return 0;

        int leftBH  = checkNode(node.left,  report);
        int rightBH = checkNode(node.right, report);

        // P4 — nó vermelho não pode ter filho vermelho
        if (node.isRed()) {
            if (node.left  != null && node.left.isRed()) {
                report.addError(String.format(
                    "P4 violada: nó %d (RED) → filho esquerdo %d também é RED.",
                    node.rule.getId(), node.left.rule.getId()));
            }
            if (node.right != null && node.right.isRed()) {
                report.addError(String.format(
                    "P4 violada: nó %d (RED) → filho direito %d também é RED.",
                    node.rule.getId(), node.right.rule.getId()));
            }
        }

        // P5 — black-height das duas subárvores deve ser igual
        if (leftBH != -1 && rightBH != -1 && leftBH != rightBH) {
            report.addError(String.format(
                "P5 violada: nó %d tem black-height esquerda=%d ≠ direita=%d.",
                node.rule.getId(), leftBH, rightBH));
            return -1;
        }

        // +1 se este nó contribui como preto
        return (leftBH == -1) ? -1 : leftBH + (node.isRed() ? 0 : 1);
    }

    public static void main(String[] args) {
        System.out.println("RBTValidator pronto. Aguardando merge de RedBlack_Router_Tree.");
    }
}
