package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.InvitationResponse
import com.placeToBeer.groupService.exceptions.InvitationNotFoundException
import com.placeToBeer.groupService.services.InvitationService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@WebMvcTest(InvitationController::class)
class InvitationControllerIntegrationTest(
    @Autowired private var mockMvc: MockMvc,
    @Autowired var objectMapper: ObjectMapper) {

        @MockBean
        private lateinit var mockInvitationService: InvitationService

        private val validUserId: Long = 2
        private val invalidUserId: Long = 1
        private val invitationList = listOf(
            Invitation(1L,"bea@mail.com",User(1,"Bea", "bea@mail.com"),User(2,"Patrick","patrick@mail.com"), Group(1,"Club Crew"), Role.MEMBER)
        )
        private val validInvitationResponseList = listOf(
            InvitationResponse(Invitation(1L,"bea@mail.com",User(1,"Bea","bea@mail.com"),User(2,"Patrick","patrick@mail.com"), Group(1,"Club Crew"), Role.MEMBER))
        )

        @Test
        fun whenGetInvitationResponseListByUserId_withValidUserId_thenReturnHttp200(){
            whenever(mockInvitationService.getInvitationsListByUserId(validUserId)).thenReturn(validInvitationResponseList)
            mockMvc.perform(MockMvcRequestBuilders.get("/api/invitations/byUser")
                .param("userId","$validUserId"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        }

        @Test
        fun whenGetInvitationResponseListByUserId_withValidUserId_thenReturnValidAnswer(){
            whenever(mockInvitationService.getInvitationsListByUserId(validUserId)).thenReturn(validInvitationResponseList)

            mockMvc.perform(MockMvcRequestBuilders.get("/api/invitations/byUser")
                .param("userId","$validUserId"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(validInvitationResponseList)))
        }

        @Test
        fun whenGetInvitationResponseListByUserId_withInvalidUserId_thenReturnHttp404(){
            whenever(mockInvitationService.getInvitationsListByUserId(invalidUserId)).thenThrow(InvitationNotFoundException::class.java)

            mockMvc.perform(MockMvcRequestBuilders.get("/api/invitations/byUser")
                .param("userId", "$invalidUserId"))

                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
        }
}
