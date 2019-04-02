package com.tulun.employee.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 阿劼
 */
public class Employee implements Serializable {
    /* */
    private Integer id;

    /* */
    private String ename;

    /* */
    private Integer deptno;

    /* */
    private Date hiredate;

    /* */
    private BigDecimal sal;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public Integer getDeptno() {
        return deptno;
    }

    public void setDeptno(Integer deptno) {
        this.deptno = deptno;
    }

    public Date getHiredate() {
        return hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    public BigDecimal getSal() {
        return sal;
    }

    public void setSal(BigDecimal sal) {
        this.sal = sal;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append(", id=").append(id);
        sb.append(", ename=").append(ename);
        sb.append(", deptno=").append(deptno);
        sb.append(", hiredate=").append(hiredate);
        sb.append(", sal=").append(sal);
        sb.append("]");
        return sb.toString();
    }
}