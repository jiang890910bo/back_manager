package com.cnvp.paladin.kit.tree;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 用途：
 * 说明：
 */
@SuppressWarnings("serial")
public class TreeNode implements Serializable {
	private int Id;
    private int Pid;
    private String nodeName;
    private Object obj;
    private TreeNode parentNode;
    private List<TreeNode> childList;

    public TreeNode() {
        initChildList();
    }
    //初始化子节点
    public void initChildList() {
        if (childList == null)
            childList = new ArrayList<TreeNode>();
    }
    //当前节点是否是叶节点（最终节点，无子节点）
    public boolean isLeaf() {
        if (childList == null) {
            return true;
        } else {
            if (childList.isEmpty()) {
                return true;
            } else {
                return false;
            }
        }
    }
    public void addChildNode(TreeNode childNode){
    	childNode.setParentNode(this);
    	this.childList.add(childNode);
    }
    
    //seter geter
    
    public void setChildList(List<TreeNode> childList) {
        this.childList = childList;
    }
    public List<TreeNode> getChildList() {
        return this.childList;
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int Pid) {
        this.Pid = Pid;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public TreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}