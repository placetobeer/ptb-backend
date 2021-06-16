package com.placeToBeer.groupService.interactors.proposal

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Proposal
import com.placeToBeer.groupService.entities.requests.ProposalRequest
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.ProposalRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class AddProposalInteractor(private var proposalRepository: ProposalRepository, private var groupRepository: GroupRepository,
                            private var groupExistValidatorPlugin: GroupExistValidatorPlugin) {

    fun execute(proposalRequest: ProposalRequest): Proposal {
        val group = getGroupById(proposalRequest.groupId)
        return createAndSafeProposal(proposalRequest, group)
    }

    private fun createAndSafeProposal(proposalRequest: ProposalRequest, group: Group): Proposal {
        val proposal = Proposal(proposalRequest, group)
        return proposalRepository.save(proposal)
    }

    private fun getGroupById(groupId: Long): Group {
        val possibleGroup = groupRepository.findById(groupId)
        return groupExistValidatorPlugin.validateAndReturn(possibleGroup, groupId)
    }

}