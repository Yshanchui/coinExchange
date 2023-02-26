package com.shanchui.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanchui.domain.WorkIssue;
import com.shanchui.mapper.WorkIssueMapper;
import com.shanchui.service.WorkIssueService;
@Service
public class WorkIssueServiceImpl extends ServiceImpl<WorkIssueMapper, WorkIssue> implements WorkIssueService{

}
