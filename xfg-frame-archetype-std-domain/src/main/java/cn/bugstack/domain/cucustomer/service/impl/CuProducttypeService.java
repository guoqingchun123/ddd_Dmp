/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.domain.cucustomer.service.impl;

import com.xunfang.mmdp.framework.persistence.Page;
import com.xunfang.mmdp.framework.service.CrudService;
import com.xunfang.mmdp.framework.utils.IdGen;
import com.xunfang.mmdp.modules.sys.entity.User;
import com.xunfang.mmdp.modules.sys.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import cn.bugstack.infrastructure.persistent.dao.CuProducttypeDao;
import cn.bugstack.infrastructure.persistent.po.CuProducttype;
import cn.bugstack.service.IcuProducttypeService;

/**
 *
 * @ClassName CuProducttypeService
 * @Description: 产品类型表Service
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月8日 上午9:10:52
 */
@Service
@Transactional(readOnly = true)
public class CuProducttypeService extends CrudService<CuProducttypeDao, CuProducttype>implements IcuProducttypeService {

	//装配产品类型DAO接口
	@Autowired
	private CuProducttypeDao cuProducttypeDao;

	/**
	 *
	  * @Title: get
	  * @Description: 根据ID获取产品类型信息
	  * @param id
	  * @return
	  * CuProducttype 返回类型
	  * @throws
	 */
	public CuProducttype get(String id) {
		return super.get(id);
	}

	/**
	 *
	  * @Title: getByName
	  * @Description: 根据产品类型名称查询产品类型信息
	  * @param name
	  * @return
	  * CuProducttype 返回类型
	  * @throws
	 */
	public CuProducttype getByName(String name) {
		return cuProducttypeDao.getByName(name);
	}

	/**
	 *
	  * @Title: list
	  * @Description: 获取产品类型信息列表
	  * @param cuProducttype
	  * @return
	  * Page<CuProducttype> 返回类型
	  * @throws
	 */
	public List<CuProducttype> findList(CuProducttype cuProducttype) {
		return super.findList(cuProducttype);
	}

	/**
	 *
	  * @Title: findList2Count
	  * @Description: 获取产品类型数量
	  * @param cuProducttype
	  * @return
	  * Integer 返回类型
	  * @throws
	 */
	public Integer findList2Count(CuProducttype cuProducttype){
	   return super.findList2Count(cuProducttype);
	}

	/**
	 *
	  * @Title: list
	  * @Description: 获取产品类型信息列表(带分页)
	  * @param page
	  * @param cuProducttype
	  * @return
	  * Page<CuProducttype> 返回类型
	  * @throws
	 */
	public Page<CuProducttype> findPage(Page<CuProducttype> page, CuProducttype cuProducttype) {
		return super.findPage(page, cuProducttype);
	}

	/**
	 *
	  * @Title: save
	  * @Description: 保存产品类型信息
	  * @param cuProducttype
	  * @return
	  * String 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void save(CuProducttype cuProducttype) {
		//创建人，修改人
		User user = UserUtils.getUser();
		if (cuProducttype.getIsNewRecord()){//新增
			//不限制ID为UUID，调用setIsNewRecord()使用自定义ID
			cuProducttype.setId(IdGen.uuid());
			//删除标记 0：未删除  1：已删除
			cuProducttype.setDelFlag("0");
			if (StringUtils.isNotBlank(user.getId())){
				cuProducttype.setUpdateBy(user);
				cuProducttype.setCreateBy(user);
			}
			//修改时间
			cuProducttype.setUpdateDate(new Date());
			//创建时间
			cuProducttype.setCreateDate(new Date());
			//产品类型信息插入
			cuProducttypeDao.insert(cuProducttype);
		}else{//修改
			//修改人
			if (StringUtils.isNotBlank(user.getId())){
				cuProducttype.setUpdateBy(user);
			}
			//修改时间
			cuProducttype.setUpdateDate(new Date());
			//产品类型信息更新
			cuProducttypeDao.update(cuProducttype);
		}
	}

	/**
	 *
	  * @Title: delete
	  * @Description: 删除产品类型信息
	  * @param cuProducttype
	  * @return
	  * String 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void delete(CuProducttype cuProducttype) {
		super.delete(cuProducttype);
	}

	/**
	 *
	  * @Title: batchDelete
	  * @Description: 批量删除产品类型信息
	  * @param cuProducttypeList
	  * void 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void batchDelete(List<CuProducttype> cuProducttypeList){
		cuProducttypeDao.batchDelete(cuProducttypeList);
	}

}
