package com.szd.z000.orm.domain.z1;

import com.szd.core.client.domain.common.ClientPageEntity;
import com.szd.core.client.domain.common.ClientRangeQuery;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 总账查询参数实体
 */
@Data
public class Z1RpParamVO extends ClientPageEntity {
    /** 路由编码 */
    private String routerCode;
    /**
     * 高级查询
     */
    private Map<String, List<ClientRangeQuery>> ranges;
}
