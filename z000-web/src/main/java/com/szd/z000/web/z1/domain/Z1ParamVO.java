package com.szd.z000.web.z1.domain;
import com.szd.core.client.domain.wf.ClientWfParamEvt;

import lombok.Data;

/**
 * Z000_DLZ_TEST03 保存结果
 */
@Data
public class Z1ParamVO {
    /**
     * 业务表单-公共数据
     */
    private Z1DataVO data;
    /**
     * 操作类型 U编辑 V查看
     */
    private ClientWfParamEvt wfEvt;
}
