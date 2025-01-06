package com.szd.z000.common.utils;

import com.szd.core.client.ClientCore;
import com.szd.core.client.domain.wf.ClientWfBussBase;
import com.szd.core.client.domain.wf.ClientWfDyInfo;
import com.szd.core.client.domain.wf.ClientWfParamBuss;
import com.szd.core.client.security.ClientSecurity;
import com.szd.core.client.util.ClientUtilReflect;
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
 * 业务主体
 *
 * @author Dave
 */
@Service
public class Z000UtilBussBaseService {
    private static final Logger logger = LoggerFactory.getLogger(Z000UtilBussBaseService.class);
    @Resource
    private ClientCore clientCore;

    /**
     * 保存业务主体
     */
    public void saveBussBase(Object header, String bussId, String cmpy, String cstc) {
        this.saveBussBase(header, bussId, cmpy, cstc, ClientSecurity.getUserId());
    }

    /**
     * 保存业务主体
     */
    public void saveBussBase(Object header, String bussId, String cmpy, String cstc, String userId) {
        ClientWfBussBase wfBussBase = new ClientWfBussBase();
        BeanUtils.copyProperties(header, wfBussBase);
        wfBussBase.setCreateBy(userId);
        wfBussBase.setBussId(bussId);
        wfBussBase.setCmpy(cmpy);
        wfBussBase.setCstc(cstc);
        ClientWfBussBase wfBussBaseOld = clientCore.wf.baseGetObj(bussId);
        if (wfBussBaseOld != null) {
            // 保存日志
            clientCore.tool.clogSaveA(bussId, "wf_buss_base", "保存", wfBussBase, wfBussBaseOld);
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
     */
    public void getBussBaseDesc(Object obj, String flag) {
        ClientWfBussBase wfBussBase = new ClientWfBussBase();
        BeanUtils.copyProperties(obj, wfBussBase);
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
        ClientWfDyInfo wfDyInfo = null;
        if (StringUtils.isNotBlank(wfBussBase.getWfDyId())) {
            wfDyInfo = clientCore.wf.dyGetObj(wfBussBase.getWfDyId());
        }
        if (wfDyInfo != null) {
            if ("0".equals(flag)) {
                wfBussBase.setWfDyName(wfDyInfo.getWfDyId() + "." + wfDyInfo.getWfDyName());
            } else {
                wfBussBase.setWfDyName(wfDyInfo.getWfDyName());
            }
        } else {
            wfBussBase.setWfDyName("");
        }
        BeanUtils.copyProperties(wfBussBase, obj);
    }

}
