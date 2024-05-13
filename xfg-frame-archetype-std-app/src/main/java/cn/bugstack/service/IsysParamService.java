package cn.bugstack.service;


import com.xunfang.mmdp.framework.persistence.Page;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.SysParam;

/**
 * Created by GuoQingchun on 2024/5/9
 */
public interface IsysParamService {


	public SysParam get(String id);

	/*public List<SysParam> findList(SysParam sysParam) {
		return super.findList(sysParam);
	}*/

	public Page<SysParam> findPage(Page<SysParam> page, SysParam sysParam);

	@Transactional(readOnly = false)
	public void save(SysParam sysParam);

	@Transactional(readOnly = false)
	public void delete(SysParam sysParam);

	/**
	 * @Title: qrySysParamByParamId
	 * @Description: 根据系统编码和status=1查询系统编码信息
	 * @param
	 * @return
	 * @author 刘振凯 4512
	 * @date 2017年12月14日 下午2:52:05
	 */
	public SysParam qrySysParamByParamId(SysParam sysParam);

	/**
	 * 功能描述：根据paramid查询系统参数对象
	 * @description: 根据paramid查询系统参数对象
	 * @author: leiqian004081
	 * @param:
	 * @updateTime: 2022/3/16 10:05
	 * @return:
	 * @throws:
	 */
	public SysParam qrySysParamByPmId(String paramid);

	/**
	 * @Title: updateSysParamByPid
	 * @Description: 根据paramid更新paramvalue信息
	 * @param
	 * @return
	 * @author jjd
	 * @date 2020年5月22日
	 */
	@Transactional(readOnly = false)
	public void updateSysParamByPid(SysParam sysParam);

	/**
	 * @Title: findList
	 * @Description: TODO(描述)
	 * @param dict
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月21日
	 */
	public List<SysParam> findList(SysParam sysParam);

	/**
	 *
	 * @Title: getCount
	 * @Description: 获取总页数
	 * @param sysParam
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月20日
	 */
	public int getCount(SysParam sysParam);

	/**
	 * @Title: insert
	 * @Description: TODO(描述)
	 * @param sysParam
	 * @return 保存参数信息
	 * @author 魏代娜   009164
	 * @date 2020年10月20日
	 */
	@Transactional(readOnly = false)
	public int insertSysParam(SysParam sysParam);

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
	public int updateSysParam(SysParam sysParam);

	public SysParam getSysParamId(SysParam sysParam);

	/**
	 * @Title: deleteSysParamById
	 * @Description: TODO(删除参数 == 修改状态)
	 * @param sysParam
	 * @return
	 * @author 魏代娜   009164
	 * @date 2020年10月21日
	 */
	@Transactional(readOnly = false)
	public int deleteSysParamById(SysParam sysParam);

	@Transactional(readOnly = false)
	public int addParamById(SysParam sysParam);

	//验证paramId是否重复
	public int checkParamId(SysParam sysParam);

}
