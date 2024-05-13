/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.dao;

import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.modules.sys.entity.User;

import org.springframework.stereotype.Repository;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.CuCustomer;

/**
 *
 * @ClassName CuCustomerDao
 * @Description: 客户关系管理-客户表DAO接口
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:36:49
 */
@Repository
public interface CuCustomerDao extends CrudDao<CuCustomer> {
	/**
	* @Title: getByName
	* @Description:根据Name查询客户名称是否存在
	* @param name 客户名称
	* @return 客户信息管理-客户管理
	* @return CuCustomer 返回类型
	* @author jm
	* @date 2018年5月7日 下午 3:45:26
	*/
	CuCustomer getByName(String name);

	/**
	 *
	  * @Title: batchDelete
	  * @Description: 批量删除客户信息
	  * @param cuCustomerList
	  * void 返回类型
	  * @throws
	 */
	void batchDelete(List<CuCustomer> cuCustomerList);

	/**
	 *
	  * @Title: batchApprovalcuCustomer
	  * @Description: 批量审批客户信息
	  * @param cuCustomerList
	  * void 返回类型
	  * @throws
	 */
	void batchApprovalcuCustomer(List<CuCustomer> cuCustomerList);

	/**
	 *
	  * @Title: findApproverList
	  * @Description: 查询客户待审批信息列表
	  * @param cuCustomer
	  * @return
	  * List<CuCustomer> 返回类型
	  * @throws
	 */
	List<CuCustomer> findApproverList(CuCustomer cuCustomer);

	/**
	 *
	  * @Title: findListApproverCount
	  * @Description: 查询客户待审批信息数量
	  * @param cuCustomer
	  * @return
	  * Integer 返回类型
	  * @throws
	 */
	Integer findListApproverCount(CuCustomer cuCustomer);

	/**
	* @Title: getviewData
	* @Description:根据ID查询客户信息
	* @param name 客户名称
	* @return 客户信息管理-客户管理
	* @return CuCustomer 返回类型
	* @author jm
	* @date 2018年5月7日 下午 3:45:26
	*/
	CuCustomer getviewData(CuCustomer cuCustomer);

	/**
	 *
	  * @Title: findProjectList
	  * @Description: 根据客户ID查询项目合作详情列表
	  * @param cuCustomer
	  * @return
	  * List<CuCustomer> 返回类型
	  * @throws
	 */
	List<CuCustomer> findProjectList(CuCustomer cuCustomer);

	/**
	 *
	  * @Title: getMajordomo
	  * @Description: 根据工号查询是否拥有教育事业部总监角色权限
	  * @param user
	  * @return
	  * int 返回类型
	  * @throws
	 */
	int getMajordomo(User user);

	/**
	 * @Description: 批量修改负责人
	 * @param cuCustomerList
	 */
	void modifyPrincipal(List<CuCustomer> cuCustomerList);


	/**
	 * @Title: getByNameAndType
	 * @Description: TODO(根据名称和类型查看客户信息)
	 * @param cuCustomer
	 * @return CuCustomer 返回类型
	 * @author 谢舒慧 010736
	 * @date 2021年12月15日
	 */
	CuCustomer getByNameAndType(CuCustomer cuCustomer);

	/**
	 * @Title: updataUnitInfo
	 * @Description: 开票申请提交后，更新客户的单位信息
	 * @param cuCustomer
	 */
	void updataUnitInfo(CuCustomer cuCustomer);

}
