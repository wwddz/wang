package com.yzq.controller;

import com.yzq.bean.*;
import com.yzq.pojo.*;
import com.yzq.response.BaseResponse;
import com.yzq.service.*;
import com.yzq.token.TokenProccessor;
import com.yzq.tool.ImageCheck;
import com.yzq.tool.SaveSession;
import com.yzq.tool.SensitiveWordUtil;
import com.yzq.tool.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/*import com.yzq.tool.OCR;
import com.yzq.tool.Pornographic;*/

/**
 * 用户控制器，包含登录，退出，查看购物车, 查看订单等等请求的处理操作
 * userController
 */

@Controller
@Slf4j
public class UserController {

  @Resource
  private UserInformationService userInformationService;
  @Resource
  private UserPasswordService userPasswordService;
  @Resource
  private UserCollectionService userCollectionService;
  @Resource
  private UserReleaseService userReleaseService;
  @Resource
  private BoughtShopService boughtShopService;
  @Resource
  private UserWantService userWantService;
  @Resource
  private ShopCarService shopCarService;
  @Resource
  private OrderFormService orderFormService;
  @Resource
  private GoodsOfOrderFormService goodsOfOrderFormService;
  @Resource
  private UserStateService userStateService;
  @Resource
  private ShopInformationService shopInformationService;
  @Resource
  private GoodsCarService goodsCarService;
  @Resource
  private SpecificeService specificeService;
  @Resource
  private ClassificationService classificationService;
  @Resource
  private AllKindsService allKindsService;
  @Resource
  private ShopContextService shopContextService;
  @Resource
  private AddressService addressService;

  //进入登录界面
  @RequestMapping(value = "/login.do", method = RequestMethod.GET)
  public String login(HttpServletRequest request, Model model) {
    String token = TokenProccessor.getInstance().makeToken();
    log.info("进入登录界面，token为:" + token);

    request.getSession().setAttribute("token", token);
    model.addAttribute("token", token);
    return "page/login_page";
  }

  //退出
  @RequestMapping(value = "/logout.do")
  public String logout(HttpServletRequest request) {
    try {
      request.getSession().removeAttribute("userInformation");
      request.getSession().removeAttribute("uid");
      System.out.println("logout");
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:/home.do";
    }
    return "redirect:/";
  }

  //用户注册,拥有插入数据
  @RequestMapping(value = "/registered.do", method = RequestMethod.POST)
  public String registered(Model model,HttpServletRequest request,
                           @RequestParam String name, @RequestParam String phone, @RequestParam String password) {
    UserInformation userInformation = new UserInformation();
    userInformation.setUsername(name);
    userInformation.setPhone(phone);
    userInformation.setModified(new Date());
    userInformation.setCreatetime(new Date());
    userInformation.setManager(false);
    if (userInformationService.insertSelective(userInformation) == 1) {
      int uid = userInformationService.selectIdByPhone(phone);
      UserPassword userPassword = new UserPassword();
      userPassword.setModified(new Date());
      password = StringUtils.getInstance().getMD5(password);
      userPassword.setPassword(password);
      userPassword.setUid(uid);
      int result = userPasswordService.insertSelective(userPassword);
      if (result != 1) {
        model.addAttribute("result", "fail");
        return "success";
      }
      request.getSession().setAttribute("uid", uid);
      model.addAttribute("result", "success");
      return "success";
    }
    model.addAttribute("result", "fail");
    return "success";
  }


  //验证登录
  @RequestMapping(value = "/login.do", method = RequestMethod.POST)
  public String login(HttpServletRequest request,
                      @RequestParam String phone, @RequestParam String password, @RequestParam String token) {
    log.info("进入登录验证界面");
    String loginToken = (String) request.getSession().getAttribute("token");
    if (StringUtils.getInstance().isNullOrEmpty(phone) || StringUtils.getInstance().isNullOrEmpty(password)) {
      return "redirect:/login.do";
    }
    //防止重复提交
    if (StringUtils.getInstance().isNullOrEmpty(token) || !token.equals(loginToken)) {
      return "redirect:/login.do";
    }
    boolean b = getId(phone, password, request);
    //失败，不存在该手机号码
    if (!b) {
      return "redirect:/login.do?msg=no_this_phone";
    }
    return "redirect:/";
  }

  //查看用户基本信息
  @RequestMapping(value = "/personal_info.do")
  public String personalInfo(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");

    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    }
    String personalInfoToken = TokenProccessor.getInstance().makeToken();
    request.getSession().setAttribute("personalInfoToken", personalInfoToken);
    model.addAttribute("token", personalInfoToken);
    model.addAttribute("userInformation", userInformation);
    return "page/personal/personal_info";
  }


  //完善用户基本信息，认证
  @RequestMapping(value = "/certification.do", method = RequestMethod.POST)
  @ResponseBody
  public Map certification(HttpServletRequest request,
                           @RequestParam(required = false) String userName,
                           @RequestParam(required = false) String realName,
                           @RequestParam(required = false) String clazz, @RequestParam String token,
                           @RequestParam(required = false) String sno, @RequestParam(required = false) String dormitory,
                           @RequestParam(required = false) String gender, @RequestParam(required = false) String school,
                           @RequestParam(required = false) String campus, @RequestParam(required = false) String contact) {

    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    Map<String, Integer> map = new HashMap<>();
    map.put("result", 0);
    //该用户还没有登录
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return map;
    }
    String certificationToken = (String) request.getSession().getAttribute("personalInfoToken");
    //防止重复提交
    //   boolean b = token.equals(certificationToken);
    if (StringUtils.getInstance().isNullOrEmpty(certificationToken)) {
      return map;
    } else {
      request.getSession().removeAttribute("certificationToken");
    }
    if (userName != null && userName.length() < 25) {
      userName = StringUtils.getInstance().replaceBlank(userName);
      userInformation.setUsername(userName);
    } else if (userName != null && userName.length() >= 25) {
      return map;
    }
    if (realName != null && realName.length() < 25) {
      realName = StringUtils.getInstance().replaceBlank(realName);
      userInformation.setRealname(realName);
    } else if (realName != null && realName.length() >= 25) {
      return map;
    }
    if (clazz != null && clazz.length() < 25) {
      clazz = StringUtils.getInstance().replaceBlank(clazz);
      userInformation.setClazz(clazz);
    } else if (clazz != null && clazz.length() >= 25) {
      return map;
    }
    if (sno != null && sno.length() < 25) {
      sno = StringUtils.getInstance().replaceBlank(sno);
      userInformation.setSno(sno);
    } else if (sno != null && sno.length() >= 25) {
      return map;
    }
    if (dormitory != null && dormitory.length() < 25) {
      dormitory = StringUtils.getInstance().replaceBlank(dormitory);
      userInformation.setDormitory(dormitory);
    } else if (dormitory != null && dormitory.length() >= 25) {
      return map;
    }
    if (gender != null && gender.length() <= 2) {
      gender = StringUtils.getInstance().replaceBlank(gender);
      userInformation.setGender(gender);
    } else if (gender != null && gender.length() > 2) {
      return map;
    }
    if (school != null && school.length() <= 50) {
      school = StringUtils.getInstance().replaceBlank(school);
      userInformation.setSchool(school);
    } else if (school != null && school.length() > 50) {
      return map;
    }
    if (campus != null && campus.length() <= 50) {
      campus = StringUtils.getInstance().replaceBlank(campus);
      userInformation.setCampus(campus);
    } else if (campus != null && campus.length() > 50) {
      return map;
    }
    if (contact != null && contact.length() <= 50) {
      contact = StringUtils.getInstance().replaceBlank(contact);
      userInformation.setContact(contact);
    } else if (contact != null && contact.length() > 50) {
      return map;
    }
    int result = userInformationService.updateByPrimaryKeySelective(userInformation);
    if (result != 1) {
      //更新失败，认证失败
      return map;
    }
    //认证成功
    request.getSession().setAttribute("userInformation", userInformation);
    map.put("result", 1);
    return map;
  }

  //enter the publishUserWant.do.html,进入求购页面
  @RequestMapping(value = "/require_product.do")
  public String enterPublishUserWant(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    }
    String error = request.getParameter("error");
    if (!StringUtils.getInstance().isNullOrEmpty(error)) {
      model.addAttribute("error", "error");
    }
    String publishUserWantToken = TokenProccessor.getInstance().makeToken();
    request.getSession().setAttribute("publishUserWantToken", publishUserWantToken);
    model.addAttribute("token", publishUserWantToken);
    model.addAttribute("userInformation", userInformation);
    return "page/require_product";
  }

  //修改求购商品
  @RequestMapping(value = "/modified_require_product.do")
  public String modifiedRequireProduct(HttpServletRequest request, Model model,
                                       @RequestParam int id) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    }
    String publishUserWantToken = TokenProccessor.getInstance().makeToken();
    request.getSession().setAttribute("publishUserWantToken", publishUserWantToken);
    model.addAttribute("token", publishUserWantToken);
    model.addAttribute("userInformation", userInformation);
    UserWant userWant = userWantService.selectByPrimaryKey(id);
    model.addAttribute("userWant", userWant);
    String sort = getSort(userWant.getSort());
    model.addAttribute("sort", sort);
    return "page/modified_require_product";
  }

  //publish userWant,发布求购
  @RequestMapping(value = "/publishUserWant.do")
