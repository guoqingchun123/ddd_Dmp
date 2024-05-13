/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.dao;

import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.framework.persistence.annotation.MyBatisDao;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.CuCustomerInterest;

/**
 *
 * @ClassName CuCustomerInterestDao
 * @Description: 客户兴趣关联表DAO接口
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:44:40
 */
@MyBatisDao
public interface CuCustomerInterestDao extends CrudDao<CuCustomerInterest> {
	/**
	 *
	  * @Title: batchInsert
	  * @Description: 批量插入客户兴趣信息
	  * @param cuCustomerInterestList
	  * void 返回类型
	  * @throws
	 */
	void batchInsert(List<CuCustomerInterest> cuCustomerInterestList);

	/**
	 *
	  * @Title: deleteByCustomerId
	  * @Description: 根据客户ID删除客户兴趣信息
	  * @param cuCustomerInterest
	  * @return
	  * void 返回类型
	  * @throws
	 */
	void deleteByCustomerId(CuCustomerInterest cuCustomerInterest);

	/**
	 *
	  * @Title: batchUpdate
	  * @Description: 批量修改客户兴趣信息
	  * @param cuCustomerInterestList
	  * void 返回类型
	  * @throws
	 */
	void batchUpdate(List<CuCustomerInterest> cuCustomerInterestList);

	/**
	 *
	  * @Title: getByCustomerId
	  * @Description: 根据客户ID查询客户兴趣信息
	  * @param cuCustomerInterest
	  * @return
	  * List<CuCustomerLinkman> 返回类型
	  * @throws
	 */
	List<CuCustomerInterest> getByCustomerId(CuCustomerInterest cuCustomerInterest);
}
