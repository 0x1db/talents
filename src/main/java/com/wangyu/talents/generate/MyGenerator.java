package com.wangyu.talents.generate;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.DbType;

/**
 * @author wangyu
 * @Date 2019/7/7 0:15
 * @Version 1.0
 **/
public class MyGenerator {

  public static void main(String[] args) {
    AutoGenerator mpg = new AutoGenerator();
    // 全局配置
    GlobalConfig gc = new GlobalConfig();
    gc.setAuthor("wangyu");
    gc.setOutputDir("D://workspace/spring-boot-mybatis/src/main/java");
    ;// 是否覆盖同名文件，默认是false
    gc.setFileOverride(false);
    // 不需要ActiveRecord特性的请改为false
    gc.setActiveRecord(true);
    // XML 二级缓存
    gc.setEnableCache(false);
    // XML ResultMap
    gc.setBaseResultMap(true);
    // XML columList
    gc.setBaseColumnList(false);

    mpg.setGlobalConfig(gc);

    // 数据源配置
    DataSourceConfig dsc = new DataSourceConfig();
    dsc.setDbType(DbType.MYSQL);
    dsc.setTypeConvert(new MySqlTypeConvert() {
      // 自定义数据库表字段类型转换【可选】
      @Override
      public DbColumnType processTypeConvert(String fieldType) {
        System.out.println("转换类型：" + fieldType);
        // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
        return super.processTypeConvert(fieldType);
      }
    });
    dsc.setDriverName("com.mysql.jdbc.Driver");
    dsc.setUsername("root");
    dsc.setPassword("123456");
    dsc.setUrl("jdbc:mysql://localhost:3306/ease-run?useUnicode=true&characterEncoding=utf8");

    mpg.setDataSource(dsc);

    //策略配置
    StrategyConfig strategy = new StrategyConfig();

  }
}
