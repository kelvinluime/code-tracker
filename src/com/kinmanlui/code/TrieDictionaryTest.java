/**
 * This tester imports a dictionary file, which is a file full of words separated by a new line.
 * Then it add all the words from this file into the trie. It then asks the user for a string prefix to search in
 * the trie. Finally, it will output all the possible words based on the string prefix, the number of words, and
 * the time it takes to search all these possible words.
 */

package com.kinmanlui.code;

import com.kinmanlui.main.Resource;
import com.kinmanlui.structures.Trie;

import java.util.Scanner;

public class TrieDictionaryTest implements Runnable {

    @Override
    public void run() {
        Trie trie = new Trie();

        trie.importStringFile(Resource.RESOURCE_PATH + "words.txt");

        boolean isRunning = true;
        String input;
        Scanner keyboard = new Scanner(System.in);
        while (isRunning) {
            System.out.println("Please enter a prefix to search (type :exit to stop): ");
            input = keyboard.nextLine();
            if (input.equals(":exit")) {
                isRunning = false;
            } else {
                System.out.println("Possible words: ");
                int numberOfWords = 0;
                long beginTime = System.currentTimeMillis();
                for (String word : trie.getWords(input)) {
                    System.out.println(word);
                    numberOfWords++;
                }
                long duration = System.currentTimeMillis() - beginTime;
                System.out.println("Total number of possible words: " + numberOfWords);
                System.out.println("Total search time: " + (float) duration / 1000 + " s.");
                System.out.println();
            }
        }
    }
}