package cn.bugstack.infrastructure.persistent.dao;

import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.framework.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import cn.bugstack.infrastructure.persistent.po.SysDataScope;
import cn.bugstack.infrastructure.persistent.po.SysUserRight;

/**
 * 用户细化权限DAO接口
 * @author caoqishun
 * @version 2018-05-14
 */
@MyBatisDao
public interface SysUserRightDao extends CrudDao<SysUserRight> {
	//根据用户ID查询用户系统权限
	public List<SysUserRight> getRightListByUserId(Map<String,String> map);
	public List<SysUserRight> getRightListByUserIdV2(Map<String,String> map);


	public List<SysDataScope> getUserDataScopeByUserId(Map<String,String> map);
	/***
	 * 批量新增用户权限
	 * @Author caoqishun
	 * @Date 2018年5月22日下午4:00:50
	 * @param userRightList
	 * @return
	 */
	public Integer insertBatch(List<SysUserRight> userRightList);


	/***
	 * 批量删除用户权限
	 * @Author caoqishun
	 * @Date 2018年5月22日下午4:00:55
	 * @param userRightList
	 * @return
	 */
	public Integer deleteUserRight(SysUserRight userRight);

	/**
	 * @description: 减掉的部门
	 * @author  009164 wdn
	 * @date: 2022/3/30 10:37
	 */
	List<SysDataScope> getReduceDataScopeByUserId(Map<String, String> queryPara);
}
