package com.kinmanlui.code;

import com.kinmanlui.main.Resource;

public class SelectionSort implements Runnable {

    @Override
    public void run() {
        Integer[] numbers = Resource.INSTANCE.getRandomNumbers();
        long start = System.currentTimeMillis();
        for(int i = 0; i < numbers.length; i++) {
            for(int j = i; j < numbers.length; j++) {
                if(numbers[i] > numbers[j]) {
                    int temp = numbers[j];
                    numbers[j] = numbers[i];
                    numbers[i] = temp;
                }
            }
        }
        long end = System.currentTimeMillis();

        for(int i : numbers) {
            System.out.println(i);
        }
        System.out.println("Selection sort takes: " + (end - start) + "ms");
    }
}
