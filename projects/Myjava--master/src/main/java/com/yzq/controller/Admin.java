package com.yzq.controller;

import com.yzq.pojo.OrderForm;
import com.yzq.pojo.ShopInformation;
import com.yzq.pojo.UserInformation;
import com.yzq.response.BaseResponse;
import com.yzq.service.OrderFormService;
import com.yzq.service.ShopInformationService;
import com.yzq.service.UserInformationService;
import com.yzq.service.UserPasswordService;
import com.yzq.tool.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 后台管理面板
 * 负责处理后台界面获取所有订单，用户, 商品信息的请求
 * 以及处理更新、删除的请求
 */
@Slf4j
@Controller
public class Admin {

  @Resource
  UserInformationService usersService;

  @Resource
  OrderFormService orderFormService;

  @Resource
  UserPasswordService userPasswordService;

  @Resource
  ShopInformationService shopInformationService;

  /**
   * 获取订单信息
   *
   * @return
   */
  @RequestMapping(value = "/admin.do")
  public String getAdmins(HttpServletRequest request, Model model) {
    UserInformation userInfo = (UserInformation) request.getSession().getAttribute("userInformation");
    //判断是否登录
    if (StringUtils.getInstance().isNullOrEmpty(userInfo)) {
      return "redirect:login.do";
    }
    //判断当前登录者是否为管理员, 不是则返回主页
    if (!userInfo.isManager()) {
      return "/home.do";
    }
    model.addAttribute("userInformation", userInfo);
    return "page/admin/admin";
  }


  /**
   * 返回所有用户信息
   * 返回layui需要的数据格式
   * {"code":0,"msg":"","count":1000,"data":[{}] }
   *
   * @param request
   * @return
   */

  @RequestMapping(value = "/getAllUsers.do")
  @ResponseBody
  public Map<String, Object> getAllUsers(HttpServletRequest request) {
    log.info("getAllUsers.do");
    //查用户数据
    List<UserInformation> usersList = usersService.selectAll();

    HashMap<String, Object> map = new HashMap<>();
    map.put("code", 0);
    map.put("msg", "success");
    map.put("count", 1000);
    map.put("data", usersList);

    log.info("getAllUsers complete");
    return map;
  }

  /**
   * 返回所有订单信息
   * 返回layui需要的数据格式
   * {"code":0,"msg":"","count":1000,"data":[{}] }
   *
   * @param request
   * @return
   */

  @RequestMapping(value = "/getAllOrders.do")
  @ResponseBody
  public Map<String, Object> getAllOrders(HttpServletRequest request) {
    log.info("getAllOrders.do");
    //查用户数据
    List<OrderForm> ordersList = orderFormService.selectAll();

    HashMap<String, Object> order_map = new HashMap<>();
    order_map.put("code", 0);
    order_map.put("msg", "success");
    order_map.put("count", 1000);
    order_map.put("data", ordersList);

    return order_map;
  }

  /**
   * 返回商品信息
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/getAllShop.do")
  @ResponseBody
  public Map<String, Object> getAllShop(HttpServletRequest request) {
    log.info("getAllShop");
    List<ShopInformation> shopInformationList = shopInformationService.selectAll();
    HashMap<String, Object> shopMap = new HashMap<>();
    shopMap.put("code", 0);
    shopMap.put("msg", "success");
    shopMap.put("count", 1000);
    shopMap.put("data", shopInformationList);
    return shopMap;
  }

  /**
   * 用户删除，(传递id数组)
   *
   * @param list
   * @return
   */

  @RequestMapping(value = "/deleteUsers.do")
  @ResponseBody
  public BaseResponse deleteUsers(@RequestParam(value = "list") int[] list) {
    log.info("deleteUsers.do");
    for (int value : list) {
      //userinformation  删除用户  userpassword 删除用户密码
      int i = usersService.deleteByPrimaryKey(value);
      int j = userPasswordService.deleteByUid(value);

      if (i == 1) {
        log.info("用户" + value + "删除成功!");
      } else {
        log.info("用户" + value + "删除失败!");
        return BaseResponse.fail("fail");
      }
    }
    return BaseResponse.success("success");
  }

  /**
   * 订单删除
   *
   * @param list
   * @return
   */

  @RequestMapping(value = "/deleteOrders.do")
  @ResponseBody
  public BaseResponse deleteOrders(@RequestParam(value = "list") int[] list) {
    log.info("deleteOrders.do");
    for (int value : list) {
      //System.out.println(value);
      OrderForm orderForm = new OrderForm();
      orderForm.setId(value);
      orderForm.setDisplay(0);
      int i = orderFormService.updateByPrimaryKeySelective(orderForm);
      if (i == 1) {
        log.info("订单" + value + "删除成功!");
      } else {
        log.info("订单" + value + "删除失败!");
        return BaseResponse.fail("fail");
      }
    }
    return BaseResponse.success("success");
  }