//    @ResponseBody
  public String publishUserWant(HttpServletRequest request, Model model,
                                @RequestParam String name,
                                @RequestParam int sort, @RequestParam int quantity,
                                @RequestParam double price, @RequestParam String remark,
                                @RequestParam String token) throws IOException {
//        Map<String, Integer> map = new HashMap<>();
    //determine whether the user exits
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      //if the user no exits in the session,
//            map.put("result", 2);
      return "redirect:/login.do";
    }
    String publishUserWantToke = (String) request.getSession().getAttribute("publishUserWantToken");
    if (StringUtils.getInstance().isNullOrEmpty(publishUserWantToke) || !publishUserWantToke.equals(token)) {
//            map.put("result", 2);
      return "redirect:require_product.do?error=3";
    } else {
      request.getSession().removeAttribute("publishUserWantToken");
    }
//        name = StringUtils.replaceBlank(name);
//        remark = StringUtils.replaceBlank(remark);
//        name = StringUtils.getInstance().txtReplace(name);
//        remark = StringUtils.getInstance().txtReplace(remark);
    try {
      if (name.length() < 1 || remark.length() < 1 || name.length() > 25 || remark.length() > 25) {
        return "redirect:require_product.do";
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:require_product.do?error=1";
    }
    UserWant userWant = new UserWant();
    userWant.setModified(new Date());
    userWant.setName(SensitiveWordUtil.dealTxt(name));
    userWant.setPrice(new BigDecimal(Double.toString(price)));
    userWant.setQuantity(quantity);
    userWant.setRemark(SensitiveWordUtil.dealTxt(remark));
    userWant.setUid((Integer) request.getSession().getAttribute("uid"));
    userWant.setSort(sort);
    int result;
    try {
      result = userWantService.insertSelective(userWant);
      if (result != 1) {
//                map.put("result", result);
        return "redirect:/require_product.do?error=2";
      }
    } catch (Exception e) {
      e.printStackTrace();
//            map.put("result", result);
      return "redirect:/require_product.do?error=2";
    }
//        map.put("result", result);
    return "redirect:/my_require_product.do";
  }

  //getUserWant,查看我的求购
  @RequestMapping(value = {"/my_require_product.do", "/my_require_product_page.do"})
  public String getUserWant(HttpServletRequest request, Model model) {
    List<UserWant> list;
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    }
    try {
      int uid = (int) request.getSession().getAttribute("uid");
//            list = selectUserWantByUid(4);
      list = selectUserWantByUid(uid);
      List<UserWantBean> userWantBeans = new ArrayList<>();
      for (UserWant userWant : list) {
        UserWantBean userWantBean = new UserWantBean();
        userWantBean.setId(userWant.getId());
        userWantBean.setModified(userWant.getModified());
        userWantBean.setName(userWant.getName());
        userWantBean.setPrice(userWant.getPrice().doubleValue());
        userWantBean.setUid(uid);
        userWantBean.setQuantity(userWant.getQuantity());
        userWantBean.setRemark(userWant.getRemark());
        userWantBean.setSort(getSort(userWant.getSort()));
        userWantBeans.add(userWantBean);
      }
      model.addAttribute("userWant", userWantBeans);
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:/";
    }
    model.addAttribute("userInformation", userInformation);
    return "page/personal/my_require_product_page";
  }

  //getUserWantCounts.do,查看求购总数
  @RequestMapping(value = "/getUserWantCounts.do")
  @ResponseBody
  public Map getUserWantCounts(HttpServletRequest request, Model model) {
    Map<String, Integer> map = new HashMap<>();
    if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
      map.put("counts", -1);
      return map;
    }
    try {
      int counts = getUserWantCounts((Integer) request.getSession().getAttribute("uid"));
      map.put("counts", counts);
    } catch (Exception e) {
      e.printStackTrace();
      map.put("counts", -1);
    }
    return map;
  }

  //删除求购
  @RequestMapping(value = "/deleteUserWant.do")
  public String deleteUserWant(HttpServletRequest request, @RequestParam int id) {
//        Map<String, Integer> map = new HashMap<>();
    if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
      return "redirect:/login.do";
    }
    UserWant userWant = new UserWant();
    userWant.setId(id);
    userWant.setDisplay(0);
    try {
      int result = userWantService.updateByPrimaryKeySelective(userWant);
      if (result != 1) {
        return "redirect:my_require_product.do";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "redirect:my_require_product.do";
  }

  //收藏
  //add the userCollection
  @RequestMapping(value = "/addUserCollection.do")
  @ResponseBody
  public BaseResponse addUserCollection(HttpServletRequest request, @RequestParam int sid) {
    //determine whether the user exits
    if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
      //if the user no exits in the session,
      return BaseResponse.fail();
    }
    UserCollection userCollection = new UserCollection();
    userCollection.setModified(new Date());
    userCollection.setSid(sid);
    userCollection.setUid((Integer) request.getSession().getAttribute("uid"));
    //begin insert the userCollection
    int result = userCollectionService.insertSelective(userCollection);
    if (result != 1) {
      return BaseResponse.fail();
    }
    return BaseResponse.success();
  }


  // delete the userCollection
  @RequestMapping(value = "/deleteUserCollection.do")
  @ResponseBody
  public BaseResponse deleteUserCollection(HttpServletRequest request, @RequestParam int ucid) {
    if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
      return BaseResponse.fail();
    }
    UserCollection userCollection = new UserCollection();
