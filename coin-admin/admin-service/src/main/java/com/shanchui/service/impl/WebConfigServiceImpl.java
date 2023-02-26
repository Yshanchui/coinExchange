package com.shanchui.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.domain.WebConfig;
import com.shanchui.mapper.WebConfigMapper;
import com.shanchui.service.WebConfigService;
@Service
public class WebConfigServiceImpl extends ServiceImpl<WebConfigMapper, WebConfig> implements WebConfigService{

}
