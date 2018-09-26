/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hpg.common.model.message;

import java.io.Serializable;

/**
 * Message sent.... ?
 *
 * @author trungpt
 */
public class LoginMessage implements Serializable {

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "LoginMessage{" + "msg=" + msg + '}';
    }
}
