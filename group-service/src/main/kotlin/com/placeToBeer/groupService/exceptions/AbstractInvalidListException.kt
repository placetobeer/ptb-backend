package com.placeToBeer.groupService.exceptions

import com.placeToBeer.groupService.entities.Invitation
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

abstract class AbstractInvalidListException(invalidListName: String, invalidListValues: MutableList<*>):
    RuntimeException("Could not create the list of $invalidListName $invalidListValues because of invalid values."
){}