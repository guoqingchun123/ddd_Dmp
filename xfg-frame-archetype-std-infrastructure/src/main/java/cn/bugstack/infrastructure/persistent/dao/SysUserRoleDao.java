package cn.bugstack.infrastructure.persistent.dao;

import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.framework.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;

import cn.bugstack.infrastructure.persistent.po.SysUserRole;

/**
 * 用户角色DAO接口
 * @author caoqishun
 * @version 2018-05-22
 */
@MyBatisDao
public interface SysUserRoleDao extends CrudDao<SysUserRole> {
	/**
	 * 查询用户角色列表
	 * @Author caoqishun
	 * @Date 2018年5月23日上午10:07:16
	 * @param queryPara
	 * @return
	 */
	public List<SysUserRole>  getUserRoleList(Map<String,String> queryPara);
	public List<SysUserRole>  getUserRoleListV2(Map<String,String> queryPara);

	/**
	 * 批量新增用户角色列表
	 * @Author caoqishun
	 * @Date 2018年5月23日上午10:07:16
	 * @param queryPara
	 * @return
	 */
	public Integer insertBatch(List<SysUserRole> userRoleList);

	/**
	 * 删除用户角色
	 * @Author caoqishun
	 * @Date 2018年5月23日上午10:07:16
	 * @param queryPara
	 * @return
	 */
	public Integer deleteUserRole(SysUserRole sysUserRole);

	/**
	 * 查询用户角色列表  getUserRoleListByRoleId
	 * @Author 李乡瑞
	 * @Date 2018年5月23日上午10:07:16
	 * @param queryPara
	 * @return
	 */
	public List<SysUserRole>  getUserRoleListByRoleId(SysUserRole sysUserRole);

	//删除CRM相关角色 并删除
	void deleteCRMRole(String userid);
}
