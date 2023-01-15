package com.begdev.kaban.utils

class DBContract {


    companion object {

        const val sqlCreateTableTasks: String = "create table tracked (\n" +
                "\t_id integer primary key AUTOINCREMENT,\n" +
                "\ttitle text not null,\n" +
                "\tdescription text,\n" +
                "\tdeadline integer,\n" +
                "\tcolor text DEFAULT 'green',\n" +
                "\trefKey text\n" +
                ")"
        const val qwe: String = "qwe"
    }

}