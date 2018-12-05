package com.ly.admin.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author liyang
 * @Create 2018/6/15
 */
@Data
public class TreeNode {
    protected int id;
    protected int parentId;
    protected List<TreeNode> children = new ArrayList<TreeNode>();

    public void add(TreeNode node) {
        children.add(node);
    }
}
