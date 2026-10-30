package com.auca.to_do_list.service;

import com.auca.to_do_list.model.TaskStatus;
import com.auca.to_do_list.repository.DeskRepository;
import com.auca.to_do_list.repository.MemberRepository;
import com.auca.to_do_list.repository.ProjectRepository;
import com.auca.to_do_list.repository.TaskRepository;
import com.auca.to_do_list.repository.WorkLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DeskRepository deskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private WorkLogRepository workLogRepository;

    public Map<String, Long> getSummary() {
        Map<String, Long> summary = new LinkedHashMap<>();
        summary.put("projects", projectRepository.count());
        summary.put("members", memberRepository.count());
        summary.put("freeDesks", (long) deskRepository.findFreeDesks().size());
        summary.put("tasksDueToday", taskRepository.countByDueDate(LocalDate.now()));
        summary.put("todo", taskRepository.countByStatus(TaskStatus.TODO));
        summary.put("workLogs", workLogRepository.count());
        return summary;
    }
}