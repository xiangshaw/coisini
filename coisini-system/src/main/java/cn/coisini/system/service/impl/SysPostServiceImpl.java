package cn.coisini.system.service.impl;

import cn.coisini.model.system.pojo.SysPost;
import cn.coisini.system.mapper.SysPostMapper;
import cn.coisini.system.service.SysPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @Author: xiaoxiang
 * @Description: 岗位 服务实现类
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements SysPostService {
}
