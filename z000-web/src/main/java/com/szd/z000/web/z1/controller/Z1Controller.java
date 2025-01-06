package com.szd.z000.web.z1.controller;

import com.github.pagehelper.util.StringUtil;
import com.szd.core.client.ClientCore;
import com.szd.core.client.constants.ClientConstMdm;
import com.szd.core.client.domain.common.ClientPageTableRet;
import com.szd.core.client.domain.inv.ClientInv;
import com.szd.core.client.domain.mdm.ClientObjCmpy;
import com.szd.core.client.domain.wf.ClientWfOper;
import com.szd.core.client.domain.common.ClientAjaxResult;
import com.szd.core.client.exception.ClientCustomException;
import com.szd.core.client.service.ClientBaseController;
import com.szd.core.client.domain.common.ClientCheckResult;
//import com.szd.core.client.util.ClientUtilBar;
import com.szd.z000.common.util.CommonService;
import com.szd.z000.common.utils.Z000Util;
import com.szd.z000.orm.domain.z1.Z1Header;
import com.szd.z000.orm.domain.z1.Z1Item;
import com.szd.z000.orm.domain.z1.Z1RpParamVO;
import com.szd.z000.web.z1.domain.*;
import com.szd.z000.web.z1.service.Z1Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * z1功能相关接口
 */
@RestController
@RequestMapping("/z000/z1")
public class Z1Controller extends ClientBaseController {
    @Resource
    private Z1Service z1Service;
    @Resource
    private ClientCore clientCore;
    @Resource
    private CommonService commonService;

    /**
     * 清单查询
     */
    @PostMapping("/list")
    public ClientPageTableRet list(@RequestBody Z1RpParamVO header) {
        startPage();
        List<Z1Header> list = z1Service.selectList(header);
        getDescList(list);
        return getPageTable(list);
    }

    /**
    * 页面查询(包含业务数据、流程按钮、字段状态)
    */
     @GetMapping("/get")
    public ClientAjaxResult getData(String bussId, String userId, String option) {
         // 业务数据
         Z1DataVO data = z1Service.selectData(bussId);
         // 添加描述
         getDesc(data);
         // 流程操作授权
         ClientWfOper wfOper = clientCore.wf.wfInit(bussId, userId, option);
         // 返回
         Z1ResultVO dataResultVO = new Z1ResultVO();
         dataResultVO.setData(data);
         dataResultVO.setWfOper(wfOper);
         return ClientAjaxResult.success(dataResultVO);
    }

    /**
    * 页面按钮操作(保存草稿，提交，同意，驳回。。。)
    */
    @PostMapping("/save")
    public ClientAjaxResult save(@RequestBody Z1ParamVO z1ParamVO) {
        // 保存业务数据
        try {
            z1ParamVO.getData().getHeader().setRouterCode("Z000_Z1_01A");
            Z1ReturnVO z1ReturnVO = z1Service.saveData(z1ParamVO);
            return ClientAjaxResult.success(z1ReturnVO);
        } catch (ClientCustomException clientCustomException) {
            return ClientAjaxResult.error(clientCustomException.getMessage());
        }
    }

    /**
    * 数据检查
    */
    @PostMapping("/check")
    public ClientCheckResult check(@RequestBody Z1ParamVO saveParam) {
        return z1Service.check(saveParam);
    }

    /**
     * 打印
     */
    @PostMapping("/print")
    public ClientAjaxResult printData(@RequestBody String[] bussIds) {
        List<Z1DataPrintVO> printVOList = new ArrayList<>();
        for (String bussId : bussIds) {
            // 返回打印页面数据及包含bussId的二维码
            Z1DataPrintVO printVO = new Z1DataPrintVO();
            printVO.setTitle("单据打印");
//            printVO.setQrCode(ClientUtilBar.getQRCode("A01." + bussId));
            // 单据抬头数据
            Z1DataVO data = z1Service.selectData(bussId);
            getDesc(data);
            printVO.setHeader(data.getHeader());
            printVO.setItemList(data.getItemList());
            printVOList.add(printVO);
        }
        return ClientAjaxResult.success(printVOList);
    }

