/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunfang.mmdp.framework.persistence.DataEntity;
import com.xunfang.mmdp.framework.utils.excel.annotation.ExcelField;

import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 *
 * @ClassName CuCustomer
 * @Description: 客户关系管理-客户表Entity Copyright: Copyright (c) 2018
 *               Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:37:51
 */
public class CuCustomer extends DataEntity<CuCustomer> {

	private static final long serialVersionUID = 1L;
	private String id;    //主键ID
	private String areaId;  //地区ID
	private String areaName; //地区名称
	private String provinceId;        // 省份ID
	private String provinceName;      // 省份Name
	private String cityId;            // 城市ID
	private String cityName;           // 城市Name
	private String customerType;		// 客户类型 0：企业客户  1：学校客户
	private String customerName;		// 客户名称
	private String schoolType;		// 学校类型 0：本科 1：高职 2：中职 3：其它
	private String createTime;		// 成立时间
	private String companyType;		// 企业类型 0：合资 1：独资 2：国有 3：私营 4：全民所有制 5：集体所有制  6：股份制 7：有限责任  8：其他
	private String busLine;		// 乘车路线
	private String address;		// 详细地址
	private String cuProducttypeId;		// 产品类型表ID
	private String state;		// 客户状态 0：接触 1：意向 2：计划 3：立项  4：销售  5：用户
	private String labStatus;		// 实验室现状
	private String professionDescribe;		// 专业现状描述
	private String principal1;		// 用户表ID
	private String principal2;		// 用户表ID
	private Date pursuitTime;		// 追踪时间
	private String legalRepresentative;		// 法定代表人
	private String unitsIc;		// 统一社会信用代码
	private String bank;		// 开户银行
	private String account;		// 开户银行帐号
	private String url;		// 网址
	private String mainBusiness;		// 主营业务
	private String sidelineBusiness;		// 兼营业务
	private String billingTelephone;		// 开票电话
	private String billingAddress;		// 开票地址
	private String inputuser;		// 用户表ID
	private String tracedate;		// 录入时间
	private String approvalOpinions;		// 审批意见
	private String approver;		// 用户表主键ID
	private Date approvalTime;		// 审批时间
	private String approvalStatus;		// 审批状态 0：待审批  1：通过  2：不通过
	private String status;		// 状态 0：禁用 1：启用

	private List<CuCustomerInterest> cuCustomerInterestList;// 客户兴趣信息List
	private List<CuCustomerLinkman> cuCustomerLinkmanList;// 客户联系人信息List
	private List<CuCustomer> cuCustomerList;//客户信息List

	private String projectName; //项目名称
	private String officeName; //归属部门
	private String name;//项目经理
	private String projectBegintime;//项目开始时间
	private String projectEndtime;//项目结束时间
	private String label;//项目状态
	private String userType; //用户类型  0：管理员  1：教育事业部 2：其他部门
	private String createUser;//创建人
	private String userMaster;//部门负责人 0:部门负责人  1:不是部门负责人
	private String officeId;//部门ID
	private String cuProducttypeName;		// 产品类型表Name

	// 分页使用
	private Integer pageNo;
	private Integer pageSize;
	private Integer startIndex;


	private String dataSql;



	public String getDataSql() {
		return dataSql;
	}

	public void setDataSql(String dataSql) {
		this.dataSql = dataSql;
	}

	public CuCustomer() {
		super();
	}

	public CuCustomer(String id) {
		super(id);
	}

	public String getCuProducttypeName() {
		return cuProducttypeName;
	}

