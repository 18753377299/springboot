package com.example.po;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class Account implements Serializable {
    private Integer accountid;

    private Integer deptid;

    private String amount;

    private static final long serialVersionUID = 1L;

    public Integer getAccountid() {
        return accountid;
    }

    public void setAccountid(Integer accountid) {
        this.accountid = accountid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", accountid=").append(accountid);
        sb.append(", deptid=").append(deptid);
        sb.append(", amount=").append(amount);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}