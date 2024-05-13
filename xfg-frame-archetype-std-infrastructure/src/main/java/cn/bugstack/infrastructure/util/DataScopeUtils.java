package cn.bugstack.infrastructure.util;

import com.xunfang.mmdp.framework.utils.SpringContextHolder;
import com.xunfang.mmdp.framework.utils.StringUtils;
import com.xunfang.mmdp.modules.sys.dao.OfficeDao;
import com.xunfang.mmdp.modules.sys.entity.Office;
import com.xunfang.mmdp.modules.sys.entity.Role;
import com.xunfang.mmdp.modules.sys.entity.User;
import com.xunfang.mmdp.modules.sys.utils.UserUtils;

import org.python.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.bugstack.infrastructure.common.DictConstants;
import cn.bugstack.infrastructure.persistent.dao.SysUserRightDao;
import cn.bugstack.infrastructure.persistent.dao.SysUserRoleDao;
import cn.bugstack.infrastructure.persistent.po.SysDataScope;

public class DataScopeUtils {

    private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    private static SysUserRightDao sysUserRightDao = SpringContextHolder.getBean(SysUserRightDao.class);
    private static SysUserRoleDao sysUserRoleDao = SpringContextHolder.getBean(SysUserRoleDao.class);

    //根据数据范围和用户ID 获取用户查询的数据范围
    public void dataScope(SysDataScope dataScope, String userId) {
        User user = UserUtils.get(userId);

        //获取配置的部门、人员权限

        //获取主管部门权限、人员权限

    }

