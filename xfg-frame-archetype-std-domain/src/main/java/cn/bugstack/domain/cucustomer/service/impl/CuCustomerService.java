/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.domain.cucustomer.service.impl;


import com.xunfang.mmdp.framework.persistence.Page;
import com.xunfang.mmdp.framework.service.CrudService;
import com.xunfang.mmdp.framework.utils.IdGen;
import com.xunfang.mmdp.modules.sys.dao.OfficeDao;
import com.xunfang.mmdp.modules.sys.dao.UserDao;
import com.xunfang.mmdp.modules.sys.entity.Office;
import com.xunfang.mmdp.modules.sys.entity.User;
import com.xunfang.mmdp.modules.sys.utils.UserUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bugstack.infrastructure.persistent.dao.CuCustomerDao;
import cn.bugstack.infrastructure.persistent.dao.CuCustomerInterestDao;
import cn.bugstack.infrastructure.persistent.dao.CuCustomerLinkmanDao;
import cn.bugstack.infrastructure.persistent.po.CuCustomer;
import cn.bugstack.infrastructure.persistent.po.CuCustomerInterest;
import cn.bugstack.infrastructure.persistent.po.CuCustomerLinkman;
import cn.bugstack.infrastructure.persistent.po.CuProducttype;
import cn.bugstack.infrastructure.persistent.po.SysParam;
import cn.bugstack.service.IcuCustomerService;
import cn.bugstack.service.IcuProducttypeService;
import cn.bugstack.service.IsysParamService;

/**
 *
 * @ClassName CuCustomerService
 * @Description: 客户关系管理-客户表Service
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:41:52
 */
@Service
@Transactional(readOnly = true)
public class CuCustomerService extends CrudService<CuCustomerDao, CuCustomer> implements IcuCustomerService {

	//装配客户DAO接口
	@Autowired
	private CuCustomerDao cuCustomerDao;

	//装配客户联系人DAO接口
	@Autowired
	private CuCustomerLinkmanDao cuCustomerLinkmanDao;

	//装配客户兴趣DAO接口
	@Autowired
	private CuCustomerInterestDao cuCustomerInterestDao;

	@Autowired
	private IcuProducttypeService cuProducttypeService;

	@Autowired
	private IsysParamService sysParamService;

	//装配用户管理DAO接口
	@Autowired
	private UserDao userDao;

	@Autowired
	private OfficeDao officeDao;



	/**
	 *
	  * @Title: get
	  * @Description: 根据主键ID获取客户信息
	  * @param id
	  * @return
	  * CuCustomer 返回类型
	  * @throws
	 */
	public CuCustomer get(String id) {
		CuCustomer cuCustomer = new CuCustomer();
		//查询客户信息
		cuCustomer = super.get(id);
		//查询客户联系人信息
		CuCustomerLinkman cuCustomerLinkman = new CuCustomerLinkman();
		cuCustomerLinkman.setCuCustomerId(id);
		List<CuCustomerLinkman> cuCustomerLinkmanList = cuCustomerLinkmanDao.getByCustomerId(cuCustomerLinkman);
	    if(cuCustomerLinkmanList !=null&& cuCustomerLinkmanList.size()>0){
			cuCustomer.setCuCustomerLinkmanList(cuCustomerLinkmanList);
		}

	    //查询客户兴趣信息
    	CuCustomerInterest cuCustomerInterest = new CuCustomerInterest();
    	cuCustomerInterest.setCuCustomerId(id);
    	List<CuCustomerInterest> cuCustomerInterestList = cuCustomerInterestDao.getByCustomerId(cuCustomerInterest);
    	if(cuCustomerInterestList !=null&& cuCustomerInterestList.size()>0){
    		cuCustomer.setCuCustomerInterestList(cuCustomerInterestList);
    	}

		return cuCustomer;
	}

