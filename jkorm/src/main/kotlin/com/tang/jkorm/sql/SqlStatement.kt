package com.tang.jkorm.sql

import com.tang.jkorm.constants.SqlString.QUESTION_MARK
import com.tang.jkorm.utils.Statements
import java.sql.PreparedStatement

/**
 * SQL statement
 *
 * @author Tang
 */
class SqlStatement(

    val sql: String,

    val parameters: MutableList<Any?>

) {

    fun getActualSql(): String {
        var actualSql = sql
        parameters.forEach { parameter ->
            actualSql = actualSql.replaceFirst(QUESTION_MARK, parameter?.let {
                if (it is String) {
                    "'$it'"
                } else {
                    it.toString()
                }
            } ?: "NULL")
        }
        return actualSql
    }

    fun setValues(statement: PreparedStatement) {
        Statements.setValues(statement, parameters)
    }

}