//        userCollection.setUid((Integer) request.getSession().getAttribute("uid"));
//        userCollection.setSid(sid);
    userCollection.setId(ucid);
    userCollection.setModified(new Date());
    userCollection.setDisplay(0);
    int result;
    result = userCollectionService.updateByPrimaryKeySelective(userCollection);
    if (result != 1) {
      return BaseResponse.fail();
    }
    return BaseResponse.success();
  }

  //购物车开始。。。。。。。。。。。
  //getShopCarCounts.do
  @RequestMapping(value = "/getShopCarCounts.do")
  @ResponseBody
  public BaseResponse getShopCarCounts(HttpServletRequest request) {
    if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
      return BaseResponse.fail();
    }
    int uid = (int) request.getSession().getAttribute("uid");
    int counts = getShopCarCounts(uid);
    return BaseResponse.success();
  }

  //check the shopping cart,查看购物车
  @RequestMapping(value = "/shopping_cart.do")
  public String selectShopCar(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      userInformation = new UserInformation();
      model.addAttribute("userInformation", userInformation);
//            list.add(shopCar);
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    int uid = userInformation.getId();
    List<GoodsCar> goodsCars = goodsCarService.selectByUid(uid);
    //得到用户地址信息
    List<Address> addresses = addressService.selectByUid(uid);

    List<GoodsCarBean> goodsCarBeans = new ArrayList<>();
    List<AddressBean> addressBeans = new ArrayList<>();

    for (GoodsCar goodsCar : goodsCars) {
      GoodsCarBean goodsCarBean = new GoodsCarBean();
      goodsCarBean.setUid(goodsCar.getUid());
      goodsCarBean.setSid(goodsCar.getSid());
      goodsCarBean.setModified(goodsCar.getModified());
      goodsCarBean.setId(goodsCar.getId());
      goodsCarBean.setQuantity(goodsCar.getQuantity());
      ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(goodsCar.getSid());
      goodsCarBean.setName(shopInformation.getName());
      goodsCarBean.setRemark(shopInformation.getRemark());
      goodsCarBean.setImage(shopInformation.getImage());
      goodsCarBean.setPrice(shopInformation.getPrice().doubleValue());
      goodsCarBean.setSort(getSort(shopInformation.getSort()));
      goodsCarBeans.add(goodsCarBean);
    }
    for (Address address : addresses) {
      AddressBean addressBean = new AddressBean();
      addressBean.setId(address.getId());
      addressBean.setAddress(address.getAddress());
      addressBean.setDeaddress(address.getDeaddress());
      addressBean.setUid(address.getUid());
      addressBean.setRecipient(address.getRecipient());
      addressBean.setRephone(address.getRephone());
      addressBeans.add(addressBean);
    }
    model.addAttribute("list", goodsCarBeans);
    model.addAttribute("address", addressBeans);
    return "page/shopping_cart";
  }

