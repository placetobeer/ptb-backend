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

    private val validGroupId = 1L
    private val validGroupName = "Group name"
    private val invalidGroupName = ""

    private val expectedGroupList: MutableList<Group> = mutableListOf(Group(1, "Bratis Kartoffeln"), Group(4, "Karaoke Kollegium"))
    private val expectedGroup = Group(validUserId, validGroupName)

    @Test
    fun whenGetGroupsByUserIdWithValidUserId_thenReturn200() {
        whenever(mockGroupService.getGroupListByUserId(validUserId)).thenReturn(expectedGroupList)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
                .param("userId", "$validUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenGetGroupsByUserIdWithValidUserId_thenReturnValidAnswer() {
        whenever(mockGroupService.getGroupListByUserId(validUserId)).thenReturn(expectedGroupList)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
                .param("userId", "$validUserId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedGroupList)))
    }

    @Test
    fun whenGetGroupsByUserIdWithInvalidUserId_thenReturn404() {
        whenever(mockGroupService.getGroupListByUserId(invalidUserId)).thenThrow(UserNotFoundException::class.java)
        mockMvc.perform(MockMvcRequestBuilders.get("/api/groups")
                .param("userId", "$invalidUserId"))

                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenCreateGroupWithValidUserIdAndGroupName_thenReturn200() {
        whenever(mockGroupService.createGroup(validUserId, validGroupName)).thenReturn(Group(validUserId, validGroupName))
        mockMvc.perform(MockMvcRequestBuilders.post("/api/groups")
                .param("userId", "$validUserId")
                .content(validGroupName))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenCreateGroupWithValidUserIdAndGroupName_thenReturnValidAnswer() {
        whenever(mockGroupService.createGroup(validUserId, validGroupName)).thenReturn(Group(validUserId, validGroupName))
        mockMvc.perform(MockMvcRequestBuilders.post("/api/groups")
                .param("userId", "$validUserId")
                .content(validGroupName))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(expectedGroup)))
    }

    @Test
    fun whenCreateGroupWithInvalidUserIdAndGroupName_thenReturn404() {
        whenever(mockGroupService.createGroup(invalidUserId, validGroupName)).thenThrow(UserNotFoundException::class.java)
        mockMvc.perform(MockMvcRequestBuilders.post("/api/groups")
                .param("userId", "$invalidUserId")
                .content(validGroupName))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

}