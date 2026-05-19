package com.example.langchain4jdemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.langchain4jdemo.entity.Appointment;

public interface AppointmentService extends IService<Appointment> {
    Appointment getOne(Appointment appointment);//我们要自己实现判断订单存不存在
}