  @RequestMapping(value = "/deleteShops.do")
  @ResponseBody
  public BaseResponse deleteShops(@RequestParam(value = "list") int[] list) {
    log.info("deleteShops");
    for (int value : list) {
      ShopInformation shopInformation = new ShopInformation();
      shopInformation.setId(value);
      shopInformation.setDisplay(0);
      int i = shopInformationService.updateByPrimaryKeySelective(shopInformation);
      if (i == 1) {
        log.info("商品" + value + "删除成功!");
      } else {
        log.info("商品" + value + "删除失败!");
        return BaseResponse.fail("fail");
      }
    }
    return BaseResponse.success("success");
  }

  /**
   * 用户更新
   *
   * @return
   */

  @RequestMapping(value = "/updateUser.do", method = RequestMethod.POST)
  @ResponseBody
  public BaseResponse updateUser(HttpServletRequest servletRequest) {
    log.info("updateuser.do");
    boolean ok = true;
    UserInformation user = new UserInformation();
    String sid = servletRequest.getParameter("id");
    int id = Integer.valueOf(sid);
    user.setId(id);
    String username = servletRequest.getParameter("username");
    if (username != null && username.length() < 25) {
      username = StringUtils.getInstance().replaceBlank(username);
      user.setUsername(username);
    } else if (username != null && username.length() >= 25) {
      log.info("username error");
      ok = false;
    }
    String realname = servletRequest.getParameter("realname");
    if (realname != null && realname.length() < 25) {
      realname = StringUtils.getInstance().replaceBlank(realname);
      user.setRealname(realname);
    } else if (realname != null && realname.length() >= 25) {
      log.info("realname error");
      ok = false;
    }

    String phone = servletRequest.getParameter("phone");
    if (StringUtils.getInstance().isPhone(phone)) {
      user.setPhone(phone);
    } else {
      log.info("phone error");
      ok = false;
    }

    String s_manager = servletRequest.getParameter("manager");
    int manager = Integer.valueOf(s_manager);
    if (manager == 1 || manager == 0)
      user.setManager(manager == 1 ? true : false);
    else
      ok = false;

    String school = servletRequest.getParameter("school");
    if (school != null && school.length() <= 50) {
      school = StringUtils.getInstance().replaceBlank(school);
      user.setSchool(school);
    } else if (school != null && school.length() > 50) {
      log.info("schol error");
      ok = false;
    }

    String clazz = servletRequest.getParameter("clazz");
    if (clazz != null && clazz.length() < 25) {
      clazz = StringUtils.getInstance().replaceBlank(clazz);
      user.setClazz(clazz);
    } else if (clazz != null && clazz.length() >= 25) {
      log.info("clazz error");
      ok = false;
    }
    String sno = servletRequest.getParameter("sno");
    if (sno != null && sno.length() < 25) {
      sno = StringUtils.getInstance().replaceBlank(sno);
      user.setSno(sno);
    } else if (sno != null && sno.length() >= 25) {
      log.info("sno error");
      ok = false;
    }
    log.info(id + " " + username + " " + realname + " " + manager);
    if (!ok) {
      log.info("参数不合格");
      return BaseResponse.fail("参数不合格");
    }

    int i = usersService.updateByPrimaryKeySelective(user);
    if (i != 1) {
      log.info("更新失败");
      return BaseResponse.fail("更新失败");
    }
    log.info("更新成功");
    return BaseResponse.success("success");
  }

  /**
   * 订单更新
   *
   * @return
   */

  @RequestMapping(value = "/updateOrder.do", method = RequestMethod.POST)
  @ResponseBody
  public BaseResponse updateOrder(HttpServletRequest request) {
    log.info("updateOrder.do");
    OrderForm orderForm = new OrderForm();

    int id = Integer.valueOf(request.getParameter("id"));
    orderForm.setId(id);

    String address = request.getParameter("readdress");
    orderForm.setReaddress(address);

    int i = orderFormService.updateByPrimaryKeySelective(orderForm);
    if (i != 1) {
      log.info("更新失败");
      return BaseResponse.fail("更新失败");
    }
    log.info("更新成功");
    return BaseResponse.success("success");
  }

  @RequestMapping(value = "/updateShop.do", method = RequestMethod.POST)
  @ResponseBody
  public BaseResponse updateShop(HttpServletRequest request) {
    log.info("updateShop");
    ShopInformation shopInformation = new ShopInformation();
    int id = Integer.valueOf(request.getParameter("id"));
    log.info(id+"");
    shopInformation.setId(id);
    int flag = Integer.valueOf(request.getParameter("display"));
    log.info(flag+"");
    if (flag == 1)
      shopInformation.setDisplay(1);
    else
      shopInformation.setDisplay(0);
    int i = shopInformationService.updateByPrimaryKeySelective(shopInformation);
    if (i != 1) {
      log.info("更新失败");
      return BaseResponse.fail("更新失败");
    }
    log.info("更新成功");
    return BaseResponse.success("success");

  }

}