	/**
	 *
	  * @Title: getviewData
	  * @Description: 根据主键ID获取客户信息(查看)
	  * @param id
	  * @return
	  * CuCustomer 返回类型
	  * @throws
	 */
	public CuCustomer getviewData(String id) {
		CuCustomer cuCustomer = new CuCustomer();
		cuCustomer.setId(id);
		//查询客户信息
		cuCustomer = cuCustomerDao.getviewData(cuCustomer);
		String producttype = "";
		if(cuCustomer !=null){
			if(cuCustomer.getCuProducttypeId()!=null&&!"".equals(cuCustomer.getCuProducttypeId())){
				if(cuCustomer.getCuProducttypeId().contains(",")){
					String[] str = cuCustomer.getCuProducttypeId().split(",");
					for(int i = 0;i<str.length;i++){
						CuProducttype cuProducttype = cuProducttypeService.get(str[i]);
						producttype = producttype+cuProducttype.getName()+",";
					}
				}else{
					if(cuCustomer.getCuProducttypeId()!=null&&!"".equals(cuCustomer.getCuProducttypeId())){
						CuProducttype cuProducttype = cuProducttypeService.get(cuCustomer.getCuProducttypeId());
						producttype = producttype+cuProducttype.getName();
					}
				}
			}
		}
		cuCustomer.setCuProducttypeName(producttype);
		//查询客户联系人信息
		CuCustomerLinkman cuCustomerLinkman = new CuCustomerLinkman();
		cuCustomerLinkman.setCuCustomerId(id);
		List<CuCustomerLinkman> cuCustomerLinkmanList = cuCustomerLinkmanDao.getByCustomerId(cuCustomerLinkman);
	    if(cuCustomerLinkmanList !=null&& cuCustomerLinkmanList.size()>0){
			cuCustomer.setCuCustomerLinkmanList(cuCustomerLinkmanList);
		}
	    //查询客户兴趣信息
    	CuCustomerInterest cuCustomerInterest = new CuCustomerInterest();
    	cuCustomerInterest.setCuCustomerId(id);
    	List<CuCustomerInterest> cuCustomerInterestList = cuCustomerInterestDao.getByCustomerId(cuCustomerInterest);
    	if(cuCustomerInterestList !=null&& cuCustomerInterestList.size()>0){
    		cuCustomer.setCuCustomerInterestList(cuCustomerInterestList);
    	}
		return cuCustomer;
	}

	/**
	 *
	  * @Title: findList
	  * @Description: 查询客户信息列表
	  * @param cuCustomer
	  * @return
	  * List<CuCustomer> 返回类型
	  * @throws
	 */
	public List<CuCustomer> findList(CuCustomer cuCustomer) {
		return super.findList(cuCustomer);
	}

	/**
	 *
	  * @Title: findList2Count
	  * @Description: 查询客户信息数量
	  * @param cuCustomer
	  * @return
	  * Integer 返回类型
	  * @throws
	 */
	public Integer findList2Count(CuCustomer cuCustomer)
	{
	   return super.findList2Count(cuCustomer);
	}

	/**
	 *
	  * @Title: findApproverList
	  * @Description: 查询客户信息列表
	  * @param cuCustomer
	  * @return
	  * List<CuCustomer> 返回类型
	  * @throws
	 */
	public List<CuCustomer> findApproverList(CuCustomer cuCustomer) {
		return cuCustomerDao.findApproverList(cuCustomer);
	}

	/**
	 *
	  * @Title: findListApproverCount
	  * @Description: 查询客户信息数量
	  * @param cuCustomer
	  * @return
	  * Integer 返回类型
	  * @throws
	 */
	public Integer findListApproverCount(CuCustomer cuCustomer){
	   return cuCustomerDao.findListApproverCount(cuCustomer);
	}

	/**
	 *
	  * @Title: findPage
	  * @Description: 查询客户信息列表(带分页)
	  * @param page
	  * @param cuCustomer
	  * @return
	  * Page<CuCustomer> 返回类型
	  * @throws
	 */
	public Page<CuCustomer> findPage(Page<CuCustomer> page, CuCustomer cuCustomer) {
		return super.findPage(page, cuCustomer);
	}

