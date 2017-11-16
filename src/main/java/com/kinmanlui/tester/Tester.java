package com.kinmanlui.tester;

import com.kinmanlui.structures.TestBase;
import com.kinmanlui.structures.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class Tester {

    private HashMap<String, Test> testMap;
    private List<Test> list;
    private ExecutorService executor;
    private TestBase testBase;

    public Tester() {
        executor = null;
        testMap = new HashMap<>();
        testBase = TestBase.INSTANCE;

        // Loads tests from the com.kinmanlui.database
        list = testBase.get();
        for(Test test : list) {
            testMap.put(test.getTestName(), test);
        }
    }

    public HashMap<String, Test> getTestMap() { return testMap; }

    public void execute(Runnable testCase) {
        if(executor == null || executor.isShutdown()) {
            executor = Executors.newCachedThreadPool();
        }
        executor.submit(testCase);
    }

    public void shutdown() {
        if(executor != null) {
            executor.shutdownNow();
            try {
                System.in.close();  // Stops blocking Scanner input when shutdown
                executor.awaitTermination(100, TimeUnit.MICROSECONDS);
            } catch (IOException|InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}