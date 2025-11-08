package com.fr4nk.binarytree;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TreeNodeTraverseTest {

    private TreeNodeTraverse treeNodeTraverse =  new TreeNodeTraverse();

    private TreeNode root;

    /**
     *          1
     *        2     3
     *      4  5   6
     *
     */
    @BeforeEach
    void setUp() {
        root = new TreeNode(1);
        TreeNode  leftLv1 = new TreeNode(2);
        TreeNode rightLv1 = new TreeNode(3);

        root.left = leftLv1;
        root.right = rightLv1;

        TreeNode  leftLv2 = new TreeNode(4);
        TreeNode rightLv2 = new TreeNode(5);
        leftLv1.left = leftLv2;
        leftLv1.right = rightLv2;

        TreeNode  leftLv3 = new TreeNode(6);
        rightLv1.left = leftLv3;
    }

    @Test
    void preOrderTraverse() {
        // Arrange

        // Act
        String preOrderStr = treeNodeTraverse.preOrderTraverse(root);
        // Assert
        Truth.assertThat(preOrderStr).isEqualTo("1 2 4 5 3 6");
    }

    @Test
    void inOrderTraverse() {
    }

    @Test
    void postOrderTraverse() {
    }
}