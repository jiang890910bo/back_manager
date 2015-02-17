package com.cnvp.paladin.kit.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.cnvp.paladin.core.BaseModel;

/**
 * 用途：树状工具
 * 说明：一个TreeKit实例，就代表一棵树
 * 一棵树下，包含一个或者多个根节点
 */
public class TreeKit{
	
	private List<TreeNode> rootNodes;
    private List<TreeNode> tempNodeList;
//    private Map<Integer, TreeNode> NodeMap = new HashMap<Integer, TreeNode>();
//    private boolean isValidTree = true;

    public TreeKit() {
    }

    public TreeKit(List<TreeNode> treeNodeList) {
        tempNodeList = treeNodeList;
        generateTree();
    }
    //将模型列表导入TreeKit转换成TreeNode
    @SuppressWarnings("rawtypes")
	public void importModels(List<? extends BaseModel> modelList){
    	if (modelList.size()==0)
    		return;
    	List<TreeNode> nodeList = new ArrayList<TreeNode>();    	
    	for (BaseModel<?> row : modelList) {
    		TreeNode tn = new TreeNode();
    		tn.setId(row.getInt("id"));
    		tn.setPid(row.getInt("pid"));
    		tn.setNodeName(row.getStr("cname"));
    		tn.setObj(row);
    		nodeList.add(tn);    		
		}
    	tempNodeList = nodeList;
        generateTree();
    }
    //生成树状select的数据
    public Map<Integer,String> getSelectMap(){
    	Map<Integer,String> selectOpts = new LinkedHashMap<Integer, String>();
    	Integer nowLevel = 0;
    	Iterator<TreeNode> it = getRootNodes().iterator();
    	while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            selectOpts.put(treeNode.getId(), treeNode.getNodeName());
            //System.out.println("selectOpts put:"+treeNode.getId()+"\t|" + treeNode.getNodeName());
            if (!treeNode.isLeaf())
            	getChildSelectMap(selectOpts,treeNode,nowLevel);
        }
    	return selectOpts;
    }
    private void getChildSelectMap(Map<Integer,String> selectOpts,TreeNode tn,Integer parentLevel){
    	Integer nowLevel = parentLevel+1;
    	Iterator<TreeNode> it = tn.getChildList().iterator();
    	while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nowLevel; i++) {
            	sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			}
//            if (it.hasNext()) 
//            	sb.append("├");
//            else
//            	sb.append("└");
            sb.append(treeNode.getNodeName());
            selectOpts.put(treeNode.getId(), sb.toString());
            //System.out.println("selectOpts put:"+treeNode.getId()+"\t|"+sb.toString());
            if(!treeNode.isLeaf())
            	getChildSelectMap(selectOpts,treeNode,nowLevel);
        }
    }
//    public Map<Integer,String> getSelect(Integer pid){
//    	TreeNode root = getRoot().findTreeNodeById(pid);  
//    	Map<Integer,String> selectOpts = new HashMap<Integer, String>();
//    	getChildSelect(root,selectOpts,0);
//		return selectOpts;
//    }
//    private void getChildSelect(TreeNode parentNode,Map<Integer,String> selectOpts,Integer level){
//    	List<TreeNode> childNodes = parentNode.getChildList();
//    	for (int i = 0; i < childNodes.size(); i++) {
//    		String optName = "";
//    		for (int j = 0; j < level; j++)
//    			optName+=" ";
//    		if (level>0) {
//    			if(i==childNodes.size()-1)
//        			optName += "└";
//        		else
//        			optName += "├";
//			}    		
//    		optName+=childNodes.get(i).getNodeName();
//			selectOpts.put(childNodes.get(i).getId(), optName);
//    		
//    		if(childNodes.get(i).getChildList().size()>0){
//    			getChildSelect(childNodes.get(i),selectOpts,level+1);
//    		}
//		}
//    }
//    public static TreeNode getTreeNodeById(TreeNode tree, int id) {
//        if (tree == null)
//            return null;
//        TreeNode treeNode = tree.findTreeNodeById(id);
//        return treeNode;
//    }
//
    /** 从已给的TreeNode生成Tree */
    public void generateTree() {
        HashMap<Integer, TreeNode> nodeMap = putNodesIntoMap();
        putChildIntoParent(nodeMap);
    }
    
    /**将TreeNodes放入map中
     * @return 包含TreeNode的HashMap
     */
    protected HashMap<Integer, TreeNode> putNodesIntoMap() {        
        HashMap<Integer, TreeNode> nodeMap = new HashMap<Integer, TreeNode>();
        Iterator<TreeNode> it = tempNodeList.iterator();
        while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            int keyId = treeNode.getId();  
            nodeMap.put(keyId, treeNode);
        }
        return nodeMap;
    }
    protected void putChildIntoParent(HashMap<Integer, TreeNode> nodeMap) {
    	Iterator<TreeNode> it = nodeMap.values().iterator();
        while (it.hasNext()) {
            TreeNode treeNode = (TreeNode) it.next();
            int parentId = treeNode.getPid();
            if (nodeMap.containsKey(parentId)) {
                TreeNode parentNode = (TreeNode) nodeMap.get(parentId);
                if (parentNode != null) {
                    parentNode.addChildNode(treeNode);
                    System.out.println(treeNode.getId()+" => "+parentNode.getId());
                }
            }
        }
    	Iterator<TreeNode> it2 = nodeMap.values().iterator();
        while (it2.hasNext()) {
            TreeNode treeNode = (TreeNode) it2.next();
            if(treeNode.getParentNode()==null)
            	getRootNodes().add(treeNode);
        }
//        print();
    }
    //输出调试
    @SuppressWarnings("unused")
	private void print(){
    	Iterator<TreeNode> it = getRootNodes().iterator();
    	Integer level = 0;
    	while (it.hasNext()) {
            TreeNode child = (TreeNode) it.next();
            System.out.println(child.getNodeName());
            if(child.getChildList()!=null)
            	print(child,level);
        }
    }
    private void print(TreeNode tn,Integer parentLevel){
    	Integer nowLevel = parentLevel+1;
    	Iterator<TreeNode> it = tn.getChildList().iterator();
    	while (it.hasNext()) {
            TreeNode child = (TreeNode) it.next();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nowLevel; i++) {
            	sb.append("\t");
			}
            sb.append(child.getNodeName());
            System.out.println(sb.toString());
            if(child.getChildList()!=null)
            	print(child,nowLevel);
        }
    }
    public List<TreeNode> getRootNodes() {
    	if (this.rootNodes==null) 
    		this.rootNodes = new ArrayList<TreeNode>();
        return this.rootNodes;        
    }

    public void setRootNodes(List<TreeNode> rootNodes) {
        this.rootNodes = rootNodes;
    }

    public List<TreeNode> getTempNodeList() {
        return tempNodeList;
    }

    public void setTempNodeList(List<TreeNode> tempNodeList) {
        this.tempNodeList = tempNodeList;
    }

}