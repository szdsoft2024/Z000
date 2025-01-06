package com.szd.z000.orm.domain.z2;

import com.szd.core.client.domain.common.ClientPageEntity;
import com.szd.core.client.domain.common.ClientRangeQuery;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 公司代码主数据
 * @author szd
 * @date 2020-06-30
 */
@Data
public class MdmCmpy extends ClientPageEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id，用于常用设置查询
     */
    private String userId;

    /**
     * 公司代码
     */
    private String cmpy;

    /**
     * 公司名称
     */
    private String cmpyName;
    /**
     * 公司简称
     */
    private String cmpyNameShort;
    /**
     * 账套
     */
    private String accSet;
    private String accSetName;
    private Map<String, List<ClientRangeQuery>> ranges;
}
