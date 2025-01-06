package com.szd.z000.web.z1.service;

import com.szd.core.client.domain.inv.ClientInv;
import com.szd.core.client.domain.inv.ClientInvGetList;
import com.szd.core.client.domain.inv.ClientInvGetRet;
import com.szd.core.client.domain.wf.*;
import com.szd.core.client.exception.ClientCustomException;
import com.szd.core.client.domain.common.ClientCheckResult;
import com.szd.core.client.ClientCore;
import com.szd.z000.common.utils.Z000Util;
import com.szd.z000.orm.domain.z1.Z1Header;
import com.szd.z000.orm.domain.z1.Z1RpParamVO;
import com.szd.z000.web.z1.domain.*;
import com.szd.z000.orm.domain.z1.Z1Item;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Z1功能业务逻辑
 */
@Service
public class Z1Service {
    private static final Logger logger = LoggerFactory.getLogger(Z1Service.class);
    @Resource
    private ClientCore clientCore;
    @Resource
    private Z1HeaderService z1HeaderService;
    @Resource
    private Z1ItemService z1ItemService;

    public List<Z1Header> selectList(Z1RpParamVO header) {
        return z1HeaderService.selectList(header);
    }

    /**
    * 查询详情
    */
    public Z1DataVO selectData(String bussId) {
        Z1DataVO z1DataVO = new Z1DataVO();
        // 抬头
        Z1Header header = z1HeaderService.selectById(bussId);
        // 行项目
        List<Z1Item> itemList = z1ItemService.selectList(bussId);
        // 发票项目
        List<ClientInv> invList = new ArrayList<>();
        ClientInvGetList clientInvGetList = new ClientInvGetList();
        clientInvGetList.setBussId(bussId);
        List<ClientInvGetList> params = new ArrayList<>();
        params.add(clientInvGetList);
        List<ClientInvGetRet> invGetRetList = clientCore.inv.getInvList(params);
        if (!CollectionUtils.isEmpty(invGetRetList)) {
            for (ClientInvGetRet clientInvGetRet : invGetRetList) {

                invList.add(clientInvGetRet.getHeader());
            }
        }
        z1DataVO.setHeader(header);
        z1DataVO.setItemList(itemList);
        z1DataVO.setInvList(invList);
        return z1DataVO;
    }

    /**
    * 保存数据
    */
    public Z1ReturnVO saveData(Z1ParamVO z1ParamVO) {
        Z1ReturnVO z1ReturnVO = new Z1ReturnVO();
        ClientWfParamBuss wfParamBuss = new ClientWfParamBuss();
        // 检查业务数据
        checkData(z1ParamVO.getData());
        // 保存业务数据
        save(z1ParamVO, wfParamBuss);
        // 审批
        ClientWfReturn wfReturn = approval(z1ParamVO.getData().getHeader(), z1ParamVO.getWfEvt(), z1ParamVO.getData().getHeader().getBussId());
        BeanUtils.copyProperties(wfReturn, z1ReturnVO);
        z1ReturnVO.setBussId(z1ParamVO.getData().getHeader().getBussId());
        z1ReturnVO.setBussDocId(z1ParamVO.getData().getHeader().getBussDocId());
        // 更新业务单据状态
        if (!"E".equals(z1ReturnVO.getRetCodeF())) {
            //草稿状态的单据，作废时直接删除
            if ("btn_delete".equals(z1ParamVO.getWfEvt().getOperate()) && "A".equals(z1ParamVO.getData().getHeader().getBussStatus())) {
                z1HeaderService.deleteById(z1ReturnVO.getBussId());
            } else {
                z1HeaderService.updateStatusAndId(z1ReturnVO.getBussId(), wfReturn.getBussDocId());
            }
        }
        return z1ReturnVO;
    }

