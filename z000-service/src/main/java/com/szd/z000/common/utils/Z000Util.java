package com.szd.z000.common.utils;

import com.szd.core.client.ClientCore;
import com.szd.core.client.domain.common.ClientResult;
import com.szd.core.client.domain.wf.ClientWfBussBase;
import com.szd.core.client.domain.wf.ClientWfParamBuss;
import com.szd.core.client.exception.ClientCustomException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * SEXP工具类
 */
@Service
public class Z000Util {
    @Resource
    private Z000UtilBussBaseService bussBaseService;
    @Resource
    private ClientCore clientCore;

    public static Z000Util z000Util;

    @PostConstruct
    public void init() {
        z000Util = this;
        z000Util.clientCore = this.clientCore;
        z000Util.bussBaseService = this.bussBaseService;
    }


    /**
     * 获取各业务主表的辅助状态 status
     */
    public static String getBussStatus(String bussId) {
        String bussStatus = "";
        ClientWfBussBase wfBussBase = z000Util.clientCore.wf.baseGetObj(bussId);
        if (wfBussBase == null) {
            return bussStatus;
        }
        if ("0".equals(wfBussBase.getRejectStatus())) {
            bussStatus = "B";
        } else if ("0".equals(wfBussBase.getStatus())) {
            bussStatus = "A";
        } else if ("1".equals(wfBussBase.getStatus())) {
            bussStatus = "C";
        } else if ("6".equals(wfBussBase.getStatus())) {
            bussStatus = "D";
        } else if ("9".equals(wfBussBase.getStatus())) {
            bussStatus = "E";
        }
        return bussStatus;
    }

    public static void checkResult(ClientResult res) {
        if (res == null || !"S".equals(res.getStatus())) {
            throw new ClientCustomException(res == null ? "校验失败" : res.getMsg());
        }
    }

    /**
     * 获取bussId
     */
    public static String getBussId() {
        return z000Util.clientCore.tool.getNumSeq();
    }

    /**
     * 保存业务主体
     */
    public static void saveBussBase(Object header, String bussId, String cmpy, String cstc) {
        z000Util.bussBaseService.saveBussBase(header, bussId, cmpy, cstc);
    }

    /**
     * 保存业务主体
     */
    public static void saveBussBase(Object header, String bussId, String cmpy, String cstc, String userId) {
        z000Util.bussBaseService.saveBussBase(header, bussId, cmpy, cstc, userId);
    }

    /**
     * 设置业务参数
     */
    public static ClientWfParamBuss setWfParamBuss(ClientWfParamBuss wfParamBuss, Object header, String bussId) {
        return z000Util.bussBaseService.setWfParamBuss(wfParamBuss, header, bussId);
    }

    /**
     * 获取业务主体描述
     */
    public static void getBussBaseDesc(Object obj, String flag) {
        z000Util.bussBaseService.getBussBaseDesc(obj, flag);
    }
}
