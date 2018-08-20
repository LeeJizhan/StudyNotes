package main.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus- on 2018/8/20.
 * 题目描述：
 * 设计一个支持以下两种操作的数据结构：
 * void addWord(word)
 * bool search(word)
 * search(word) 可以搜索文字或正则表达式字符串，字符串只包含字母 . 或 a-z 。 . 可以表示任何一个字母。
 *
 * 示例：
 * addWord("bad")
 * addWord("dad")
 * addWord("mad")
 * search("pad") -> false
 * search("bad") -> true
 * search(".ad") -> true
 * search("b..") -> true
 */

public class WordDictionary {

    private Node root;

    class Node {

        public Map<Character, Node> children = new HashMap<>();

        private boolean isEnd;

        public void setEnd() {
            this.isEnd = true;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void put(char key, Node node) {
            children.put(key, node);
        }

        public Node get(char key) {
            return children.get(key);
        }

        public boolean containsKey(char key) {
            return children.containsKey(key);
        }

    }

    /**
     * Initialize your data structure here.
     */
    public WordDictionary() {
        root = new Node();
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        Node node = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (!node.containsKey(ch)) {
                node.put(ch, new Node());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        Node node = root;
        return search(node, word, 0);
    }

    public boolean search(Node node, String word, int index) {
        if (index == word.length()) {
            return node.isEnd();
        }
        char key = word.charAt(index);
        if (key != '.') {
            node = node.get(key);
            if (node == null)
                return false;
            return search(node, word, index + 1);
        } else {
            for (Map.Entry<Character, Node> entry : node.children.entrySet()) {
                if (search(entry.getValue(), word, index + 1))
                    return true;
            }
            return false;
        }
    }

    public static void main(String[] args){
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");
        System.out.println(wordDictionary.search("pad"));
        System.out.println(wordDictionary.search("bad"));
        System.out.println(wordDictionary.search(".ad"));
        System.out.println(wordDictionary.search("b.."));
    }
}
