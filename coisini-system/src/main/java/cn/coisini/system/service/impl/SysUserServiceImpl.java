package cn.coisini.system.service.impl;

import cn.coisini.common.exception.CoisiniException;
import cn.coisini.common.utils.PageUtils;
import cn.coisini.model.common.dto.Result;
import cn.coisini.model.common.enums.ResultEnum;
import cn.coisini.model.system.pojo.SysRole;
import cn.coisini.model.system.pojo.SysUser;
import cn.coisini.model.system.pojo.SysUserRole;
import cn.coisini.model.system.vo.AssginRoleVo;
import cn.coisini.model.system.vo.RouterVo;
import cn.coisini.model.system.vo.SysUserQueryVo;
import cn.coisini.system.mapper.SysRoleMapper;
import cn.coisini.system.mapper.SysUserMapper;
import cn.coisini.system.mapper.SysUserRoleMapper;
import cn.coisini.system.service.SysMenuService;
import cn.coisini.system.service.SysUserService;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: xiaoxiang
 * @Description: 用户表 服务实现类
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final SysMenuService menuService;

    public SysUserServiceImpl(SysRoleMapper roleMapper, SysUserRoleMapper userRoleMapper, SysMenuService menuService) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.menuService = menuService;
    }

    // 分页条件查询用户
    @Override
    public Result<SysUser> pagingQuery(SysUserQueryVo userQueryVo) {
        String keyword = userQueryVo.getKeyword();
        String createTimeBegin = userQueryVo.getCreateTimeBegin();
        String createTimeEnd = userQueryVo.getCreateTimeEnd();
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        // 根据用户名称、真实名称、手机号模糊查询，，，大于等于开始时间、小于等于创建时间
        if (CharSequenceUtil.isNotBlank(userQueryVo.getKeyword())) {
            wrapper.and(x -> x.like("username", keyword)
                    .or()
                    .like("name", keyword)
                    .or()
                    .like("phone", keyword));
        }
        if (CharSequenceUtil.isNotBlank(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (CharSequenceUtil.isNotBlank(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }
        wrapper.eq("del_flag", 0).orderByDesc("id");
        Page<SysUser> page = new Page<>(userQueryVo.getCurrent(), userQueryVo.getLimit());
        Page<SysUser> sysUserPage = page(page, wrapper);
        return Result.ok(new PageUtils(sysUserPage));
    }

    // 根据id获取用户
    @Override
    public Result<SysUser> getUserId(Long id) {
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        SysUser sysUser = getById(id);
        if (sysUser == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(sysUser);
    }

    // 保存用户
    @Override
    public Result<SysUser> saveUser(SysUser sysUser) {
        // 1.检查参数
        if (sysUser == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<SysUser> list = list(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUsername, sysUser.getUsername()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该用户名已经注册");
        }
        // 3.输入的密码进行BCryptPasswordEncoder加密
        sysUser.setPassword(new BCryptPasswordEncoder().encode(sysUser.getPassword()));
        boolean b = save(sysUser);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户保存失败");
    }

    // 修改用户
    @Override
    public Result<SysUser> updateUser(SysUser sysUser) {
        // 1.检查参数
        if (sysUser == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.修改并判断结果
        boolean b = updateById(sysUser);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户修改失败");
    }

    // 删除用户
    @Override
    public Result<SysUser> removeUser(Long id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前角色是否存在
        SysUser user = getById(id);
        if (user == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除并判断结果
        boolean b = removeById(id);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户删除失败");
    }

    // 修改用户状态（0正常 1禁用）
    @Override
    public Result<SysUser> updateStatus(Long id, Boolean status) {
        // 1.检查参数
        if (id == null && status == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据用户id查询
        SysUser user = getById(id);
        // 3.设置状态值
        user.setStatus(false);
        user.setUpdateTime(new Date());
        //  4.修改并判断结果
        boolean b = updateById(user);
        if (b) {
            Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户状态修改失败");
    }

    // 获取用户id获取角色数据
    @Override
    public Result<Map<String, Object>> getRolesByUserId(Long id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.获取所有角色
        List<SysRole> roleList = roleMapper.selectList(null);
        // 3.根据用户id查询已经分配的角色
        List<SysUserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", id));
        // 4.获取所有角色id
        List<Long> roleIds = userRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
        // 5.封装到map
        HashMap<String, Object> map = new HashMap<>();
        // 5.1 所有角色
        map.put("allRoles", roleList);
        // 5.2 用户分配角色id集合
        map.put("userRoleIds", roleIds);
        return Result.ok(map);
    }

    // 给用户分配角色
    @Override
    public Result<SysUserRole> doAssign(AssginRoleVo assginRoleVo) {
        // 1.检查参数
        if (assginRoleVo.getUserId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据用户id删除原来分配的角色
        userRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", assginRoleVo.getUserId()));
        // 3.获取所有角色id，添加角色用户关系表
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        roleIdList.forEach(roleId -> {
            SysUserRole userRoleEntity = new SysUserRole();
            userRoleEntity.setUserId(assginRoleVo.getUserId());
            userRoleEntity.setRoleId(roleId);
            userRoleMapper.insert(userRoleEntity);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 用户登录（交给Spring Security了）
    @Override
    public SysUser getUserInfoByName(String username) {
        if (username.equals("null")) {
            throw new CoisiniException(ResultEnum.PARAM_INVALID);
        }
        return baseMapper.selectOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    // 获取用户信息以及权限数据
    @Override
    public Map<String, Object> getUserInfo(Long userId, String username) {
        // 1.检查参数
        if (userId == null && StringUtils.isEmpty(username)) {
            throw new CoisiniException(ResultEnum.PARAM_INVALID);
        }
        // 2.根据用户id查询菜单权限值
        List<RouterVo> routerVoList = menuService.findUserMenuList(userId);
        HashMap<String, Object> map = new HashMap<>();
        // 3.根据用户id查询按钮权限值
        List<String> permsList = menuService.findUserButtonList(userId);
        map.put("name", username);
        map.put("avatar", "");
        // 菜单权限数据
        map.put("routers", routerVoList);
        // 按钮权限数据
        map.put("buttons", permsList);
        return map;
    }

    // 用户注册
    @Override
    public Result<SysUser> registerUser(SysUser sysUser) {
        if (StringUtils.isEmpty(sysUser.getUsername()) || StringUtils.isEmpty(sysUser.getPhone())) {
            return Result.error(ResultEnum.PARAM_REQUIRE, "用户名和手机号不能为空");
        } else {
            sysUser.setHeadUrl("");
            boolean b = save(sysUser);
            if (b) {
                return Result.ok(ResultEnum.SUCCESS);
            }
            return Result.error(ResultEnum.FAIL, "用户注册失败");
        }
    }
}