	/**
	 *
	  * @Title: save
	  * @Description: 保存客户信息
	  * @param cuCustomer
	  * @return
	  * void 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void save(CuCustomer cuCustomer) {
		//创建人，修改人
		User user = UserUtils.getUser();
		//审批人信息
		if("".equals(cuCustomer.getApprover())|| cuCustomer.getApprover()==null){
			User approverUser = getNextApprover(cuCustomer);
			cuCustomer.setApprover(approverUser.getId());
		}
		if (cuCustomer.getIsNewRecord()){//新增
			// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
			cuCustomer.setId(IdGen.uuid());
			//创建人，修改人
			if (StringUtils.isNotBlank(user.getId())){
				cuCustomer.setUpdateBy(user);
				cuCustomer.setCreateBy(user);
			}
			if("0".equals(cuCustomer.getCustomerType())){//企业用户不需要审批
				//审批状态
				cuCustomer.setApprovalStatus("1");
			}else{//学校用户需要审批
				//审批状态
				cuCustomer.setApprovalStatus("0");
			}
			//修改时间
			cuCustomer.setUpdateDate(new Date());
			//创建时间
			cuCustomer.setCreateDate(cuCustomer.getUpdateDate());
			//客户信息插入
			cuCustomerDao.insert(cuCustomer);
		}else{//修改
			//修改人
			if (StringUtils.isNotBlank(user.getId())){
				cuCustomer.setUpdateBy(user);
			}
			//审批状态
			if(cuCustomer.getApprovalStatus()==null){
				cuCustomer.setApprovalStatus("0");
			}
			//修改时间
			cuCustomer.setUpdateDate(new Date());
			//客户信息更新
			cuCustomerDao.update(cuCustomer);
		}
		//根据客户ID删除客户兴趣信息
		CuCustomerInterest cuCustomerInterest = new CuCustomerInterest();
		cuCustomerInterest.setCuCustomerId(cuCustomer.getId());
		cuCustomerInterestDao.deleteByCustomerId(cuCustomerInterest);
		//根据客户ID删除客户联系人信息
		CuCustomerLinkman cuCustomerLinkman = new CuCustomerLinkman();
		cuCustomerLinkman.setCuCustomerId(cuCustomer.getId());
		cuCustomerLinkmanDao.deleteByCustomerId(cuCustomerLinkman);
		//页面传过来的客户联系人List
		List<CuCustomerLinkman> cuCustomerLinkmanList = cuCustomer.getCuCustomerLinkmanList();
		if(cuCustomerLinkmanList !=null&& cuCustomerLinkmanList.size()>0){
			//new一个新的list，存放处理完的客户联系人信息
			List<CuCustomerLinkman> cuCustomerLinkmanListNew = new ArrayList<CuCustomerLinkman>();
			//new一个新的map,存放处理完的客户兴趣信息
			Map<String,List<CuCustomerInterest>> cuCustomerInterestMap = new HashMap<String,List<CuCustomerInterest>>();
			//遍历客户联系人信息
			for (CuCustomerLinkman cuCustomerLinkman2 : cuCustomerLinkmanList) {
				// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
				cuCustomerLinkman2.setId(IdGen.uuid());
				//客户ID
				cuCustomerLinkman2.setCuCustomerId(cuCustomer.getId());
				//创建人，修改人
				if (StringUtils.isNotBlank(user.getId())){
					cuCustomerLinkman2.setUpdateBy(user);
					cuCustomerLinkman2.setCreateBy(user);
				}
				//修改时间
				cuCustomerLinkman2.setUpdateDate(new Date());
				//创建时间
				cuCustomerLinkman2.setCreateDate(cuCustomer.getUpdateDate());
				//将处理完的客户联系人信息加到list中
				cuCustomerLinkmanListNew.add(cuCustomerLinkman2);

				//页面传过来的客户兴趣List
				List<CuCustomerInterest> cuCustomerInterestList = cuCustomer.getCuCustomerInterestList();
				if(cuCustomerInterestList !=null&& cuCustomerInterestList.size()>0){
					//new一个新的list，存放处理完的客户兴趣信息
					List<CuCustomerInterest> cuCustomerInterestListNew = new ArrayList<CuCustomerInterest>();
					for (CuCustomerInterest cuCustomerInterest2 : cuCustomerInterestList) {
						if(cuCustomerInterest2.getLinkmanName().equals(cuCustomerLinkman2.getName())){
							cuCustomerInterestListNew.add(cuCustomerInterest2);
						}
					}
					cuCustomerInterestMap.put(cuCustomerLinkman2.getId(), cuCustomerInterestListNew);
				}
			}
			//执行客户联系人批量插入
			if(cuCustomerLinkmanListNew !=null&& cuCustomerLinkmanListNew.size()>0){
				cuCustomerLinkmanDao.batchInsert(cuCustomerLinkmanListNew);
			}

			if(cuCustomerInterestMap!=null&&cuCustomerInterestMap.size()>0){
				for (Map.Entry entry : cuCustomerInterestMap.entrySet()) {
				   String cuCustomerLinkmanId = entry.getKey().toString();
				   List<CuCustomerInterest> cuCustomerInterestList = (List) entry.getValue();
				   List<CuCustomerInterest> cuCustomerInterestListNew = new ArrayList<CuCustomerInterest>();
				   for (CuCustomerInterest cuCustomerInterest2 : cuCustomerInterestList) {
			    	    // 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
					   cuCustomerInterest2.setCuInterestId(cuCustomerInterest2.getId());
			    	   cuCustomerInterest2.setCuCustomerLinkmanId(cuCustomerLinkmanId);
			    	   cuCustomerInterest2.setId(IdGen.uuid());
			    	   cuCustomerInterestListNew.add(cuCustomerInterest2);
				   }
				   if(cuCustomerInterestListNew !=null&& cuCustomerInterestListNew.size()>0){
					   cuCustomerInterestDao.batchInsert(cuCustomerInterestListNew);
				   }
				}
			}
		}
	}

	/**
	 *
	  * @Title: delete
	  * @Description: 根据主键ID删除客户信息
	  * @param cuCustomer
	  * @return
	  * void 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void delete(CuCustomer cuCustomer) {
		super.delete(cuCustomer);
		if(cuCustomer !=null&&!"".equals(cuCustomer)){
			//删除客户联系人信息
			CuCustomerLinkman cuCustomerLinkman = new CuCustomerLinkman();
			cuCustomerLinkman.setCuCustomerId(cuCustomer.getId());
			cuCustomerLinkmanDao.deleteByCustomerId(cuCustomerLinkman);
			//删除客户兴趣信息
			CuCustomerInterest cuCustomerInterest = new CuCustomerInterest();
			cuCustomerInterest.setCuCustomerId(cuCustomer.getId());
			cuCustomerInterestDao.deleteByCustomerId(cuCustomerInterest);
		}
	}

	/**
	 *
	  * @Title: batchDelete
	  * @Description: 批量删除客户信息
	  * @param cuCustomerList
	  * void 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void batchDelete(List<CuCustomer> cuCustomerList){
		//遍历客户信息
		for (CuCustomer cuCustomer2 : cuCustomerList) {
			if(cuCustomer2 !=null&&!"".equals(cuCustomer2)){
				CuCustomerLinkman cuCustomerLinkman = new CuCustomerLinkman();
				cuCustomerLinkman.setCuCustomerId(cuCustomer2.getId());
				//删除客户联系人信息
				cuCustomerLinkmanDao.deleteByCustomerId(cuCustomerLinkman);
			}
		}
		//进行批量删除操作
		cuCustomerDao.batchDelete(cuCustomerList);
	}

	/**
	 *
	  * @Title: batchApprovalcuCustomer
	  * @Description: 批量审批客户信息
	  * @param cuCustomerList
	  * void 返回类型
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void batchApprovalcuCustomer(List<CuCustomer> cuCustomerList, CuCustomer cuCustomer){
		//当前时间
		Date nowDate = new Date();
		//判断客户信息是否为空
		if(cuCustomerList !=null&& cuCustomerList.size()>0){
			//遍历客户信息
			for (CuCustomer cuCustomersEntity : cuCustomerList) {
				//审批人,修改人
				User user = UserUtils.getUser();
				if (StringUtils.isNotBlank(user.getId())){
					//审批人
					cuCustomersEntity.setApprover(user.getId());
					//修改人
					cuCustomersEntity.setUpdateBy(user);
				}
				if(cuCustomer !=null){
					//审批状态
					cuCustomersEntity.setApprovalStatus(cuCustomer.getApprovalStatus());
					//审批意见
					cuCustomersEntity.setApprovalOpinions(cuCustomer.getApprovalOpinions());
				}
				//审批时间
				cuCustomersEntity.setApprovalTime(nowDate);
				//修改时间
				cuCustomersEntity.setUpdateDate(nowDate);
			}
			//进行批量审批操作
			cuCustomerDao.batchApprovalcuCustomer(cuCustomerList);
		}
	}

	/**
	 *
	  * @Title: approvalcuCustomer
	  * @Description: 单条审批客户信息
	  * @throws
	 */
	@Transactional(readOnly = false)
	public void approvalcuCustomer(CuCustomer cuCustomer){
		//当前时间
		Date nowDate = new Date();
		//判断客户信息是否为空
		if(cuCustomer !=null&&!"".equals(cuCustomer)){
			//审批人,修改人
			User user = UserUtils.getUser();
			if (StringUtils.isNotBlank(user.getId())){
				//审批人
				cuCustomer.setApprover(user.getId());
				//修改人
				cuCustomer.setUpdateBy(user);
			}
			//审批时间
			cuCustomer.setApprovalTime(nowDate);
			//修改时间
			cuCustomer.setUpdateDate(nowDate);
			//进行批量审批操作
			cuCustomerDao.update(cuCustomer);
		}
	}

