/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.dao;

import com.xunfang.mmdp.framework.persistence.CrudDao;
import com.xunfang.mmdp.framework.persistence.annotation.MyBatisDao;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.CuProducttype;

/**
 *
 * @ClassName CuProducttypeDao
 * @Description: 产品类型表DAO接口
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月7日 下午6:07:56
 */
@MyBatisDao
public interface CuProducttypeDao extends CrudDao<CuProducttype> {
	/**
	* @Title: getByName
	* @Description:根据Name查询产品类型名称是否存在
	* @param name 产品类型名称
	* @return 客户信息管理-产品类型管理
	* @return CuProducttype 返回类型
	* @author jm
	* @date 2018年5月7日 下午 3:45:26
	*/
	CuProducttype getByName(String name);

	/**
	 *
	  * @Title: batchDelete
	  * @Description: 批量删除兴趣信息
	  * @param cuProducttypeList
	  * void 返回类型
	  * @throws
	 */
	void batchDelete(List<CuProducttype> cuProducttypeList);

}
