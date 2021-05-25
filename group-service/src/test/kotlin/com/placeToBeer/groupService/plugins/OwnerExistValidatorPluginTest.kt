package com.placeToBeer.groupService.plugins

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.*
import com.placeToBeer.groupService.entities.requests.InvitationRequest
import com.placeToBeer.groupService.exceptions.GroupNameIsInvalidException
import com.placeToBeer.groupService.exceptions.MembershipNotFoundException
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.gateways.MembershipRepository
import com.placeToBeer.groupService.gateways.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.Exception
import java.util.*
import javax.swing.text.html.Option

internal class OwnerExistValidatorPluginTest {

    private val membershipExistValidatorPlugin: MembershipExistValidatorPlugin = mock()
    private val membershipRepository: MembershipRepository = mock()
    private val userExistValidatorPlugin: UserExistValidatorPlugin = mock()
    private val userRepository: UserRepository = mock()

    private val ownerExistValidatorPlugin = OwnerExistValidatorPlugin(
        membershipRepository, userRepository, userExistValidatorPlugin, membershipExistValidatorPlugin )

    private var exception: Exception? = null

    private val validGroup = Group(1, "Bratis Kartoffeln")
    private val validOwner = User(4, "Patrick")
    private val validInvitation = Invitation( "a.b@gmail.com", validOwner, validGroup, Role.ADMIN)
    private val validOwnerMembership = Membership(42, validGroup, validOwner, Role.OWNER)

    init {
        whenever(userRepository.findById(validInvitation.emitter.id!!)).thenReturn(Optional.of(validOwner))
        whenever(membershipRepository.findByMemberAndGroup(validOwner, validInvitation.group)).thenReturn(Optional.of(validOwnerMembership))
    }

    @BeforeEach
    fun setUp(){
        exception = null
    }

    @Test
    fun validateWithExistingOwner_ThenDoNothing() {
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validOwner), validInvitation.emitter.id!!)).thenReturn(validOwner)
        whenever(membershipExistValidatorPlugin.validateAndReturn(Optional.of(validOwnerMembership), validOwnerMembership.id!!)).thenReturn(validOwnerMembership)

        doValidate(validInvitation)
        Assertions.assertThat(exception).isNull()
    }

    @Test
    fun validateWithNotExistingUser_ThenThrowUserNotFoundException() {
        val invalidOwner = User(-1, "Patrick")
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(invalidOwner), validInvitation.emitter.id!!)).thenThrow(UserNotFoundException(-1))
        whenever(membershipExistValidatorPlugin.validateAndReturn(Optional.of(validOwnerMembership), validOwnerMembership.id!!)).thenReturn(validOwnerMembership)

        doValidate(validInvitation)
        Assertions.assertThat(exception).isInstanceOf(UserNotFoundException::class.java)
    }

    // TODO fix test -> invalidOwnerMembership must be empty
    @Test
    fun validateWithNotExistingMembership_ThenThrowMembershipNotFoundException() {
        val invalidOwnerMembership: Optional<Membership> = Optional.of(Membership())
        whenever(userExistValidatorPlugin.validateAndReturn(Optional.of(validOwner), validInvitation.emitter.id!!)).thenReturn(validOwner)
        whenever(membershipExistValidatorPlugin.validateAndReturn(invalidOwnerMembership, -1)).thenThrow(MembershipNotFoundException(40))

        doValidate(validInvitation)
        Assertions.assertThat(exception).isInstanceOf(MembershipNotFoundException::class.java)
    }

    private fun doValidate(invitation: Invitation){
        try {
            ownerExistValidatorPlugin.validate(invitation)
        } catch (exception: Exception){
            this.exception = exception
        }
    }
}