	/**
	 *
	  * @Title: getByName
	  * @Description: 根据客户名称查询客户信息
	  * @param name
	  * @return
	  * CuInterest 返回类型
	  * @throws
	 */
	public CuCustomer getByName(String name) {
		return cuCustomerDao.getByName(name);
	}

	/**
	 *
	  * @Title: findProjectList
	  * @Description: 根据客户ID查询项目合作详情列表
	  * @param cuCustomer
	  * @return
	  * List<CuCustomer> 返回类型
	  * @throws
	 */
	public List<CuCustomer> findProjectList(CuCustomer cuCustomer){
		return cuCustomerDao.findProjectList(cuCustomer);
	}

	/**
     *
      * @Title: getUserByNOAndOfficeId
      * @Description: 根据用户ID和部门ID查询该用户是否存在
      * @param user
      * @return
      * int 返回类型
      * @throws
     */
	public int getUserByNOAndOfficeId(User user){
		return userDao.getUserByNOAndOfficeId(user);
	}

	/**
	 *
	  * @Title: getUserMasterByNo
	  * @Description: 根据工号查询是否为部门负责人
	  * @param user
	  * @return
	  * int 返回类型
	  * @throws
	 */
	public int getUserMasterByNo(User user){
		return userDao.getUserMasterByNo(user);
	}

