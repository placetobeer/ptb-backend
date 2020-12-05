package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.entities.responses.UserMembership
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

    private val groupId = 1L
    private val userMemberships = listOf(
            UserMembership(User(1, "Bea"), Role.OWNER),
            UserMembership(User(2, "Patrick"), Role.ADMIN),
            UserMembership(User(3, "Lucie"), Role.MEMBER))

    @Test
    fun whenValidInput_thenReturns200() {
        mockMvc.perform(MockMvcRequestBuilders.get("/membership")
                .param("groupId", "$groupId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenValidInput_thenReturnValidAnswer() {
        whenever(mockMembershipService.getMembershipList(groupId)).thenReturn(userMemberships)

        mockMvc.perform(MockMvcRequestBuilders.get("/membership")
                .param("groupId", "$groupId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(userMemberships)))
    }

}