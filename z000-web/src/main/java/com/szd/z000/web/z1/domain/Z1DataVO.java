package com.szd.z000.web.z1.domain;

import com.szd.core.client.domain.inv.ClientInv;
import com.szd.z000.orm.domain.z1.Z1Header;
import com.szd.z000.orm.domain.z1.Z1Item;
import lombok.Data;

import java.util.List;

/**
 * Z1功能业务数据对象
 */
@Data
public class Z1DataVO {
    private String routerCode;
    /**
     * 单据抬头
     */
    private Z1Header header;
    /**
     * 单据行项目
     */
    private List<Z1Item> itemList;
    /**
     * 发票项目
     */
    private List<ClientInv> invList;
}
