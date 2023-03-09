package com.xiangbian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiangbian.reggie.entity.Employee;
import com.xiangbian.reggie.mapper.EmployeeMapper;
import com.xiangbian.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
