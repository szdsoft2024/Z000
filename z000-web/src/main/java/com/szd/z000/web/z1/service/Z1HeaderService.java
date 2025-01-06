package com.szd.z000.web.z1.service;

import com.szd.core.client.ClientCore;
import com.szd.core.client.domain.common.ClientResult;
import com.szd.core.client.domain.wf.ClientWfBussBase;
import com.szd.core.client.security.ClientSecurity;
import com.szd.z000.common.utils.Z000Util;
import com.szd.z000.orm.domain.z1.Z1Header;
import com.szd.z000.orm.domain.z1.Z1RpParamVO;
import com.szd.z000.orm.mapper.z1.Z1HeaderMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 费用报销业务抬头Service
 */
@Service
@Slf4j
public class Z1HeaderService {
    @Resource
    private Z1HeaderMapper headerMapper;
    @Resource
    private Z1ItemService z1ItemService;
    @Resource
    private ClientCore clientCore;

    public List<Z1Header> selectList(Z1RpParamVO header) {
        List<Z1Header> headerList = headerMapper.selectList(header);
        if (!CollectionUtils.isEmpty(headerList)) {
            List<String> bussIds = new ArrayList<>(16);
            for (Z1Header temp : headerList) {
                bussIds.add(temp.getBussId());
            }
            List<ClientWfBussBase> clientwfBussBaselist= clientCore.wf.baseGetObjBatch(bussIds);
            for (Z1Header temp : headerList) {
                ClientWfBussBase wfBussBase = clientwfBussBaselist.stream().filter(item -> item.getBussId().equals(temp.getBussId())).findFirst().orElse(null);
                if (wfBussBase != null) {
                    BeanUtils.copyProperties(wfBussBase, temp);
                }
            }
        }
        return  headerList;
    }
    /**
     * 查询抬头
     */
    public Z1Header selectById(String costId) {
        Z1Header header = headerMapper.selectById(costId);
        if (header == null) {
            return new Z1Header();
        }
        ClientWfBussBase wfBussBase = clientCore.wf.baseGetObj(costId);
        if (wfBussBase != null) {
            BeanUtils.copyProperties(wfBussBase, header);
        }
        return header;
    }

    /**
     * 保存抬头
     */
    public int insert(Z1Header costHeader) {
        // 初始化状态
        costHeader.setBussStatus("A");
        costHeader.setCreateBy(ClientSecurity.getUserId());
        return headerMapper.insert(costHeader);
    }

    /**
     * 更新抬头
     */
    public int update(Z1Header costHeader) {
        return headerMapper.update(costHeader);
    }

    public void updateStatusByBussId(String bussDocId, String status) {
        headerMapper.updateStatusByBussId(status, bussDocId);
    }

    public void updateStatusAndId(String bussId, String bussDocId) {
        String status = Z000Util.getBussStatus(bussId);
        if (StringUtils.isNotBlank(status)) {
            headerMapper.updateStatusAndId(bussId, status, bussDocId);
        }
    }

    /**
     * 删除抬头
     */
    @Transactional(rollbackFor = Exception.class)
    public int deleteById(String costId) {
        batchDeleteItems(costId);
        return headerMapper.deleteById(costId);
    }

    /**
     * 关联删除明细
     */
    private void batchDeleteItems(String costId) {
        z1ItemService.deleteByBussId(costId);
    }

    /**
     * 抬头数据校验
     */
    public void checkData(Z1Header header) {
        header.setBstp(header.getBstp());
        clientCore.mdm.checkAE("CORE_USER", header.getUserId(), "用户", "1");
        clientCore.mdm.checkAE("CORE_CMPY", header.getCmpyCreate(), "制单人公司", "1");
        clientCore.mdm.checkBE("CORE_CSTC", header.getCmpyCreate(), header.getCstcCreate(), "制单人部门", "1");
        clientCore.mdm.checkAE("CORE_CMPY", header.getCmpyExp(), "承担公司", "1");
        clientCore.mdm.checkBE("CORE_CSTC", header.getCmpyExp(), header.getCstcExp(), "承担部门", "1");
        clientCore.mdm.checkAE("CORE_CMPY", header.getCmpyBuss(), "业务发生公司", "1");
        clientCore.mdm.checkBE("CORE_CSTC", header.getCmpyBuss(), header.getCstcBuss(), "业务发生部门", "1");
        clientCore.mdm.checkAE("CORE_BSTP", header.getBstp(), "业务类型", "1");
        clientCore.mdm.checkAE("CORE_CURR", header.getCurr(), "货币", "1");
        if (StringUtils.isBlank(header.getBussStatus()) || "0".equals(header.getBussStatus())) {
            ClientResult retResult = clientCore.wf.dyCheck(header.getBstp(), header.getUserId(), header.getWfDyId());
            header.setWfDyId(retResult.getDocId());
        }
    }
}
