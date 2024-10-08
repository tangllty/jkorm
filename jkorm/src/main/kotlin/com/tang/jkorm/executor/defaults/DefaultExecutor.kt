package com.tang.jkorm.executor.defaults

import com.tang.jkorm.executor.Executor
import com.tang.jkorm.logging.getLogger
import com.tang.jkorm.session.Configuration
import com.tang.jkorm.sql.SqlStatement
import com.tang.jkorm.transaction.Transaction
import com.tang.jkorm.utils.ResultSetHandlers
import java.sql.Connection

/**
 * @author Tang
 */
class DefaultExecutor(

    private val configuration: Configuration,

    private val transaction: Transaction

) : Executor {

    companion object {
        private val LOGGER = getLogger
    }

    override fun getConnection(): Connection {
        return transaction.getConnection()
    }

    override fun <T> count(statement: SqlStatement, type: Class<T>): Long {
        val connection = getConnection()
        val preparedStatement = connection.prepareStatement(statement.sql)
        statement.setValues(preparedStatement)
        return runCatching {
            val resultSet = preparedStatement.executeQuery()
            return ResultSetHandlers.getCount(resultSet)
        }.onFailure {
            it.printStackTrace()
            connection.rollback()
        }.also {
            preparedStatement.close()
            LOGGER.info("count prepared statement closed")
        }.getOrDefault(0)
    }

    override fun <T> query(statement: SqlStatement, type: Class<T>): List<T> {
        val connection = getConnection()
        val preparedStatement = connection.prepareStatement(statement.sql)
        statement.setValues(preparedStatement)
        return runCatching {
            val resultSet = preparedStatement.executeQuery()
            return ResultSetHandlers.getList(resultSet, type)
        }.onFailure {
            it.printStackTrace()
            connection.rollback()
        }.also {
            preparedStatement.close()
            LOGGER.info("insert prepared statement closed")
        }.getOrDefault(emptyList())
    }

    override fun update(statement: SqlStatement, parameter: Any): Int {
        val connection = getConnection()
        val preparedStatement = connection.prepareStatement(statement.sql)
        statement.setValues(preparedStatement)
        return runCatching {
            preparedStatement.executeUpdate()
        }.onFailure {
            it.printStackTrace()
            connection.rollback()
        }.also {
            preparedStatement.close()
            LOGGER.info("update prepared statement closed")
        }.getOrDefault(0)
    }

    override fun commit() {
        transaction.commit()
    }

    override fun rollback() {
        transaction.rollback()
    }

    override fun close() {
        transaction.close()
    }

}
