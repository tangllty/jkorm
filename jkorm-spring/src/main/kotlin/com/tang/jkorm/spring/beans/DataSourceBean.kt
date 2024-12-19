package com.tang.jkorm.spring.beans

import com.tang.jkorm.datasource.defaults.DefaultDataSourceFactory
import com.tang.jkorm.io.Resources
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean
import javax.sql.DataSource

/**
 * DataSource bean
 *
 * @author Tang
 */
class DataSourceBean(private var resource: String) : FactoryBean<DataSource>, InitializingBean {

    private lateinit var dataSource: DataSource

    override fun getObject(): DataSource {
        return dataSource
    }

    override fun getObjectType(): Class<*> {
        return dataSource::class.java
    }

    override fun afterPropertiesSet() {
        val resource = Resources.getResourceAsStream(resource)
        val datasource = Resources.getDataSourceProperties(resource)
        val dataSourceFactory = DefaultDataSourceFactory(datasource)
        dataSource = dataSourceFactory.getDataSource()
    }

}
