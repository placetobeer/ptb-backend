package com.placeToBeer.groupService.restPresenters

import com.placeToBeer.groupService.entities.Proposal
import com.placeToBeer.groupService.entities.requests.ProposalRequest
import com.placeToBeer.groupService.services.ProposalService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@Api(description = "Operations directly related to proposals")
@RequestMapping("/proposals")
class ProposalController (private val proposalService: ProposalService) {

    @ApiOperation(value = "View list of proposals that refer to a group")
    @GetMapping(value = [""], params = ["groupId"], produces = ["application/json;charset=UTF-8"])
    fun getProposalListByGroupId(@RequestParam groupId: Long): List<Proposal> {
        return proposalService.getProposalListByGroupId(groupId)
    }

    @ApiOperation("Create proposal")
    @PostMapping(value = [""], consumes = ["application/json;charset=UTF-8"], produces = ["application/json;charset=UTF-8"])
    fun createProposal(@RequestBody proposalRequest: ProposalRequest): Proposal {
        return proposalService.createProposal(proposalRequest)
    }
}