//    //通过购物车的id获取购物车里面的商品
//    @RequestMapping(value = "/selectGoodsOfShopCar")
//    @ResponseBody
//    public List<GoodsCar> selectGoodsCar(HttpServletRequest request) {
//        List<GoodsCar> list = new ArrayList<>();
//        GoodsCar goodsCar = new GoodsCar();
//        if (Empty.isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
//            list.add(goodsCar);
//            return list;
//        }
//        try {
//            int scid = shopCarService.selectByUid((Integer) request.getSession().getAttribute("uid")).getId();
//            list = goodsCarService.selectByUid(scid);
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return list;
//        }
//    }

  //添加到购物车
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  @RequestMapping(value = "/insertGoodsCar.do")
  @ResponseBody
  public BaseResponse insertGoodsCar(HttpServletRequest request, @RequestParam int id) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return BaseResponse.fail();
    }
    int uid = userInformation.getId();
    ShopInformation shop = shopInformationService.selectByPrimaryKey(id);
    int shopQuantity = shop.getQuantity();
    if (shopQuantity == 0)
      return BaseResponse.fail(3);

    shop.setQuantity(shopQuantity - 1);
    if (shop.getQuantity() == 0)
      shop.setDisplay(0);
    GoodsCar get = goodsCarService.selectByUidAndSid(uid, id);
    if (get != null) {
      int quantity = get.getQuantity() + 1;
      get.setQuantity(quantity);
      goodsCarService.updateByPrimaryKeySelective(get);
      shopInformationService.updateByPrimaryKeySelective(shop);
      return BaseResponse.success();
    }
    GoodsCar goodsCar = new GoodsCar();
    goodsCar.setDisplay(1);
    goodsCar.setModified(new Date());
    goodsCar.setQuantity(1);
    goodsCar.setUid(uid);
    goodsCar.setSid(id);
    int result = goodsCarService.insertSelective(goodsCar);
    shopInformationService.updateByPrimaryKeySelective(shop);
    return BaseResponse.success();
  }


  //更新购物车的商品
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  @RequestMapping(value = "/updateShopCar.do")
  @ResponseBody
  public BaseResponse updateShopCar(HttpServletRequest request, @RequestParam int id, @RequestParam int sid, @RequestParam int quantity,@RequestParam int type) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return BaseResponse.fail();
    }
    int uid = userInformation.getId();
    GoodsCar goodsCar = new GoodsCar();
    goodsCar.setId(id);
    goodsCar.setSid(sid);
    goodsCar.setUid(uid);

    ShopInformation shop = shopInformationService.selectByPrimaryKey(sid);
    if(type == 0){  //购物车减
      if (shop.getDisplay() == 0)
        shop.setDisplay(1);
      shop.setQuantity(shop.getQuantity() + 1);
      goodsCar.setQuantity(quantity);
    }
    else{ //购物车加
      if(shop.getQuantity() - 1 == 0)
        shop.setDisplay(0);
      if(shop.getQuantity() - 1 < 0)
        return BaseResponse.fail();
      shop.setQuantity(shop.getQuantity() - 1);
      goodsCar.setQuantity(quantity + 1);

    }
    int result = goodsCarService.updateByPrimaryKeySelective(goodsCar);
    shopInformationService.updateByPrimaryKeySelective(shop);
    if (result != 1) {
      return BaseResponse.fail();
    }
    return BaseResponse.success();
  }

  //删除购物车的商品
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  @RequestMapping(value = "/deleteShopCar.do")
  @ResponseBody
  public BaseResponse deleteShopCar(HttpServletRequest request, @RequestParam int id, @RequestParam int sid, @RequestParam int quantity) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return BaseResponse.fail();
    }
    int uid = userInformation.getId();
    GoodsCar goodsCar = new GoodsCar();
    goodsCar.setDisplay(0);
    goodsCar.setId(id);
    goodsCar.setSid(sid);
    goodsCar.setUid(uid);

    ShopInformation shop = shopInformationService.selectByPrimaryKey(sid);
    if (shop.getDisplay() == 0)
      shop.setDisplay(1);
    shop.setQuantity(shop.getQuantity() + quantity);
    int result = goodsCarService.updateByPrimaryKeySelective(goodsCar);
    shopInformationService.updateByPrimaryKeySelective(shop);
    if (result != 1) {
      return BaseResponse.fail();
    }
    return BaseResponse.success();
  }

  //发布商品
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
  @RequestMapping(value = "/insertGoods.do", method = RequestMethod.POST)
  public String insertGoods(@RequestParam String name, @RequestParam int level,
                            @RequestParam String remark, @RequestParam double price,
                            @RequestParam int sort, @RequestParam int quantity,
                            @RequestParam String token, @RequestParam(required = false) MultipartFile image,
                            @RequestParam int action, @RequestParam(required = false) int id,
                            HttpServletRequest request, Model model) throws IOException {
    String goodsToken = (String) request.getSession().getAttribute("goodsToken");
//        String publishProductToken = TokenProccessor.getInstance().makeToken();
//        request.getSession().setAttribute("token",publishProductToken);
    //防止重复提交
    if (StringUtils.getInstance().isNullOrEmpty(goodsToken) || !goodsToken.equals(token)) {
      return "redirect:publish_product.do?error=1";
    } else {
      request.getSession().removeAttribute("goodsToken");
    }
    //
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    model.addAttribute("userInformation", userInformation);
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      //如果用户不存在，
      return "redirect:/login.do";
    }
    name = StringUtils.getInstance().replaceBlank(SensitiveWordUtil.dealTxt(name));
    remark = StringUtils.getInstance().replaceBlank(SensitiveWordUtil.dealTxt(remark));
    //judge the data`s format
    if (StringUtils.getInstance().isNullOrEmpty(name) || StringUtils.getInstance().isNullOrEmpty(level) || StringUtils.getInstance().isNullOrEmpty(remark) || StringUtils.getInstance().isNullOrEmpty(price)
        || StringUtils.getInstance().isNullOrEmpty(sort) || StringUtils.getInstance().isNullOrEmpty(quantity) || name.length() > 25 || remark.length() > 122) {
      model.addAttribute("message", "请输入正确的格式!!!!!");
      model.addAttribute("token", goodsToken);
      request.getSession().setAttribute("goodsToken", goodsToken);
      return "redirect:publish_product.do";
    }
    //插入
    if (action == 1) {
      if (StringUtils.getInstance().isNullOrEmpty(image)) {
        model.addAttribute("message", "请选择图片!!!");
        model.addAttribute("token", goodsToken);
        request.getSession().setAttribute("goodsToken", goodsToken);
        return "redirect:publish_product.do?error=please_enter_image";
      }
      String random;
      String path = "D:/Java/projects/Myjava--master";
      //String path ="/usr/local/tomcat9/webapps/campus-1.0-SNAPSHOT";
      String save = "";
      random = "image" + File.separator + StringUtils.getInstance().getRandomChar() + System.currentTimeMillis() + ".jpg";
      log.info("random = " + random);

      StringBuilder thumbnails = new StringBuilder();
      thumbnails.append(path);
      thumbnails.append(File.separator + "image" + File.separator + "thumbnails" + File.separator);

      //创建缩略图文件夹
      File thumbnailsFile = new File(thumbnails.toString());
      log.info("thumbnailile=  " + thumbnailsFile.getAbsolutePath());
      if (!thumbnailsFile.exists()) {
        thumbnailsFile.mkdirs();
      }

      StringBuilder yzq = new StringBuilder();
      yzq.append(StringUtils.getInstance().getRandomChar()).append(System.currentTimeMillis()).append(".jpg");
      thumbnails.append(yzq);
      log.info("thumbnails(yzq) = " + thumbnails);

      File file = new File(path, random);
      log.info(file.getAbsolutePath());
      if (!file.exists()) {
        file.mkdirs();
      }
      try {
        image.transferTo(file);
      } catch (Exception e) {
        e.printStackTrace();
      }
      log.info("path+random = " + path + File.separator + random);
      //检测图片合法性
      String pornograp = ImageCheck.check(path + File.separator + random);
      log.info(pornograp);
      if (pornograp.equals("不合规")) {
        return "redirect:publish_product.do?error=Picture_non-compliant";
      }

      log.info("path+random = " + path + File.separator + random);
      log.info("thumbnails = " + thumbnails.toString());
      if (StringUtils.getInstance().thumbnails(path + File.separator + random, thumbnails.toString())) {
        save = File.separator + "images" + File.separator + "thumbnails" + File.separator + yzq.toString();
        log.info("save = " + save);
      } else {
        return "redirect:publish_product.do?error=fail_to_generator_thumbnails";
      }
      //begin insert the shopInformation to the MySQL
      ShopInformation shopInformation = new ShopInformation();
      shopInformation.setName(name);
      shopInformation.setLevel(level);
      shopInformation.setRemark(remark);
      shopInformation.setPrice(new BigDecimal(Double.toString(price)));
      shopInformation.setSort(sort);
      shopInformation.setQuantity(quantity);
      shopInformation.setModified(new Date());
      shopInformation.setImage(random);//This is the other uniquely identifies
      shopInformation.setThumbnails(save);

      int uid = (int) request.getSession().getAttribute("uid");
      shopInformation.setUid(uid);
      try {
        int result = shopInformationService.insertSelective(shopInformation);
        //插入失败？？？
        if (result != 1) {
          model.addAttribute("message", "请输入正确的格式!!!!!");
          model.addAttribute("token", goodsToken);
          request.getSession().setAttribute("goodsToken", goodsToken);
          return "redirect:publish_product.do";
        }
      } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("token", goodsToken);
        model.addAttribute("message", "请输入正确的格式!!!!!");
        request.getSession().setAttribute("goodsToken", goodsToken);
        return "redirect:publish_product.do";
      }
      int sid = shopInformationService.selectIdByImage(random);// get the id which is belongs shopInformation
      //将发布的商品的编号插入到用户的发布中
      UserRelease userRelease = new UserRelease();
      userRelease.setModified(new Date());
      userRelease.setSid(sid);
      userRelease.setUid(uid);
      try {
        int result = userReleaseService.insertSelective(userRelease);
        //如果关联失败，删除对应的商品和商品图片
        if (result != 1) {
          //if insert failure,transaction rollback.
          shopInformationService.deleteByPrimaryKey(sid);
          model.addAttribute("token", goodsToken);
          model.addAttribute("message", "请输入正确的格式!!!!!");
          request.getSession().setAttribute("goodsToken", goodsToken);
          return "redirect:publish_product.do";
        }
      } catch (Exception e) {
        //if insert failure,transaction rollback.
        shopInformationService.deleteByPrimaryKey(sid);
        e.printStackTrace();
        model.addAttribute("token", goodsToken);
        model.addAttribute("message", "请输入正确的格式!!!!!");
        request.getSession().setAttribute("goodsToken", goodsToken);
        return "redirect:publish_product.do";
      }
      shopInformation.setId(sid);
      goodsToken = TokenProccessor.getInstance().makeToken();
      request.getSession().setAttribute("goodsToken", goodsToken);
      model.addAttribute("token", goodsToken);
      model.addAttribute("shopInformation", shopInformation);
      model.addAttribute("userInformation", userInformation);
      String sb = getSort(sort);
      model.addAttribute("sort", sb);
      model.addAttribute("action", 2);
      return "redirect:/my_publish_product_page.do";
    } else if (action == 2) {//更新商品
      ShopInformation shopInformation = new ShopInformation();
      shopInformation.setModified(new Date());
      shopInformation.setQuantity(quantity);
      shopInformation.setSort(sort);
      shopInformation.setPrice(new BigDecimal(Double.toString(price)));
      shopInformation.setRemark(remark);
      shopInformation.setLevel(level);
      shopInformation.setName(name);
      shopInformation.setId(id);
      try {
        int result = shopInformationService.updateByPrimaryKeySelective(shopInformation);
        if (result != 1) {
          return "redirect:publish_product.do";
        }
      } catch (Exception e) {
        e.printStackTrace();
        return "redirect:publish_product.do";
      }
      goodsToken = TokenProccessor.getInstance().makeToken();
      request.getSession().setAttribute("goodsToken", goodsToken);
      model.addAttribute("token", goodsToken);
      shopInformation = shopInformationService.selectByPrimaryKey(id);
      model.addAttribute("userInformation", userInformation);
      model.addAttribute("shopInformation", shopInformation);
      model.addAttribute("action", 2);
      model.addAttribute("sort", getSort(sort));
    }
    return "redirect:my_publish_product_page.do";
  }

  //从发布的商品直接跳转到修改商品
  @RequestMapping(value = "/modifiedMyPublishProduct.do")
  public String modifiedMyPublishProduct(HttpServletRequest request, Model model,
                                         @RequestParam int id) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    }
    String goodsToken = TokenProccessor.getInstance().makeToken();
    request.getSession().setAttribute("goodsToken", goodsToken);
    model.addAttribute("token", goodsToken);
    ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(id);
    model.addAttribute("userInformation", userInformation);
    model.addAttribute("shopInformation", shopInformation);
    model.addAttribute("action", 2);
    model.addAttribute("sort", getSort(shopInformation.getSort()));
    return "page/publish_product";
  }

  //发表留言
  @RequestMapping(value = "/insertShopContext.do")
  @ResponseBody
  public Map insertShopContext(@RequestParam int id, @RequestParam String context, @RequestParam String token,
                               HttpServletRequest request) throws IOException {
    String goodsToken = (String) request.getSession().getAttribute("goodsToken");
    Map<String, String> map = new HashMap<>();
    map.put("result", "1");
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      map.put("result", "2");
      return map;
    }
    if (StringUtils.getInstance().isNullOrEmpty(goodsToken) || !token.equals(goodsToken)) {
      return map;
    }
    ShopContext shopContext = new ShopContext();
    shopContext.setContext(SensitiveWordUtil.dealTxt(context));
    Date date = new Date();
    shopContext.setModified(date);
    shopContext.setSid(id);
    int uid = (int) request.getSession().getAttribute("uid");
    shopContext.setUid(uid);
    try {
      int result = shopContextService.insertSelective(shopContext);
      if (result != 1) {
        return map;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return map;
    }
    map.put("result", "1");
    map.put("username", userInformation.getUsername());
    map.put("context", context);
    map.put("time", StringUtils.getInstance().DateToString(date));
    return map;
  }

  //下架商品
  @RequestMapping(value = "/deleteShop.do")
  public String deleteShop(HttpServletRequest request, Model model, @RequestParam int id) {
//        Map<String, Integer> map = new HashMap<>();
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    ShopInformation shopInformation = new ShopInformation();
    shopInformation.setModified(new Date());
    shopInformation.setDisplay(0);
    shopInformation.setId(id);
    try {
      int result = shopInformationService.updateByPrimaryKeySelective(shopInformation);
      if (result != 1) {
        return "redirect:my_publish_product_page.do";
      }
      return "redirect:my_publish_product_page.do";
    } catch (Exception e) {
      e.printStackTrace();
      return "redirect:my_publish_product_page.do";
    }
  }

  //查看发布的所有商品总数
  @RequestMapping(value = "/getReleaseShopCounts.do")
  @ResponseBody
  public Map getReleaseShopCounts(HttpServletRequest request) {
    Map<String, Integer> map = new HashMap<>();
    if (StringUtils.getInstance().isNullOrEmpty(request.getSession().getAttribute("userInformation"))) {
      map.put("counts", -1);
      return map;
    }
    int counts = getReleaseCounts((Integer) request.getSession().getAttribute("uid"));
    map.put("counts", counts);
    return map;
  }

  //查看我的发布的商品
  @RequestMapping(value = "/my_publish_product_page.do")
  public String getReleaseShop(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    int uid = (int) request.getSession().getAttribute("uid");
    List<ShopInformation> shopInformations = shopInformationService.selectUserReleaseByUid(uid);
    List<ShopInformationBean> list = new ArrayList<>();
    String stringBuffer;
//            int i=0;
    for (ShopInformation shopInformation : shopInformations) {
//                if (i>=5){
//                    break;
//                }
//                i++;
      stringBuffer = getSort(shopInformation.getSort());
      ShopInformationBean shopInformationBean = new ShopInformationBean();
      shopInformationBean.setId(shopInformation.getId());
      shopInformationBean.setName(shopInformation.getName());
      shopInformationBean.setLevel(shopInformation.getLevel());
      shopInformationBean.setPrice(shopInformation.getPrice().doubleValue());
      shopInformationBean.setRemark(shopInformation.getRemark());
      shopInformationBean.setSort(stringBuffer);
      shopInformationBean.setQuantity(shopInformation.getQuantity());
      shopInformationBean.setTransaction(shopInformation.getTransaction());
      shopInformationBean.setUid(shopInformation.getUid());
      shopInformationBean.setImage(shopInformation.getImage());
      list.add(shopInformationBean);
    }
    model.addAttribute("shopInformationBean", list);
    return "page/personal/my_publish_product_page";
  }


  /**
   * //生成订单， 订单id(id自动生成) ,订单人id(从session获取) ,地址(从session获取) ,生成时间(后台生成)
   * 商品id、商品价格(从数据库查)、商品数量(参数传递)
   *
   * @return
   */

