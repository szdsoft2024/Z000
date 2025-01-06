package com.szd.z000.orm.mapper.z2;

import com.szd.z000.orm.domain.z2.MdmCmpy;

import java.util.List;

/**
 * 公司代码Mapper接口
 * @author szd
 * @date 2020-06-30
 */
public interface MdmCmpyMapper {
    /**
     * 查询公司代码
     */
    MdmCmpy selectById(String cmpy);

    /**
     * 查询公司代码列表
     */
    List<MdmCmpy> selectList(MdmCmpy cmpy);

    /**
     * 新增公司代码
     */
    int insert(MdmCmpy cmpy);

    /**
     * 修改公司代码
     */
    int update(MdmCmpy cmpy);

    /**
     * 批量删除公司代码
     */
    int deleteByIds(String[] cmpys);

    /**
     * 查询常用设置
     */
    List<MdmCmpy> selectCmpyCommonList(MdmCmpy cmpy);
}
