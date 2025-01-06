package com.szd.z000.web.z2.service;

import com.szd.core.client.ClientCore;
import com.szd.core.client.exception.ClientCustomException;
import com.szd.z000.orm.domain.z2.MdmCmpy;
import com.szd.z000.orm.mapper.z2.MdmCmpyMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service业务层处理
 *
 * @author szd
 * @date 2020-06-30
 */
@Service
public class MdmCmpyService {
    @Resource
    private MdmCmpyMapper mdmCmpyMapper;
    @Resource
    private ClientCore clientCore;

    public MdmCmpy selectCompanyByCode(String cmpy) {
        return mdmCmpyMapper.selectById(cmpy);
    }

    public List<MdmCmpy> selectCompanyList(MdmCmpy cmpy) {
        return mdmCmpyMapper.selectList(cmpy);
    }

    public int insertCompany(MdmCmpy cmpy) {
        checkData(cmpy);
        return mdmCmpyMapper.insert(cmpy);
    }

    public int updateCompany(MdmCmpy cmpy) {
        checkData(cmpy);
        return mdmCmpyMapper.update(cmpy);
    }

    public int deleteCompanyByCodes(String[] cmpys) {
        return mdmCmpyMapper.deleteByIds(cmpys);
    }

    private void checkData(MdmCmpy cmpy) {
        clientCore.mdm.checkA("CORE_VCH_ACCS", cmpy.getAccSet(), "账套", "0");
    }
}
