package com.fr4nk.binarytree;

import java.util.LinkedList;

public class TreeNodeTraverse implements TreeNodeTraverseService {

    /**
     * 前序遍历： 根 - 左子树 - 右子树
     *
     * 算法步骤
     * 将根节点压入栈
     *
     * 循环执行以下步骤直到栈为空：
     * 弹出栈顶节点并访问
     * 先将该节点的右子节点压栈（如果存在）
     * 再将该节点的左子节点压栈（如果存在）
     *
     * @param node
     * @return
     */
    @Override
    public String preOrderTraverse(TreeNode node) {
        if (node != null) {
            TreeNode curr =  node;
            StringBuilder sb = new StringBuilder();
            LinkedList<TreeNode> stack = new LinkedList<>();
            stack.push(curr);
            while(!stack.isEmpty()){
                curr = stack.pop();
                sb.append(curr.data)
                        .append(" ");
                if (curr.right != null) {
                    stack.push(curr.right);
                }
                if (curr.left != null){
                    stack.push(curr.left);
                }
            }
            return sb.toString().trim();
        }
        return "";
    }

    /**
     * 左子树 - 树根 - 右子树
     *
     * 算法步骤
     * 从根节点开始，将其和所有左子节点依次入栈
     *
     * 当左子节点为空时，弹出栈顶节点并访问
     *
     * 转向该节点的右子树，重复上述过程
     * @param node
     * @return
     */
    @Override
    public String inOrderTraverse(TreeNode node) {
        return "";
    }

    @Override
    public String postOrderTraverse(TreeNode node) {
        return "";
    }
}
