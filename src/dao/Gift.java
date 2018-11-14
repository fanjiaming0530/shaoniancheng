package dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.omg.PortableServer.LIFESPAN_POLICY_ID;
import redis.clients.jedis.Jedis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Gift {
    private int res;  //状态
    private String pid;
    private String gprice;
    private String gid;   //礼物id
    private String num;   // 数量
    private String col;   //颜色
    private String mod;   //型号
    private String size;   //大小
    private String limit;  //  分页

    public String getGprice() {
        return gprice;
    }

    public void setGprice(String gprice) {
        this.gprice = gprice;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    //连接redis
    private static Jedis jedis = new Jedis("127.0.0.1", 6379);
    private Connector conn = new Connector();
    private Statement sm = null;
    private ResultSet rs = null;

    //数据库查询
    public List<Object> searchgift(String gid) {
        List<Object> list = new ArrayList<>();
        String sql = "select * from gift where" + gid;
        try {
            sm = conn.connect().createStatement();
            rs = sm.executeQuery(sql);
            while (rs.next()) {
                list.add(rs.getString("gift"));
            }
            sm.close();
            conn.connect().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    //    //修改用户数据库

    public  void updategift(String pid, String giftnum){
        String sql="update user set pt="+giftnum+" where uid="+"'"+pid+"'";//生成一条mysql语句
        System.out.println(sql);
        Statement stet = null;
        try {
            stet = conn.connect().createStatement();
            stet.executeUpdate(sql);//执行SQL语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("修改数据库成功");
       //修改库存
    }

    public  void updategiftnum(String gid, String buynum){
        String sql="update gift set giftnum=giftnum-"+buynum+" where gid="+"'"+gid+"'";//生成一条mysql语句
        System.out.println(sql);
        Statement stet = null;
        try {
            stet = conn.connect().createStatement();
            stet.executeUpdate(sql);//执行SQL语句
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("修改数据库成功");

    }
    @Test
//    public void test(){
//        updategift("1","200");
//        updategiftnum("20", "1");
//    }

    //giftid
    //分页
    public String Page(String pid, String limit) {
        List<Object> list = new ArrayList<>();
        list = searchgift(pid);
        String listJson = "";
//    logger.info("------开始分页---------");
        int page = Integer.parseInt(limit);
        if (list.size() >= 10) {
            page = (page - 1) * 10;
            for (int j = page; j < page + 10; j++) {
                listJson = listJson + list.get(j) + "\n";
            }

        }
        return listJson;
    }

    public  boolean buyGift(String pid, String gid) {
        //首先查询该账户id的余额
        System.out.println(jedis.hmget(pid, "pt"));
        String num = jedis.hget(pid, "pt");
        int pt = Integer.parseInt(num);
//        logger.info("当前所有的课时数-------" + coursenum);
        //该课程所需的学时数
        String price = String.valueOf(jedis.hget(gid, "gprice"));
        int gprice = Integer.parseInt(price);
//        logger.info("商品所需的课时数-------" + courseprice);
        if (pt >= gprice) {
            /*对redis进行更改*/
            pt = pt - gprice;
            num = String.valueOf(pt);
            jedis.hset(pid, "pt", num);
//            logger.info("当前所有的课时数-------" + jedis.hget(pid, "coursenum"));
            System.out.println(jedis.hget(pid, "pt"));
            //然后在该账户的已购课程中加入该课程的id
            jedis.hset(pid, "yigou", jedis.hget(gid, "gid"));
            System.out.println("已购礼物： " + jedis.hget(pid, "yigou"));
            //对数据库进行更改
            updategift(pid, num);
            return true;
        } else {
            System.out.println("余额不足");
            return false;
        }
    }
    public Gift getMessage(String json){
        User user = new User();
        Gift gift = new Gift();
        JSONObject object= JSON.parseObject(json);
        gift.setGid((String) object.get("gid"));
        gift.setNum((String) object.get("num"));
        gift.setGprice((String) object.get("gprice"));
        user.getPid(object.get("pid"));
        user.getPt((String) object.get("pt"));
        return gift;
    }


}
