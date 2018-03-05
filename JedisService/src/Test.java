import com.xlbs.jedis.utils.JedisGlobal;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {
        try {
            Map<String,String> tem = new HashMap<>();
            tem.put("212","xlbs");
            tem.put("92","hejianjun");
            tem.put("33","xlbs");
            JedisGlobal.JedisUtil_DATA.updateMapToRedis("UserInfo",tem);
//            List<String> list = new ArrayList<>();
//            list.add("USER_NO:xlbs.com,USER_NAME:xlbs");
//            list.add("USER_NO:zry,USER_NAME:xlbs");
//            JedisGlobal.JedisUtil.insertListToRedis("UserInfo2",list);
            Map<String,String> map1 = JedisGlobal.JedisUtil_DATA.queryJedisMapAllObj("UserInfo");
            System.out.println(map1.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
