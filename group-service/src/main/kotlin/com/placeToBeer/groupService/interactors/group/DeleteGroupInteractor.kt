package com.placeToBeer.groupService.interactors.group

import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DeleteGroupInteractor(private var groupRepository: GroupRepository) {

    private var logger: Logger = LoggerFactory.getLogger(DeleteGroupInteractor::class.java)

    fun execute(groupId: Long) {
        if(groupRepository.existsById(groupId)){
            groupRepository.deleteById(groupId)
        } else {
            throwGroupNotFoundError(groupId)
        }
    }

    private fun throwGroupNotFoundError(groupId: Long) {
        logger.error("No group with groupId $groupId found")
        throw GroupNotFoundException(groupId)
    }
}