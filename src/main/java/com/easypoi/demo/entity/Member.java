package com.easypoi.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 首先创建一个导入导出的实体类。
 * 需要导入导出的字段使用@Excel注解
 */
public class Member implements IExcelModel {

    @Excel(name = "用户id")
    @NotNull
    private Long id;

    @Excel(name = "用户姓名")
    @NotEmpty
    private String name;

    @Excel(name = "生日", format = "yyyy-MM-dd HH:mm:ss")
    private Date birthday;

    @Excel(name = "性别", replace = {"男_0", "女_1"}) //replace使用的时候直接"实际含义_数据库实际内容"即可，导入导出均适用
    @Pattern(regexp = "^[0-1]$", message = "性别输入有误")
    private String sex;

    @Excel(name = "用户年龄")
    @Pattern(regexp = "^\\d{1,3}$", message = "年龄输入有误")
    private String age;

    @Excel(name = "电话", width = 16)
    @Pattern(regexp = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", message = "电话输入有误")
    private String phone;

    @Excel(name = "用户账号", width = 16)
    private String loginName;

    @Excel(name = "用户头像", width = 32, height = 32, type = 2)
    private String pic;

    public Member() {}

    public Member(Long id, String name, String sex, Date birthday, String age, String phone, String loginName, String pic) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.age = age;
        this.phone = phone;
        this.loginName = loginName;
        this.pic = pic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", phone='" + phone + '\'' +
                ", loginName='" + loginName + '\'' +
                ", pic='" + pic + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }

    //自定义errorMsg接收IExcelModel.setErrorMsg传过来的errorMsg
    private String errorMsg;

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
