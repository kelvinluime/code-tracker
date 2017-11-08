/**
 *
 */
package com.kinmanlui.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Test {
    private Runnable testThread;
    private String sourceCode;
    private String testName;

    public Test(Runnable testThread, String className, String testName) {
        this.testThread = testThread;
        this.testName = testName;
        try {
            sourceCode = new String(Files.readAllBytes(Paths.get(Resource.CODE_PATH + className)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getTestName() { return testName; }

    public Runnable getTestThread() {
        return testThread;
    }

    public String getSourceCode() {
        return sourceCode;
    }
}
