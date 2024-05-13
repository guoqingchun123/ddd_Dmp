package cn.bugstack.domain.cucustomer.service.impl;


import com.xunfang.mmdp.framework.persistence.Page;
import com.xunfang.mmdp.framework.service.CrudService;
import com.xunfang.mmdp.framework.utils.DateUtils;
import com.xunfang.mmdp.framework.utils.StringUtils;
import com.xunfang.mmdp.modules.sys.utils.UserUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cn.bugstack.infrastructure.persistent.dao.SysParamDao;
import cn.bugstack.infrastructure.persistent.po.SysParam;
import cn.bugstack.service.IsysParamService;

/**
 * 系统参数Service
 *
 * @author lzk
 * @version 2017-12-14
 */
@Service
@Transactional(readOnly = true)
public class SysParamService extends CrudService<SysParamDao, SysParam> implements IsysParamService {
	Log LOG = LogFactory.getLog(SysParamService.class);
	@Autowired
	SysParamDao sysParamDao;

	public SysParam get(String id) {
		return super.get(id);
	}

	/*public List<SysParam> findList(SysParam sysParam) {
		return super.findList(sysParam);
	}*/

	public Page<SysParam> findPage(Page<SysParam> page, SysParam sysParam) {
		return super.findPage(page, sysParam);
	}

	@Transactional(readOnly = false)
	public void save(SysParam sysParam) {
		super.save(sysParam);
	}

	@Transactional(readOnly = false)
	public void delete(SysParam sysParam) {
		super.delete(sysParam);
	}

	/**
	 * @Title: qrySysParamByParamId
	 * @Description: 根据系统编码和status=1查询系统编码信息
	 * @param
	 * @return
	 * @author 刘振凯 4512
	 * @date 2017年12月14日 下午2:52:05
	 */
	public SysParam qrySysParamByParamId(SysParam sysParam) {
		LOG.debug("SysParamService:qrySysParamByParamId is start...");
		SysParam paramResult = new SysParam();
		try {
			if (sysParam != null) {
				paramResult = sysParamDao.qrySysParamByParamId(sysParam);
				LOG.debug("SysParamService:qrySysParamByParamId is end...");
				return paramResult;
			}
			LOG.debug("SysParamService:qrySysParamByParamId is fail...");
			return paramResult;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("SysParamService:qrySysParamByParamId is error:" + e.getMessage());
			return paramResult;
		}
	}
	/**
	 * 功能描述：根据paramid查询系统参数对象
	 * @description: 根据paramid查询系统参数对象
	 * @author: leiqian004081
	 * @param:
	 * @updateTime: 2022/3/16 10:05
	 * @return:
	 * @throws:
	 */
	public SysParam qrySysParamByPmId(String paramid) {
		LOG.debug("SysParamService:qrySysParamByPmId is start...");
		SysParam paramResult = new SysParam();
		try {
			if (StringUtils.isNotBlank(paramid)) {
				paramResult = sysParamDao.qrySysParamByPmId(paramid);
				LOG.debug("SysParamService:qrySysParamByPmId is end...");
				return paramResult;
			}
			LOG.debug("SysParamService:qrySysParamByPmId is fail...");
			return paramResult;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("SysParamService:qrySysParamByPmId is error:" + e.getMessage());
			return paramResult;
		}
	}
	/**
	 * @Title: updateSysParamByPid
	 * @Description: 根据paramid更新paramvalue信息
	 * @param
	 * @return
	 * @author jjd
	 * @date 2020年5月22日
	 */
	@Transactional(readOnly = false)
	public void updateSysParamByPid(SysParam sysParam) {
		sysParamDao.updateSysParamByPid(sysParam);
	}
	/**
	 * @Title: findList
	 * @Description: TODO(描述)
	 * @param dict
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月21日
	 */
	public List<SysParam> findList(SysParam sysParam) {
		//查询字典列表数据
		List<SysParam> result = sysParamDao.findList(sysParam);
		return result;
	}
	/**
	 *
	 * @Title: getCount
	 * @Description: 获取总页数
	 * @param sysParam
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月20日
	 */
	public int getCount(SysParam sysParam) {
	 		//获取描述
			/*String description = dict.getDescription();
			if(description !=null && !"".equals(description)){
				//处理描述模糊查询%和_查询出不来的问题
				String myString =SpecialCharacherUtils.toMyString(description);
				dict.setDescription(myString);
			}*/
	 		//查询字典的条数
	 		int result = sysParamDao.getCount(sysParam);
			return result;
	}

	/**
	 * @Title: insert
	 * @Description: TODO(描述)
	 * @param sysParam
	 * @return 保存参数信息
	 * @author 魏代娜   009164
	 * @date 2020年10月20日
	 */
	@Transactional(readOnly = false)
	public int insertSysParam(SysParam sysParam) {
		String curDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		//设置主键id
		int id = sysParamDao.selectMaxId()+1;
		sysParam.setId(id+"");
		// 设置创建人信息
		sysParam.setCreateBy(UserUtils.getUser());
		// 设置添加时间
		sysParam.setCreateDate(DateUtils.parseDate(curDate));
		// 参数状态：0 失效，1 生效
		sysParam.setStatus("1");
		//查询字典的条数
 		int result = sysParamDao.insertSysParam(sysParam);
		return result;
	}
	/**
	 *
	 * @Title: updateSysParam
	 * @Description: TODO(修改参数信息)
	 * @param sysParam
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月21日
	 */
	@Transactional(readOnly = false)
	public int updateSysParam(SysParam sysParam) {
		String curDate = DateUtils.getDate("yyyy-MM-dd HH:mm:ss");
		// 设置修改人信息
		sysParam.setUpdateBy(UserUtils.getUser());
		// 设置修改人时间
		sysParam.setUpdateDate(DateUtils.parseDate(curDate));
		//修改是否成功
 		int result = sysParamDao.updateSysParam(sysParam);
		return result;
	}

	public SysParam getSysParamId(SysParam sysParam) {
		//根据ID查询字典管理信息
		SysParam result = sysParamDao.getSysParamById(sysParam);
		return result;
	}
	/**
	 * @Title: deleteSysParamById
	 * @Description: TODO(删除参数 == 修改状态)
	 * @param sysParam
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月21日
	 */
	@Transactional(readOnly = false)
	public int deleteSysParamById(SysParam sysParam) {
		//参数状态：0 失效，1 生效
		sysParam.setStatus("0");
		//删除是否成功
 		int result = sysParamDao.changeStatusSysParamById(sysParam);
		return result;
	}
	@Transactional(readOnly = false)
	public int addParamById(SysParam sysParam) {
		//参数状态：0 失效，1 生效
		sysParam.setStatus("1");
		//删除是否成功
		int result = sysParamDao.changeStatusSysParamById(sysParam);
		return result;
	}
    //验证paramId是否重复
	public int checkParamId(SysParam sysParam) {
		int result = sysParamDao.checkParamId(sysParam);
		return result;
	}

}
