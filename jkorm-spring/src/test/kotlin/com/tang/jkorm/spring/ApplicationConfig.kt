package com.tang.jkorm.spring

import com.tang.jkorm.session.SqlSession
import com.tang.jkorm.session.factory.SqlSessionFactory
import com.tang.jkorm.spring.annotation.MapperScan
import com.tang.jkorm.spring.beans.DataSourceBean
import com.tang.jkorm.spring.beans.session.SqlSessionBean
import com.tang.jkorm.spring.beans.session.factory.SqlSessionFactoryBean
import com.tang.jkorm.spring.constants.BeanNames
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.support.TransactionTemplate
import javax.sql.DataSource

/**
 * @author Tang
 */
@EnableTransactionManagement
@Configuration
@MapperScan(["com.tang.jkorm.spring.mapper"])
open class ApplicationConfig {

    @Bean(BeanNames.DATA_SOURCE)
    open fun dataSource(): DataSource {
        var dataSource = DataSourceBean("jkorm-config.yml")
        dataSource.afterPropertiesSet()
        return dataSource.getObject()
    }

    @Bean(BeanNames.SQL_SESSION_FACTORY)
    open fun sqlSessionFactory(@Qualifier(BeanNames.DATA_SOURCE) dataSource: DataSource): SqlSessionFactory {
        val sqlSessionFactoryBean = SqlSessionFactoryBean(dataSource)
        sqlSessionFactoryBean.afterPropertiesSet()
        return sqlSessionFactoryBean.getObject()
    }

    @Bean(BeanNames.SQL_SESSION)
    open fun sqlSession(@Qualifier(BeanNames.SQL_SESSION_FACTORY) sqlSessionFactory: SqlSessionFactory): SqlSession {
        return SqlSessionBean(sqlSessionFactory).sqlSession
    }

    @Bean
    open fun platformTransactionManager(dataSource: DataSource): PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource)
    }

    @Bean
    open fun transactionTemplate(transactionManager: PlatformTransactionManager): TransactionTemplate {
        return TransactionTemplate(transactionManager);
    }

}
