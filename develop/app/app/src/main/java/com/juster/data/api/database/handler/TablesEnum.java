package com.juster.data.api.database.handler;

public enum TablesEnum {

    ORDER_BY_ASC(" ASC "),
    ORDER_BY_DESC(" DESC ");

    private String mTablesQuery;

    TablesEnum(String tablesQuery) {
        mTablesQuery = tablesQuery;
    }

    public String getTablesQuery() {
        return mTablesQuery;
    }
}
