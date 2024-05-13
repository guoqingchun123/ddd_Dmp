/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package cn.bugstack.infrastructure.persistent.po;

import com.xunfang.mmdp.framework.persistence.DataEntity;

import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 *
 * @ClassName CuProducttype
 * @Description: 产品类型表Entity
 * Copyright: Copyright (c) 2018
 * Company:深圳市讯方技术股份有限公司
 *
 * @author jiangmin
 * @date 2018年5月8日 上午9:10:40
 */
public class CuProducttype extends DataEntity<CuProducttype> {

	private static final long serialVersionUID = 1L;
	private String id;        //主键ID
	private String name;		// 名称
	private String description;		// 描述
	private String delFlag;         // 删除标记 0：未删除  1：已删除
	private List<CuProducttype> cuProducttypeList;//产品类型信息List

	//分页使用
	private Integer pageNo;
    private Integer pageSize;
    private Integer startIndex;

	public CuProducttype() {
		super();
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public CuProducttype(String id){
		super(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Length(min=0, max=50, message="名称长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=0, max=200, message="描述长度必须介于 0 和 200 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<CuProducttype> getCuProducttypeList() {
		return cuProducttypeList;
	}

	public void setCuProducttypeList(List<CuProducttype> cuProducttypeList) {
		this.cuProducttypeList = cuProducttypeList;
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
