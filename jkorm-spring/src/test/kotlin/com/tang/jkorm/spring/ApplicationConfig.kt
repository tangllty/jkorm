package com.tang.jkorm.spring

import com.tang.jkorm.session.SqlSession
import com.tang.jkorm.session.factory.SqlSessionFactory
import com.tang.jkorm.spring.annotation.MapperScan
import com.tang.jkorm.spring.beans.session.SqlSessionBean
import com.tang.jkorm.spring.beans.session.factory.SqlSessionFactoryBean
import com.tang.jkorm.spring.constants.BeanNames
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Tang
 */
@Configuration
@MapperScan(["com.tang.jkorm.spring.mapper"])
open class ApplicationConfig {

    @Bean(BeanNames.SQL_SESSION_FACTORY)
    open fun sqlSessionFactory(): SqlSessionFactory {
        val sqlSessionFactoryBean = SqlSessionFactoryBean()
        sqlSessionFactoryBean.resource = "jkorm-config.yml"
        sqlSessionFactoryBean.afterResourcesSet()
        sqlSessionFactoryBean.afterPropertiesSet()
        return sqlSessionFactoryBean.getObject()
    }

    @Bean(BeanNames.SQL_SESSION)
    open fun sqlSession(@Qualifier(BeanNames.SQL_SESSION_FACTORY) sqlSessionFactory: SqlSessionFactory): SqlSession {
        return SqlSessionBean(sqlSessionFactory).sqlSession
    }

}
