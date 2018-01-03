package com.kinmanlui.info;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public enum Resource {
    INSTANCE;

    public static final String DATABASE_PATH = System.getProperty("user.dir") + "/src/main/resources/com/kinmanlui/database/";
    public static final String ALGORITHM_PATH = System.getProperty("user.dir") + "/src/main/java/com/kinmanlui/user/";
    public static final String PACKAGE_STATEMENT = "package com.kinmanlui.user;";
    public static final String ALGORITHM_PATH_WITH_DOTS = "com.kinmanlui.user.";
    public static final String RESOURCE_PATH = "/com/kinmanlui/misc/";
    private int[] randomNumbers = null;

    /**
     * Read the txt file with 1000 random numbers for sorting algorithms.
     * @return an integer array with 1000 random numbers
     */
    public int[] getRandomNumbers() {
        if(randomNumbers == null) {
            Scanner reader = null;
            List<Integer> numbers = new LinkedList<>();
            try {
                reader = new Scanner(new BufferedReader(new FileReader(getClass().getResource("/com/kinmanlui/misc/random-numbers.txt").toString())));
                while (reader.hasNext()) {
                    numbers.add(Integer.parseInt(reader.next()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }

            randomNumbers = new int[numbers.size()];
            for (int i = 0; i < numbers.size(); i++) {
                randomNumbers[i] = numbers.get(i);
            }
        }
        return randomNumbers;
    }
}
