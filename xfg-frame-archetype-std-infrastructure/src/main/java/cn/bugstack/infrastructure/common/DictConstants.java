package cn.bugstack.infrastructure.common;

public final class DictConstants {

	//权限资源类型定义
	public static final String Right_Item_Type =  "right_item_type";
	//菜单
	public static final String Right_Item_Type_MENU =  "1";
	//数据范围
	public static final String Right_Item_Type_DATASCOPE =  "2";
	//页面元素
	public static final String Right_Item_Type_PAGEELE =  "3";
	//流程
	public static final String Right_Item_Type_FLOW=  "4";
	//文件
	public static final String Right_Item_Type_FILE =  "5";
	//功能模块
	public static final String Right_Item_Type_FUNC =  "6";

	//删除标志位
	public static final String DEL_FLAG_NORMAL = "0";

	//权限增删标志位
	public static final String RIGHT_FLAG_ADD  = "1";
	public static final String RIGHT_FLAG_DEL  = "0";
	//状态定义
	//有效
	public static final String STATUS_VALIDATE = "1";
	//无效
	public static final String STATUS_INVALIDATE = "0";

	//是否
	public static final String YESNO_YES = "1";
	public static final String YESNO_NO = "0";

	//对象操作类型
	public static final String OBJ_OPERTYPE_I = "I";
	public static final String OBJ_OPERTYPE_D = "D";
	public static final String OBJ_OPERTYPE_M = "M";
	public static final String OBJ_OPERTYPE_O = "O";

	//数据权限类型定义(字典data_scope_type)
	public static final String DATA_SCOPE_TYPE_ALL = "0";//所有
	public static final String DATA_SCOPE_TYPE_PERSON = "1";//个性化人员
	public static final String DATA_SCOPE_TYPE_DEPT = "2";  //个性化部门
	public static final String DATA_SCOPE_TYPE_DEPTANDCHILD = "3"; //个性化部门及下级部门
	public static final String DATA_SCOPE_TYPE_OFFICE_AND_CHILD = "4";  //所在部门及其下属部门
	public static final String DATA_SCOPE_TYPE_OFFICE = "5"; //所在部门
	public static final String DATA_SCOPE_TYPE_SELF = "6";  //自有数据

	public static final String DATA_SCOPE_VALUE_ALL = "ALL";//所有的下级
}
