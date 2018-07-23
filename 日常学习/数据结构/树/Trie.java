package main;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus- on 2018/7/23.
 * 前缀树/字典树
 */
public class Trie {

    public Node root;

    //树节点
    class Node {
//        /**
//         * 使用数组来存储子节点
//         * 优缺点：
//         * 访问子节点十分快捷。但可能会导致空间的浪费。
//         */
//        public static final int N = 26;
//        public Node[] children = new Node[N];

        /**
         * 使用 HashMap 来存储子节点。
         * 优缺点：
         * 通过相应的字符来访问特定的子节点更为容易。但它可能比使用数组稍慢一些。
         * 但是，由于我们只存储我们需要的子节点，因此节省了空间。
         * 这个方法也更加灵活，因为我们不受到固定长度和固定范围的限制。
         */
        public Map<Character, Node> children = new HashMap<>();

        private boolean isEnd;

        //put操作
        public void put(char ch, Node node) {
            children.put(ch, node);
        }

        //get操作
        public Node get(char ch) {
            return children.get(ch);
        }

        //是否包含该key
        public boolean contansKey(char ch) {
            return children.containsKey(ch);
        }

        //是否为最后一个节点
        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd() {
            isEnd = true;
        }
    }

    public Trie() {
        //初始化root
        root = new Node();
    }

    /**
     * 前缀树插入操作
     *
     * @param str
     */
    public void insert(String str) {
        Node node = root;
        //逐个判断字符串的字符
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            //如果不在节点里面，就把新建一个节点并插入
            if (!node.contansKey(currentChar)) {
                node.put(currentChar, new Node());
            }
            node = node.get(currentChar);
        }
        node.setEnd();
    }

    /**
     * 前缀树查询操作，查询是否含有该前缀，isEnd()不一定为true
     *
     * @param str
     * @return Node
     */
    public Node searchPrefix(String str) {
        Node node = root;
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            if (node.contansKey(currentChar)) {
                node = node.get(currentChar);
            } else {
                return null;
            }
        }
        return node;
    }

    /**
     * 查询字符串是否在前缀树中
     *
     * @param str
     * @return boolean
     */
    public boolean isIn(String str) {
        Node node = searchPrefix(str);
        return node != null && node.isEnd();
    }

    /**
     * 查询是否有该前缀
     *
     * @param str
     * @return boolean
     */
    public boolean startsWith(String str) {
        Node node = searchPrefix(str);
        return node != null;
    }

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("abc");
        trie.insert("bcd");
        //存在该前缀，返回true
        System.out.println(trie.startsWith("ab"));
        //字符串ab不在前缀树中，返回false
        System.out.println(trie.isIn("ab"));
        //字符串abc在前缀树中，返回true
        System.out.println(trie.isIn("abc"));
        //存在该前缀，返回true
        System.out.println(trie.startsWith("bc"));
        //字符串bc不在前缀树中，返回false
        System.out.println(trie.isIn("bc"));
        //字符串bcd在前缀树中，返回true
        System.out.println(trie.isIn("bcd"));
    }
}
