package com.tulun.employee;

import com.tulun.employee.bean.Employee;
import com.tulun.employee.dao.EmployeeDao;
import com.tulun.employee.mybatisutil.Utils;
import com.tulun.sqlfactory.TlSqlSession;

/**
 * description：
 *
 * @author ajie
 * data 2018/10/29 16:19
 */
public class Test {

    @org.junit.Test
    public void getAllEmployeeTest(){
        TlSqlSession session = Utils.getSession();
        try{
            EmployeeDao userDao = session.getMapper(EmployeeDao.class);
            Employee employee = userDao.getEmployeeById(1);
            System.out.println(employee);
        } finally {
            session.close();
        }
    }
}
