/**
 *
 */
package com.kinmanlui.structures.algorithm;

import com.kinmanlui.res.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Test {
    private Runnable testThread;
    private String content;
    private String className;
    private String testName;

    public Test(Runnable testThread, String className, String testName) {
        this.testThread = testThread;
        this.testName = testName;
        this.className = className;
        try {
            content = new String(Files.readAllBytes(Paths.get(Resource.CODE_PATH + className + ".java")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassName() { return className; }

    public String getTestName() { return testName; }

    public Runnable getTestThread() {
        return testThread;
    }

    public String getContent() {
        return content;
    }
}
