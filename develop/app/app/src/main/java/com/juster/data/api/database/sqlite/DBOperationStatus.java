package com.juster.data.api.database.sqlite;

/**
 * Created by Anurag Singh on 30/11/15 4:02 PM.
 */
public class DBOperationStatus {

    private int result;
    private long _id;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public interface IQueryStatus{
        int FAIL=0;
        int SUCCESS=1;
    }
}
