package com.unicorn.oemadapter.mapper;

import com.unicorn.oemadapter.model.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Mapper
@Service
public interface OrderMapper {
    @Select("select * from orders;")
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "materialId", column = "material_id"),
            @Result(property = "materialName", column = "material_name"),
            @Result(property = "materialNo", column = "material_no")
    })
    ArrayList<Order> getOrders();

    @Select("insert into orders(material_id, material_name, material_no, amount, oem) values (#{materialId}, #{materialName}, #{materialNo}, #{amount}, #{oem})")
    void add(Order order);
}
