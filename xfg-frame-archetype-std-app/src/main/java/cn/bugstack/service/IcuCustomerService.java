package cn.bugstack.service;

import com.xunfang.mmdp.framework.persistence.Page;
import com.xunfang.mmdp.modules.sys.entity.User;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import cn.bugstack.infrastructure.persistent.po.CuCustomer;

/**
 * Created by GuoQingchun on 2024/5/9
 */
public interface IcuCustomerService {


		/**
		 *
		 * @Title: get
		 * @Description: 根据主键ID获取客户信息
		 * @param id
		 * @return
		 * CuCustomerEntity 返回类型
		 * @throws
		 */
		public CuCustomer get(String id);

	/**
		 *
		 * @Title: getviewData
		 * @Description: 根据主键ID获取客户信息(查看)
		 * @param id
		 * @return
		 * CuCustomerEntity 返回类型
		 * @throws
		 */
		public CuCustomer getviewData(String id);

	/**
		 *
		 * @Title: findList
		 * @Description: 查询客户信息列表
		 * @param CuCustomer
		 * @return
		 * List<CuCustomerEntity> 返回类型
		 * @throws
		 */
		public List<CuCustomer> findList(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: findList2Count
		 * @Description: 查询客户信息数量
		 * @param CuCustomer
		 * @return
		 * Integer 返回类型
		 * @throws
		 */
		public Integer findList2Count(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: findApproverList
		 * @Description: 查询客户信息列表
		 * @param CuCustomer
		 * @return
		 * List<CuCustomerEntity> 返回类型
		 * @throws
		 */
		public List<CuCustomer> findApproverList(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: findListApproverCount
		 * @Description: 查询客户信息数量
		 * @param CuCustomer
		 * @return
		 * Integer 返回类型
		 * @throws
		 */
		public Integer findListApproverCount(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: findPage
		 * @Description: 查询客户信息列表(带分页)
		 * @param page
		 * @param CuCustomer
		 * @return
		 * Page<CuCustomerEntity> 返回类型
		 * @throws
		 */
		public Page<CuCustomer> findPage(Page<CuCustomer> page, CuCustomer CuCustomer);

	/**
		 *
		 * @Title: save
		 * @Description: 保存客户信息
		 * @param CuCustomer
		 * @return
		 * void 返回类型
		 * @throws
		 */
		@Transactional(readOnly = false)
		public void save(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: delete
		 * @Description: 根据主键ID删除客户信息
		 * @param CuCustomer
		 * @return
		 * void 返回类型
		 * @throws
		 */
		@Transactional(readOnly = false)
		public void delete(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: batchDelete
		 * @Description: 批量删除客户信息
		 * @param cuCustomerList
		 * void 返回类型
		 * @throws
		 */
		@Transactional(readOnly = false)
		public void batchDelete(List<CuCustomer> cuCustomerList);

	/**
		 *
		 * @Title: batchApprovalCuCustomerEntity
		 * @Description: 批量审批客户信息
		 * @param cuCustomerList
		 * void 返回类型
		 * @throws
		 */
		@Transactional(readOnly = false)
		public void batchApprovalcuCustomer(List<CuCustomer> cuCustomerList, CuCustomer CuCustomer);

	/**
		 *
		 * @Title: approvalCuCustomerEntity
		 * @Description: 单条审批客户信息
		 * @throws
		 */
		@Transactional(readOnly = false)
		public void approvalcuCustomer(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: getByName
		 * @Description: 根据客户名称查询客户信息
		 * @param name
		 * @return
		 * CuInterest 返回类型
		 * @throws
		 */
		public CuCustomer getByName(String name);

	/**
		 *
		 * @Title: findProjectList
		 * @Description: 根据客户ID查询项目合作详情列表
		 * @param CuCustomer
		 * @return
		 * List<CuCustomerEntity> 返回类型
		 * @throws
		 */
		public List<CuCustomer> findProjectList(CuCustomer CuCustomer);

	/**
		 *
		 * @Title: getUserByNOAndOfficeId
		 * @Description: 根据用户ID和部门ID查询该用户是否存在
		 * @param user
		 * @return
		 * int 返回类型
		 * @throws
		 */
		public int getUserByNOAndOfficeId(User user);

	/**
		 *
		 * @Title: getUserMasterByNo
		 * @Description: 根据工号查询是否为部门负责人
		 * @param user
		 * @return
		 * int 返回类型
		 * @throws
		 */
		public int getUserMasterByNo(User user);

	/**
		 *
		 * @Title: getMajordomo
		 * @Description: 根据工号查询是否拥有教育事业部总监角色权限
		 * @param user
		 * @return
		 * int 返回类型
		 * @throws
		 */
		public int getMajordomo(User user);

	/**
		 *
		 * @Title: getUserById
		 * @Description: 根据用户Id获取用户信息
		 * @return
		 * user 返回类型
		 * @throws
		 */
		public User getUserByLoginName(String id);

	/**
		 * @Title: getNextApprover
		 * @Description: 得到下一步审批人
		 * @return 参数值
		 * @return User 返回类型
		 * @author 张晓晓 4640
		 * @date 2018年5月7日 下午3:30:17
		 */
		@Transactional(readOnly = false)
		public User getNextApprover(CuCustomer CuCustomer);

	/**
		 * @Description: 批量修改负责人
		 * @param cuCustomerList
		 * @param CuCustomer
		 */
		@Transactional(readOnly = false)
		public void modifyPrincipal(List<CuCustomer> cuCustomerList, CuCustomer CuCustomer);

}
