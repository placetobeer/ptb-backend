package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.services.GroupService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class GroupExistValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(GroupExistValidatorPlugin::class.java)

    fun validateAndReturn(group: Optional<Group>, groupId: Long): Group {
        if(group.isEmpty){
            throwGroupNotFoundError(groupId)
        }
        return group.get()
    }

    private fun throwGroupNotFoundError(groupId: Long) {
        logger.error("No group with groupId $groupId found")
        throw GroupNotFoundException(groupId)
    }

}