package com.tang.jkorm.constants

/**
 * SQL string constants
 *
 * @author Tang
 */
object SqlString {

    // Punctuation marks
    const val DOT = "."
    const val SPACE = " "
    const val COMMA = ","
    const val COMMA_SPACE = COMMA + SPACE
    const val EQUAL = "="
    const val EQUAL_BRACKET = " = "
    const val PERCENT = "%"
    const val ASTERISK = "*"
    const val SEMICOLON = ";"
    const val UNDERSCORE = "_"
    const val SINGLE_QUOTE = "'"
    const val LEFT_BRACKET = "("
    const val RIGHT_BRACKET = ")"
    const val QUESTION_MARK = "?"

    // SQL keywords
    const val OR = "OR"
    const val IN = "IN"
    const val IS = "IS"
    const val NOT = "NOT"
    const val AND = "AND"
    const val AND_BRACKET = " AND "
    const val SET = " SET "
    const val ASC = " ASC"
    const val DESC = " DESC"
    const val NULL = "NULL"
    const val LIKE = "LIKE"
    const val FROM = "FROM "
    const val WHERE = " WHERE "
    const val LIMIT = " LIMIT "
    const val SELECT = "SELECT "
    const val INSERT = "INSERT "
    const val UPDATE = "UPDATE "
    const val DELETE = "DELETE "
    const val VALUES = " VALUES "
    const val OFFSET = " OFFSET "
    const val ORDER_BY = "ORDER BY "
    const val INTO = "INTO "
    const val INSERT_INTO = INSERT + INTO
    const val DELETE_FROM = DELETE + FROM
    const val ROWS_FETCH_NEXT = " ROWS FETCH NEXT "
    const val ROWS_ONLY = " ROWS ONLY"

    // SQL statement
    const val SELECT_ALL_FROM = SELECT + ASTERISK + SPACE + FROM
    const val SELECT_COUNT_FROM = SELECT + "COUNT(*)" + SPACE + FROM

}