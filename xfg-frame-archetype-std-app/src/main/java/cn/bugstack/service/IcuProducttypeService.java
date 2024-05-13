package cn.bugstack.service;

import com.xunfang.mmdp.framework.persistence.Page;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.CuProducttype;

/**
 * Created by GuoQingchun on 2024/5/9
 */
public interface IcuProducttypeService {

	/**
	 *
	 * @Title: get
	 * @Description: 根据ID获取产品类型信息
	 * @param id
	 * @return
	 * CuProducttype 返回类型
	 * @throws
	 */
	public CuProducttype get(String id);

	/**
	 *
	 * @Title: getByName
	 * @Description: 根据产品类型名称查询产品类型信息
	 * @param name
	 * @return
	 * CuProducttype 返回类型
	 * @throws
	 */
	public CuProducttype getByName(String name);

	/**
	 *
	 * @Title: list
	 * @Description: 获取产品类型信息列表
	 * @param cuProducttype
	 * @return
	 * Page<CuProducttype> 返回类型
	 * @throws
	 */
	public List<CuProducttype> findList(CuProducttype cuProducttype);

	/**
	 *
	 * @Title: findList2Count
	 * @Description: 获取产品类型数量
	 * @param cuProducttype
	 * @return
	 * Integer 返回类型
	 * @throws
	 */
	public Integer findList2Count(CuProducttype cuProducttype);

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
	public Page<CuProducttype> findPage(Page<CuProducttype> page, CuProducttype cuProducttype);

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
	public void save(CuProducttype cuProducttype);

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
	public void delete(CuProducttype cuProducttype);

	/**
	 *
	 * @Title: batchDelete
	 * @Description: 批量删除产品类型信息
	 * @param cuProducttypeList
	 * void 返回类型
	 * @throws
	 */
	@Transactional(readOnly = false)
	public void batchDelete(List<CuProducttype> cuProducttypeList);

}
