package com.placeToBeer.groupService.interactors.membership

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.plugins.MembershipExistValidatorPlugin
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*


internal class SetRoleInteractorTest() {
    private val membershipRepository: MembershipRepository = mock()
    private val membershipExistValidatorPlugin: MembershipExistValidatorPlugin = mock()

    private val setRoleInteractor = SetRoleInteractor(membershipRepository,membershipExistValidatorPlugin)

    private val validMembershipId = 1L
    private val validMembership = Membership(validMembershipId, Group(), User(), Role.MEMBER)
    private val invalidMembershipId = 2L

    var exception: Exception? = null

    init {
        whenever(membershipRepository.findById(validMembershipId)).thenReturn(Optional.of(validMembership))
        whenever(membershipRepository.findById(invalidMembershipId)).thenReturn(Optional.empty())

        whenever(membershipExistValidatorPlugin.validateAndReturn(eq(Optional.of(validMembership)), any())).thenReturn(validMembership)
        whenever(membershipExistValidatorPlugin.validateAndReturn(eq(Optional.empty()), any())).thenThrow(MembershipNotFoundException::class.java)
    }

    @Test
    fun whenExecuteWithExistingMembership_ThenMembershipWasUpdated(){
        doExecute(validMembershipId, Role.ADMIN)
        assertThat(exception).isNull()
        val newMembership = validMembership.copy(role = Role.ADMIN)
        verify(membershipRepository, times(1)).save(newMembership)
    }

    @Test
    fun whenExecuteWithNonExistingMembership_ThenThrowError(){
        doExecute(invalidMembershipId, Role.MEMBER)
        assertThat(exception).isInstanceOf(MembershipNotFoundException::class.java)
        verify(membershipRepository, never()).save(any())
    }

    private fun doExecute(membershipId: Long, role: Role){
        try {
            setRoleInteractor.execute(membershipId, role)
        } catch (e: Exception) {
            exception = e
        }
    }
}