public class AVL_Router_Tree {

    public class AVLNode {
        PacketRule rule;
        AVLNode left;
        AVLNode right;
        int height;

        AVLNode(PacketRule rule) {
            this.rule = rule;
            this.height = 1;
        }
    }

    private AVLNode root;
    
    private long rotationCount = 0; 

    public AVLNode getRoot() {
        return root;
    }

    public long getRotationCount() {
        return rotationCount;
    }

    private int height(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    private int getBalance(AVLNode node) {
        if (node == null) return 0;
        return height(node.left) + height(node.right);
    }

    private void updateHeight(AVLNode node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        rotationCount++;
        return x;
    }

    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        rotationCount++;
        return y;
    }

    public void insert(PacketRule rule) {
        root = insertRec(root, rule);
    }

    private AVLNode insertRec(AVLNode node, PacketRule rule) {

        if (node == null) return new AVLNode(rule);

        if (rule.compareTo(node.rule) < 0) {
            node.left = insertRec(node.left, rule);
        } else if (rule.compareTo(node.rule) > 0) {
            node.right = insertRec(node.right, rule);
        } else {
            return node;
        }

        updateHeight(node);

        int balance = getBalance(node);

        if (balance > 1 && rule.compareTo(node.left.rule) < 0) {
            return rotateRight(node);
        }

        if (balance < -1 && rule.compareTo(node.right.rule) > 0) {
            return rotateLeft(node);
        }

        if (balance > 1 && rule.compareTo(node.left.rule) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }

        if (balance < -1 && rule.compareTo(node.right.rule) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }

        return node;
    }

    public void delete(int ruleId) {
        PacketRule tempRule = new PacketRule(ruleId, "", "", 0);
        root = deleteNode(root, tempRule);
    }

    private AVLNode deleteNode(AVLNode root, PacketRule rule) {

        if (root == null) return root;

        if (rule.compareTo(root.rule) < 0) {
            root.left = deleteNode(root.left, rule);
        } else if (rule.compareTo(root.rule) > 0) {
            root.right = deleteNode(root.right, rule);
        } else {

            if ((root.left == null) || (root.right == null)) {
                AVLNode temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                AVLNode temp = minValueNode(root.right);
                root.rule = temp.rule;
                root.right = deleteNode(root.right, temp.rule);
            }
        }

        if (root == null) return root;
        updateHeight(root);
        int balance = getBalance(root);

        if (balance > 1 && getBalance(root.left) >= 0) {
            return rotateRight(root);
        }

        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = rotateLeft(root.left);
            return rotateRight(root);
        }

        if (balance < -1 && getBalance(root.right) <= 0) {
            return rotateLeft(root);
        }

        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rotateRight(root.right);
            return rotateLeft(root);
        }

        return root;
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    public PacketRule search(int id) {
        AVLNode res = searchRec(root, id);
        return (res != null) ? res.rule : null;
    }

    private AVLNode searchRec(AVLNode root, int id) {
        if (root == null || root.rule.getId() == id) {
            return root;
        }
        if (root.rule.getId() > id) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }
}