package com.easypoi.demo.handler;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import com.easypoi.demo.entity.Member;

/**
 * 自定义的导入校验器
 */
public class MyVerifyHandler implements IExcelVerifyHandler<Member> {

    @Override
    public ExcelVerifyHandlerResult verifyHandler(Member member) {
        ExcelVerifyHandlerResult result = new ExcelVerifyHandlerResult();
        //假设我们要添加用户，
        //现在去数据库查询loginName，如果存在则表示校验不通过。
        //假设现在数据库中有个loginName 1234560
        if ("1234560".equals(member.getLoginName())) {
            result.setMsg("该用户已存在");
            result.setSuccess(false);
            return result;
        }
        result.setSuccess(true);
        return result;
    }
}
