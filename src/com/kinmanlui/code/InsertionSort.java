package com.kinmanlui.code;

import com.kinmanlui.main.Resource;

public class InsertionSort implements Runnable {

    @Override
    public void run() {

        Integer[] numbers = Resource.INSTANCE.getRandomNumbers();

        long start = System.currentTimeMillis();
        for(int i = 0; i < numbers.length; i++) {
            int temp = numbers[i];
            for(int j = i; j > 0; j--) {
                if(temp < numbers[j - 1]) {
                    numbers[i] = numbers[j - 1];
                    numbers[j - 1] = temp;
                }
            }
        }
        long end = System.currentTimeMillis();

        for(int i : numbers) {
            System.out.println(i);
        }
        System.out.println("Insertion sort takes: " + (end - start) + "ms.");
    }
}
