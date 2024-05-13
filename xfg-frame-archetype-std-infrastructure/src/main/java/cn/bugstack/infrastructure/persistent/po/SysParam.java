package cn.bugstack.infrastructure.persistent.po;

import com.xunfang.mmdp.framework.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 系统参数Entity
 * @author lzk
 * @version 2017-12-14
 */
public class SysParam extends DataEntity<SysParam> {

	private static final long serialVersionUID = 1L;
	private String paramid;		// 主键
	private String paramname;		// 参数名
	private String paramvalue;		// 参数值
	private String status;		// 参数状态：0 失效，1 生效
	private List<String> paramids;	// 主键集合
	protected Integer pageNo;  //现在的页数
	protected Integer pageSize; //显示条数
	protected Integer startIndex; //开始的索引

	public SysParam() {
		super();
	}

	public SysParam(String id){
		super(id);
	}

	@Length(min=1, max=32, message="主键长度必须介于 1 和 32 之间")
	public String getParamid() {
		return paramid;
	}

	public void setParamid(String paramid) {
		this.paramid = paramid;
	}

	@Length(min=1, max=128, message="参数名长度必须介于 1 和 128 之间")
	public String getParamname() {
		return paramname;
	}

	public void setParamname(String paramname) {
		this.paramname = paramname;
	}

	@Length(min=1, max=128, message="参数值长度必须介于 1 和 128 之间")
	public String getParamvalue() {
		return paramvalue;
	}

	public void setParamvalue(String paramvalue) {
		this.paramvalue = paramvalue;
	}

	@Length(min=1, max=1, message="参数状态：0 失效，1 生效长度必须介于 1 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		if (pageNo == null || pageSize == null)
		{
			return 0;
		}
		return pageNo*pageSize;
	}

	public void setStartIndex(Integer startIndex) {
	}

	public List<String> getParamids() {
		return paramids;
	}

	public void setParamids(List<String> paramids) {
		this.paramids = paramids;
	}
}
