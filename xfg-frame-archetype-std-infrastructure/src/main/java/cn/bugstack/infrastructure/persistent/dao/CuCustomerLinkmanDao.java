/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.dao;

import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.framework.persistence.annotation.MyBatisDao;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.CuCustomerLinkman;

/**
 *
 * @ClassName CuCustomerLinkmanDao
 * @Description: 客户联系人表DAO接口
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:48:47
 */
@MyBatisDao
public interface CuCustomerLinkmanDao extends CrudDao<CuCustomerLinkman> {
	/**
	 *
	  * @Title: batchInsert
	  * @Description: 批量插入客户联系人信息
	  * @param cuCustomerLinkmanList
	  * void 返回类型
	  * @throws
	 */
	void batchInsert(List<CuCustomerLinkman> cuCustomerLinkmanList);

	/**
	 *
	  * @Title: deleteByCustomerId
	  * @Description: 根据客户ID删除客户联系人信息
	  * @param cuCustomerLinkman
	  * @return
	  * void 返回类型
	  * @throws
	 */
	void deleteByCustomerId(CuCustomerLinkman cuCustomerLinkman);

	/**
	 *
	  * @Title: batchUpdate
	  * @Description: 批量修改客户联系人信息
	  * @param cuCustomerLinkmanList
	  * void 返回类型
	  * @throws
	 */
	void batchUpdate(List<CuCustomerLinkman> cuCustomerLinkmanList);

	/**
	 *
	  * @Title: getByCustomerId
	  * @Description: 根据客户ID查询客户联系人信息
	  * @param cuCustomerLinkman
	  * @return
	  * List<CuCustomerLinkmanEntity> 返回类型
	  * @throws
	 */
	List<CuCustomerLinkman> getByCustomerId(CuCustomerLinkman cuCustomerLinkman);
}
