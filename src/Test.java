import com.alibaba.fastjson.JSON;
import dao.Gift;
import dao.User;
import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        User user1 = new User();
        Gift buyGift = new Gift();
        Gift gift = new Gift();
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dd = sdf.format(date);// new Date()为获取当前系统时间
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        String orderId = newDate + result;
    }
//    String h =
}

//{"res":true,"data":"+dd","orderid":"+orderId"}
