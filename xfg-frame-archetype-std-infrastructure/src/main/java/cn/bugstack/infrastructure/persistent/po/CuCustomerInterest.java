/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.po;

import com.xunfang.mmdp.framework.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

/**
 *
 * @ClassName CuCustomerInterest
 * @Description: 客户兴趣关联表Entity
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月12日 下午4:44:51
 */
public class CuCustomerInterest extends DataEntity<CuCustomerInterest> {

	private static final long serialVersionUID = 1L;
	private String id;    //主键ID
	private String cuInterestId;		// 兴趣表ID
	private String cuInterestName;  //兴趣名称
	private String cuCustomerLinkmanId;		// 客户联系人表ID
	private String cuCustomerId; //客户表ID
	private String linkmanName; //客户联系人表Name

	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public CuCustomerInterest() {
		super();
	}

	public CuCustomerInterest(String id){
		super(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCuCustomerId() {
		return cuCustomerId;
	}

	public void setCuCustomerId(String cuCustomerId) {
		this.cuCustomerId = cuCustomerId;
	}

	public String getCuInterestName() {
		return cuInterestName;
	}

	public void setCuInterestName(String cuInterestName) {
		this.cuInterestName = cuInterestName;
	}

	@Length(min=0, max=64, message="兴趣表ID长度必须介于 0 和 64 之间")
	public String getCuInterestId() {
		return cuInterestId;
	}

	public void setCuInterestId(String cuInterestId) {
		this.cuInterestId = cuInterestId;
	}

	public String getLinkmanName() {
		return linkmanName;
	}

	public void setLinkmanName(String linkmanName) {
		this.linkmanName = linkmanName;
	}

	@Length(min=0, max=64, message="客户联系人表ID长度必须介于 0 和 64 之间")
	public String getCuCustomerLinkmanId() {
		return cuCustomerLinkmanId;
	}

	public void setCuCustomerLinkmanId(String cuCustomerLinkmanId) {
		this.cuCustomerLinkmanId = cuCustomerLinkmanId;
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
