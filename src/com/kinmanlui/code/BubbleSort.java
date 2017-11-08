package com.kinmanlui.code;

import com.kinmanlui.main.Resource;

public class BubbleSort implements Runnable {

    @Override
    public void run() {

        Integer[] numbers = Resource.INSTANCE.getRandomNumbers();

        long start = System.currentTimeMillis();
        for(int i = 0; i < numbers.length - 1; i++) {
            for(int j = 0; j < numbers.length - 1; j++) {
                if(numbers[j + 1] < numbers[j]) {
                    int temp = numbers[j + 1];
                    numbers[j + 1] = numbers[j];
                    numbers[j] = temp;
                }
            }
        }
        long end = System.currentTimeMillis();

        for(int i :numbers) {
            System.out.println(i);
        }
        System.out.println("Bubble sort takes: " + (end - start) + "ms");

    }
}
