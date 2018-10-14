package com.easypoi.demo.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.ArrayList;
import java.util.List;

/**
 * excel导入错误信息实体类
 */
public class MemberFailed extends Member {

    @Excel(name = "错误信息")
    private String errorMsg;

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static MemberFailed member2MemberFailed(Member member) {
        MemberFailed failed = new MemberFailed();
        failed.setErrorMsg(member.getErrorMsg());
        failed.setAge(member.getAge());
        failed.setBirthday(member.getBirthday());
        failed.setId(member.getId());
        failed.setLoginName(member.getLoginName());
        failed.setName(member.getName());
        failed.setPhone(member.getPhone());
        failed.setPic(member.getPic());
        failed.setSex(member.getSex());
        return failed;
    }

    public static List<MemberFailed> members2MemberFaileds(List<Member> members) {
        List<MemberFailed> list = new ArrayList<>();
        for (Member member : members) {
            list.add(member2MemberFailed(member));
        }
        return list;
    }
}
