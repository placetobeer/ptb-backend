package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.services.GroupService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(GroupController::class)
class GroupControllerIntegrationTest(@Autowired private var mockMvc: MockMvc, @Autowired
private var objectMapper: ObjectMapper) {

    @MockBean
    private lateinit var mockGroupService: GroupService

    private val validUserId = 1L

    @Test
    fun whenValidInput_thenReturns200() {
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "$validUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenValidInput_thenReturnValidAnswer() {
        val userId = 1L
        val groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))

        whenever(mockGroupService.getGroupList(userId)).thenReturn(groupList)

        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "$userId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(groupList)))
    }

}