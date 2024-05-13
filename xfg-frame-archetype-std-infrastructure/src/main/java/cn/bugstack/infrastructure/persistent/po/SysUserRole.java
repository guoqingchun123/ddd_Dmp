package cn.bugstack.infrastructure.persistent.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunfang.mmdp.framework.persistence.DataEntity;
import com.xunfang.mmdp.modules.sys.entity.Role;
import com.xunfang.mmdp.modules.sys.entity.User;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * 用户角色Entity
 * @author caoqishun
 * @version 2018-05-22
 */
public class SysUserRole extends DataEntity<SysUserRole> {

	private static final long serialVersionUID = 1L;
	private User user;		// 用户编号
	private String roleId;		// 角色编号
	private Date startDate;		// 开始日期
	private Date endDate;		// 结束日期
	private String status;		// 状态
	private String remark;		// 备注
	private String flag;		// 标志位
	private SysRole role;//角色定义

	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public SysUserRole() {
		super();
	}

	public SysUserRole(String id){
		super(id);
	}

	@NotNull(message="用户编号不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min=1, max=64, message="角色编号长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
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

	@Length(min=0, max=24, message="备注长度必须介于 0 和 24 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Length(min=0, max=1, message="标志位长度必须介于 0 和 1 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
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

	public SysRole getRole() {
		return role;
	}

	public void setRole(SysRole role) {
		this.role = role;
	}


}
