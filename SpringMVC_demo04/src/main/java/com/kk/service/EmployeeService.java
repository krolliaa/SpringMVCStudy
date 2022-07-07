package com.kk.service;

import com.kk.mapper.EmployeeMapper;
import com.kk.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    public Collection<Employee> getAll() {
        return employeeMapper.getAll();
    }

    public Employee get(Integer id) {
        return employeeMapper.get(id);
    }

    public int delete(Integer id) {
        return employeeMapper.delete(id);
    }

    public int insert(Employee employee) {
        return employeeMapper.insert(employee);
    }

    public int update(Employee employee) {
        return employeeMapper.update(employee);
    }
}