	public void setCuProducttypeName(String cuProducttypeName) {
		this.cuProducttypeName = cuProducttypeName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getUserMaster() {
		return userMaster;
	}

	public void setUserMaster(String userMaster) {
		this.userMaster = userMaster;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	@Length(min=0, max=1, message="客户类型 0：企业客户  1：学校客户长度必须介于 0 和 1 之间")
	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	@Length(min=0, max=1, message="学校类型长度必须介于 0 和 1 之间")
	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	@Length(min=0, max=500, message="乘车路线长度必须介于 0 和 500 之间")
	public String getBusLine() {
		return busLine;
	}

	public void setBusLine(String busLine) {
		this.busLine = busLine;
	}

	@Length(min=0, max=100, message="客户名称长度必须介于 0 和 100 之间")
	@ExcelField(title="企业名称", align=2, sort=1)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Length(min=0, max=200, message="详细地址长度必须介于 0 和 200 之间")
	@ExcelField(title="详细地址", align=2, sort=5)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Length(min=0, max=10, message="成立时间长度必须介于 0 和 10 之间")
	@ExcelField(title="成立时间", align=2, sort=2)
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	@Length(min=0, max=64, message="产品类型表ID长度必须介于 0 和 64 之间")
	public String getCuProducttypeId() {
		return cuProducttypeId;
	}

	public void setCuProducttypeId(String cuProducttypeId) {
		this.cuProducttypeId = cuProducttypeId;
	}

	@Length(min=0, max=1, message="客户状态长度必须介于 0 和 1 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Length(min=0, max=1, message="企业类型 0：合资 1：独资 2：国有 3：私营 4：全民所有制 5：集体所有制  6：股份制 7：有限责任  8：其他长度必须介于 0 和 1 之间")
	@ExcelField(title="企业类型", align=2, sort=3,dictType="Company_Type")
	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	@Length(min=0, max=500, message="实验室现状长度必须介于 0 和 500 之间")
	public String getLabStatus() {
		return labStatus;
	}

	public void setLabStatus(String labStatus) {
		this.labStatus = labStatus;
	}

	@Length(min=0, max=500, message="专业现状描述长度必须介于 0 和 500 之间")
	public String getProfessionDescribe() {
		return professionDescribe;
	}

	public void setProfessionDescribe(String professionDescribe) {
		this.professionDescribe = professionDescribe;
	}

	@Length(min=0, max=64, message="业务负责人1长度必须介于 0 和 64 之间")
	public String getPrincipal1() {
		return principal1;
	}

	public void setPrincipal1(String principal1) {
		this.principal1 = principal1;
	}

	@Length(min=0, max=64, message="业务负责人2长度必须介于 0 和 64 之间")
	public String getPrincipal2() {
		return principal2;
	}

	public void setPrincipal2(String principal2) {
		this.principal2 = principal2;
	}

	@Length(min=0, max=64, message="录入人长度必须介于 0 和 64 之间")
	public String getInputuser() {
		return inputuser;
	}

	public void setInputuser(String inputuser) {
		this.inputuser = inputuser;
	}

	@Length(min=0, max=10, message="录入时间长度必须介于 0 和 10 之间")
	public String getTracedate() {
		return tracedate;
	}

	public void setTracedate(String tracedate) {
		this.tracedate = tracedate;
	}

	@Length(min=0, max=100, message="审批意见长度必须介于 0 和 100 之间")
	public String getApprovalOpinions() {
		return approvalOpinions;
	}

	public void setApprovalOpinions(String approvalOpinions) {
		this.approvalOpinions = approvalOpinions;
	}

	@Length(min=0, max=64, message="审批人长度必须介于 0 和 64 之间")
	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	public Date getApprovalTime() {
		return approvalTime;
	}

	public void setApprovalTime(Date approvalTime) {
		this.approvalTime = approvalTime;
	}

	@Length(min=0, max=128, message="法定代表人长度必须介于 0 和 128 之间")
	@ExcelField(title="法定代表人", align=2, sort=4)
	public String getLegalRepresentative() {
		return legalRepresentative;
	}

	public void setLegalRepresentative(String legalRepresentative) {
		this.legalRepresentative = legalRepresentative;
	}

	@Length(min=0, max=1, message="审批状态长度必须介于 0 和 1 之间")
	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	@Length(min=0, max=32, message="统一社会信用代码长度必须介于 0 和 32 之间")
	@ExcelField(title="统一社会信用代码", align=2, sort=6)
	public String getUnitsIc() {
		return unitsIc;
	}

	public void setUnitsIc(String unitsIc) {
		this.unitsIc = unitsIc;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getPursuitTime() {
		return pursuitTime;
	}

	public void setPursuitTime(Date pursuitTime) {
		this.pursuitTime = pursuitTime;
	}

	@Length(min=0, max=255, message="开户银行长度必须介于 0 和 255 之间")
	@ExcelField(title="开户银行", align=2, sort=7)
	public String getBank() {
		return bank;
	}

	@Length(min=0, max=11, message="开票电话长度必须介于 0 和 11 之间")
	@ExcelField(title="开户银行", align=2, sort=8)
	public String getBillingTelephone() {
		return billingTelephone;
	}

	public void setBillingTelephone(String billingTelephone) {
		this.billingTelephone = billingTelephone;
	}

	@Length(min=0, max=200, message="开票地址长度必须介于 0 和 200 之间")
	@ExcelField(title="开户银行", align=2, sort=9)
	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Length(min=0, max=32, message="开户银行帐号长度必须介于 0 和 32 之间")
	@ExcelField(title="开户银行帐号", align=2, sort=10)
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Length(min=0, max=128, message="网址长度必须介于 0 和 128 之间")
	@ExcelField(title="网址", align=2, sort=11)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Length(min=0, max=255, message="主营业务长度必须介于 0 和 255 之间")
	@ExcelField(title="主营业务", align=2, sort=12)
	public String getMainBusiness() {
		return mainBusiness;
	}

	public void setMainBusiness(String mainBusiness) {
		this.mainBusiness = mainBusiness;
	}

	@Length(min=0, max=255, message="兼营业务长度必须介于 0 和 255 之间")
	@ExcelField(title="兼营业务", align=2, sort=13)
	public String getSidelineBusiness() {
		return sidelineBusiness;
	}

	public void setSidelineBusiness(String sidelineBusiness) {
		this.sidelineBusiness = sidelineBusiness;
	}

	@Length(min=0, max=1, message="状态 0：启用  1：禁用长度必须介于 0 和 1 之间")
	@ExcelField(title="状态", align=2, sort=14)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CuCustomerInterest> getCuCustomerInterestList() {
		return cuCustomerInterestList;
	}

	public void setCuCustomerInterestList(List<CuCustomerInterest> cuCustomerInterestList) {
		this.cuCustomerInterestList = cuCustomerInterestList;
	}

	public List<CuCustomerLinkman> getCuCustomerLinkmanList() {
		return cuCustomerLinkmanList;
	}

	public void setCuCustomerLinkmanList(List<CuCustomerLinkman> cuCustomerLinkmanList) {
		this.cuCustomerLinkmanList = cuCustomerLinkmanList;
	}

	public List<CuCustomer> getCuCustomerList() {
		return cuCustomerList;
	}

	public void setCuCustomerList(List<CuCustomer> cuCustomerList) {
		this.cuCustomerList = cuCustomerList;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProjectBegintime() {
		return projectBegintime;
	}

	public void setProjectBegintime(String projectBegintime) {
		this.projectBegintime = projectBegintime;
	}

	public String getProjectEndtime() {
		return projectEndtime;
	}

	public void setProjectEndtime(String projectEndtime) {
		this.projectEndtime = projectEndtime;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStartIndex() {
		if (pageNo == null || pageSize == null) {
			return 0;
		}
		return pageNo * pageSize;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
}
