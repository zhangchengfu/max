
package com.laozhang.mybatisPlus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;


/**
 * 代码生成器演示
 */
public class MpGenerator {
 
    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 选择 freemarker 引擎，默认 Veloctiy
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
 
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setAuthor("laozhang");
        gc.setOutputDir("D://PROJECT/WORKSPACE/base-info-conf-service/src/main/java");
        gc.setFileOverride(true);
        // 是否覆盖同名文件，默认是false
        gc.setActiveRecord(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setEnableCache(false);
        // XML 二级缓存
        gc.setBaseResultMap(true);
        // XML ResultMap
        gc.setBaseColumnList(false);
        // XML columList
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        // gc.setMapperName("%sDao");
        // gc.setXmlName("%sDao");
        // gc.setServiceName("MP%sService");
        // gc.setServiceImplName("%sServiceDiy");
        // gc.setControllerName("%sAction");
        mpg.setGlobalConfig(gc);
 
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });

        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("taobang");
        dsc.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=utf8");
        mpg.setDataSource(dsc);
        //把数据源配置添加到代码生成器主类
 
        //数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表等信息
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表映射到实体的命名策略:下划线转驼峰
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 自定义继承类全称，带包名 (以下为示例)
        strategy.setEntityLombokModel(true);
        // 【实体】是否为lombok模型（默认 false）
        strategy.setRestControllerStyle(true);
        // 生成 @RestController 控制器
       // strategy.setSuperEntityColumns("id");
        // 写于父类中的公共字段
        /*strategy.setInclude("dictionary","employer_undertaker","unit_labor_cost","org_rule_conf","bus_rule_conf",
                "bus_rule_dic","bus_rule_res","project_manager","project_manager_level","project_manager_attach",
                "dev_activity","dev_activity_version");*/
        // 需要包含的表名，允许正则表达式（与exclude二选一配置）
        //strategy.setExclude("m_fnd_user1");
        strategy.setInclude("user");
        // 需要排除的表名，允许正则表达式
        strategy.setControllerMappingHyphenStyle(true);
        // 驼峰转连字符
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 是否生成实体时，生成字段注解 默认false;
        mpg.setStrategy(strategy);
        // 把策略配置添加到代码生成器主类

        
        
        //向代码生成器主类上配置模板引擎，这是是freemarker，mpg.execute()方法就是执行生成代码类。
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
 
        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.laozhang.mybatisPlus");
        //pc.setModuleName("auth");
        pc.setService("service");
        // Service包名
        pc.setEntity("entity");
        // Entity包名
        pc.setServiceImpl("service.impl");
        // ServiceImpl包名
        pc.setMapper("mapper");
        // Mapper包名
        pc.setController("controller");
        // Contoller包名
        mpg.setPackageInfo(pc);
        
        InjectionConfig cfg = new InjectionConfig() {
            /*
             * 使用initMap是抽象方法，所以必须被重写，
             * 注入自定义 Map 对象(注意需要setMap放进去)，该对象可以传递到模板引擎通过 cfg.xxx 引用
             */
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>(16);
                map.put("mapperId", "testMapperId");
                this.setMap(map);
            }
        };
        mpg.setCfg(cfg);

        mpg.execute();
 

    }
 
}