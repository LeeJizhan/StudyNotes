package main.trie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Asus- on 2018/8/20.
 * 前缀树使用之单词替换
 * <p>
 * 题目描述：
 * 在英语中，我们有一个叫做 词根(root)的概念，
 * 它可以跟着其他一些词组成另一个较长的单词——我们称这个词为 继承词(successor)。
 * 例如，词根an，跟随着单词 other(其他)，可以形成新的单词 another(另一个)。
 * 现在，给定一个由许多词根组成的词典和一个句子。
 * 你需要将句子中的所有继承词用词根替换掉。如果继承词有许多可以形成它的词根，则用最短的词根替换它。
 * 你需要输出替换之后的句子。
 * <p>
 * 输入：
 * dict = ["cat", "bat", "rat"]
 * sentence = "the cattle was rattled by the battery"
 * <p>
 * 输出：
 * "the cat was rat by the bat"
 */
public class WordReplace {

    private Node root = new Node();

    class Node {
        public Map<Character, Node> myMap = new HashMap<>();
        private boolean isEnd;

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd() {
            this.isEnd = true;
        }

        public void put(char c, Node node) {
            myMap.put(c, node);
        }

        public Node get(char c) {
            return myMap.get(c);
        }

        public boolean containsKey(char c) {
            return myMap.containsKey(c);
        }
    }


    public String replaceWords(List<String> dict, String sentence) {
        String[] words = sentence.split(" ");
        //将词根插入前缀树中
        for (String s : dict) {
            insert(s);
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < words.length; i++) {
            sb.append(search(words[i]));
            sb.append(" ");
        }
        return sb.substring(0, sb.length() - 1).toString();
    }

    /**
     * 前缀树插入操作
     */
    public void insert(String str) {
        Node node = root;
        for (int i = 0; i < str.length(); i++) {
            char current = str.charAt(i);
            if (!node.containsKey(current)) {
                node.put(current, new Node());
            }
            node = node.get(current);
        }
        node.setEnd();
    }

    /**
     * 前缀树查询操作
     */
    public String search(String str) {
        Node node = root;
        String s = str;
        boolean isHave = false;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (node.containsKey(c)) {
                sb.append(c);
                node = node.get(c);
                if (node.isEnd) {
                    isHave = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (isHave) {
            return sb.toString();
        } else {
            return s;
        }
    }

    public static void main(String[] args) {
        List<String> mList = new ArrayList<>();
        mList.add("cat");
        mList.add("bat");
        mList.add("rat");
        String sentence = "the cat was rat by the bat";
        WordReplace wordReplace = new WordReplace();
        System.out.println(wordReplace.replaceWords(mList, sentence));
    }
}
