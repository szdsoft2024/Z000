package com.szd.z000.orm.domain.z1;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.szd.core.client.domain.wf.ClientWfBussBase;
import lombok.Data;

import java.util.Date;

/**
 * SDEM1-抬头
 */
@Data
public class Z1Header extends ClientWfBussBase {
    /** 业务ID */
    private String bussId;
    /** 制单人公司代码 */
    private String cmpyCreate;
    private String cmpyCreateName;
    /** 制单人成本中心 */
    private String cstcCreate;
    private String cstcCreateName;
    /** 费用承担公司 */
    private String cmpyExp;
    private String cmpyExpName;
    /** 费用承担成本中心 */
    private String cstcExp;
    private String cstcExpName;
    /** 业务发生成本公司 */
    private String cmpyBuss;
    private String cmpyBussName;
    /** 业务发生成本中心 */
    private String cstcBuss;
    private String cstcBussName;
    /** 报账人 */
    private String userId;
    private String userName;
    /** 币种 */
    private String curr;
    private String currName;
    /** 汇率 */
    private String exchangeRate;
    /** 业务日期 */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date bussDate;
    /** 状态 */
    private String bussStatus;
    private String bussStatusName;
    /** 外围系统状态描述 */
    private String sapStatusDesc;
    /** 付款方式 */
    private String paymentType;
    private String paymentTypeName;
    /** 费用说明 */
    private String remark;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
}
