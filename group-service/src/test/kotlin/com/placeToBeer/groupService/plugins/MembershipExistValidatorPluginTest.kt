package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*

internal class MembershipExistValidatorPluginTest() {

    private val membershipExistValidatorPlugin = MembershipExistValidatorPlugin()

    private var exception: Exception? = null

    private val validMembershipId = 1L
    private val validMembership = Membership(validMembershipId, Group(), User(), Role.MEMBER)

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateAndReturnWithExistingMembership_ThenReturnMembership() {
        val membership = doValidateAndReturn(Optional.of(validMembership), validMembershipId)
        assertThat(exception).isNull()
        assertThat(membership).isEqualTo(validMembership)
    }

    @Test
    fun validateAndReturnWithEmptyMembership_ThenReturnMembership() {
        val membership = doValidateAndReturn(Optional.empty(), validMembershipId)
        assertThat(exception).isInstanceOf(MembershipNotFoundException::class.java)
        assertThat(membership).isNull()
    }

    private fun doValidateAndReturn(membership: Optional<Membership>, membershipId: Long): Membership? {
        var realMembership: Membership? = null
        try {
            realMembership = membershipExistValidatorPlugin.validateAndReturn(membership, membershipId)
        } catch (exception: Exception){
            this.exception = exception
        }
        return realMembership
    }
}