    /**
     * 保存草稿
     */
    @Transactional(rollbackFor = Exception.class)
    public ClientWfResult save(Z1ParamVO z1ParamVO, ClientWfParamBuss wfParamBuss) {
        ClientWfResult wfResult = new ClientWfResult();
        try {
            // 业务数据
            Z1DataVO data = z1ParamVO.getData();
            Z1Header header = data.getHeader();
            // 业务类型赋值
            header.setBstp(header.getBstp());
            String bussId;
            // 首次保存
            if (StringUtils.isBlank(header.getBussId())) {
                // 单据id
                bussId = Z000Util.getBussId();
                header.setBussId(bussId);
                // 保存业务主体
                Z000Util.saveBussBase(header, header.getBussId(), header.getCmpyExp(), header.getCstcExp());
                // 新增
                insert(data);
            } else {
                // 保存业务主体
                Z000Util.saveBussBase(header, header.getBussId(), header.getCmpyExp(), header.getCstcExp());
                // 更新
                update(z1ParamVO);
            }
            // 设置工作流字段
            Z000Util.setWfParamBuss(wfParamBuss, header, header.getBussId());
        } catch (Exception e) {
            logger.error("保存失败", e);
            throw new ClientCustomException("保存失败:" + e.getMessage());
        }
        return wfResult;
    }

    /**
     * SEXP审批
     *
     * @param header      业务抬头表
     * @param wfEvt       流程事件
     * @param bussId      业务单号
     * @return
     */
    public ClientWfReturn approval(Z1Header header, ClientWfParamEvt wfEvt, String bussId) {
        ClientWfReturn wfReturn = new ClientWfReturn();
        try {
            ClientWfParamBuss wfParamBuss = new ClientWfParamBuss();
            // 业务参数-通用
            Z000Util.setWfParamBuss(wfParamBuss, header, bussId);
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
            String status = Z000Util.getBussStatus(bussId);
            // 审批完成
            if ("D".equals(status)) {
                // 其他业务处理
            }
        } catch (Exception e) {
            wfReturn.setRetCodeF("E");
            wfReturn.setRetMsg(e.getMessage());
        }
        return wfReturn;
    }

    /**
     * 业务数据新增
     */
    void insert(Z1DataVO data) {
        /** 抬头数据 */
        Z1Header header = data.getHeader();
        // 保存抬头
        z1HeaderService.insert(header);
        // 保存行项目
        z1ItemService.batchInsert(header.getBussId(), data.getItemList());
    }

    /**
     * 业务数据更新
     */
    void update(Z1ParamVO z1ParamVO) {
        /** 抬头数据 */
        Z1DataVO data = z1ParamVO.getData();
        ClientWfParamEvt wfEvt = z1ParamVO.getWfEvt();
        this.saveLog(data, "btn_save");
        Z1Header header = data.getHeader();
        // 更新抬头
        z1HeaderService.update(header);
        // 更新费用行项目
        z1ItemService.update(header.getBussId(), data.getItemList());

    }

    /**
    * 记录日志
    */
    public void saveLog(Z1DataVO data, String operate) {
        Z1Header header = data.getHeader();
        String bussId = header.getBussId();
        // 日志
        Z1DataVO dataOld = this.selectData(bussId);
        clientCore.tool.clogSaveA(bussId, "z000_header", operate, data.getHeader(), dataOld.getHeader());
        clientCore.tool.clogSaveB(bussId, "itemNo", "z000_item", operate, data.getItemList(), dataOld.getItemList());

    }

    /**
    * 数据检查
    */
    public ClientCheckResult check(Z1ParamVO z1ParamVO) {
        try {
            checkData(z1ParamVO.getData());
        } catch (ClientCustomException e) {
            return ClientCheckResult.error("E", 500, e.getMessage(), z1ParamVO.getData().getHeader().getBussId());
        }
        return ClientCheckResult.success("S", 200, "检查通过");
    }

    /**
    * 数据检查
    */
    private void checkData(Z1DataVO z1DataVO) {
        /** 校验-抬头数据 */
        z1HeaderService.checkData(z1DataVO.getHeader());
        z1ItemService.checkData(z1DataVO.getItemList());
    }
}
