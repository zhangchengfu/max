package com.laozhang.max_interview;

import com.google.common.collect.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

public class CommonsUtil {

    public static void main(String[] args) throws ParseException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, IOException {

        // 1 Java自带工具方法
        // 1.1 List集合拼接成以逗号分隔的字符串
        // 如何把list集合拼接成以逗号分隔的字符串 a,b,c
        List<String> list = Arrays.asList("a", "b", "c");
        // 第一种方法，可以用stream流
        String join = list.stream().collect(Collectors.joining(","));
        System.out.println(join); // 输出 a,b,c
        // 第二种方法，其实String也有join方法可以实现这个功能
        String join2 = String.join(",", list);
        System.out.println(join2); // 输出 a,b,c

        // 1.2 比较两个字符串是否相等，忽略大小写
        if (join.equalsIgnoreCase(join2)) {
            System.out.println("相等");
        }

        // 1.3 比较两个对象是否相等
        Objects.equals(join, join2);

        // 1.4 两个List集合取交集
        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        List<String> list2 = new ArrayList<>();
        list2.add("a");
        list2.add("b");
        list2.add("d");
        list1.retainAll(list2);
        System.out.println(list1); // 输出[a, b]


        // 2. apache commons工具类库
        /**
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.12.0</version>
        </dependency>*/
        // 2.1.1 字符串判空
        StringUtils.isBlank(join);

        // 2.1.2 首字母转成大写
        String str = "yideng";
        String capitalize = StringUtils.capitalize(str);
        System.out.println(capitalize); // 输出Yideng

        // 2.1.3 重复拼接字符串
        String str2 = StringUtils.repeat("ab", 2);
        System.out.println(str2); // 输出abab

        // 2.1.4 格式化日期
        // Date类型转String类型
        String date = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        System.out.println(date); // 输出 2021-05-01 01:01:01
        // String类型转Date类型
        Date date1 = DateUtils.parseDate("2021-05-01 01:01:01", "yyyy-MM-dd HH:mm:ss");
        // 计算一个小时后的日期
        Date date2 = DateUtils.addHours(new Date(), 1);

        // 2.1.5 包装临时对象
        // 当一个方法需要返回两个及以上字段时，我们一般会封装成一个临时对象返回，现在有了Pair和Triple就不需要了
        // 返回两个字段
        ImmutablePair<Integer, String> pair = ImmutablePair.of(1, "yideng");
        System.out.println(pair.getLeft() + "," + pair.getRight()); // 输出 1,yideng
        // 返回三个字段
        ImmutableTriple<Integer, String, Date> triple = ImmutableTriple.of(1, "yideng", new Date());
        System.out.println(triple.getLeft() + "," + triple.getMiddle() + "," + triple.getRight()); // 输出 1,yideng,Wed Apr 07 23:30:00 CST 2021

        // 2.2 commons-collections 集合工具类
        /*<dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-collections4</artifactId>
            <version>4.4</version>
        </dependency>*/
        // 2.2.1 集合
        List<String> listA = new ArrayList<>();
        listA.addAll(Arrays.asList("a", "b", "c"));
        List<String> listB = new ArrayList<>();
        listB.addAll(Arrays.asList("d", "b", "c"));
        // 两个集合取交集
        Collection<String> collection1 = CollectionUtils.retainAll(listA, listB);
        System.out.println(collection1);
        // 两个集合取并集
        Collection<String> collection2 = CollectionUtils.union(listA, listB);
        System.out.println(collection2);
        // 两个集合取差集
        Collection<String> collection3 = CollectionUtils.subtract(listA, listB);
        System.out.println(collection3);

        // 2.3 common-beanutils 操作对象
        /*<dependency>
            <groupId>commons-beanutils</groupId>
            <artifactId>commons-beanutils</artifactId>
            <version>1.9.4</version>
        </dependency>*/
        // 对象和map互转
        User user = new User();
        user.setId(1);
        user.setName("yideng");
        // 对象转map
        Map<String, String> map = org.apache.commons.beanutils.BeanUtils.describe(user);
        System.out.println(map); // 输出 {"id":"1","name":"yideng"}
        // map转对象
        User newUser = new User();
        org.apache.commons.beanutils.BeanUtils.populate(newUser, map);
        System.out.println(newUser); // 输出 {"id":1,"name":"yideng"}

        // 2.4 commons-io 文件流处理
        /*<dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.8.0</version>
        </dependency>*/
        // 文件处理
        File file = new File("demo1.txt");
        // 读取文件
        //List<String> lines = FileUtils.readLines(file, Charset.defaultCharset());
        // 写入文件
        //FileUtils.writeLines(new File("demo2.txt"), lines);
        // 复制文件
        File srcFile = new File("src.txt");
        File destFile = new File("dest.txt");
        //FileUtils.copyFile(srcFile, destFile);


        // 3. Google Guava 工具类库
        /*<dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>30.1.1-jre</version>
        </dependency>*/
        // 3.1 创建集合
        List<String> list5 = Lists.newArrayList();
        List<Integer> list6 = Lists.newArrayList(1, 2, 3, 4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21);
        // 反转list
        List<Integer> reverse = Lists.reverse(list6);
        System.out.println(reverse); // 输出 [3, 2, 1]
        // list集合元素太多，可以分成若干个集合，每个集合10个元素
        List<List<Integer>> partition = Lists.partition(list6, 10);
        for (List<Integer> item : partition) {
            System.out.println(item);
        }
        Map<String, String> map5 = Maps.newHashMap();
        Set<String> set = Sets.newHashSet();

        // 3.2 黑科技集合
        // 3.2.1 Multimap 一个key可以映射多个value的HashMap
        Multimap<String, Integer> map6 = ArrayListMultimap.create();
        map6.put("key", 1);
        map6.put("key", 2);
        Collection<Integer> values = map6.get("key");
        System.out.println(map6); // 输出 {"key":[1,2]}
        // 还能返回你以前使用的臃肿的Map
        Map<String, Collection<Integer>> collectionMap = map6.asMap();
        System.out.println(collectionMap);

        // 3.2.2 BiMap 一种连value也不能重复的HashMap
        BiMap<String, String> biMap = HashBiMap.create();
        // 如果value重复，put方法会抛异常，除非用forcePut方法
        biMap.put("key","value");
        System.out.println(biMap); // 输出 {"key":"value"}
        // 既然value不能重复，何不实现个翻转key/value的方法，已经有了
        BiMap<String, String> inverse = biMap.inverse();
        System.out.println(inverse); // 输出 {"value":"key"}

        // 3.2.3 Table 一种有两个key的HashMap
        // 一批用户，同时按年龄和性别分组
        Table<Integer, String, String> table = HashBasedTable.create();
        table.put(18, "男", "yideng");
        table.put(18, "女", "Lily");
        System.out.println(table.get(18, "男")); // 输出 yideng
        // 这其实是一个二维的Map，可以查看行数据
        Map<String, String> row = table.row(18);
        System.out.println(row); // 输出 {"男":"yideng","女":"Lily"}
        // 查看列数据
        Map<Integer, String> column = table.column("男");
        System.out.println(column); // 输出 {18:"yideng"}

        // 3.2.4 Multiset 一种用来计数的Set
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("apple");
        multiset.add("apple");
        multiset.add("orange");
        System.out.println(multiset.count("apple")); // 输出 2
        // 查看去重的元素
        Set<String> set5 = multiset.elementSet();
        System.out.println(set5); // 输出 ["orange","apple"]
        // 还能查看没有去重的元素
        Iterator<String> iterator = multiset.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        // 还能手动设置某个元素出现的次数
        multiset.setCount("apple", 5);
    }

    public static class User {
        private Integer id;
        private String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
