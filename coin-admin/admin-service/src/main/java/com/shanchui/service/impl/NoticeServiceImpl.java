package com.shanchui.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.mapper.NoticeMapper;
import com.shanchui.domain.Notice;
import com.shanchui.service.NoticeService;
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService{

}
