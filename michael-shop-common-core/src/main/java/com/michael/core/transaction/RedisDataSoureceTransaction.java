package com.michael.core.transaction;

import com.michael.core.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import sun.applet.Main;

/**
 * @description: 
 * @author: Michael
 * @date: 2020/3/3 13:30
 */
public class RedisDataSoureceTransaction {

    /**
     * redis
     */
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 数据源事务管理器
     */
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 构造方法
     */
    public RedisDataSoureceTransaction(StringRedisTemplate stringRedisTemplate, DataSourceTransactionManager dataSourceTransactionManager) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    /**
     * 开始事务(采用默认传播行为)
     */
    public TransactionStatus begin() {
        // 手动begin数据库事务
        // 1.开启数据库的事务 事务传播行为
        TransactionStatus transaction = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        // 2.开启redis事务
        // 开启Redis 事务权限
        stringRedisTemplate.setEnableTransactionSupport(true);
        // 开启事务
        stringRedisTemplate.multi();
        return transaction;
    }

    /**
     * 提交事务
     */
    public void commit(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        // 支持Redis与数据库事务同时提交
        dataSourceTransactionManager.commit(transactionStatus);
    }

    /**
     * 回滚事务
     */
    public void rollback(TransactionStatus transactionStatus) throws Exception {
        if (transactionStatus == null) {
            throw new Exception("transactionStatus is null");
        }
        // 1.回滚数据库事务 redis事务和数据库的事务同时回滚
        dataSourceTransactionManager.rollback(transactionStatus);
    }
}