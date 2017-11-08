package com.kinmanlui.structures;

public class BinaryTree<T> {

    private BinaryNode head;


    private class BinaryNode {
        private T data;
        private BinaryNode leftChildNode;
        private BinaryNode rightChildNode;

        private BinaryNode(T data) { this.data = data; }

        private BinaryNode(T data, BinaryNode leftChildNode, BinaryNode rightChildNode) {
            this.data = data;
            this.leftChildNode = leftChildNode;
            this.rightChildNode = rightChildNode;
        }
    }
}