/*  @RequestMapping(value = "/insert_order.do", method = RequestMethod.POST)
  @ResponseBody
  public BaseResponse addOrders(@RequestBody Map<String, Object> paramsMap, HttpServletRequest request,
                                HttpServletResponse response, Model model) throws IOException {

    String readdress = (String) paramsMap.get("address");
    //商品id,数量列表
    List data_list = (List) paramsMap.get("data_list");
    log.info("data_list size:" + data_list.size());

    //测试
    for (int i = 0; i < data_list.size(); i++) {
      log.info("商品id:" + ((LinkedHashMap) data_list.get(i)).get("shopId"));
      log.info("购买数量:" + ((LinkedHashMap) data_list.get(i)).get("shopNum"));
    }
    log.info("订单生成 controller!");
    UserInformation userInfo = (UserInformation) request.getSession().getAttribute("userInformation");
    model.addAttribute("userInformation", userInfo);
    int uid = userInfo.getId();

    OrderForm orderForm = new OrderForm();
    orderForm.setId(null);
    orderForm.setUid(Integer.valueOf(uid));
    orderForm.setModified(new Date());
    orderForm.setReaddress(readdress);
    orderForm.setDisplay(1);
    //配置Mybatis返回自增主键赋值给 orderForm的id
    int order_id = orderFormService.insertSelective(orderForm);//插入订单-用户数据表
    log.info("自增订单id:" + orderForm.getId());
    log.info("循环订单-商品表开始：");

    int goodsOF_result = 0;
    //循环插入订单-商品表
    for (int i = 0; i < data_list.size(); i++) {
      //类型转换  商品id
      Object shopId = ((LinkedHashMap) data_list.get(i)).get("shopId");
      String s_shopId = shopId.toString();
      Integer shopId_int = Integer.valueOf(s_shopId);
      //对应商品id的数量
      Object shopNum = ((LinkedHashMap) data_list.get(i)).get("shopNum");
      String s_shopNum = shopNum.toString();
      Integer shopNum_int = Integer.valueOf(s_shopNum);

      //对应商品id的数量
      Object oid = ((LinkedHashMap) data_list.get(i)).get("oid");
      String oid_s = oid.toString();
      Integer oid_int = Integer.valueOf(oid_s);
      log.info(shopId_int + " " + shopNum_int + " " + oid_int);
      GoodsOfOrderForm goodsOfOrderForm = new GoodsOfOrderForm();
      GoodsCar goodsCar = new GoodsCar();

      goodsOfOrderForm.setId(null);
      goodsCar.setId(oid_int);

      goodsOfOrderForm.setOfid(orderForm.getId());

      goodsOfOrderForm.setSid(shopId_int);
      goodsCar.setSid(shopId_int);

      goodsOfOrderForm.setQuantity(shopNum_int);
      goodsCar.setUid(uid);

      goodsOfOrderForm.setDisplay(1);
      goodsCar.setDisplay(0);

      goodsOfOrderForm.setModified(new Date());
      goodsCar.setModified(new Date());
      goodsOF_result = goodsOfOrderFormService.insertSelective(goodsOfOrderForm);//插入订单-商品数据表
      goodsOF_result = goodsCarService.updateByPrimaryKeySelective(goodsCar);
    }

    log.info("订单创建成功：" + orderFormService.selectByPrimaryKey(orderForm.getId()));
    //删除购物车商品

    return BaseResponse.success();
  }
  */

  /**
   * 结算
   *
   * @param request
   * @param model
   * @return
   */
  @RequestMapping(value = "/settlement.do")
  @ResponseBody
  @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = Exception.class)
  public BaseResponse getSettlement(HttpServletRequest request, Model model, ShopCarPojo shopCarPojo) {
    log.info("enter settlemtn");
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return BaseResponse.fail(1);
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    Integer uid = (int) request.getSession().getAttribute("uid");
    //查询购物车商品
    String[] sid = shopCarPojo.getIds().split(",");
    String readdress = shopCarPojo.getReaddress();
    List<Map<String, Object>> shopCarList = new ArrayList<>();
    for (int i = 0; i < sid.length; i++) {
      // 查询购物车里面的数据
      GoodsCar goodsCar = goodsCarService.selectByUidAndSid(uid, Integer.parseInt(sid[i]));
      ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(goodsCar.getSid());
      Map<String, Object> map = new HashMap<>();
      // 商品id
      map.put("shopCarId", goodsCar.getId());
      map.put("sid", goodsCar.getSid());
      // 商品数量
      map.put("quantity", goodsCar.getQuantity());
      /**
       * 商品价格
       */
      map.put("price", (shopInformation.getPrice().multiply(new BigDecimal(goodsCar.getQuantity()))).intValue());
      // 卖家
      map.put("uid", shopInformation.getUid());

      //商品数量为0 则隐藏之
      if (shopInformation.getQuantity() == 0)
        shopInformation.setDisplay(0);
      shopInformationService.updateByPrimaryKey(shopInformation);
      shopCarList.add(map);
    }


    for (int i = 0; i < shopCarList.size(); i++) {
      // 添加订单表
      OrderForm orderForm = new OrderForm();
      orderForm.setModified(new Date());
      orderForm.setUid(uid);
      orderForm.setShopId(Integer.parseInt(shopCarList.get(i).get("sid").toString()));
      // 金额

      shopCarList.get(i).get("price");
      orderForm.setDisplay((int) shopCarList.get(i).get("price"));
      // 数量
      orderForm.setQuantity((int) shopCarList.get(i).get("quantity"));

      orderForm.setReaddress(readdress);
       /* //TODO 自己不能购买自己的商品
        if(uid == Integer.parseInt(shopCarList.get(i).get("uid").toString()))
          return BaseResponse.fail(4);*/

      orderForm.setSellerId(Integer.parseInt(shopCarList.get(i).get("uid").toString()));
      orderForm.setStatus(1);
      orderFormService.insertSelective(orderForm);
      //删除购物车
      goodsCarService.deleteByPrimaryKey(Integer.parseInt(shopCarList.get(i).get("shopCarId").toString()));

    }

    return BaseResponse.success();
  }

  //购买订单
  @RequestMapping(value = "/purchase_shop.do")
  public String purchaseShop(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      userInformation = new UserInformation();
      model.addAttribute("userInformation", userInformation);
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    int uid = userInformation.getId();
    log.info("purchase_shop " + uid);
    List<OrderBean> orderBeanList = orderFormService.selectByUidAll(uid);

    model.addAttribute("list", orderBeanList);
    return "page/purchase_shop";
  }

  //卖出商品订单
  @RequestMapping(value = "/sell_out_shop.do")
  public String sellOutShop(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      userInformation = new UserInformation();
      model.addAttribute("userInformation", userInformation);
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    int uid = userInformation.getId();
    List<OrderSellerBean> orderSellerBeans = orderFormService.selectBySellAll(uid);
    model.addAttribute("list", orderSellerBeans);
    return "page/sell_out_shop";
  }

  // 确认发货
  @RequestMapping(value = "/requireConfirmGoods.do")
  @ResponseBody
  public Map<String, Object> requireConfirmGoods(HttpServletRequest request, Model model, Integer id) {
    OrderForm orderForm = orderFormService.selectByPrimaryKey(id);
    orderForm.setStatus(2);
    orderFormService.updateByPrimaryKey(orderForm);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "success");
    return map;
  }

  // 确认收货
  @RequestMapping(value = "/requireConfirm.do")
  @ResponseBody
  public Map<String, Object> requireConfirm(HttpServletRequest request,
                                            Model model, Integer id) {
    OrderForm orderForm = orderFormService.selectByPrimaryKey(id);
    orderForm.setStatus(3);
    orderFormService.updateByPrimaryKey(orderForm);
    Map<String, Object> map = new HashMap<>();
    map.put("status", "success");
    return map;
  }

  @RequestMapping(value = "/getAddress.do")
  public String showAddress(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      //如果当前没有登录则跳转到登录界面
      return "redirect:/login.do";
    }

    model.addAttribute("userInformation", userInformation);
    int uid = userInformation.getId();
    //存放地址id的list
    List<Address> addresses = addressService.selectByUid(uid);

    model.addAttribute("addressList", addresses);
    return "page/shopping_cart";

  }

  @RequestMapping("/add_address.do")
  public String enter_address(HttpServletRequest request, Model model) {
    //先判断用户有没有登录
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      //如果没有登录
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    return "page/personal/add_address";
  }

  @RequestMapping(value = "/insert_address.do", method = RequestMethod.POST)
  public String addAddress(HttpServletRequest request, Model model) {
    UserInformation userInformation = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInformation)) {
      return "redirect:/login.do";
    } else {
      model.addAttribute("userInformation", userInformation);
    }
    Address add_address = new Address();
    int uid = (int) request.getSession().getAttribute("uid");
    add_address.setUid(uid);
    String recipient = request.getParameter("recipient");
    String rephone = request.getParameter("rephone");
    String address = request.getParameter("address");
    add_address.setRecipient(recipient);
    add_address.setRephone(rephone);
    add_address.setAddress(address);
    try {
      int result = addressService.insertSelective(add_address);
      if (result == 1)
        return "redirect:/shopping_cart.do";
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "page/shopping_cart?false";
  }


  /**
   * 个人账号获取订单
   */
  /*
  @RequestMapping(value = "/getMyOrders.do")
  public String showMyOrders(HttpServletRequest request, Model model) {
    UserInformation userInfo = (UserInformation) request.getSession().getAttribute("userInformation");
    if (StringUtils.getInstance().isNullOrEmpty(userInfo)) {
      //如果当前没有登录则跳转到登录界面
      return "redirect:/login.do";
    }
    model.addAttribute("userInformation", userInfo);
    int uid = userInfo.getId();

    //存订单id的list
    ArrayList<Integer> orderIdList = new ArrayList<>();
    //存商品列表的list
    List<GoodsCarBean> goodsCarBeans = new ArrayList<>();
    //获取当前账号的订单列表
    //返回model， 通过user id 获取订单号，返回订单中包含的商品信息(名字，价格，图片等等)
    List<OrderForm> orderForms = orderFormService.selectAllByUid(Integer.valueOf(uid));
    for (OrderForm o : orderForms) {
      orderIdList.add(o.getId());
      //订单-商品表 对象   //整个list的订单号都是一样的
      List<GoodsOfOrderForm> goodsOfOrderForms = goodsOfOrderFormService.selectByOFid(o.getId());
      log.info("订单-商品表 对象" + goodsOfOrderForms.toString());
      //for循环去读订单-商品表
      for (GoodsOfOrderForm goodsOfOrderForm : goodsOfOrderForms) {
        GoodsCarBean goodsCarBean = new GoodsCarBean();
        goodsCarBean.setUid(o.getUid());
        goodsCarBean.setModified(o.getModified());
        goodsCarBean.setId(o.getId());
        System.out.println("每次当前商品id:" + goodsOfOrderForm.getSid());
        //当前商品数量
        goodsCarBean.setQuantity(goodsOfOrderForm.getQuantity());
        //通过商品id获取商品信息表 对象
        ShopInformation shopInformation = shopInformationService.selectByPrimaryKey(goodsOfOrderForm.getSid());
        log.info("订单-商品表id：" + goodsOfOrderForm.getId() + "商品号：" + shopInformation.getId());
        goodsCarBean.setSid(shopInformation.getId());
        goodsCarBean.setName(shopInformation.getName());
        goodsCarBean.setRemark(shopInformation.getRemark());
        goodsCarBean.setImage(shopInformation.getImage());
        goodsCarBean.setPrice(shopInformation.getPrice().doubleValue());
        goodsCarBean.setSort(getSort(shopInformation.getSort()));
        goodsCarBeans.add(goodsCarBean);
      }
    }
    model.addAttribute("orderList", goodsCarBeans);     //返回订单里的商品里列表 Model
    model.addAttribute("orderIdList", orderIdList);     //返回订单里的商品里列表 Model
    for (GoodsCarBean gcb : goodsCarBeans) {
      System.out.println(gcb.toString());
    }
    return "page/personal/my_orders";
  }*/

  //=======================================================================
  //  分割线
  // 下面都是封装好的方法，便于contrller处理方法中的操作调用

  //更新商品信息
  private String getSort(int sort) {
    StringBuilder sb = new StringBuilder();
    Specific specific = selectSpecificBySort(sort);
    int cid = specific.getCid();
    Classification classification = selectClassificationByCid(cid);
    int aid = classification.getAid();
    AllKinds allKinds = selectAllKindsByAid(aid);
    sb.append(allKinds.getName());
    sb.append("-");
    sb.append(classification.getName());
    sb.append("-");
    sb.append(specific.getName());
    return sb.toString();
  }

  //查看用户收藏的货物的总数
  private int getCollectionCounts(int uid) {
    int counts;
    try {
      counts = userCollectionService.getCounts(uid);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
    return counts;
  }

  //查看收藏，一次10个
  private List<UserCollection> selectContectionByUid(int uid, int start) {
    try {
      return userCollectionService.selectByUid(uid, (start - 1) * 10);
    } catch (Exception e) {
      e.printStackTrace();
      List<UserCollection> list = new ArrayList<>();
      list.add(new UserCollection());
      return list;
    }
  }

  //查看用户发布的货物的总数
  private int getReleaseCounts(int uid) {
    try {
      return userReleaseService.getCounts(uid);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  //查看发布的货物，一次10个
  private List<UserRelease> selectReleaseByUid(int uid, int start) {
    try {
      return userReleaseService.selectByUid(uid, (start - 1) * 10);
    } catch (Exception e) {
      e.printStackTrace();
      List<UserRelease> list = new ArrayList<>();
      list.add(new UserRelease());
      return list;
    }
  }

  //查看用户购买到的物品的总数
  private int getBoughtShopCounts(int uid) {
    try {
      return boughtShopService.getCounts(uid);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  //查看用户的购买，10个
  private List<BoughtShop> selectBoughtShopByUid(int uid, int start) {
    try {
      return boughtShopService.selectByUid(uid, (start - 1) * 10);
    } catch (Exception e) {
      e.printStackTrace();
      List<BoughtShop> list = new ArrayList<>();
      list.add(new BoughtShop());
      return list;
    }
  }

  //查看用户的求购总个数
  private int getUserWantCounts(int uid) {
    try {
      return userWantService.getCounts(uid);
    } catch (Exception e) {
      return -1;
    }
  }

  //求购列表10
  private List<UserWant> selectUserWantByUid(int uid) {
    try {
      return userWantService.selectMineByUid(uid);
    } catch (Exception e) {
      e.printStackTrace();
      List<UserWant> list = new ArrayList<>();
      list.add(new UserWant());
      return list;
    }
  }

  //我的购物车总数
  private int getShopCarCounts(int uid) {
    try {
      return shopCarService.getCounts(uid);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  //购物车列表  10
  private ShopCar selectShopCarByUid(int uid) {
    try {
      return shopCarService.selectByUid(uid);
    } catch (Exception e) {
      e.printStackTrace();
//            List<ShopCar> list
      return new ShopCar();
    }
  }

  //查看订单总数
  private int getOrderFormCounts(int uid) {
    try {
      return orderFormService.getCounts(uid);
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }
  }

  //订单列表 10个
  private List<OrderForm> selectOrderFormByUid(int uid, int start) {
    try {
      return orderFormService.selectByUid(uid, (start - 1) * 10);
    } catch (Exception e) {
      e.printStackTrace();
      List<OrderForm> list = new ArrayList<>();
      list.add(new OrderForm());
      return list;
    }
  }

  //订单中的商品
  private List<GoodsOfOrderForm> selectGoodsOfOrderFormByOFid(int ofid) {
    try {
      return goodsOfOrderFormService.selectByOFid(ofid);
    } catch (Exception e) {
      e.printStackTrace();
      List<GoodsOfOrderForm> list = new ArrayList<>();
      list.add(new GoodsOfOrderForm());
      return list;
    }
  }

  //查看用户的状态
  private UserState selectUserStateByUid(int uid) {
    try {
      return userStateService.selectByUid(uid);
    } catch (Exception e) {
      e.printStackTrace();
      return new UserState();
    }
  }

  //判断该手机号码及其密码是否一一对应
  private boolean getId(String phone, String password, HttpServletRequest request) {
    int uid = userInformationService.selectIdByPhone(phone);
    log.info("uid=" + uid);
    if (uid == 0 || StringUtils.getInstance().isNullOrEmpty(uid)) {
      return false;
    }
    UserInformation userInformation = userInformationService.selectByPrimaryKey(uid);

    if (null == userInformation) {
      return false;
    }

    password = StringUtils.getInstance().getMD5(password);

    String password2 = userPasswordService.selectByUid(userInformation.getId()).getPassword();

    if (!password.equals(password2)) {
      return false;
    }
    //如果密码账号对应正确，将userInformation存储到session中
    request.getSession().setAttribute("userInformation", userInformation);
    request.getSession().setAttribute("uid", uid);
    SaveSession.getInstance().save(phone, System.currentTimeMillis());
    return true;
  }

  //获取最详细的分类，第三层
  private Specific selectSpecificBySort(int sort) {
    return specificeService.selectByPrimaryKey(sort);
  }

  //获得第二层分类
  private Classification selectClassificationByCid(int cid) {
    return classificationService.selectByPrimaryKey(cid);
  }

  //获得第一层分类
  private AllKinds selectAllKindsByAid(int aid) {
    return allKindsService.selectByPrimaryKey(aid);
  }

  public void save(ShopInformation shopInformation, UserRelease userRelease) {
    shopInformationService.insertSelective(shopInformation);
    userReleaseService.insertSelective(userRelease);
  }

/*  //发布商品
  @RequestMapping(value = "/test")
  public String insertGoods() {

    //begin insert the shopInformation to the MySQL
//            ShopInformation shopInformation = new ShopInformation();
//            shopInformation.setName(name);
//            shopInformation.setLevel(level);
//            shopInformation.setRemark(remark);
//            shopInformation.setPrice(new BigDecimal(price));
//            shopInformation.setSort(sort);
//            shopInformation.setQuantity(quantity);
//            shopInformation.setModified(new Date());
//            shopInformation.setImage(image);//This is the other uniquely identifies
//            shopInformation.setUid(uid);
//            //将发布的商品的编号插入到用户的发布中
//            UserRelease userRelease = new UserRelease();
//            userRelease.setModified(new Date());
//            userRelease.setSid(sid);
//            userRelease.setUid(uid);
//            shopInformation.setId(sid);
    Random random = new Random();
    ShopInformation shopInformation;
    UserRelease userRelease;
    int level, uid, quantity;
    double price;
    for (int i = 1, k = 1, j = 189; i < 1000; i++, j++, k++) {
      if (k > 94) {
        k = 1;
      }
      level = random.nextInt(10) + 1;
      price = Math.random() * 1000 + 1;
      quantity = random.nextInt(10) + 1;
      uid = random.nextInt(100) + 1;
      shopInformation = new ShopInformation();
      shopInformation.setId(j);
      shopInformation.setName("百年孤独");
      shopInformation.setModified(new Date());
      shopInformation.setLevel(level);
      shopInformation.setRemark("看上的请联系我，QQ：test，微信：test");
//            double price = Math.random()*1000.00+1;
      shopInformation.setPrice(new BigDecimal(price));
      shopInformation.setSort(k);
      shopInformation.setQuantity(quantity);
      shopInformation.setImage("/image/QyBHYiMfYQ4XZFCqxEv0.jpg");
//            int uid = random.nextInt(100)+1;
      shopInformation.setUid(uid);
//            userRelease = new UserRelease();
//            userRelease.setUid(uid);
//            userRelease.setSid(j);
//            userRelease.setModified(new Date());
//            userRelease.setDisplay(1);
      shopInformationService.updateByPrimaryKeySelective(shopInformation);
//            userReleaseService.insertSelective(userRelease);
    }
    System.out.println("success");
    return "page/publish_product";
  }*/
}