    /**
     * 获取字段描述-报表
     */
    private void getDescList(List<Z1Header> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(header -> {
                header.setCmpyExpName(clientCore.mdm.getDescA("CORE_CMPY", header.getCmpyExp(), "0"));
                header.setCstcExpName(clientCore.mdm.getDescA("CORE_CSTC", header.getCstcExp(), "0"));
                header.setUserId(clientCore.mdm.getDescA("CORE_USER", header.getUserId(), "0"));
                header.setBstp(clientCore.mdm.getDescA("CORE_BSTP", header.getBstp(), "0"));
                header.setBussStatus(clientCore.mdm.getDescA("SEXP_DOC_STAU", header.getBussStatus(), "0"));
                header.setCreateBy(clientCore.mdm.getDescA("CORE_USER", header.getCreateBy(), "0"));
                header.setUpdateBy(clientCore.mdm.getDescA("CORE_USER", header.getUpdateBy(), "0"));
                commonService.getBussBaseDesc(header, "0");
            });
        }
    }

    /**
     * 获取字段描述
     */
    private void getDesc(Z1DataVO data) {
        if (data.getHeader() != null) {
            data.getHeader().setUserName(clientCore.mdm.getDescA("CORE_USER", data.getHeader().getUserId(), "1"));
            data.getHeader().setBstpName(clientCore.mdm.getDescA("CORE_BSTP", data.getHeader().getBstp(), "1"));
            data.getHeader().setCurrName(clientCore.mdm.getDescA("CORE_CURR", data.getHeader().getCurr(), "1"));
            data.getHeader().setCmpyCreateName(clientCore.mdm.getDescA("CORE_CMPY", data.getHeader().getCmpyCreate(), "1"));
            data.getHeader().setCstcCreateName(clientCore.mdm.getDescA("CORE_CSTC", data.getHeader().getCstcCreate(), "1"));
            data.getHeader().setCmpyExpName(clientCore.mdm.getDescA("CORE_CMPY", data.getHeader().getCmpyExp(), "1"));
            data.getHeader().setCstcExpName(clientCore.mdm.getDescA("CORE_CSTC", data.getHeader().getCstcExp(), "1"));
            data.getHeader().setCmpyBussName(clientCore.mdm.getDescA("CORE_CMPY", data.getHeader().getCmpyBuss(), "1"));
            data.getHeader().setCstcBussName(clientCore.mdm.getDescA("CORE_CSTC", data.getHeader().getCstcBuss(), "1"));
            Z000Util.getBussBaseDesc(data.getHeader(), "1");
        }
        if (!CollectionUtils.isEmpty(data.getInvList())) {
            for (ClientInv clientInv : data.getInvList()) {
                clientInv.setInvTypeCode(clientCore.mdm.getDescA("CORE_INVT", clientInv.getInvTypeCode(), "0"));
                clientInv.setCheckFlag(clientCore.mdm.getDescA("CORE_INV_REPTYP", clientInv.getCheckFlag(), "0"));
            }
        }
        if (!CollectionUtils.isEmpty(data.getItemList())) {
            ClientObjCmpy cmpyObj = clientCore.mdm.getObjA(ClientConstMdm.CORE_CMPY, data.getHeader().getCmpyExp(), ClientObjCmpy.class);
            for (Z1Item item : data.getItemList()) {
                item.setBsubName(clientCore.mdm.getDescA("CORE_BSUB", item.getBsub(), "1"));
                item.setSuppName(clientCore.mdm.getDescA("CORE_SUPP", item.getSupp(), "1"));
                item.setCustName(clientCore.mdm.getDescA("CORE_CUST", item.getCust(), "1"));
                item.setCstcName(clientCore.mdm.getDescA("CORE_CSTC", item.getCstc(), "1"));
                item.setPrfcName(clientCore.mdm.getDescA("CORE_PRFC", item.getPrfc(), "1"));
                item.setBuscName(clientCore.mdm.getDescA("CORE_BUSC", item.getBusc(), "1"));
                item.setSegmentName(clientCore.mdm.getDescA("CORE_SEGM", item.getSegmentCode(), "1"));
                if (cmpyObj != null && StringUtil.isNotEmpty(cmpyObj.getAccSet())) {
                    item.setAccsName(clientCore.mdm.getDescB("CORE_ACCS", cmpyObj.getAccSet(), item.getAccs(), "1"));
                }
            }
        }
    }
}
