package com.iqb.eatep.ec.contract.ssq.sign.bean;

public class EcGetImageGroupByTypeRequestMessage {

    private Integer id;
    private String sign_type;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }
}
