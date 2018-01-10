package com.unicorn.oemadapter.service;

import com.unicorn.oemadapter.mapper.OrderMapper;
import com.unicorn.oemadapter.model.Order;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;
    public ArrayList<Order> getOrders() {
        return orderMapper.getOrders();
    }

    public void postOrder(final int materialId, final String materialName, final int materialNo, final String oem) {
        String fromAccount = "TAC Inc";
        String toAccount = oem;

        Order order = new Order();
        order.setMaterialId(materialId);
        order.setMaterialName(materialName);
        order.setMaterialNo(materialNo);
        order.setOem(oem);
        if (oem == "Foxconn Technology Group") {
            order.setAmount(contactOem(materialId, materialNo, 8081));
        } else {
            order.setAmount(contactOem(materialId, materialNo, 8082));
        }

        if (transfer(fromAccount, toAccount, order.getAmount())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    putWarehouse(materialId, materialNo);
                }
            }).start();
        }
        orderMapper.add(order);
    }

    public int contactOem(int materialId, int materialNo, int oemPorts) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String content = "{\"id\":"+ materialId +
                ",\"num\":" + materialId + "}";
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder()
                .url("http://10.0.1.52:" + oemPorts + "/oem/order")
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);

        try {
            Response response = call.execute();
            System.out.println(response.body().string());
//            TODO
            return 100;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void putWarehouse(int materialId, int materialNo) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String content = "{\"id\":"+ materialId +
                ",\"num\":" + materialNo + "}";
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder()
                .url("http://10.0.1.52:8080/warehouse/put")
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);

        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean transfer(String fromAccount, String toAccount, int amount) {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        String content = "{\"fromAccount\":\""+ fromAccount +
                "\",\"toAccount\":\"" + toAccount + "\", \"sum\":" + amount + "}";
        RequestBody requestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), content);
        Request request = new Request.Builder()
                .url("http://10.0.1.52:8080/finance/transfer")
                .header("Content-Type", "application/json")
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);

        try {
            Response response = call.execute();
            System.out.println(response.body().string());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
