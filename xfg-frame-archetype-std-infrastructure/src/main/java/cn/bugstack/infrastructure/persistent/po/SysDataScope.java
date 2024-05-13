/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunfang.mmdp.framework.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 数据权限Entity
 * @author caoqishun
 * @version 2018-05-04
 */
public class SysDataScope extends DataEntity<SysDataScope> {

	private static final long serialVersionUID = 1L;
	private String scopeName;		// 范围名称
	private String scopeCode;		// 范围编码
	private String scopeType;		// 类型
	private String muduleId;		// 模块ID
	private String moduleCode;		// 模块名称
	private String muduleName;     //模块名称
	private String menuId;		// 菜单ID
	private String menuCode;		// 菜单编码
	private String menuName;		// 菜单名称
	private String scopeValue;		// 范围值
	private String level;		// 级别
	private String description;		// 描述
	private Date startDate;		// 开始日期
	private Date endDate;		// 结束日期
	private String status;		// 状态
	private String remark;		// 备注
	private String rightId;     //权限Id
	private String adjustmentType;     //数据调整类型 1：增 0：减

	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public SysDataScope() {
		super();
	}

	public SysDataScope(String id){
		super(id);
	}


	public String getAdjustmentType() {
		return adjustmentType;
	}

	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}

	@Length(min=0, max=128, message="scope_name长度必须介于 0 和 128之间")
	public String getScopeName() {
		return scopeName;
	}

	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}

	@Length(min=0, max=64, message="scope_code长度必须介于 0 和 64 之间")
	public String getScopeCode() {
		return scopeCode;
	}

	public void setScopeCode(String scopeCode) {
		this.scopeCode = scopeCode;
	}

	@Length(min=0, max=16, message="类型长度必须介于 0 和 16 之间")
	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	@Length(min=0, max=64, message="模块ID长度必须介于 0 和 64 之间")
	public String getMuduleId() {
		return muduleId;
	}

	public void setMuduleId(String muduleId) {
		this.muduleId = muduleId;
	}

	@Length(min=0, max=64, message="模块名称长度必须介于 0 和 64 之间")
	public String getModuleCode() {
		return moduleCode;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	@Length(min=0, max=64, message="菜单ID长度必须介于 0 和 64 之间")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Length(min=0, max=64, message="菜单编码长度必须介于 0 和 64 之间")
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	@Length(min=0, max=2048, message="范围值长度必须介于 0 和 2048 之间")
	public String getScopeValue() {
		return scopeValue;
	}

	public void setScopeValue(String scopeValue) {
		this.scopeValue = scopeValue;
	}

	@Length(min=0, max=2, message="级别长度必须介于 0 和 2 之间")
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	@Length(min=0, max=256, message="描述长度必须介于 0 和 256 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Length(min=0, max=1, message="状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=0, max=256, message="remark长度必须介于 0 和 256 之间")
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

	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getMuduleName() {
		return muduleName;
	}

	public void setMuduleName(String muduleName) {
		this.muduleName = muduleName;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


}
