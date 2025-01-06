package com.szd.z000.web.z1.domain;

import com.szd.z000.orm.domain.z1.Z1Header;
import com.szd.z000.orm.domain.z1.Z1Item;
import lombok.Data;

import java.util.List;

/**
 * 差旅费用单据打印视图对象
 */
@Data
public class Z1DataPrintVO {
    /** 打印标题 */
    private String title;
    /** 单据二维码 */
    private String qrCode;
    /** 单据头信息 */
    private Z1Header header;
    /** 行项目列表 */
    private List<Z1Item> itemList;
}
