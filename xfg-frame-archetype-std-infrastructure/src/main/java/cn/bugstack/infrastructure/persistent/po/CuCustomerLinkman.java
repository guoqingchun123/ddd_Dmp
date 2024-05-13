/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.po;

import com.xunfang.mmdp.framework.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

/**
 *
 * @ClassName CuCustomerLinkman
 * @Description: 客户联系人表Entity
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:51:18
 */
public class CuCustomerLinkman extends DataEntity<CuCustomerLinkman> {

	private static final long serialVersionUID = 1L;
	private String id;  		//主键ID
	private String cuCustomerId;		// 客户表ID
	private String name;		// 联系人姓名
	private String dept;		// 部门
	private String duty;		// 职位
	private String ismain;		// 是否主联系人 0：是  1：不是
	private String sex;		// 性别  0：男 1：女
	private String officephone;		// 办公电话/单位电话
	private String mobile;		// 移动电话/联系人电话
	private String email;		// 电子邮件
	private String position;		// 决策链位置 0：信息提供 1：需求提供 2：技术建议 3：技术决策 4：商务决策 5：供应商决策 6：接口人
	private String age;		// 年龄段 0：20-30  1：30-40  2：40-50  3：50-60 4：60-70
	private String workaddress;		// 工作地址/单位地址
	private String fullAddress;		// 详细地址
	private String postcode;		// 邮政编码
	private String familyaddress;		// 家庭住址
	private String fax;		// 传真
	private String familyphone;		// 家庭电话
	private String instantcomm;		// 即时通讯
	private String other;		// 其它
	private String remark;		// 备注

	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public CuCustomerLinkman() {
		super();
	}

	public CuCustomerLinkman(String id){
		super(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(min=0, max=64, message="客户表ID长度必须介于 0 和 64 之间")
	public String getCuCustomerId() {
		return cuCustomerId;
	}

	public void setCuCustomerId(String cuCustomerId) {
		this.cuCustomerId = cuCustomerId;
	}

	@Length(min=0, max=10, message="联系人姓名长度必须介于 0 和 10 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=30, message="部门长度必须介于 0 和 30 之间")
	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Length(min=0, max=20, message="职位长度必须介于 0 和 20 之间")
	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	@Length(min=0, max=1, message="是否主联系人 0：是  1：不是长度必须介于 0 和 1 之间")
	public String getIsmain() {
		return ismain;
	}

	public void setIsmain(String ismain) {
		this.ismain = ismain;
	}

	@Length(min=0, max=1, message="性别  0：男 1：女长度必须介于 0 和 1 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Length(min=0, max=20, message="办公电话/单位电话长度必须介于 0 和 20 之间")
	public String getOfficephone() {
		return officephone;
	}

	public void setOfficephone(String officephone) {
		this.officephone = officephone;
	}

	@Length(min=0, max=11, message="移动电话/联系人电话长度必须介于 0 和 11 之间")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Length(min=0, max=30, message="电子邮件长度必须介于 0 和 30 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=1, message="决策链位置 0：信息提供 1：需求提供 2：技术建议 3：技术决策 4：商务决策 5：供应商决策 6：接口人长度必须介于 0 和 1 之间")
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Length(min=0, max=1, message="年龄段 0：20-30  1：30-40  2：40-50  3：50-60 4：60-70长度必须介于 0 和 1 之间")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Length(min=0, max=200, message="工作地址/单位地址长度必须介于 0 和 200 之间")
	public String getWorkaddress() {
		return workaddress;
	}

	public void setWorkaddress(String workaddress) {
		this.workaddress = workaddress;
	}

	@Length(min=0, max=255, message="详细地址长度必须介于 0 和 255 之间")
	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	@Length(min=0, max=6, message="邮政编码长度必须介于 0 和 6 之间")
	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Length(min=0, max=100, message="家庭住址长度必须介于 0 和 100 之间")
	public String getFamilyaddress() {
		return familyaddress;
	}

	public void setFamilyaddress(String familyaddress) {
		this.familyaddress = familyaddress;
	}

	@Length(min=0, max=16, message="传真长度必须介于 0 和 16 之间")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Length(min=0, max=20, message="家庭电话长度必须介于 0 和 20 之间")
	public String getFamilyphone() {
		return familyphone;
	}

	public void setFamilyphone(String familyphone) {
		this.familyphone = familyphone;
	}

	@Length(min=0, max=100, message="即时通讯长度必须介于 0 和 100 之间")
	public String getInstantcomm() {
		return instantcomm;
	}

	public void setInstantcomm(String instantcomm) {
		this.instantcomm = instantcomm;
	}

	@Length(min=0, max=100, message="其它长度必须介于 0 和 100 之间")
	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	@Length(min=0, max=500, message="备注长度必须介于 0 和 500 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
