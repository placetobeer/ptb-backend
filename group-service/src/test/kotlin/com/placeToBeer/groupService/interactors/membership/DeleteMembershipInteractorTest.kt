package com.placeToBeer.groupService.interactors.membership

import com.nhaarman.mockitokotlin2.*
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.gateways.MembershipRepository
import org.assertj.core.api.Assertions.*
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception

internal class DeleteMembershipInteractorTest{

    private val membershipRepository: MembershipRepository = mock()

    private val deleteMembershipInteractor = DeleteMembershipInteractor(membershipRepository)

    private val validMembershipId = 1L
    private val invalidMembershipId = 2L

    var exception: Exception? = null

    init {
        whenever(membershipRepository.existsById(validMembershipId)).thenReturn(true)
        whenever(membershipRepository.existsById(invalidMembershipId)).thenReturn(false)
    }

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun whenExecuteWithExistingMembership_ThenNoError() {
        doExecute(validMembershipId)
        assertThat(exception).isNull()
        verify(membershipRepository, times(1)).deleteById(validMembershipId)
    }

    @Test
    fun whenExecuteWithNonExistingMembership_ThenError() {
        doExecute(invalidMembershipId)
        assertThat(exception).isInstanceOf(MembershipNotFoundException::class.java)
        verify(membershipRepository, never()).deleteById(any())
    }


    private fun doExecute(membershipId: Long){
        try {
            deleteMembershipInteractor.execute(membershipId)
        } catch (e: Exception) {
            exception = e
        }
    }
}