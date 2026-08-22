/* Copyright 2026 上海如静知华信息科技有限公司 · https://www.zhuatech.cn/ */
package cn.zhuatech.bidagent.controller;
import cn.zhuatech.bidagent.agent.AgentRuntime;
import cn.zhuatech.bidagent.common.ApiResponse;
import cn.zhuatech.bidagent.dto.BidAgentDto.*;
import cn.zhuatech.bidagent.service.BidAgentService;
import cn.zhuatech.bidagent.service.BidProposalGuardService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/shopfloor") @PreAuthorize("hasAnyRole('DOMAIN_USER','ADMIN')")
public class WorkspaceController {
 private final BidAgentService service; private final AgentRuntime runtime; private final BidProposalGuardService domainAgent;
 public WorkspaceController(BidAgentService service,AgentRuntime runtime,BidProposalGuardService domainAgent){this.service=service;this.runtime=runtime;this.domainAgent=domainAgent;}
 @GetMapping("/dashboard") public ApiResponse<Dashboard> dashboard(){return ApiResponse.ok(service.shopfloorDashboard());}
 @PostMapping("/work-orders/{id}/reports") public ApiResponse<ReportResult> report(@PathVariable Long id,@Valid @RequestBody ReportRequest request){return ApiResponse.ok("反馈提交成功",service.report(id,request));}
 @PostMapping("/agent-preview") public ApiResponse<AgentRuntime.AgentResult> preview(@RequestBody Map<String,String> body){return ApiResponse.ok(runtime.run(new AgentRuntime.AgentRequest(body.getOrDefault("objective","整理招标要求并形成应答草案"),Map.of("mode","demo","approval","required"))));}
 @PostMapping("/proposal-guard") public ApiResponse<BidProposalGuardService.ProposalDecision> domainAction(@Valid @RequestBody BidProposalGuardService.ProposalRequest request){return ApiResponse.ok("投标应答风险检查完成",domainAgent.inspect(request));}
}
