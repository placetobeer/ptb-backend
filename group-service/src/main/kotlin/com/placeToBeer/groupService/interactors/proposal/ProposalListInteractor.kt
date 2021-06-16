package com.placeToBeer.groupService.interactors.proposal

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Proposal
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.ProposalRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.ProposalExistValidatorPlugin
import org.springframework.stereotype.Component

@Component
class ProposalListInteractor(private var proposalRepository: ProposalRepository, private var groupRepository: GroupRepository,
                             private var proposalExistValidatorPlugin: ProposalExistValidatorPlugin, private var groupExistValidatorPlugin: GroupExistValidatorPlugin) {

    fun execute(groupId: Long): List<Proposal> {
        val group = getGroupByGroupId(groupId)
        return proposalRepository.findAllByGroup(group)
    }

    private fun getGroupByGroupId(groupId: Long): Group {
        val possibleGroup = groupRepository.findById(groupId)
        return groupExistValidatorPlugin.validateAndReturn(possibleGroup, groupId)
    }

}