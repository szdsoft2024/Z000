package com.szd.z000.orm.mapper.z1;

import com.szd.z000.orm.domain.z1.Z1Item;
import java.util.List;

/**
 * SDEM1-行项目
 *
 * @author ADMIN
 */
public interface Z1ItemMapper {
    /**
     * 查询列表
     */
    List<Z1Item> selectList(String bussId);
    /**
     * 批量新增     */
    int batchInsert(List<Z1Item> list);
    /**
     * 批量删除
     */
    int deleteByBussId(String bussId);
}
