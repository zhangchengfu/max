package com.laozhang.maxweb.tree;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MenuTree {

    public static final String STATUS_A = "1";

    private List<Menu> resourceList = new ArrayList();

    private List<Menu> flatChildList = new ArrayList();

    private boolean dropRoot = false;

    private boolean dropDisabled = false;

    public MenuTree(List<Menu> resourceList) {
        this.resourceList = resourceList;
    }

    public MenuTree(List<Menu> resourceList, boolean dropRoot) {
        this.resourceList = resourceList;
        this.dropRoot = dropRoot;
    }

    public MenuTree() {}

    /**
     * 构建随机树
     * @param part 部分资源列表
     * @param all 全部资源列表
     * @return
     */
    public List<Menu> getRandomTree(List<Menu> part, List<Menu> all) {
        List<Menu> result = new ArrayList<Menu>();
        resourceList = all;
        if (all == null || all.size() == 0 || part == null || part.size() == 0) {
            return result;
        }
        flatChildList.addAll(part);
        for (Menu resource : part) {
            handle(resource);
        }
        // 排序
        Collections.sort(flatChildList, new Comparator<Menu>() {
            public int compare(Menu o1, Menu o2) {
                if (StringUtils.isEmpty(o1.getSort()) || StringUtils.isEmpty(o2)) {
                    return 0;
                }

                return new Integer(o1.getSort()).compareTo(new Integer(o2.getSort()));
            }
        });
        return randomBuilTree();
    }

    private List<Menu> randomBuilTree(){
        List<Menu> treeMenus =new ArrayList<Menu>();
        List<Menu> root = new ArrayList();
        for (Menu menuNode : flatChildList) {
            if(menuNode.getParentResCode() == null || StringUtils.isEmpty(menuNode.getParentResCode())) {
                if (dropDisabled) {
                    if (STATUS_A.equals(menuNode.getResStatus())) {
                        root.add(menuNode);
                    }
                } else {
                    root.add(menuNode);
                }
            }
        }
        List<Menu> droppedRoots = new ArrayList();
        if (dropRoot) {
            for (Menu resource : root) {
                for (Menu menuNode : flatChildList) {
                    if (null != menuNode.getParentResCode()
                            && menuNode.getParentResCode().equals(resource.getResCode())) {
                        if (dropDisabled) {
                            if (STATUS_A.equals(menuNode.getResStatus())) {
                                droppedRoots.add(menuNode);
                            }
                        } else {
                            droppedRoots.add(menuNode);
                        }
                    }
                }
            }
        }
        for(Menu menuNode : dropRoot == true ? droppedRoots : root) {
            menuNode=buildChilTree2(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    /**
     *
     * @param pNode 当前节点
     * @return
     */
    private Menu buildChilTree2(Menu pNode){
        List<Menu> chilMenus =new ArrayList<Menu>();
        for(Menu menuNode : flatChildList) {
            if(menuNode.getParentResCode() != null
                    && menuNode.getParentResCode().equals(pNode.getResCode())) {
                menuNode.setParentResName(pNode.getResName());
                if (dropDisabled) {
                    if (STATUS_A.equals(menuNode.getResStatus())) {
                        chilMenus.add(buildChilTree2(menuNode));
                    }
                } else {
                    chilMenus.add(buildChilTree2(menuNode));
                }
            }
        }
        pNode.setChild(chilMenus);
        return pNode;
    }

    /**
     * 获取所有上级父节点
     * @param resource 资源
     */
    private void handle(Menu resource) {
        for (Menu item : resourceList) {
            if (item.getResCode().equals(resource.getParentResCode())){
                if (!hasContain(item)) {
                    flatChildList.add(item);
                }
                handle(item);
            }
        }
    }

    /**
     * 是否包含
     * @param resource 资源
     * @return
     */
    private boolean hasContain(Menu resource) {
        boolean flag = false;
        for (Menu item : flatChildList) {
            if (item.getResCode().equals(resource.getResCode())) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 获取子节点（非树形）
     * @param resCode 资源编码
     * @param recursion 是否需要递归查找
     * @return
     */
    public List<Menu> getChildFlat(String resCode, boolean recursion) {
        buildChildFlat(resCode, recursion);
        return flatChildList;
    }

    /**
     * 构建子节点（非树形）
     * @param resCode 资源编码
     * @param recursion 是否需要递归查找
     */
    private void buildChildFlat(String resCode, boolean recursion) {
        for(Menu menuNode : resourceList) {
            if (null !=  menuNode.getParentResCode()
                    && menuNode.getParentResCode().equals(resCode)) {
                flatChildList.add(menuNode);
                if (recursion) {
                    buildChildFlat(menuNode.getResCode(), recursion);
                }
            }
        }
    }

    /**
     * 获取子节点树形结构
     * @param resCode
     * @return
     */
    public List<Menu> getChildTree(String resCode) {
        List<Menu> childMenuLists =new  ArrayList<Menu>();
        for(Menu menuNode : resourceList) {
            if(null != menuNode.getParentResCode()
                    && menuNode.getParentResCode().equals(resCode)) {
                childMenuLists.add(menuNode);
            }
        }
        List<Menu> treeMenus =new  ArrayList<Menu>();
        for(Menu menuNode : childMenuLists) {
            menuNode=buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    /**
     * 建立树形结构
     * @return
     */
    public List<Menu> builTree(){
        List<Menu> treeMenus =new  ArrayList<Menu>();
        for(Menu menuNode : getRootNode()) {
            menuNode=buildChilTree(menuNode);
            treeMenus.add(menuNode);
        }
        return treeMenus;
    }

    /**
     * 递归，建立子树形结构
     * @param pNode
     * @return
     */
    private Menu buildChilTree(Menu pNode){
        List<Menu> chilMenus =new ArrayList<Menu>();
        for(Menu menuNode : resourceList) {
            if(null != menuNode.getParentResCode() && menuNode.getParentResCode().equals(pNode.getResCode())) {
                if (dropDisabled) {
                    if (menuNode.getResStatus().equals(STATUS_A)) {
                        chilMenus.add(buildChilTree(menuNode));
                    }
                } else {
                    chilMenus.add(buildChilTree(menuNode));
                }
            }
        }
        pNode.setChild(chilMenus);
        return pNode;
    }

    /**
     * 获取根节点
     * @return
     */
    private List<Menu> getRootNode() {
        List<Menu> rootMenuLists =new  ArrayList<Menu>();
        for(Menu menuNode : resourceList) {
            if(menuNode.getParentResCode() == null || StringUtils.isEmpty(menuNode.getParentResCode())) {
                if (dropDisabled) {
                    if (menuNode.getResStatus().equals(STATUS_A)) {
                        rootMenuLists.add(menuNode);
                    }
                } else {
                    rootMenuLists.add(menuNode);
                }
            }
        }
        if (!dropRoot) {
            return rootMenuLists;
        } else {
            List<Menu> list =new  ArrayList();
            for (Menu resource : rootMenuLists) {
                for (Menu item : resourceList) {
                    if (null != item.getParentResCode()
                            && item.getParentResCode().equals(resource.getResCode())) {
                        list.add(item);
                    }
                }
            }
            return list;
        }
    }

    public boolean isDropRoot() {
        return dropRoot;
    }

    public void setDropRoot(boolean dropRoot) {
        this.dropRoot = dropRoot;
    }

    public boolean isDropDisabled() {
        return dropDisabled;
    }

    public void setDropDisabled(boolean dropDisabled) {
        this.dropDisabled = dropDisabled;
    }
}
