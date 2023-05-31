package com.yzq.controller;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.yzq.pojo.UserInformation;
import com.yzq.response.BaseResponse;
import com.yzq.service.UserInformationService;
import com.yzq.tool.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller
public class SendEmailController {

  @Resource
  private UserInformationService userInformationService;
  private static final Logger log = LoggerFactory.getLogger(SendEmailController.class);

  //send the Email to the phone
  @RequestMapping(value = "/sendCode.do", method = {RequestMethod.POST, RequestMethod.GET})
  @ResponseBody
  public BaseResponse sendEmail(HttpServletRequest req, HttpServletResponse res,
                                @RequestParam String phone, @RequestParam String action,
                                @RequestParam String token, @RequestParam String name) {
    res.setContentType("text/html;charset=UTF-8");
    //token，防止重复提交
    System.out.println("phone:" + phone + "---" + "action:" + action + "----" + "token:" + token + "---" + name + "---");
    String sendCodeToken = (String) req.getSession().getAttribute("token");
    if (StringUtils.getInstance().isNullOrEmpty(sendCodeToken) || !sendCodeToken.equals(token)) {
      return BaseResponse.fail();
    }
    //判断手机号码是否为正确
    if (!StringUtils.getInstance().isPhone(phone)) {
      return BaseResponse.fail(0);
    }
    //如果是忘记密码提交的发送短信
    if ("forget".equals(action)) {
      if (!isUserPhoneExists(phone)) {
        //失败
        return BaseResponse.fail();
      }
    } else if ("register".equals(action)) {
      //失败
      if (isUserPhoneExists(phone)) {
        return BaseResponse.fail(-1);
      }
    }
    //get the random num to phone which should check the phone to judge the phone is belong user
    req.getSession().setAttribute("phone1", phone);
    req.getSession().setAttribute("name1", name);

    getRandomForCodePhone(req, phone);

    String ra = (String) req.getSession().getAttribute("codePhone");

    try {
      String realPhone = phone;

      req.getSession().setAttribute("phone", realPhone);
      return BaseResponse.success();
    } catch (Exception me) {
      me.printStackTrace();
      return BaseResponse.fail();
    }
  }

  // get the random phone`s code
  private void getRandomForCodePhone(HttpServletRequest req, String phone) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 4; i++) {
      sb.append(random.nextInt(10));
    }
    System.out.println("验证码：" + sb.toString());

    // 短信应用SDK AppID
    int appid = 1400344672; // 1400开头
    // 短信应用SDK AppKey
    String appkey = "064cd077a4da4c85face08d7b1e9a00e";

    String smsSign = "yzqfrist";
    // 短信模板ID，需要在短信应用中申请
    int templateId = 606388; // NOTE: 真实的模板ID需要在短信控制台中申请

    try {
      String[] params = {sb.toString(), "5"};//数组具体的元素个数和模板中变量个数必须一致
      SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
      SmsSingleSenderResult result = ssender.sendWithParam("86", phone,
          templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
      System.out.println(result);
    } catch (HTTPException e) {
      // HTTP响应码错误
      e.printStackTrace();
    } catch (JSONException e) {
      // json解析错误
      e.printStackTrace();
    } catch (IOException e) {
      // 网络IO错误
      e.printStackTrace();
    }
    req.getSession().setAttribute("codePhone", sb.toString());
  }


  //To determine whether the user's mobile phone number exists
  private boolean isUserPhoneExists(String phone) {
    boolean result = false;
    try {
      int id = userInformationService.selectIdByPhone(phone);
      if (id == 0) {
        return result;
      }
      UserInformation userInformation = userInformationService.selectByPrimaryKey(id);

      if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
        return false;
      }
      String userPhone = userInformation.getPhone();
      result = !userPhone.equals("");
    } catch (Exception e) {
      e.printStackTrace();
      return result;
    }
    return result;
  }


}

