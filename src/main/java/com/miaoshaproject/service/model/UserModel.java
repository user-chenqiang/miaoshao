package com.miaoshaproject.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

//redis默认序列化的方式使用jdk序列化的方式，因此要存入redis需要实现Serializable接口
//方法二：这里也可以不实现该接口，而设置redis使用json序列化方式，在跨系统应用中是最好的
public class UserModel implements Serializable {

    private Integer id;
    //不能为空字符串和null
    @NotBlank(message ="用户名不能为空")
    private String name;
    //属性不能为null
    @NotNull(message ="性别不能不填写")
    private Byte gender;
    @NotNull(message ="年龄不能不填写")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄必须小于150岁")
    private Integer age;
    @NotBlank(message ="手机号不能为空")
    private String telphone;
    private String registerModel;
    private String thirdPartyId;
    @NotBlank(message ="密码不能为空")
    private String encrptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegisterModel() {
        return registerModel;
    }

    public void setRegisterModel(String registerModel) {
        this.registerModel = registerModel;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
