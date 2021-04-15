package com.miaoshaproject.controller;

import com.miaoshaproject.controller.viewobject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

//origins = "http://localhost:8080",methods = RequestMethod.POST
//外面参数解决跨域问题，里面参数解决session不共享配合前端ajaxxhrFields:{withCredentials:true},参数使用
//default_allow_credentials=true:需要配合xhrFields授信后使得跨域session共享_
//DEFAULT_ALLOWED_HEADERS:允许跨域传输所有的hearder参数，将用于token放入header域做session共享的跨域请求
//@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
@Controller("user")
@RequestMapping("/user")
@CrossOrigin(origins={"*"}, allowCredentials = "true")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;


    @Autowired
    private HttpServletRequest httpServletRequest;
    //HttpServletRequest通过bean方式注入进来，表明这个相当于一个单例模式，
    // 一个单例怎么可以支持一个request被多个用户的并发访问呢，这个通过springbean包装的httpservletrequest
    // 因为其本质是proxy，它内部有threadlocal的map，去让用户在每个线程当中处理它自己对应的request，并且有threadlocal清除的机制

    @Autowired
    private RedisTemplate redisTemplate;
    //用户注册接口
    @RequestMapping(value="/register",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name="telphone")String telphone,
                                     @RequestParam(name="otpCode")String otpCode,
                                     @RequestParam(name="name")String name,
                                     @RequestParam(name="gender")Integer gender,
                                     @RequestParam(name="password")String password,
                                     @RequestParam(name="age")Integer age) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //验证手机号和对应的optcode相符合
        String inSessionOtpCode=(String)this.httpServletRequest.getSession().getAttribute(telphone);
        if(!com.alibaba.druid.util.StringUtils.equals(otpCode,inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"短信验证码不合法");
        }
        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterModel("byphone");
        userModel.setEncrptPassword(this.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder= new BASE64Encoder();
        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        System.out.println(newstr);
        return newstr;

    }

    //用户登录接口
    @RequestMapping(value = "/login",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name="telphone")String telphone,
                                  @RequestParam(name="password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        //入参校验
        if(StringUtils.isEmpty(telphone)|| StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //用户登录服务，用来校验用户登录是否合法
        UserModel userModel=userService.validateLogin(telphone, this.EncodeByMd5(password));
        //修改成若用户登录验证成功后将对应的登录信息和登录凭证一起存入redis中
        //生成登录的凭证token，UUID
        String uuidToken = UUID.randomUUID().toString();
        uuidToken=uuidToken.replace("-","");
        //建立token和用户登录态之间的联系
        redisTemplate.opsForValue().set(uuidToken,userModel);
        redisTemplate.expire(uuidToken,1, TimeUnit.HOURS);
//        //将登录凭证加入到用户登录成功的session内
//        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
//        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
        //下发了token
        return CommonReturnType.create(uuidToken);

    }




    //用户获取opt短信接口
    @RequestMapping(value="/getotp",method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name="telphone")String telphone){
        //需要按照一定的规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt+=10000;
        String otpCode=String.valueOf(randomInt);
        //将OTP验证码同对应用户关联
        httpServletRequest.getSession().setAttribute(telphone,otpCode);
        //将OTP验证码通过短信通道发送给用户，省略
        System.out.printf("telphone ="+telphone+"&otpCode="+otpCode);
        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name="id")Integer id) throws BusinessException {
        //调用service服务获取对应id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);

        //将核心领域的对象转化为可供UI使用的viewobject
        UserVO userVO = convertFromModel(userModel);
        //若获取的对应用户信息不存在
        if (userModel == null){
            //userModel.setEncrptPassword("123");//注释下面异常可以人为制造未知错误
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }

        //返回通用对象
        return CommonReturnType.create(userVO);
    }
    private UserVO convertFromModel(UserModel userModel){
        if(userModel == null){
            return null;
        }
        else {
            UserVO userVO= new UserVO();
            BeanUtils.copyProperties(userModel,userVO);
            return userVO;
        }

    }

}

//跨域问题解决方案，有问题的童鞋看过来
//         先是版本不兼容
//        google配置后仍然无法正确获取otpcode。由于谷歌浏览器的SameSite安全机制的问题，浏览器在跨域的时候不允许request请求携带cookie，
//        导致每次sessionId都是新的，这里有个出问题前提：跨域，刚好和调试时的环境情况一致。浏览器版本chrome84.0.4147.135
//        （谷歌游览器好像从80版本之后就加入了SameSite安全机制）,直接在地址栏里输入chrome://flags/，然后在搜索框里搜索关键字SameSite,
//        找到与之匹配的项SameSite by default cookies，将其设置为Disabled,然后关闭浏览器再打开，请求。