	/**
	 *
	  * @Title: getMajordomo
	  * @Description: 根据工号查询是否拥有教育事业部总监角色权限
	  * @param user
	  * @return
	  * int 返回类型
	  * @throws
	 */
	public int getMajordomo(User user){
		return cuCustomerDao.getMajordomo(user);
	}

	/**
	 *
	  * @Title: getUserById
	  * @Description: 根据用户Id获取用户信息
	  * @return
	  * user 返回类型
	  * @throws
	 */
	public User getUserByLoginName(String id){
		return userDao.get(id);
	}

	/**
	 * @Title: getNextApprover
	 * @Description: 得到下一步审批人
	 * @return 参数值
	 * @return User 返回类型
	 * @author 张晓晓 4640
	 * @date 2018年5月7日 下午3:30:17
	 */
	@Transactional(readOnly = false)
	public User getNextApprover(CuCustomer cuCustomer){
		User u = new User();
		User user = new User();
		if(cuCustomer ==null|| cuCustomer.getPrincipal1()==null){
			user = UserUtils.getUser();
		}else{
			user = userDao.get(cuCustomer.getPrincipal1());
		}
		String officeId = user.getOffice().getId();
		SysParam sysParam = new SysParam();
		// 查询教育事业部部门id
		sysParam.setParamid("jiaoyushiyebu");
		SysParam paramResult1 = sysParamService.qrySysParamByParamId(sysParam);
		// 查询总裁工号
		sysParam.setParamid("president");
		SysParam paramResult2 = sysParamService.qrySysParamByParamId(sysParam);
		Office office = officeDao.get(officeId);
		if (user != null&&office!=null) {
			// 如果是总经理提交请假申请
			if (user.getLoginName().equals(paramResult2.getParamvalue())) {
				// 查询总经理
				sysParam.setParamid("generalManager");
				SysParam paramResult3 = sysParamService.qrySysParamByParamId(sysParam);
				u = UserUtils.getByLoginName(paramResult3.getParamvalue());

			} else if (officeId.equals(paramResult1.getParamvalue())
					|| office.getParentIds().indexOf(paramResult1.getParamvalue()) != -1) {// 如果是教育事业部
				// 如果当前申请人是部部门负责人
				if (office.getPrimaryPerson().getId().equals(user.getId())) {
					// 查询父级部门负责人
					Office parentOffice = officeDao.get(office.getParentId());
					u = UserUtils.get(parentOffice.getPrimaryPerson().getId());
				} else {
					// 如果是普通员工
					u = UserUtils.get(office.getPrimaryPerson().getId());
				}
			} else {
				if (office.getPrimaryPerson().getId().equals(user.getId())) {
					// 查询父级部门负责人
					Office parentOffice = officeDao.get(office.getParentId());
					u = UserUtils.get(parentOffice.getPrimaryPerson().getId());
				} else {
					u = UserUtils.get(office.getPrimaryPerson().getId());
				}
			}
		}

		return u;
	}

	/**
	 * @Description: 批量修改负责人
	 * @param cuCustomerList
	 * @param cuCustomer
	 */
	@Transactional(readOnly = false)
	public void modifyPrincipal(List<CuCustomer> cuCustomerList, CuCustomer cuCustomer){
		//修改人
		User user = UserUtils.getUser();
		cuCustomerList.get(0).setPrincipal1(cuCustomer.getPrincipal1());
		cuCustomerList.get(0).setPrincipal2(cuCustomer.getPrincipal2());
		cuCustomerList.get(0).setUpdateBy(user);
		cuCustomerList.get(0).setUpdateDate(new Date());
		//进行批量修改操作
		cuCustomerDao.modifyPrincipal(cuCustomerList);
	}
}
