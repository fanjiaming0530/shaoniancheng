package dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class User {

    private String orderid;
    private String ADD;
    private String pid;  //用户id
    private String pt;   //用户积分
    private boolean res;
    private String data;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getADD() {
        return ADD;
    }

    public void setADD(String ADD) {
        this.ADD = ADD;
    }

    public String getPid(Object pid) {
        return this.pid;
    }

    public String getPt() {
        return pt;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPt(String pt) {
        return pt;
    }

    public void setPt(String pt) {
        this.pt = pt;
    }

    public boolean isRes() {
        return res;
    }

    public void setRes(boolean res) {
        this.res = res;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOrderId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate = sdf.format(new Date());
        String result = "";
        Random random = new Random();
        for (int i = 0; i < 3; i++) {
            result += random.nextInt(10);
        }
        String orderId = newDate + result;
        return orderId;
    }

    public String getDate(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dd = df.format(date);
        return dd;
    }


    public String getjson(){
        String json;
        ArrayList<User> userArrayList = new ArrayList<>();
        User user= new User();
        user.getDate();
        user.getOrderId();
        user.setRes(true);
        userArrayList.add(user);
        json = JSON.toJSONString(userArrayList);
        System.out.println(json);
        return json;
    }
    public User getMessage(String json){
        User user = new User();
        JSONObject object = JSON.parseObject(json);
        user.getPid(object.get("pid"));
        user.getPt((String) object.get("pt"));
        return user;
    }
}
