package com.laozhang.maxweb.tree;

import java.util.ArrayList;
import java.util.List;

public class MenuTest {

    public static void main(String[] args) {
        // 获取树形结构
        List<Menu> menus = new ArrayList<Menu>();
        Menu root = new Menu();
        root.setId(1L);
        root.setResCode("1");
        root.setResName("1");
        menus.add(root);
        Menu menu1 = new Menu();
        menu1.setId(2L);
        menu1.setResCode("2");
        menu1.setResName("2");
        menu1.setParentResCode("1");
        menus.add(menu1);
        Menu menu2 = new Menu();
        menu2.setId(3L);
        menu2.setResCode("3");
        menu2.setResName("3");
        menu2.setParentResCode("1");
        menus.add(menu2);
        Menu menu3 = new Menu();
        menu3.setId(4L);
        menu3.setResCode("4");
        menu3.setResName("4");
        menu3.setParentResCode("2");
        menus.add(menu3);
        MenuTree tree = new MenuTree(menus);
        List list = tree.builTree();

        System.out.println(list);
    }
}
