package com.szd.z000.orm.domain.z1;

import lombok.Data;

import java.math.BigDecimal;

/**
 * SDEM1-行项目
 *
 * @author ADMIN
 */
@Data
public class Z1Item {
    /**
     * 费用ID
     */
    private String bussId;
    /**
     * 行项目
     */
    private Integer itemNo;
    private String bsub;
    private String bsubName;
    /**
     * 会计科目
     */
    private String accs;
    private String accsName;
    /**
     * 借贷标识，S借 H贷
     */
    private String debitCreditFlag;
    /**
     * 税率
     */
    private BigDecimal taxr;
    /**
     * 金额
     */
    private BigDecimal amtApply;
    private BigDecimal amtApplyNoTax;
    private BigDecimal amtApplyTax;
    /**
     * 供应商
     */
    private String supp;
    private String suppName;
    /**
     * 客户
     */
    private String cust;
    private String custName;
    /**
     * 特别总账标识
     */
    private String accSubjectFlag;
    /**
     * 摘要
     */
    private String summary;
    /**
     * 成本中心
     */
    private String cstc;
    private String cstcName;
    /**
     * 利润中心
     */
    private String prfc;
    private String prfcName;
    /**
     * 业务范围
     */
    private String busc;
    private String buscName;
    /**
     * 段
     */
    private String segmentCode;
    private String segmentName;
    /**
     * 原因代码
     */
    private String reas;
    private String reasName;
    private String invNo;
    private String invCode;
}
