package cn.bugstack.infrastructure.persistent.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xunfang.mmdp.framework.persistence.DataEntity;
import com.xunfang.mmdp.modules.sys.entity.User;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户细化权限Entity
 * @author caoqishun
 * @version 2018-05-14
 */
public class SysUserRight extends DataEntity<SysUserRight> {

	private static final long serialVersionUID = 1L;
	private User user;		// 用户
	private String rightId;		// 权限
	private Date startDate;		// 开始日期
	private Date endDate;		// 结束日期
	private String status;		// 是否有效
	private String flag;		// 权限增删位
	private String remark;		// 备注
	private String itemType;    //资源类型
	private String itemId;      //资源Id
	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public SysUserRight() {
		super();
	}

	public SysUserRight(String id){
		super(id);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Length(min=0, max=10, message="权限长度必须介于 0 和 10 之间")
	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
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

	@Length(min=0, max=1, message="是否有效长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Length(min=0, max=1, message="权限增删位长度必须介于 0 和 1 之间")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Length(min=0, max=24, message="备注长度必须介于 0 和 24 之间")
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
