package cn.coisini.system.service.impl;

import cn.coisini.model.system.pojo.SysDept;
import cn.coisini.system.mapper.SysDeptMapper;
import cn.coisini.system.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaoxiang
 * @Description: 部门 服务实现类
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
}
