package com.placeToBeer.groupService.interactors.group

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.plugins.GroupExistValidatorPlugin
import com.placeToBeer.groupService.plugins.GroupNameValidatorPlugin
import org.springframework.stereotype.Component

@Component
class SetGroupNameInteractor(private var groupRepository: GroupRepository, private var groupNameValidatorPlugin: GroupNameValidatorPlugin,
                             private var groupExistValidatorPlugin: GroupExistValidatorPlugin){

    fun execute(groupId: Long, groupName: String) {
        groupNameValidatorPlugin.validate(groupName)
        val group = getGroupByGroupId(groupId)
        group.name = groupName
        groupRepository.save(group)
    }

    private fun getGroupByGroupId(groupId: Long): Group {
        val possibleGroup = groupRepository.findById(groupId)
        return groupExistValidatorPlugin.validateAndReturn(possibleGroup, groupId)
    }

}