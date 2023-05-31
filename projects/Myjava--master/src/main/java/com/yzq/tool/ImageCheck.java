package com.yzq.tool;

import com.baidu.aip.contentcensor.AipContentCensor;
import com.baidu.aip.contentcensor.EImgType;
import org.json.JSONObject;

import java.io.File;

public class ImageCheck {
  //设置APPID/AK/SK
  private static final String APP_ID = "19461549";
  private static final String API_KEY = "SGFRbDuX3NkevHomwKTqg7w9";
  private static final String SECRET_KEY = "w5q2wy3GCTQE076D7Aoc4vGYHU70H4Gr";

  /**
   * @param path 图片地址
   */
  public static String check(String path) {
    if (!new File(path).exists()) {
      throw new NullPointerException("图片不存在");
    }
    // 初始化一个AipImageCensor
    AipContentCensor client = new AipContentCensor(APP_ID, API_KEY, SECRET_KEY);
    // 可选：设置网络连接参数
    client.setConnectionTimeoutInMillis(2000);
    client.setSocketTimeoutInMillis(60000);
    // 调用接口
    JSONObject response = client.imageCensorUserDefined(path, EImgType.FILE, null);
    String str1 = response.toString().substring(1);
    String str2 = str1.split(",", 2)[0].split(":")[1];
    String str = str2.substring(1, str2.length() - 1);
    return str;
  }

  public static void main(String[] args) {
    ImageCheck check = new ImageCheck();
    String path = "C:\\Users\\23919\\Desktop\\20200403095832687.jpg";
    String s = check.check(path);
    System.out.println(s);
  }
}
