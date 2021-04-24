package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.GroupsMembership
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.services.MembershipService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(MembershipController::class)
class MembershipControllerIntegrationTest(@Autowired private var mockMvc: MockMvc, @Autowired
private var objectMapper: ObjectMapper) {

    @MockBean
    private lateinit var mockMembershipService: MembershipService

    private val validGroupId = 1L
    private val invalidGroupId = 2L
    private val userMemberships = listOf(
            GroupsMembership(User(1, "Bea","bea@mail.com"), Role.OWNER),
            GroupsMembership(User(2, "Patrick","patrick@mail.com"), Role.ADMIN),
            GroupsMembership(User(3, "Lucie","lucie@mail.com"), Role.MEMBER))

    @Test
    fun whenGetUserMembershipListByGroupId_withValidGroupId_thenReturnHttp200() {
        mockMvc.perform(MockMvcRequestBuilders.get("/memberships")
                .param("groupId", "$validGroupId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenGetUserMembershipListByGroupId_withValidGroupId_thenReturnValidAnswer() {
        whenever(mockMembershipService.getGroupsMembershipListByGroupId(validGroupId)).thenReturn(userMemberships)

        mockMvc.perform(MockMvcRequestBuilders.get("/memberships")
                .param("groupId", "$validGroupId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(userMemberships)))
    }

    @Test
    fun whenGetUserMembershipListByGroupId_withInvalidGroupId_thenReturnHttp404() {
        whenever(mockMembershipService.getGroupsMembershipListByGroupId(invalidGroupId)).thenThrow(GroupNotFoundException::class.java)

        mockMvc.perform(MockMvcRequestBuilders.get("/memberships")
                .param("groupId", "$invalidGroupId"))

                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }
}