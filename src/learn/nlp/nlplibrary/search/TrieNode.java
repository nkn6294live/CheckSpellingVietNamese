package learn.nlp.nlplibrary.search;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import learn.nlp.nlplibrary.document.DocumentReader;

public class TrieNode {

    public TrieNode() {
        phraseID = -1;
        this.children = new HashMap<>();
    }

    public TrieNode(int id) {
        this.phraseID = id;
        this.children = new HashMap<>();
    }

    public Map<String, TrieNode> getChildren() {
        return this.children;
    }

    public int getId() {
        return this.phraseID;
    }

    public void setId(int id) {
        this.phraseID = id;
    }

    public void addPhrase(String phrase, int phraseId) {
        TrieNode node = this;
        String[] words = phrase.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!node.getChildren().containsKey(word)) {
                node.getChildren().put(word, new TrieNode());
            }
            node = node.getChildren().get(word);
            if (i == words.length - 1) {
                node.setId(phraseId);
            }
        }
    }

    public void addPhrase(DocumentReader reader, int phraseId) throws Exception {
        TrieNode node = this;
        String word = reader.nextWord();
        while (true) {
            if (!node.getChildren().containsKey(word)) {
                node.getChildren().put(word, new TrieNode());
            }
            node = node.getChildren().get(word);
            word = reader.nextWord();
            if (word == null) {
                node.setId(phraseId);
                break;
            }
        }
    }

    public List<Integer> findPharases(String textBody) {
        TrieNode node = this;
        List<Integer> foundPharases = new ArrayList<>();
        String[] words = textBody.split(" ");
        for (int i = 0; i < words.length;) {
            String word = words[i];
            if (node.getChildren().containsKey(word)) {
                node = node.getChildren().get(word);
                ++i;
            } else {
                if (node.getId() != -1) {
                    foundPharases.add(node.getId());
                }
                if (node == this) {
                    ++i;
                } else {
                    node = this;
                }
            }
        }
        if (node.getId() != -1) {
            foundPharases.add(node.getId());
        }
        return foundPharases;
    }

    @Override
    public String toString() {
        if (this.children.isEmpty()) {
            return ":[" + this.phraseID + "]";
        }
        StringWriter writer = new StringWriter();
        writer.append(":[" + this.phraseID + "]");
        writer.append("{");
        Iterator<String> iterator = this.children.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            TrieNode itemNode = this.children.get(key);
            writer.append("{");
            writer.append(key);
            writer.append(itemNode.toString());
            writer.append("}");
            if (iterator.hasNext()) {
                writer.append(";");
            } else {
                break;
            }
        }
        writer.append("}");
        return writer.toString();
    }
    private int phraseID;
    private final Map<String, TrieNode> children;
}
