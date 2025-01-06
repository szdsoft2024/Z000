package com.szd.z000.web.z1.service;

import com.szd.core.client.ClientCore;
import com.szd.z000.orm.domain.z1.Z1Item;
import com.szd.z000.orm.mapper.z1.Z1ItemMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * 费用-报销项目Service
 */
@Service
public class Z1ItemService {
    @Resource
    private Z1ItemMapper itemMapper;
    @Resource
    private ClientCore clientCore;

    /**
     * 查询费用-报销项目列表
     */
    public List<Z1Item> selectList(String bussId) {
        return itemMapper.selectList(bussId);
    }

    /**
     * 新增费用-报销项目
     */
    public void batchInsert(String bussId, List<Z1Item> itemList) {
        if (!CollectionUtils.isEmpty(itemList)) {
            itemList.forEach(i -> {
                i.setBussId(bussId);

            });
            itemMapper.batchInsert(itemList);
        }
    }

    /**
     * 删除费用-报销项目
     */
    public int deleteByBussId(String costId) {
        return itemMapper.deleteByBussId(costId);
    }

    /**
     * 报销项目数据校验
     */
    public void checkData(List<Z1Item> itemList) {
        if (!CollectionUtils.isEmpty(itemList)) {
            itemList.forEach(t -> {
                clientCore.mdm.checkAE("CORE_BSUB", t.getBsub(), "业务小类", "1");
                clientCore.mdm.checkAE("CORE_ACCS", t.getAccs(), "会计科目", "1");
                clientCore.mdm.checkAE("CORE_SUPP", t.getSupp(), "供应商", "1");
                clientCore.mdm.checkAE("CORE_CUST", t.getCust(), "客户", "1");
                clientCore.mdm.checkAE("CORE_CSTC", t.getCstc(), "成本中心", "1");
                clientCore.mdm.checkAE("CORE_PRFC", t.getPrfc(), "利润中心", "1");
                clientCore.mdm.checkAE("CORE_BUSC", t.getBusc(), "业务范围", "1");
                clientCore.mdm.checkAE("CORE_SEGM", t.getSegmentCode(), "段", "1");
            });
        }
    }

    public void update(String bussId, List<Z1Item> itemList) {
        // 删除行项目
        itemMapper.deleteByBussId(bussId);
        batchInsert(bussId, itemList);
    }
}
