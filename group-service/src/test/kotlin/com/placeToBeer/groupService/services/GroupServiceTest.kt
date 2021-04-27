package com.placeToBeer.groupService.services

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.GroupRepository
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.AdditionalAnswers
import java.lang.Exception
import java.util.*
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.ArgumentCaptor
import org.mockito.Mockito

import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import java.lang.reflect.Member
import kotlin.test.assertNull


internal class GroupServiceTest {


    private val userId = 1L
    private val wrongUserId = 2L
    private val user = User()
    private var expectedGroupList: List<Group> = emptyList()
    private var expectedNewGroup: Group? = null

    private var exception: Exception? = null

    init {
        this.user.id = userId

        val group1 = Group()
        val group2 = Group()
        expectedGroupList = listOf(group1, group2)

        val membership1 = Membership()
        membership1.group = group1
        membership1.member = user
        val membership2 = Membership()
        membership2.group = group2
        membership2.member = user

        expectedNewGroup = Group(userId, "Testgruppe")
    }

    @BeforeEach
    fun init(){
        exception = null
    }












}