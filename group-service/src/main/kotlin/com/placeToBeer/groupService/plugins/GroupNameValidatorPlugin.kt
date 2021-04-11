package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.services.GroupService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class GroupNameValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(GroupNameValidatorPlugin::class.java)

    fun validate(groupName: String) {
        if(groupName.isNullOrEmpty()){
            throwGroupNameIsInvalidError(groupName)
        }
    }

    private fun throwGroupNameIsInvalidError(groupName: String) {
        logger.error("Group name $groupName is invalid")
        throw GroupNameIsInvalidException(groupName)
    }
}