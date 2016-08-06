package com.juster.data.api.database.user.model;

/**
 * Created by deepakj on 2/8/16.
 */
public class UserModel  {

    long rowId;
    long user_id;
    String useremail;
    String phoneNo;
    String password;
    String basic_token;
    String gEmail;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    String gToken;

    public long getRowId() {
        return rowId;
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasic_token() {
        return basic_token;
    }

    public void setBasic_token(String basic_token) {
        this.basic_token = basic_token;
    }

    public String getgEmail() {
        return gEmail;
    }

    public void setgEmail(String gEmail) {
        this.gEmail = gEmail;
    }

    public String getgToken() {
        return gToken;
    }

    public void setgToken(String gToken) {
        this.gToken = gToken;
    }
}
