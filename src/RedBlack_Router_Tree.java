public class RedBlack_Router_Tree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    public class RBTNode {
        public PacketRule rule; 
        public RBTNode left;    
        public RBTNode right;   
        public RBTNode parent;
        public boolean color;

        public RBTNode(PacketRule rule) {
            this.rule = rule;
            this.color = RED; 
        }

        public boolean isRed() {
            return this.color == RED;
        }
    }

    private RBTNode root;
    private long rotationCount = 0;

    public RBTNode getRoot() {
        return root;
    }

    public long getRotationCount() {
        return rotationCount;
    }

    private void leftRotate(RBTNode x) {
        RBTNode y = x.right;
        x.right = y.left;
        
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        
        if (x.parent == null) {
            this.root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        
        y.left = x;
        x.parent = y;
        rotationCount++;
    }

    private void rightRotate(RBTNode y) {
        RBTNode x = y.left;
        y.left = x.right;
        
        if (x.right != null) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        
        if (y.parent == null) {
            this.root = x;
        } else if (y == y.parent.right) {
            y.parent.right = x;
        } else {
            y.parent.left = x;
        }
        
        x.right = y;
        y.parent = x;
        rotationCount++; 
    }

    public void insert(PacketRule rule) {
        RBTNode node = new RBTNode(rule);
        RBTNode y = null;
        RBTNode x = this.root;

        while (x != null) {
            y = x;
            if (node.rule.compareTo(x.rule) < 0) {
                x = x.left;
            } else if (node.rule.compareTo(x.rule) > 0) {
                x = x.right;
            } else {
                return;
            }
        }

        node.parent = y;
        if (y == null) {
            root = node;
        } else if (node.rule.compareTo(y.rule) < 0) {
            y.left = node;
        } else {
            y.right = node;
        }

        if (node.parent == null) {
            node.color = BLACK;
            return;
        }

        if (node.parent.parent == null) {
            return;
        }

        insertFixup(node);
    }

    private void insertFixup(RBTNode k) {
        while (k.parent != null && k.parent.color == RED) {
            if (k.parent == k.parent.parent.left) {
                RBTNode u = k.parent.parent.right;
                
                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {

                    if (k == k.parent.right) {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    rightRotate(k.parent.parent);
                }
            } else {
                RBTNode u = k.parent.parent.left;
                
                if (u != null && u.color == RED) {
                    k.parent.color = BLACK;
                    u.color = BLACK;
                    k.parent.parent.color = RED;
                    k = k.parent.parent;
                } else {
                    if (k == k.parent.left) {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = BLACK;
                    k.parent.parent.color = RED;
                    leftRotate(k.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    public void delete(int ruleId) {
        RBTNode node = searchRec(root, ruleId);
        if (node == null) return;

        RBTNode y = node;
        boolean yOriginalColor = y.color;
        RBTNode x;
        RBTNode xParent;

        if (node.left == null) {
            x = node.right;
            xParent = node.parent;
            transplant(node, node.right);
        } else if (node.right == null) {
            x = node.left;
            xParent = node.parent;
            transplant(node, node.left);
        } else {
            y = minimum(node.right);
            yOriginalColor = y.color;
            x = y.right;
            xParent = y.parent;
            
            if (y.parent == node) {
                if (x != null) xParent = y;
                else xParent = y;
            } else {
                transplant(y, y.right);
                y.right = node.right;
                y.right.parent = y;
            }

            transplant(node, y);
            y.left = node.left;
            y.left.parent = y;
            y.color = node.color;
        }

        if (yOriginalColor == BLACK) {
            deleteFixup(x, xParent);
        }
    }

    private void deleteFixup(RBTNode x, RBTNode xParent) {
        while (x != root && (x == null || x.color == BLACK)) {
            if (x == xParent.left) {
                RBTNode w = xParent.right;
                
                if (w != null && w.color == RED) {
                    w.color = BLACK;
                    xParent.color = RED;
                    leftRotate(xParent);
                    w = xParent.right;
                }
                
                if ((w == null || w.left == null || w.left.color == BLACK) && 
                    (w == null || w.right == null || w.right.color == BLACK)) {
                    if (w != null) w.color = RED;
                    x = xParent;
                    xParent = x.parent;
                } else {
        
                    if (w.right == null || w.right.color == BLACK) {
                        if (w.left != null) w.left.color = BLACK;
                        w.color = RED;
                        rightRotate(w);
                        w = xParent.right;
                    }

                    if (w != null) {
                        w.color = xParent.color;
                        if (w.right != null) w.right.color = BLACK;
                    }
                    xParent.color = BLACK;
                    leftRotate(xParent);
                    x = root;
                }
            } else {
                RBTNode w = xParent.left;
                
                if (w != null && w.color == RED) {
                    w.color = BLACK;
                    xParent.color = RED;
                    rightRotate(xParent);
                    w = xParent.left;
                }
                
                if ((w == null || w.right == null || w.right.color == BLACK) && 
                    (w == null || w.left == null || w.left.color == BLACK)) {
                    if (w != null) w.color = RED;
                    x = xParent;
                    xParent = x.parent;
                } else {
                    if (w.left == null || w.left.color == BLACK) {
                        if (w.right != null) w.right.color = BLACK;
                        w.color = RED;
                        leftRotate(w);
                        w = xParent.left;
                    }
                    if (w != null) {
                        w.color = xParent.color;
                        if (w.left != null) w.left.color = BLACK;
                    }
                    xParent.color = BLACK;
                    rightRotate(xParent);
                    x = root;
                }
            }
        }
        if (x != null) {
            x.color = BLACK;
        }
    }

    private void transplant(RBTNode u, RBTNode v) {
        if (u.parent == null) {
            root = v;
        } else if (u == u.parent.left) {
            u.parent.left = v;
        } else {
            u.parent.right = v;
        }
        if (v != null) {
            v.parent = u.parent;
        }
    }

    private RBTNode minimum(RBTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public PacketRule search(int id) {
        RBTNode res = searchRec(root, id);
        return (res != null) ? res.rule : null;
    }

    private RBTNode searchRec(RBTNode root, int id) {
        if (root == null || root.rule.getId() == id) {
            return root;
        }
        if (root.rule.getId() > id) {
            return searchRec(root.left, id);
        }
        return searchRec(root.right, id);
    }
}