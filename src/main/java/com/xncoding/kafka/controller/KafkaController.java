package com.xncoding.kafka.controller;

import com.xncoding.kafka.producer.Ksend;
import com.xncoding.redis.RedisUtil;
import com.xncoding.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class KafkaController {

    @Autowired
    Ksend ksend;

    @GetMapping("/setnx/{key}/{val}")
   public boolean setnx(@PathVariable String key, @PathVariable String val) {
        return RedisUtil.setnx(key, val);
    }

    @GetMapping("/delnx/{key}/{val}")
    public int delnx(@PathVariable String key, @PathVariable String val) {
        return RedisUtil.delnx(key, val);
    }

    @GetMapping("/send")
    public String send(HttpServletRequest request){

        for (int i = 0; i < 10; i++) {
            ksend.sendMessage("pqpqpqpq"+i);
        }
        String uid =  request.getSession().getAttribute("uid").toString();

        return RedisUtil.exists("user:"+uid)?RedisUtil.get("user:"+uid):"not exist !";
    }

    @GetMapping("/set")
    public String set(HttpServletRequest request) {

        String uid = IdUtils.generateSimleUUid();
        request.getSession().setAttribute("uid", uid);
        return  RedisUtil.set("user:"+uid, ""+uid);

    }
}
