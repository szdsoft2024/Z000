package com.szd.z000.web.z2.controller;

import com.szd.core.client.ClientCore;
import com.szd.core.client.constants.ClientConstMdm;
import com.szd.core.client.domain.common.ClientAjaxResult;
import com.szd.core.client.domain.common.ClientPageTableRet;
import com.szd.core.client.service.ClientBaseController;
import com.szd.core.client.util.ClientUtilString;
import com.szd.z000.orm.domain.z2.MdmCmpy;
import com.szd.z000.web.z2.service.MdmCmpyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 公司代码接口层
 *
 * @author szd
 * @date 2020-06-30
 */
@RestController
@RequestMapping("/z000/mdm/cmpy")
public class MdmCmpyController extends ClientBaseController {
    @Resource
    private MdmCmpyService mdmCmpyService;
    @Resource
    private ClientCore clientCore;

    @PostMapping("/list")
    public ClientPageTableRet list(@RequestBody MdmCmpy mdmCmpy) {
        startPage();
        List<MdmCmpy> list = mdmCmpyService.selectCompanyList(mdmCmpy);
        list.forEach(com -> getDesc(com,"0"));
        return getPageTable(list);
    }

    @GetMapping(value = "/{cmpy}")
    public ClientAjaxResult getInfo(@PathVariable("cmpy") String cmpy) {
        MdmCmpy company = mdmCmpyService.selectCompanyByCode(cmpy);
        getDesc(company,"1");
        return ClientAjaxResult.success(company);
    }

    @PostMapping
    public ClientAjaxResult add(@RequestBody MdmCmpy mdmCmpy) {
        if (mdmCmpy == null
                || ClientUtilString.isBlank((mdmCmpy.getCmpy()))
                || ClientUtilString.isBlank((mdmCmpy.getCmpyName()))
                || ClientUtilString.isBlank((mdmCmpy.getAccSet()))) {
            return ClientAjaxResult.error("新增公司失败，编码、名称或账套不能为空");
        }
        MdmCmpy exists = mdmCmpyService.selectCompanyByCode(mdmCmpy.getCmpy());
        if (exists != null) {
            return ClientAjaxResult.error("新增公司失败，编码'" + mdmCmpy.getCmpy() + "'已存在");
        }
        clientCore.mdm.checkAE("CORE_VCH_ACCS", mdmCmpy.getAccSet(), "账套", "0");
        return ClientAjaxResult.success(mdmCmpyService.insertCompany(mdmCmpy));
    }

    @PutMapping
    public ClientAjaxResult edit(@RequestBody MdmCmpy mdmCmpy) {
        MdmCmpy exists = mdmCmpyService.selectCompanyByCode(mdmCmpy.getCmpy());
        if (exists == null) {
            return ClientAjaxResult.error("数据不存在");
        }
        clientCore.mdm.checkAE("CORE_VCH_ACCS", mdmCmpy.getAccSet(), "账套", "0");
        return ClientAjaxResult.success(mdmCmpyService.updateCompany(mdmCmpy));
    }

    @DeleteMapping
    public ClientAjaxResult remove(@RequestBody String[] cmpys) {
        return ClientAjaxResult.success(mdmCmpyService.deleteCompanyByCodes(cmpys));
    }

    private void getDesc(MdmCmpy mdmCmpy,String flag) {
        mdmCmpy.setAccSetName(clientCore.mdm.getDescA("CORE_VCH_ACCS", mdmCmpy.getAccSet(), flag));
    }
}
