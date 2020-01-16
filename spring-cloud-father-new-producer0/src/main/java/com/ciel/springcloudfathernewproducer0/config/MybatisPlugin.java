package com.ciel.springcloudfathernewproducer0.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.commons.lang.math.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MybatisPlugin {

    @Bean
    public PaginationInterceptor paginationInterceptor() {  //自动分页
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }

    @Bean //乐观锁插件 //仅支持 updateById(id) 与 update(entity, wrapper) 方法
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }


    @Bean //分布式id生成器
    public IdentifierGenerator identifierGenerator(){

        return new IdentifierGenerator() {
            @Override
            public Number nextId(Object entity) {

                //可以将当前传入的class全类名来作为bizKey,或者提取参数来生成bizKey进行分布式Id调用生成.
                String bizKey = entity.getClass().getName();
                //根据bizKey调用分布式ID生成

                long id = bizKey.hashCode()+entity.hashCode() + System.currentTimeMillis() ;

                //返回生成的id值即可.
                return id;

            }
        };
    }


//    /**
//     * 攻击 SQL 阻断解析器
//     */
//    @Bean
//    public PaginationInterceptor paginationInterceptor(){
//        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
//        List<ISqlParser> sqlParserList = new ArrayList<>();
//        sqlParserList.add(new BlockAttackSqlParser());
//
//        paginationInterceptor.setSqlParserList(sqlParserList);
//        return new PaginationInterceptor();
//    }



}
