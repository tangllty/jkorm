package com.tang.jkorm.sql.provider.mysql

import com.tang.jkorm.sql.provider.AbstractSqlProvider
import com.tang.jkorm.sql.provider.ProviderType

/**
 * MySQL SQL provider
 *
 * @author Tang
 */
class MysqlSqlProvider : AbstractSqlProvider() {

    override fun providerType(): ProviderType {
        return ProviderType.MYSQL
    }

}
