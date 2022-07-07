package com.kk.mapper;

import com.kk.pojo.Employee;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EmployeeMapper {
    public abstract Collection<Employee> getAll();
    public abstract Employee get(Integer id);
    public abstract int delete(Integer id);
    public abstract int insert(Employee employee);
    public abstract int update(Employee employee);
}
