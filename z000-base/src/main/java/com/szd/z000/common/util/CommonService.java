package com.szd.z000.common.util;

import com.szd.core.client.ClientCore;
import com.szd.core.client.domain.wf.*;
import com.szd.core.client.security.ClientSecurity;
import com.szd.core.client.util.ClientUtilReflect;
import com.szd.z000.common.constant.sysConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用方法
 *
 * @author chx
 */
@Service
public class CommonService {
    private static final Logger logger = LoggerFactory.getLogger(CommonService.class);


    @Resource
    private ClientCore clientCore;

    /**
     * sexp审批处理
     */
    public ClientWfReturn approval(Object header, ClientWfParamEvt wfEvt, String bussId, boolean accDocSap) {
        ClientWfReturn wfReturn = new ClientWfReturn();
        try {
            ClientWfParamBuss wfParamBuss = new ClientWfParamBuss();
            // 业务参数-通用
            this.setWfParamBuss(wfParamBuss, header, bussId);

            // 是否调用工作流
            if (clientCore.wf.wfCheckEvt(wfEvt.getOperate())) {
                // 调用工作流
                ClientWfParam wfParam = new ClientWfParam(wfEvt, wfParamBuss);
                wfReturn = clientCore.wf.wfCall(wfParam);
            } else {
                wfReturn.setRetCodeF("S");
                wfReturn.setRetMsg("处理成功");
            }
            if ("B".equals(wfReturn.getRetCodeF())) {
                wfReturn.setRetMsg("处理成功");
            }

            String status = SdemUtil.getBussStatus(bussId);

        } catch (Exception e) {
            wfReturn.setRetCodeF("E");
            wfReturn.setRetMsg(e.getMessage());
        }
        return wfReturn;
    }

    /**
     * 保存业务主体
     */
    public void saveBussBase(Object header, String bussId, String cmpy, String cstc) {
        ClientWfBussBase wfBussBase = new ClientWfBussBase();
        BeanUtils.copyProperties(header, wfBussBase);
        wfBussBase.setCreateBy(ClientSecurity.getUserId());
        wfBussBase.setBussId(bussId);
        wfBussBase.setCmpy(cmpy);
        wfBussBase.setCstc(cstc);
        ClientWfBussBase wfBussBaseOld = clientCore.wf.baseGetObj(bussId);
        if (wfBussBaseOld != null) {
            // 保存日志
            clientCore.tool.clogSaveA(bussId, sysConstant.WF_BUSS_BASE, "保存", wfBussBase, wfBussBaseOld);
        }
        clientCore.wf.baseSave(wfBussBase);
    }

    /**
     * 设置业务参数
     */
    public ClientWfParamBuss setWfParamBuss(ClientWfParamBuss wfParamBuss, Object header, String bussId) {
        // 查询业务类型对应的流程组-流程-流程字段
        Map<String, String> wfFields = new HashMap<>(16);
        Map<String, String> stringStringMap = clientCore.wf.wfSetField(header);
        if (CollectionUtils.isEmpty(stringStringMap)) {
            stringStringMap = new HashMap<>();
        }
        if (!stringStringMap.containsKey("amtApply")) {
            stringStringMap.put("amtApply", "");
        }
        for (String code : stringStringMap.keySet()) {
            String refField = "wf" + code.substring(0, 1).toUpperCase() + code.substring(1);
            Object fieldValue = ClientUtilReflect.getFieldValue(header, refField);
            if (fieldValue != null) {
                wfFields.put(code, fieldValue.toString());
            }
        }
        wfParamBuss.setBussId(bussId);
        Object cmpy = ClientUtilReflect.getFieldValue(header, "cmpyExp");
        if (cmpy != null) {
            wfFields.put("cmpy", cmpy.toString());
        }
        Object cstc = ClientUtilReflect.getFieldValue(header, "cstcExp");
        if (cstc != null) {
            wfFields.put("cstc", cstc.toString());
        }
        wfParamBuss.setWfFields(wfFields);
        return wfParamBuss;
    }

    /**
     * 获取业务主体描述
     *
     * @param flag (0-code.name;1-name)
     */
    public void getBussBaseDesc(Object obj, String flag) {
        ClientWfBussBase wfBussBase = new ClientWfBussBase();
        BeanUtils.copyProperties(obj, wfBussBase);
        getDesc(wfBussBase, flag);
        BeanUtils.copyProperties(wfBussBase, obj);
    }

    public void getDesc(ClientWfBussBase wfBussBase, String flag) {
        wfBussBase.setCmpyName(clientCore.mdm.getDescA("CORE_CMPY", wfBussBase.getCmpy(), flag));
        wfBussBase.setCstcName(clientCore.mdm.getDescA("CORE_CSTC", wfBussBase.getCstc(), flag));
        wfBussBase.setBstpName(clientCore.mdm.getDescA("CORE_BSTP", wfBussBase.getBstp(), flag));
        if ("0".equals(flag)) {
            wfBussBase.setStatusName(clientCore.mdm.getDescA("CORE_WF_DOST", wfBussBase.getStatus(), flag));
            wfBussBase.setRejectStatusName(clientCore.mdm.getDescA("CORE_SYS_YENO", wfBussBase.getRejectStatus(), flag));
        }
        wfBussBase.setCreateByName(clientCore.mdm.getDescA("CORE_USER", wfBussBase.getCreateBy(), flag));
        wfBussBase.setCommitByName(clientCore.mdm.getDescA("CORE_USER", wfBussBase.getCommitBy(), flag));
        wfBussBase.setUpdateByName(clientCore.mdm.getDescA("CORE_USER", wfBussBase.getUpdateBy(), flag));
    }
}
