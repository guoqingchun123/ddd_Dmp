package cn.bugstack.infrastructure.persistent.dao;


import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.framework.persistence.annotation.MyBatisDao;
import com.xunfang.mmdp.modules.sys.entity.User;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.SysParam;

/**
 * 系统参数DAO接口
 * @author lzk
 * @version 2017-12-14
 */
@MyBatisDao
public interface SysParamDao extends CrudDao<SysParam> {
	/**
	* @Title: qrySysParamByParamId
	* @Description: 根据系统编码和生效状态查询系统编码信息
	* @param
	* @return
	* @author 刘振凯 4512
	* @date 2017年12月14日 下午2:48:43
	*/
	SysParam qrySysParamByParamId(SysParam sysParam);

	/**
	 * 功能描述：根据paramid查询系统参数对象
	 * @description: 根据paramid查询系统参数对象
	 * @author: leiqian004081
	 * @param:
	 * @updateTime: 2022/3/16 10:06
	 * @return:
	 * @throws:
	 */
	SysParam qrySysParamByPmId(String paramid);

	/**
	* @Title:人员树用以批量回显
	* @author tianshuo 4202
	* @date 2018年5月30日 下午2:48:43
	*/
	List<User> queryUserInfoForUserTreeViewBack(User user);
	/**
	 * @description:回显人员用（单人情况）
	 * @author  009164 wdn
	 * @date: 2022/3/29 15:03
	 */
	List<User> queryUserInfoForUserTreeViewBackOne(User user);
	//人员树（工号存在字母的回显）
	List<User> queryUserInfoForUserTreeViewBackByNos(User user);

	/**
	* @Title:根据paramid更新paramvalue信息
	* @author jjd
	* @date 2020年5月22日
	*/
	void updateSysParamByPid(SysParam sysParam);

	//系统参数的条数
	int getCount(SysParam sysParam);
	//保存参数
	int insertSysParam(SysParam sysParam);
    //查询当前系统中最大id
	int selectMaxId();
	//修改参数信息
	int updateSysParam(SysParam sysParam);
    //根据id获取参数信息
	SysParam getSysParamById(SysParam sysParam);
	//删除参数信息
	int changeStatusSysParamById(SysParam sysParam);
    //验证paramId是否重复
	int checkParamId(SysParam sysParam);


}
