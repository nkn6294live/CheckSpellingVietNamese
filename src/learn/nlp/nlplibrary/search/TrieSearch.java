package learn.nlp.nlplibrary.search;

import java.util.ArrayList;
import java.util.List;

public class TrieSearch {

    public void AddPhrase(TrieNode root, String phrase, int phraseID) {  //ref Node root, String pharase, int phraseID.
        // a pointer to traverse the trie without damaging the original reference
        TrieNode node = root;
        //break phrase into words
        String[] words = phrase.split(" ");
        //start traversal at root
        for (int i = 0; i < words.length; i++) {
            //if the current word does not exist as a child to current node, add it
            if (node.getChildren().containsKey(words[i]) == false) {
                node.getChildren().put(words[i], new TrieNode());
            }
            //move traversal pointer to current word
            node = node.getChildren().get(words[i]);
            //if current word is the last one, mark it with pharaseid
            if (i == words.length - 1) {
                node.setId(phraseID);
            }
        }
    }

    public List<Integer> FindPharases(TrieNode root, String textBody) {
        // a pointer to traverse the trie without damaging the original reference
        TrieNode node = root;
        // a list of found ids
        List<Integer> foundPhrases = new ArrayList<>();
        //break text body into words
        String[] words = textBody.split(" ");
        //starting traversal at trie root and first word in the text body
        for (int i = 0; i < words.length;) {
            //if current node has current word as a child move both node and words pointer forward
            if (node.getChildren().containsKey(words[i])) {
                //move trie pointer forward
                node = node.getChildren().get(words[i]);
                //move words pointer forward
                i++;
            } else {
                //current node does not have current word in its  children.
                // if there is a phrase id, then the previous sequence of words matched a pharase, add id to  found list
                if (node.getId() != -1) {
                    foundPhrases.add(node.getId());
                }
                if (node == root) {
                    //if trie pointer is already at root, increament words pointer.
                    ++i;
                } else {
                    //if not, leave words pointer at current word and return trie pointer to root
                    node = root;
                }
            }
        }
        // on case remain, word pointer as reached the end and the loop is over but the trie pointer is pointing to a phraseID
        if (node.getId() != -1) {
            foundPhrases.add(node.getId());
        }
        return foundPhrases;
    }

}