    /**
     * 数据范围过滤
     *
     * @param user        当前用户对象，通过“entity.getCurrentUser()”获取
     * @param officeAlias 机构表别名，多个用“,”逗号隔开。
     * @param userAlias   用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
     * @return 标准连接条件对象
     */
    public static String dataScopeFilter(User user, String officeAlias, String userAlias, String moduleCode, String menuCode) {

        StringBuilder sqlString = new StringBuilder();

        //获取主管部门
        //List<Office> userMngOffice =  DataScopeUtils.getUserMngOffice(user);
        //获取权限配置
        Map<String, List<String>> scopeMap = getUserDataScope(user, menuCode, moduleCode);
        List<String> userIds = scopeMap.get("userIds");
        List<String> deptIds = scopeMap.get("deptIds");
        List<String> deptsChildIds = scopeMap.get("deptsChildIds");


        //List<SysDataScope>
        // 超级管理员，跳过权限过滤
        if (!user.isAdmin()) {
            boolean isDataScopeAll = false;

            if (!CollectionUtils.isEmpty(deptIds)
                    && !CollectionUtils.isEmpty(userIds)
                    && !CollectionUtils.isEmpty(deptsChildIds)
                    && StringUtils.equals(userIds.get(0), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(deptIds.get(0), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(deptsChildIds.get(0), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                isDataScopeAll = true;
            } else {
                StringBuilder ids = new StringBuilder();
                //增加权限部门数据（单部门 ）
                if (!CollectionUtils.isEmpty(deptIds)) {
                    for (String officeId : deptIds) {
                        ids.append("'" + officeId + "',");
                    }

                    for (String oa : StringUtils.split(officeAlias, ",")) {
                        sqlString.append(" OR " + oa + ".id in (" +
                                ids.substring(0, ids.length() - 1) + ")");
                    }
                }

                //增加权限部门数据（部门及下级）
                if (!CollectionUtils.isEmpty(deptsChildIds)) {
                    for (String officeId : deptsChildIds) {
                        Office office = officeDao.get(officeId);
                        for (String oa : StringUtils.split(officeAlias, ",")) {
                            sqlString.append(" OR " + oa + ".parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(sqlString)) {
                StringBuilder sqlTemp = new StringBuilder("(" + sqlString.substring(4) + ")");
                sqlString = sqlTemp;
            }
            // 如果没有全部数据权限，并设置了用户别名，则增加可以访问人的权限
            if (!isDataScopeAll) {
                StringBuilder sqlUser = new StringBuilder();
                if (StringUtils.isNotBlank(userAlias)) {
                    StringBuilder ids = new StringBuilder();
                    if (!CollectionUtils.isEmpty(userIds)) {
                        for (String userId : userIds) {
                            ids.append("'" + userId + "',");
                        }
                        for (String ua : StringUtils.split(userAlias, ",")) {
                            sqlUser.append(" OR " + ua + ".id in (" +
                                    ids.substring(0, ids.length() - 1) + ")");
                        }
                    }
//					for (String ua : StringUtils.split(userAlias, ",")){
//						for(String userId : userIds){
//							sqlString.append(" OR " + ua + ".id = '" + userId + "'");
//						}
//					}
                } else {
                    for (String oa : StringUtils.split(officeAlias, ",")) {
                        //sqlString.append(" OR " + oa + ".id  = " + user.getOffice().getId());
                        sqlUser.append(" OR " + oa + ".id IS NULL");
                    }
                }
                //整理SQL
                if (StringUtils.isNotBlank(sqlUser)) {
                    StringBuilder sqlTemp = new StringBuilder(" OR (" + sqlUser.substring(4) + ")");
                    sqlString.append(sqlTemp);
                }
            } else {
                // 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
                sqlString = new StringBuilder();
            }
        }
        if (StringUtils.isNotBlank(sqlString.toString())) {
            return " AND (" + sqlString.toString() + ")";
        }
        return "";
    }

    /**
     * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
     *
     * @param entity       当前过滤的实体类
     * @param sqlMapKey    sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
     * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
     * @param userWheres   user表条件，组成：用户表字段=业务表的用户字段
     * @example dataScopeFilter(user, " dsf ", " id = a.office_id ", " id = a.create_by ");
     * dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
     */
    public static String dataScopeFilterUseExist(User user, String officeWheres, String userWheres, String moduleCode, String menuCode) {

        // 如果是超级管理员，则不过滤数据
        if (user.isAdmin()) {
            return "";
        }

        // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
        StringBuilder sqlString = new StringBuilder();

        Map<String, List<String>> scopeMap = getUserDataScope(user, menuCode, moduleCode);
        List<String> userIds = scopeMap.get("userIds");
        List<String> deptIds = scopeMap.get("deptIds");
        List<String> deptsChildIds = scopeMap.get("deptsChildIds");

        //生成部门权限SQL语句
        for (String where : StringUtils.split(officeWheres, ",")) {
            StringBuilder ids = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            for (String id : deptIds) {
                ids.append("'" + id + "',");
            }
            for (String id : deptsChildIds) {
                Office office = officeDao.get(id);
                if (office != null && office.getId() != null) {

                    ids.append("'" + id + "',");
                    sql.append(" OR parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");


                }


            }
            if (ids.length() > 0) {
                sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
                sqlString.append(" WHERE (id in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlString.append(sql.toString() + ")");
                sqlString.append(" AND " + where + ")");
            }
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            StringBuilder sqlUser = new StringBuilder();
            StringBuilder ids = new StringBuilder();
            for (String id : userIds) {
                ids.append("'" + id + "',");
            }

            // 生成人权限SQL语句
            for (String where : StringUtils.split(userWheres, ",")) {
                sqlUser.append(" OR EXISTS (SELECT 1 FROM sys_user");
                sqlUser.append(" WHERE id  in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlUser.append(" AND " + where + ")");

            }
            if (StringUtils.isNotBlank(sqlUser.toString())) {
                StringBuilder sqlTmp = new StringBuilder();
                sqlTmp.append(" OR (");
                sqlTmp.append(sqlUser.substring(4));
                sqlTmp.append(" ) ");
                sqlString.append(sqlTmp);
            }
        }

//		System.out.println("dataScopeFilter: " + sqlString.toString());
        if (StringUtils.isNotBlank(sqlString.toString())) {
            StringBuilder sql = new StringBuilder(" AND ( " + sqlString.substring(4) + " ) ");
            sqlString = sql;
        }
        // 设置到自定义SQL对象
        return sqlString.toString();

    }


    /**
     * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
     * 适配业务进行数据权限的特殊处理，直接传入deptlist、userlist
     *
     * @param entity       当前过滤的实体类
     * @param sqlMapKey    sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
     * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
     * @param userWheres   user表条件，组成：用户表字段=业务表的用户字段]
     * @param String       moduleCode  模块编码
     * @param String       menuCode     菜单编码
     * @param List<String> qryDeptList  传入的部门列表
     * @param List<String> userList  传入的用户列表
     * @example dataScopeFilter(user, " dsf ", " id = a.office_id ", " id = a.create_by ");
     * dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
     */
    public static String dataScopeFilterUseExist(User user, String officeWheres, String userWheres, String moduleCode, String menuCode, List<String> qryDeptList, List<String> qryUserList) {

        // 如果是超级管理员，则不过滤数据
        if (user.isAdmin()) {
            return "";
        }

        // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
        StringBuilder sqlString = new StringBuilder();

        Map<String, List<String>> scopeMap = getUserDataScope(user, menuCode, moduleCode);
        List<String> userIds = scopeMap.get("userIds");
        if (CollectionUtils.isNotEmpty(qryUserList)) {
            userIds.addAll(qryUserList);
        }
        List<String> deptIds = scopeMap.get("deptIds");
        if (CollectionUtils.isNotEmpty(qryDeptList)) {
            deptIds.addAll(qryDeptList);
        }

        List<String> deptsChildIds = scopeMap.get("deptsChildIds");

        //生成部门权限SQL语句
        for (String where : StringUtils.split(officeWheres, ",")) {
            StringBuilder ids = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            for (String id : deptIds) {
                ids.append("'" + id + "',");
            }
            for (String id : deptsChildIds) {
                Office office = officeDao.get(id);
                if (office != null && office.getId() != null) {

                    ids.append("'" + id + "',");
                    sql.append(" OR parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");


                }


            }
            if (ids.length() > 0) {
                sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
                sqlString.append(" WHERE (id in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlString.append(sql.toString() + ")");
                sqlString.append(" AND " + where + ")");
            }
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            StringBuilder sqlUser = new StringBuilder();
            StringBuilder ids = new StringBuilder();
            for (String id : userIds) {
                ids.append("'" + id + "',");
            }

            // 生成人权限SQL语句
            for (String where : StringUtils.split(userWheres, ",")) {
                sqlUser.append(" OR EXISTS (SELECT 1 FROM sys_user");
                sqlUser.append(" WHERE id  in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlUser.append(" AND " + where + ")");

            }
            if (StringUtils.isNotBlank(sqlUser.toString())) {
                StringBuilder sqlTmp = new StringBuilder();
                sqlTmp.append(" OR (");
                sqlTmp.append(sqlUser.substring(4));
                sqlTmp.append(" ) ");
                sqlString.append(sqlTmp);
            }
        }

//		System.out.println("dataScopeFilter: " + sqlString.toString());
        if (StringUtils.isNotBlank(sqlString.toString())) {
            StringBuilder sql = new StringBuilder(" AND ( " + sqlString.substring(4) + " ) ");
            sqlString = sql;
        }
        // 设置到自定义SQL对象
        return sqlString.toString();

    }

    /**
     * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）(人事信息查询重载重载)
     *
     * @param entity       当前过滤的实体类
     * @param sqlMapKey    sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
     * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
     * @param userWheres   user表条件，组成：用户表字段=业务表的用户字段
     * @example dataScopeFilter(user, " dsf ", " id = a.office_id ", " id = a.create_by ");
     * dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
     */
    public static String dataScopeFilterUseExist(User user, String officeWheres, String userWheres, String moduleCode, String menuCode, String type) {

        // 如果是超级管理员，则不过滤数据
        if (user.isAdmin()) {
            return "";
        }

        // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
        StringBuilder sqlString = new StringBuilder();
        Map<String, List<String>> scopeMap = getUserDataScopeForHremployee(user, menuCode, moduleCode);
        List<String> userIds = scopeMap.get("userIds");
        List<String> deptIds = scopeMap.get("deptIds");
        List<String> deptsChildIds = scopeMap.get("deptsChildIds");
        //减掉权限的员工
        List<String> userIdsReduce = scopeMap.get("userIdsReduce");
        //减掉权限的部门
        List<String> deptIdsReduce = scopeMap.get("deptIdsReduce");
        //减掉权限的部门(为了避免指定部门及其下级部门中，增的仅仅选择了最上级部门，而减的是他的子部门，无法移除子部门，所以也要加not exists); zgq 2023/09/12 17:55:29
        List<String> deptsChildIdsReduce = scopeMap.get("deptsChildIdsReduce");
        //生成部门权限SQL语句
        for (String where : StringUtils.split(officeWheres, ",")) {
            StringBuilder ids = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            for (String id : deptIds) {
                ids.append("'" + id + "',");
            }
            for (String id : deptsChildIds) {
                Office office = officeDao.get(id);
                if (office != null && office.getId() != null) {
                  ids.append("'" + id + "',");
                  sql.append(" OR parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");
                }

            }
            if (ids.length() > 0) {
                sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
                sqlString.append(" WHERE (id in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlString.append(sql.toString() + ")");
                sqlString.append(" AND " + where + ")");
            }
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            StringBuilder sqlUser = new StringBuilder();
            StringBuilder ids = new StringBuilder();
            for (String id : userIds) {
                ids.append("'" + id + "',");
            }

            // 生成人权限SQL语句
            for (String where : StringUtils.split(userWheres, ",")) {
                sqlUser.append(" OR EXISTS (SELECT 1 FROM sys_user");
                sqlUser.append(" WHERE id  in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlUser.append(" AND " + where + ")");

            }
            if (StringUtils.isNotBlank(sqlUser.toString())) {
                StringBuilder sqlTmp = new StringBuilder();
                sqlTmp.append(" OR (");
                sqlTmp.append(sqlUser.substring(4));
                sqlTmp.append(" ) ");
                sqlString.append(sqlTmp);
            }
        }

        StringBuilder sqlnoString = new StringBuilder();//如果这个人是部门副职
        if(StringUtils.equals("hremployeeCode",menuCode)){
            User user1 = UserUtils.getUser();
            //查询用户所在部门 主管
            Office office = officeDao.get(user1.getOffice().getId());
            //查询当前登录用户 是部门副职的部门
            List<Office> deputyList = officeDao.getListByDeputyId(user1.getId());
            if(deputyList.size()>0 ||userIdsReduce.size()>0 || deptIdsReduce.size()>0){
                // 生成人权限SQL语句
                if(deputyList.size()>0 ||userIdsReduce.size()>0 )
                {
                StringBuilder sqlNoUser = new StringBuilder();
                StringBuilder ids = new StringBuilder();
                for (Office office1 : deputyList) {
                    ids.append("'" + office1.getPrimaryPerson().getId() + "',");
                }
                for (String usrId : userIdsReduce) {
                    ids.append("'" + usrId + "',");
                }
                    for (String where : StringUtils.split(userWheres, ",")) {
                        sqlNoUser.append(" NOT EXISTS (SELECT 1 FROM sys_user");
                        sqlNoUser.append(" WHERE id  in(" + ids.substring(0, ids.length() - 1) + ")");
                        sqlNoUser.append(" AND " + where + ")");
                    }
                    if (StringUtils.isNotBlank(sqlNoUser.toString())) {
                        StringBuilder sqlTmp = new StringBuilder();
                        sqlTmp.append(" AND (");
                        sqlTmp.append(sqlNoUser);
                        sqlTmp.append(" ) ");
                        sqlnoString.append(sqlTmp);
                    }

                }
                StringBuilder sqlNoDept = new StringBuilder();
                StringBuilder deptids = new StringBuilder();
                if(deptIdsReduce.size()>0)
                {
                    for (String deptId : deptIdsReduce) {
                        deptids.append("'" + deptId + "',");
                    }
                    // 生成人权限SQL语句
                    for (String where : StringUtils.split(officeWheres, ",")) {
                        sqlNoDept.append(" NOT EXISTS (SELECT 1 FROM sys_office");
                        sqlNoDept.append(" WHERE id  in(" + deptids.substring(0, deptids.length() - 1) + ")");
                        sqlNoDept.append(" AND " + where + ")");
                    }
                    if (StringUtils.isNotBlank(sqlNoDept.toString())) {
                        StringBuilder sqlTmp = new StringBuilder();
                        sqlTmp.append(" AND (");
                        sqlTmp.append(sqlNoDept);
                        sqlTmp.append(" ) ");
                        sqlnoString.append(sqlTmp);
                    }

                }

            }
        }

        for (String where : StringUtils.split(officeWheres, ",")) {
            StringBuilder ids = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            StringBuilder noChildDeptStrihng = new StringBuilder();
            for (String id : deptsChildIdsReduce) {
                Office office = officeDao.get(id);
                if (office != null && office.getId() != null) {
                    ids.append("'" + id + "',");
                    sql.append(" OR parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");
                }

            }

            if(ids.length()>0){
                noChildDeptStrihng.append(" NOT EXISTS (SELECT 1 FROM SYS_OFFICE");
                noChildDeptStrihng.append(" WHERE (id in(" + ids.substring(0, ids.length() - 1) + ")");
                noChildDeptStrihng.append( sql.toString() + ")");
                noChildDeptStrihng.append(" AND " + where + ")  ");
            }

            if (StringUtils.isNotBlank(noChildDeptStrihng.toString())) {
                StringBuilder sqlTmp = new StringBuilder();
                sqlTmp.append(" AND (");
                sqlTmp.append(noChildDeptStrihng);
                sqlTmp.append(" ) ");
                sqlnoString.append(sqlTmp);
            }
        }

//		System.out.println("dataScopeFilter: " + sqlString.toString());
        if (StringUtils.isNotBlank(sqlString.toString())) {
            StringBuilder sql = new StringBuilder(" AND ( " + sqlString.substring(4) + " ) ");
            sqlString = sql;
            sqlString.append(sqlnoString);
        }
        // 设置到自定义SQL对象
        return sqlString.toString();

    }

    //获取用户当前权限转为描述语言 zgq 2023/09/12 17:56:26
    public static String dataScopeFilterToDescribe(User user, String moduleCode, String menuCode) {

        /*// 如果是超级管理员，则不过滤数据
        if (user.isAdmin()) {
            return "全部门";
        }*/

        // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
        StringBuilder sqlString = new StringBuilder();

        Map<String, List<String>> scopeMap = getUserDataScopeForHremployee(user, menuCode, moduleCode);
        List<String> deptIds = scopeMap.get("deptIds");//配置了指定的部门不包含下级的部门ids
        List<String> deptsChildIds = scopeMap.get("deptsChildIds");//配置了指定的部门并包含下级的部门ids

        List<String> userIds = scopeMap.get("userIds");//专门配置的要看到的员工ids
        //减掉权限的员工
        List<String> userIdsReduce = scopeMap.get("userIdsReduce");//专门配置的不要看到的员工ids
        List<String> deptsChildIdsReduce = scopeMap.get("deptsChildIdsReduce");

        //部门权限
        /** 获取所有`增`的部门，包含`增`的下级部门，然后减去 `减`的部门，以及`减`的下级部门  todo */
        List<String> hasChildsDeptList = Lists.newArrayList();

        Office officeVo = new Office();
        for (String deptsChildId : deptsChildIds) {
            officeVo.setId(deptsChildId);
            List<Office> officeListWithPowerAPP = officeDao.getOfficeListWithPowerAPP(officeVo);
            for (Office office : officeListWithPowerAPP) {
                if(!hasChildsDeptList.contains(office.getId())){
                    hasChildsDeptList.add(office.getId());
                }
            }
        }

        for (String deptId : deptIds) {
            if(!hasChildsDeptList.contains(deptId)){
                hasChildsDeptList.add(deptId);
            }
        }

        List<String> allReduce = Lists.newArrayList();

        for (String reduce : deptsChildIdsReduce) {
            officeVo.setId(reduce);
            List<Office> officeListWithPowerAPP = officeDao.getOfficeListWithPowerAPP(officeVo);
            for (Office office : officeListWithPowerAPP) {
                if(!allReduce.contains(office.getId())){
                    allReduce.add(office.getId());
                }
            }
        }

        Iterator<String> iterator = hasChildsDeptList.iterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            if(allReduce.contains(next)){
                iterator.remove();
            }
        }

        for (String officeId : hasChildsDeptList) {
            sqlString.append(officeDao.get(officeId).getName());
            sqlString.append(",");
        }

        String message = sqlString.toString();

        // 设置到自定义SQL对象
        return message.substring(0,message.lastIndexOf(","));

    }


    /**
     * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）(报销查询重载)
     *
     * @param entity       当前过滤的实体类
     * @param sqlMapKey    sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
     * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
     * @param userWheres   user表条件，组成：用户表字段=业务表的用户字段
     * @example dataScopeFilter(user, " dsf ", " id = a.office_id ", " id = a.create_by ");
     * dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
     */
    public static String dataScopeFilterUseExist(User user, String officeWheres, String userWheres, String moduleCode, String menuCode, Boolean isRbs) {

        // 如果是超级管理员，则不过滤数据
        if (user.isAdmin()) {
            return "";
        }

        // 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
        StringBuilder sqlString = new StringBuilder();

        Map<String, List<String>> scopeMap = getUserDataScopeForRbs(user, menuCode, moduleCode);
        List<String> userIds = scopeMap.get("userIds");
        List<String> deptIds = scopeMap.get("deptIds");
        List<String> deptsChildIds = scopeMap.get("deptsChildIds");

        //生成部门权限SQL语句
        for (String where : StringUtils.split(officeWheres, ",")) {
            StringBuilder ids = new StringBuilder();
            StringBuilder sql = new StringBuilder();
            for (String id : deptIds) {
                ids.append("'" + id + "',");
            }
            for (String id : deptsChildIds) {
                Office office = officeDao.get(id);
                if (office != null && office.getId() != null) {

                    ids.append("'" + id + "',");
                    sql.append(" OR parent_ids LIKE '" + office.getParentIds() + office.getId() + ",%'");


                }

            }
            if (ids.length() > 0) {
                sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
                sqlString.append(" WHERE (id in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlString.append(sql.toString() + ")");
                sqlString.append(" AND " + where + ")");
            }
        }

        if (!CollectionUtils.isEmpty(userIds)) {
            StringBuilder sqlUser = new StringBuilder();
            StringBuilder ids = new StringBuilder();
            for (String id : userIds) {
                ids.append("'" + id + "',");
            }

            // 生成人权限SQL语句
            for (String where : StringUtils.split(userWheres, ",")) {
                sqlUser.append(" OR EXISTS (SELECT 1 FROM sys_user");
                sqlUser.append(" WHERE id  in(" + ids.substring(0, ids.length() - 1) + ")");
                sqlUser.append(" AND " + where + ")");

            }
            if (StringUtils.isNotBlank(sqlUser.toString())) {
                StringBuilder sqlTmp = new StringBuilder();
                sqlTmp.append(" OR (");
                sqlTmp.append(sqlUser.substring(4));
                sqlTmp.append(" ) ");
                sqlString.append(sqlTmp);
            }
        }

//		System.out.println("dataScopeFilter: " + sqlString.toString());
        if (StringUtils.isNotBlank(sqlString.toString())) {
            StringBuilder sql = new StringBuilder(" AND ( " + sqlString.substring(4) + " ) ");
            sqlString = sql;
        }
        // 设置到自定义SQL对象
        return sqlString.toString();

    }


    /***
     * 获取用户主管部门
     * @Author caoqishun
     * @Date 2018年6月14日下午4:15:54
     * @return
     */
    public static List<Office> getUserMngOffice(User user) {
        return officeDao.findUserOfficeList(user);
    }


    /**
     * 根据数据范围类型划分数组
     *
     * @param dataScope
     * @param userIds
     * @param deptIds
     * @param deptsChildIds
     * @Author caoqishun
     * @Date 2018年6月14日下午7:44:06
     */
    public static void dealDataScope(User user, SysDataScope dataScope, List<String> userIds,
									 List<String> deptIds, List<String> deptsChildIds) {
        if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_ALL)) {
            userIds.add(0, DictConstants.DATA_SCOPE_VALUE_ALL);
            deptIds.add(0, DictConstants.DATA_SCOPE_VALUE_ALL);
            deptsChildIds.add(0, DictConstants.DATA_SCOPE_VALUE_ALL);
        }
        //人员
        else if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_PERSON)) {
            userIds.addAll(Arrays.asList(dataScope.getScopeValue().split(",")));
        }
        //个性化部门
        else if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_DEPT)) {
            deptIds.addAll(Arrays.asList(dataScope.getScopeValue().split(",")));
        }
        //个性化部门及其下属部门
        else if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_DEPTANDCHILD)) {
            deptsChildIds.addAll(Arrays.asList(dataScope.getScopeValue().split(",")));
        }
        //所在部门及其下属部门
        else if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_OFFICE_AND_CHILD)) {
            deptsChildIds.addAll(Arrays.asList(dataScope.getScopeValue().split(",")));
        }
        //所在部门
        else if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_OFFICE)) {
            deptIds.addAll(Arrays.asList(dataScope.getScopeValue().split(",")));
        }
        //个人数据
        else if (StringUtils.equals(dataScope.getScopeType(), DictConstants.DATA_SCOPE_TYPE_SELF)) {
            userIds.addAll(Arrays.asList(dataScope.getScopeValue().split(",")));
        } else {
            return;
        }
        return;
    }

    public static void spiltUserDataScope(User user, List<SysDataScope> userDataScopeList, String menuCode,
										  String moduleCode, List<String> userIds,
										  List<String> deptIds, List<String> deptsChildIds) {
        for (SysDataScope dataScope : userDataScopeList) {
            //模块+菜单
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                dealDataScope(user, dataScope, userIds, deptIds, deptsChildIds);
            }
        }


    }


    /**
     * 返回用户数据权限范围（人事信息查询重载）
     *
     * @param user
     * @return
     * @Author caoqishun
     * @Date 2018年6月14日下午6:12:38
     */
    public static Map<String, List<String>> getUserDataScopeForHremployee(User user, String menuCode, String moduleCode) {
        //输出数据范围
        Map<String, List<String>> scopeMap = new HashMap<String, List<String>>();
        List<SysDataScope> userDataScopeList = UserUtils2.getDataScope(user);
        if (CollectionUtils.isEmpty(userDataScopeList)) {
            //return null;
        }

        List<String> userIds = Lists.newArrayList();
        List<String> deptIds = Lists.newArrayList();
        List<String> deptsChildIds = Lists.newArrayList();
        for (SysDataScope dataScope : userDataScopeList) {
            if (StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode)
                    && StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, menuCode)) {
                for (SysDataScope dataScope1 : userDataScopeList) {
                    dealDataScope(user, dataScope1, userIds, deptIds, deptsChildIds);
                }
            }

            //模块+菜单
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeList, menuCode, moduleCode,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //针对菜单
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeList, menuCode, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //模块权限
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeList, DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //所有
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeList, DictConstants.DATA_SCOPE_VALUE_ALL, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }

        }
        //排除的
        //取减的范围、删除以增加的；
        List<SysDataScope> userDataScopeReduceList = UserUtils2.getReduceDataScope(user);
        List<String> userIdsReduce = Lists.newArrayList();
        List<String> deptIdsReduce = Lists.newArrayList();
        List<String> deptsChildIdsReduce = Lists.newArrayList();
        for (SysDataScope dataScope : userDataScopeReduceList) {
            if (StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode)
                    && StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, menuCode)) {
                for (SysDataScope dataScope1 : userDataScopeReduceList) {
                    dealDataScope(user, dataScope1, userIdsReduce, deptIdsReduce, deptsChildIdsReduce);
                }
            }
            //模块+菜单
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeReduceList, menuCode, moduleCode,
                        userIdsReduce, deptIdsReduce, deptsChildIdsReduce);
                break;
            }
            //针对菜单
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeReduceList, menuCode, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIdsReduce, deptIdsReduce, deptsChildIdsReduce);
                break;
            }
            //模块权限
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeReduceList, DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode,
                        userIdsReduce, deptIdsReduce, deptsChildIdsReduce);
                break;
            }
            //所有
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeReduceList, DictConstants.DATA_SCOPE_VALUE_ALL, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIdsReduce, deptIds, deptsChildIds);
                break;
            }
        }

        //获取主管部门
        List<Office> userMngOffice = getUserMngOffice(user);
        //增加主管部门数据
        if (!CollectionUtils.isEmpty(userMngOffice)) {
            for (Office office : userMngOffice) {
                deptsChildIds.add(office.getId());
            }
        }
		/*//如果含有部门主管角色，则用户所在部门作为主管部门
		if (chkUserHaveDeptMngRole(user)){

			if(null != user.getOffice())
			{
				deptsChildIds.add(user.getOffice().getId());
			}
			if(StringUtils.isNotEmpty(user.getOfficeId()))
			{
				deptsChildIds.add(user.getOfficeId());
			}
		}*/
        //获取用户角色的部门数据
	    /*  List<Office> userOffList = new ArrayList<Office>();
		if(null != UserUtils.getUser()){
			userOffList = UserUtils.getOfficeList();
		}
		List<String> userRoleOffList = new ArrayList<String>();
		for(Office off : userOffList){
			userRoleOffList.add(off.getId());
		} */
        //添加当前用户
        userIds.add(user.getId());
        //按照用户获取
        HashSet userSet = new HashSet(userIds);
        userIds.clear();
        userIds.addAll(userSet);
        scopeMap.put("userIds", userIds);
        HashSet deptSet = new HashSet(deptIds);
        //deptSet.addAll(userRoleOffList);
        deptIds.clear();
        deptIds.addAll(deptSet);
        //删除去掉的元素
        deptIds = delDataList(deptIds, deptIdsReduce);
        //取当前部门
        scopeMap.put("deptIds", deptIds);

        //取部门及其下属部门
        HashSet deptsChildSet = new HashSet(deptsChildIds);
        deptsChildIds.clear();
        deptsChildIds.addAll(deptsChildSet);
        //删除去掉的元素
        deptsChildIds = delDataList(deptsChildIds, deptsChildIdsReduce);
        scopeMap.put("deptsChildIds", deptsChildIds);
        scopeMap.put("userIdsReduce",userIdsReduce);
        scopeMap.put("deptIdsReduce",deptIdsReduce);
        scopeMap.put("deptsChildIdsReduce",deptsChildIdsReduce);
        return scopeMap;
    }

    /**
     * 返回用户数据权限范围 (报销查询重载)
     *
     * @param user
     * @return
     * @Author shuo
     * @Date 2021年6月14日下午6:12:38
     */
    public static Map<String, List<String>> getUserDataScopeForRbs(User user, String menuCode, String moduleCode) {
        //输出数据范围
        Map<String, List<String>> scopeMap = new HashMap<String, List<String>>();
        List<SysDataScope> userDataScopeList = UserUtils2.getDataScope(user);
        if (CollectionUtils.isEmpty(userDataScopeList)) {
            //return null;
        }

        List<String> userIds = Lists.newArrayList();
        List<String> deptIds = Lists.newArrayList();
        List<String> deptsChildIds = Lists.newArrayList();
        for (SysDataScope dataScope : userDataScopeList) {
            if (StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode)
                    && StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, menuCode)) {
                for (SysDataScope dataScope1 : userDataScopeList) {
                    dealDataScope(user, dataScope1, userIds, deptIds, deptsChildIds);
                }
            }

            //模块+菜单
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeList, menuCode, moduleCode,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //针对菜单
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeList, menuCode, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //模块权限
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeList, DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //所有
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeList, DictConstants.DATA_SCOPE_VALUE_ALL, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }

        }

        //获取主管部门
        List<Office> userMngOffice = getUserMngOffice(user);
        //增加主管部门数据
        if (!CollectionUtils.isEmpty(userMngOffice)) {
            for (Office office : userMngOffice) {
                deptsChildIds.add(office.getId());
            }
        }
        //如果含有部门主管角色，则用户所在部门作为主管部门
        if (chkUserHaveDeptMngRole(user)) {

            if (null != user.getOffice()) {
                deptsChildIds.add(user.getOffice().getId());
            }
            if (StringUtils.isNotEmpty(user.getOfficeId())) {
                deptsChildIds.add(user.getOfficeId());
            }
        }
        //获取用户角色的部门数据
        List<Office> userOffList = new ArrayList<Office>();
        if (null != UserUtils.getUser()) {
            userOffList = UserUtils.getOfficeList();
        }
        List<String> userRoleOffList = new ArrayList<String>();
        for (Office off : userOffList) {
            userRoleOffList.add(off.getId());
        }
        //添加当前用户
        userIds.add(user.getId());
        //按照用户获取
        HashSet userSet = new HashSet(userIds);
        userIds.clear();
        userIds.addAll(userSet);
        scopeMap.put("userIds", userIds);
        HashSet deptSet = new HashSet(deptIds);
        deptSet.addAll(userRoleOffList);
        deptIds.clear();
        deptIds.addAll(deptSet);
        //取当前部门
        scopeMap.put("deptIds", deptIds);

        //取部门及其下属部门
        HashSet deptsChildSet = new HashSet(deptsChildIds);
        deptsChildIds.clear();
        deptsChildIds.addAll(deptsChildSet);
        scopeMap.put("deptsChildIds", deptsChildIds);
        return scopeMap;
    }


    /**
     * 返回用户数据权限范围
     *
     * @param user
     * @return
     * @Author caoqishun
     * @Date 2018年6月14日下午6:12:38
     */
    public static Map<String, List<String>> getUserDataScope(User user, String menuCode, String moduleCode) {
        //输出数据范围
        Map<String, List<String>> scopeMap = new HashMap<String, List<String>>();
        //增加的范围
        List<SysDataScope> userDataScopeList = UserUtils2.getDataScope(user);
        if (CollectionUtils.isEmpty(userDataScopeList)) {
            //return null;
        }

        List<String> userIds = Lists.newArrayList();
        List<String> deptIds = Lists.newArrayList();
        List<String> deptsChildIds = Lists.newArrayList();
        for (SysDataScope dataScope : userDataScopeList) {
            if (StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode)
                    && StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, menuCode)) {
                for (SysDataScope dataScope1 : userDataScopeList) {
                    dealDataScope(user, dataScope1, userIds, deptIds, deptsChildIds);
                }
            }

            //模块+菜单
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeList, menuCode, moduleCode,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //针对菜单
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeList, menuCode, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //模块权限
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeList, DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode,
                        userIds, deptIds, deptsChildIds);
                break;
            }

            //所有
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeList, DictConstants.DATA_SCOPE_VALUE_ALL, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }

        }

        //获取主管部门
        List<Office> userMngOffice = getUserMngOffice(user);
        //增加主管部门数据
        if (!CollectionUtils.isEmpty(userMngOffice)) {
            for (Office office : userMngOffice) {
                deptsChildIds.add(office.getId());
            }
        }
        //如果含有部门主管角色，则用户所在部门作为主管部门
        if (chkUserHaveDeptMngRole(user)) {

            if (null != user.getOffice()) {
                deptsChildIds.add(user.getOffice().getId());
            }
            if (StringUtils.isNotEmpty(user.getOfficeId())) {
                deptsChildIds.add(user.getOfficeId());
            }
        }
        //获取用户角色的部门数据
	/*  List<Office> userOffList = new ArrayList<Office>();
		if(null != UserUtils.getUser()){
			userOffList = UserUtils.getOfficeList();
		}
		List<String> userRoleOffList = new ArrayList<String>();
		for(Office off : userOffList){
			userRoleOffList.add(off.getId());
		} */
        //取减的范围、删除以增加的；
        List<SysDataScope> userDataScopeReduceList = UserUtils2.getReduceDataScope(user);

        List<String> deptIdsReduce = Lists.newArrayList();
        List<String> deptsChildIdsReduce = Lists.newArrayList();
        for (SysDataScope dataScope : userDataScopeReduceList) {
            if (StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode)
                    && StringUtils.equals(DictConstants.DATA_SCOPE_VALUE_ALL, menuCode)) {
                for (SysDataScope dataScope1 : userDataScopeReduceList) {
                    dealDataScope(user, dataScope1, userIds, deptIdsReduce, deptsChildIdsReduce);
                }
            }
            //模块+菜单
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeReduceList, menuCode, moduleCode,
                        userIds, deptIdsReduce, deptsChildIdsReduce);
                break;
            }
            //针对菜单
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), menuCode)) {
                spiltUserDataScope(user, userDataScopeReduceList, menuCode, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIdsReduce, deptsChildIdsReduce);
                break;
            }
            //模块权限
            if (StringUtils.equals(dataScope.getModuleCode(), moduleCode)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeReduceList, DictConstants.DATA_SCOPE_VALUE_ALL, moduleCode,
                        userIds, deptIdsReduce, deptsChildIdsReduce);
                break;
            }
            //所有
            if (StringUtils.equals(dataScope.getModuleCode(), DictConstants.DATA_SCOPE_VALUE_ALL)
                    && StringUtils.equals(dataScope.getMenuCode(), DictConstants.DATA_SCOPE_VALUE_ALL)) {
                spiltUserDataScope(user, userDataScopeReduceList, DictConstants.DATA_SCOPE_VALUE_ALL, DictConstants.DATA_SCOPE_VALUE_ALL,
                        userIds, deptIds, deptsChildIds);
                break;
            }
        }
        //添加当前用户
        userIds.add(user.getId());
        //按照用户获取
        HashSet userSet = new HashSet(userIds);
        userIds.clear();
        userIds.addAll(userSet);
        scopeMap.put("userIds", userIds);
        HashSet deptSet = new HashSet(deptIds);
        //deptSet.addAll(userRoleOffList);
        deptIds.clear();
        deptIds.addAll(deptSet);
        //删除去掉的元素
        deptIds = delDataList(deptIds, deptIdsReduce);
        //取当前部门
        scopeMap.put("deptIds", deptIds);

        //取部门及其下属部门
        HashSet deptsChildSet = new HashSet(deptsChildIds);
        deptsChildIds.clear();
        deptsChildIds.addAll(deptsChildSet);
        //删除去掉的元素
        deptsChildIds = delDataList(deptsChildIds, deptsChildIdsReduce);

        scopeMap.put("deptsChildIds", deptsChildIds);

        return scopeMap;
    }

    public static List<String> delDataList(List<String> sourceList, List<String> targetList) {
        Iterator<String> it = sourceList.iterator();
        while (it.hasNext()) {
            String str = it.next();
            if (targetList.contains(str)) {
                it.remove();
            }
        }
        return sourceList;
    }

    public static boolean chkUserHaveDeptMngRole(User user) {
        List<Role> roleList = UserUtils2.getRoleListV2();
        if (null == roleList) {
            return false;
        }
        /**fea9b5bb76d14b6ba6367859c3f28429*/
        for (Role role : roleList) {
            if (StringUtils.equals(role.getId(), "fea9b5bb76d14b6ba6367859c3f28429")
                    || StringUtils.equals(role.getEnname(), "deptadmin")) {
                return true;
            }
        }
        return false;
    }

}
