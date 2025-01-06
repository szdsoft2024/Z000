package com.szd.z000.orm.mapper.z1;

import com.szd.z000.orm.domain.z1.Z1Header;
import com.szd.z000.orm.domain.z1.Z1RpParamVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SDEM1-抬头
 *
 * @author ADMIN
 */
public interface Z1HeaderMapper {
    /**
     * 查询列表
     */
    List<Z1Header> selectList(Z1RpParamVO header);
    /**
     * 查询详情
     */
    Z1Header selectById(String bussId);
    /**
     * 新增
     */
    int insert(Z1Header header);
    /**
     * 修改
     */
    int update(Z1Header header);
    /**
     * 删除
     */
    int deleteById(String bussId);

    void updateStatusByBussId(@Param("bussStatus") String bussStatus, @Param("bussDocId") String bussDocId);

    void updateStatusAndId(@Param("bussId") String bussId, @Param("bussStatus") String bussStatus, @Param("bussDocId") String bussDocId);
}
