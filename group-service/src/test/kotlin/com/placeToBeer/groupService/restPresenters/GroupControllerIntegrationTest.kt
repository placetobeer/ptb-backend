package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.exceptions.UserNotFoundException
import com.placeToBeer.groupService.services.GroupService
import org.junit.Before
import org.junit.jupiter.api.BeforeEach
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
    private val invalidUserId = 2L
    private val groupName = "Group name"

    private val expectedGroupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))
    private val expectedGroup = Group(validUserId, groupName)

    @Test
    fun whenGetGroupsByUserIdWithValidUserId_thenReturn200() {
        whenever(mockGroupService.getGroupListByUserId(validUserId)).thenReturn(expectedGroupList)
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "$validUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenGetGroupsByUserIdWithValidUserId_thenReturnValidAnswer() {
        whenever(mockGroupService.getGroupListByUserId(validUserId)).thenReturn(expectedGroupList)
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "$validUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedGroupList)))
    }

    @Test
    fun whenGetGroupsByUserIdWithInvalidUserId_thenReturn404() {
        whenever(mockGroupService.getGroupListByUserId(invalidUserId)).thenThrow(UserNotFoundException::class.java)
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "$invalidUserId"))

                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenCreateGroupWithValidUserIdAndGroupName_thenReturn200() {
        whenever(mockGroupService.createGroup(validUserId, groupName)).thenReturn(Group(validUserId, groupName))
        mockMvc.perform(MockMvcRequestBuilders.post("/groups")
                .param("userId", "$validUserId")
                .param("groupName", groupName))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenCreateGroupWithValidUserIdAndGroupName_thenReturnValidAnswer() {
        whenever(mockGroupService.createGroup(validUserId, groupName)).thenReturn(Group(validUserId, groupName))
        mockMvc.perform(MockMvcRequestBuilders.post("/groups")
                .param("userId", "$validUserId")
                .param("groupName", groupName))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedGroup)))
    }

    @Test
    fun whenCreateGroupWithInvalidUserIdAndGroupName_thenReturn404() {
        whenever(mockGroupService.createGroup(invalidUserId, groupName)).thenThrow(UserNotFoundException::class.java)
        mockMvc.perform(MockMvcRequestBuilders.post("/groups")
                .param("userId", "$invalidUserId")
                .param("groupName", groupName))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

}