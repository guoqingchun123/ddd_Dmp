package cn.bugstack.infrastructure.persistent.po;

import com.xunfang.mmdp.framework.persistence.DataEntity;
import com.xunfang.mmdp.modules.sys.entity.Office;

import org.hibernate.validator.constraints.Length;

/**
 * 系统角色Entity
 * @author caoqishun
 * @version 2018-05-14
 */
public class SysRole extends DataEntity<SysRole> {

	private static final long serialVersionUID = 1L;
	private Office office;		// 归属机构
	private String name;		// 角色名称
	private String enname;		// 英文名称
	private String roleType;		// 角色类型
	private String dataScope;		// 数据范围
	private String isSys;		// 是否系统数据
	private String useable;		// 是否可用

	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public SysRole() {
		super();
	}

	public SysRole(String id){
		super(id);
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min=1, max=100, message="角色名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=255, message="英文名称长度必须介于 0 和 255 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	@Length(min=0, max=255, message="角色类型长度必须介于 0 和 255 之间")
	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	@Length(min=0, max=1, message="数据范围长度必须介于 0 和 1 之间")
	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	@Length(min=0, max=64, message="是否系统数据长度必须介于 0 和 64 之间")
	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}

	@Length(min=0, max=64, message="是否可用长度必须介于 0 和 64 之间")
	public String getUseable() {
		return useable;
	}

	public void setUseable(String useable) {
		this.useable = useable;
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
