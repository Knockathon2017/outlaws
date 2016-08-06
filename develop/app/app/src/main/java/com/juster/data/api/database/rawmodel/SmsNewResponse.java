package com.juster.data.api.database.rawmodel;

import java.io.Serializable;

/**
 * Created by deepakj on 4/8/16.
 */
public class SmsNewResponse implements Serializable {
    long id;
    String email_address;
    String phone_number;
    String pin;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
