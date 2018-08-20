package main.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus- on 2018/8/20.
 *
 * 题目描述：
 * 实现一个 MapSumPairs 类里的两个方法，insert 和 sum。
 * 对于方法 insert，你将得到一对（字符串，整数）的键值对。
 * 字符串表示键，整数表示值。如果键已经存在，那么原来的键值对将被替代成新的键值对。
 * 对于方法 sum，你将得到一个表示前缀的字符串，你需要返回所有以该前缀开头的键的值的总和。
 *
 * 输入输出示例
 * 输入: insert("apple", 3), 输出: Null
 * 输入: sum("ap"), 输出: 3
 * 输入: insert("app", 2), 输出: Null
 * 输入: sum("ap"), 输出: 5
 */
public class MapSumPairs {

    public Node root;

    class Node {
        public Map<Character, Node> map = new HashMap<>();
        private int val;

        //put操作
        public void put(char ch, Node node) {
            map.put(ch, node);
        }

        //get操作
        public Node get(char ch) {
            return map.get(ch);
        }

        //是否包含该key
        public boolean containsKey(char ch) {
            return map.containsKey(ch);
        }

        public void setVal(int val) {
            this.val = val;
        }

        public int getVal() {
            return val;
        }
    }

    /**
     * Initialize your data structure here.
     */
    public MapSumPairs() {
        root = new Node();
    }

    public void insert(String key, int val) {
        Node node = root;
        for (int i = 0; i < key.length(); i++) {
            char cur = key.charAt(i);
            if (!node.containsKey(cur)) {
                node.put(cur, new Node());
            }
            node = node.get(cur);
        }
        node.setVal(val);
    }

    public int sum(String prefix) {
        Node node = root;
        //首先判断前缀是否存在
        for (int i = 0; i < prefix.length(); i++) {
            char cur = prefix.charAt(i);
            if (!node.containsKey(cur)) {
                return 0;
            }
            node = node.get(cur);
        }
        //存在该前缀则进行计算
        return countVal(node);
    }

    public int countVal(Node node) {
        int sum = node.getVal();
        for (char c : node.map.keySet()) {
            sum += countVal(node.get(c));
        }
        return sum;
    }

    public static void main(String[] args){
        MapSumPairs mapSumPairs = new MapSumPairs();
        mapSumPairs.insert("apple", 3);
        System.out.println(mapSumPairs.sum("ap"));
        mapSumPairs.insert("app", 2);
        System.out.println(mapSumPairs.sum("ap"));
    }
}
