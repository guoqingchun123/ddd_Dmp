package cn.bugstack.infrastructure.util;


import com.xunfang.mmdp.framework.utils.SpringContextHolder;
import com.xunfang.mmdp.modules.sys.dao.MenuDao;
import com.xunfang.mmdp.modules.sys.dao.RoleDao;
import com.xunfang.mmdp.modules.sys.entity.Menu;
import com.xunfang.mmdp.modules.sys.entity.Role;
import com.xunfang.mmdp.modules.sys.entity.User;
import com.xunfang.mmdp.modules.sys.utils.UserUtils;

import org.python.google.common.collect.Lists;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bugstack.infrastructure.common.DictConstants;
import cn.bugstack.infrastructure.persistent.dao.SysUserRightDao;
import cn.bugstack.infrastructure.persistent.dao.SysUserRoleDao;
import cn.bugstack.infrastructure.persistent.po.SysDataScope;
import cn.bugstack.infrastructure.persistent.po.SysUserRole;

/**
 * 用户工具类V2
 * @author mmdp
 * @version 2018-06-13
 */
public class UserUtils2 {
	private static SysUserRightDao userRightDao = SpringContextHolder.getBean(SysUserRightDao.class);
	private static SysUserRoleDao userRoleDao   = SpringContextHolder.getBean(SysUserRoleDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);

	//MMDP cache key
	public static final String MMDP_CACHE_MENU_LIST = "mmdpMenuList";
	public static final String MMDP_CACHE_ROLE_LIST = "mmdpRoleList";

	/***
	 * MMDP 获取用户角色列表   用户角色+用户归属群组角色 + 岗位角色
	 * @Author caoqishun
	 * @Date 2018年6月12日下午8:28:20
	 * @return
	 */
	public static  List<Role> getRoleListV2(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>) UserUtils.getCache(MMDP_CACHE_ROLE_LIST);
		if (roleList == null || roleList.size() <= 0){
			User user = UserUtils.getUser();
			if (user.isAdmin()){
				roleList = roleDao.findAllList(new Role());
			}else{
				Map<String,String> queryPara = new HashMap<String,String>();
				queryPara.put("DEL_FLAG_NORMAL", Role.DEL_FLAG_NORMAL);
				queryPara.put("STATUS_VALIDATE", Role.STATUS_VALIDATE);
				queryPara.put("userId", user.getId());
				List<SysUserRole> userRoleList = userRoleDao.getUserRoleListV2(queryPara);
				roleList = Lists.newArrayList();
				for(SysUserRole sysRole : userRoleList){
					Role role = new Role();
					role.setId(sysRole.getRoleId());

					if(null != sysRole.getRole())
					{
						role.setEnname(sysRole.getRole().getEnname());
						role.setDataScope(sysRole.getRole().getDataScope());
						role.setRoleType(sysRole.getRole().getRoleType());
					}
					roleList.add(role);
				}
			}
			UserUtils.putCache(MMDP_CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}

	/**
	 * MMDP ---获取当前用户授权菜单（关联sys_right_item）
	 * @return
	 */
	public static List<Menu> getMenuListV2(){
		@SuppressWarnings("unchecked")
		List<Menu> menuList = (List<Menu>) UserUtils.getCache(MMDP_CACHE_MENU_LIST);
		if (menuList == null || menuList.size() <= 0){
			User user = UserUtils.getUser();
			if (user.isAdmin()){
				menuList = menuDao.findAllListV2(new Menu());
			}else{
				Menu m = new Menu();
				m.setUserId(user.getId());
				menuList = menuDao.findByUserIdV2(m);
			}
			UserUtils.putCache(MMDP_CACHE_MENU_LIST, menuList);
		}
		return menuList;
	}

	public static List<SysDataScope> getDataScope(User user){
		//User user = UserUtils.getUser();
		String userId = "";
		if(null == user){
			userId = UserUtils.getUser().getId();
		}
		else{
			userId =  user.getId();
		}
		Map<String, String> queryPara = new HashMap<String, String>();
		queryPara.put("STATUS_VALIDATE", DictConstants.STATUS_VALIDATE);
		queryPara.put("DEL_FLAG_NORMAL", DictConstants.DEL_FLAG_NORMAL);
		queryPara.put("itemType", DictConstants.Right_Item_Type_DATASCOPE);
		queryPara.put("userId", userId);
		return  userRightDao.getUserDataScopeByUserId(queryPara);
	}

	/**
	 * @description: 减掉的部门
	 * @author  009164 wdn
	 * @date: 2022/3/30 10:37
	 */
	public static List<SysDataScope> getReduceDataScope(User user) {
		String userId = "";
		if(null == user){
			userId = UserUtils.getUser().getId();
		}
		else{
			userId =  user.getId();
		}
		Map<String, String> queryPara = new HashMap<String, String>();
		queryPara.put("STATUS_VALIDATE", DictConstants.STATUS_VALIDATE);
		queryPara.put("DEL_FLAG_NORMAL", DictConstants.DEL_FLAG_NORMAL);
		queryPara.put("itemType", DictConstants.Right_Item_Type_DATASCOPE);
		queryPara.put("userId", userId);
		return  userRightDao.getReduceDataScopeByUserId(queryPara);
	}
}
