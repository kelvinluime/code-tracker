package com.kinmanlui.structures;
/**
 * implements trie data structure to store string prefixes
 * @since 2017-8-28
 * @author Kelvin Lui
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Trie {

    private TrieNode head;

    public Trie() {
        head = new TrieNode();
    }

    // TODO: Add a delimiter parameter which accepts different delimiter other than "\n"
    // TODO: 10/17/17 Move import file method to Resource
    public void importStringFile(String filePath) {
        Scanner dictionaryReader = null;

        try {
            dictionaryReader = new Scanner(new BufferedReader(new FileReader(filePath)));
            while(dictionaryReader.hasNext()) {
                addWord(dictionaryReader.next());
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if(dictionaryReader != null) {
                dictionaryReader.close();
            }
        }
    }

    /**
     * inserts word into the trie.
     * @param word
     */
    public void addWord(String word) {
        if(word.isEmpty()) throw new IllegalArgumentException("Empty string.");
        TrieNode current = head;
        word = word.toLowerCase();

        // TODO: Also allow white space between alphabets for test names 
        for(int i = 0; i < word.length(); i++) {
            String prefix = word.substring(0, i + 1);
            if(!current.children.containsKey(prefix)) {
                current.children.put(prefix, new TrieNode(prefix));
            }
            current = current.children.get(prefix);
        }
        current.isWord = true;
    }

    /**
     * searches all the possible words in the trie based on prefix.
     * @param prefix is the string prefix that is used to search the words.
     * @return is an array list of all possible words.
     */
    public ArrayList<String> getWords(String prefix) {
        TrieNode current = head;
        ArrayList<String> words = new ArrayList<>();
        Stack<TrieNode> nodeStack = new Stack<>();

        // Get to the end of the prefix before looking for possible words
        for(int i = 0; i < prefix.length(); i++) {
            String subPrefix = prefix.substring(0, i + 1);
            if(!current.children.containsKey(subPrefix)) {
                return words;   // Return empty ArrayList if there doesn't exist the prefix
            }
            current = current.children.get(subPrefix);
        }

        // If prefix is a word itself, add it into the list
        if(current.isEndOfWord()) {
            words.add(prefix);
        }

        // Find all the words based on the prefix if it exists in the trie
        nodeStack.push(current);
        while(!nodeStack.isEmpty()) {
            TrieNode topNode = nodeStack.pop();
            for(TrieNode nextChild : topNode.children.values()) {
                if(nextChild.isEndOfWord()) {
                    words.add(nextChild.prefix);
                }
                nodeStack.push(nextChild);
            }
        }
        return words;
    }

    @Override
    public String toString() {
        Stack<TrieNode> nodeStack = new Stack<>();
        TrieNode current = head;
        nodeStack.push(current);
        String result = "Words: ";

        while(!nodeStack.isEmpty()) {
            TrieNode topNode = nodeStack.pop();
            if(!topNode.isLeaf()) {
                Iterator<TrieNode> childIterator = topNode.children.values().iterator();
                while(childIterator.hasNext()) {
                    nodeStack.push(childIterator.next());
                }
            }
            if(topNode.isEndOfWord()) {
                result += (topNode.prefix + " ");
            }
        }
        return result;
    }

    private class TrieNode {
        private String prefix;
        private HashMap<String, TrieNode> children;
        private boolean isWord;

        private TrieNode(String prefix) {
            this.prefix = prefix;
            children = new HashMap<>();
            isWord = false;
        }

        private TrieNode() {
            this("");
        }

        private boolean isLeaf() {
            return children.isEmpty();
        }

        private boolean isEndOfWord() {
            return (children.isEmpty() || isWord);
        }

    }
}
