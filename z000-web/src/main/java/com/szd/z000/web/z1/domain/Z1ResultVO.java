package com.szd.z000.web.z1.domain;

import com.szd.core.client.domain.wf.ClientWfOper;
import lombok.Data;

/**
 * 业务参数
 */
@Data
public class Z1ResultVO {
    /** 业务表单数据 */
    private Z1DataVO data;
    /** 流程操作授权 */
    private ClientWfOper wfOper;
}

