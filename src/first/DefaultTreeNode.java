package first;

/**
 * @author Filip Schepers, Moritz Binneweiß, Daniel Faigle, Vanessa Schoger, Denis Schaffer
 * @version 1, 06.12.2022
 */

public class DefaultTreeNode implements SimpleTreeNode {
    private int CAPACITY;
    private String name;
    private SimpleTreeNode[] childs;
    private int childcnt;

    public DefaultTreeNode(String name) {
        CAPACITY = 1;
        this.name = name;
        childs = new SimpleTreeNode[CAPACITY];
        childcnt = 0;
    }

    @Override
    public void addChild(SimpleTreeNode child) {
        if (childcnt >= CAPACITY) {
            CAPACITY *= 2;
            SimpleTreeNode[] newchilds = new SimpleTreeNode[CAPACITY];
            for (int i = 0; i < childcnt; i++) {
                newchilds[i] = childs[i];
            }
            childs = newchilds;
        }
        childs[childcnt++] = child;
    }

    @Override
    public int getChildCnt() {
        return childcnt;
    }

    @Override
    public SimpleTreeNode getChild(int pos) {
        return childs[pos];
    }

    public String toString() {
        return name;
    }
}
