package com.placeToBeer.groupService.restPresenters

import com.fasterxml.jackson.databind.ObjectMapper
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.services.GroupService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(GroupController::class)
class GroupControllerIntegrationTest (@Autowired private var mockMvc: MockMvc, @Autowired
private var objectMapper: ObjectMapper){

    @MockBean
    private lateinit var mockGroupService : GroupService

    @Test
    fun whenValidInput_thenReturns200() {
        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "0"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
    }

    @Test
    fun whenValidInput_thenReturnValidAnswer() {
        val userId = 1
        val membership1 = Membership(1,Group(1,"ClubCrew"), User(1, "Tom"), Role.MEMBER)
        val membership2 = Membership(2,Group(1,"ClubCrew"), User(2, "Patrick"), Role.MEMBER)
        val membership3 = Membership(3,Group(2, "Hüttengaudis"),User(1, "Tom"), Role.MEMBER)
        val membership4 = Membership(4, Group(2, "Hüttengaudis"), User(3, "Lucie"), Role.MEMBER)

        //val membershipList1: MutableList<Membership> = mutableListOf(Membership(1,Group(1,"ClubCrew"), User(1, "Tom"), Role.MEMBER), Membership(2,Group(2, "Hüttengaudis"), User(2, "Patrick"), Role.MEMBER))
        //val membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        val groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))




        /*
        old code:
        val membershipList1: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(2, User(2, "Patrick"), Role.MEMBER))
        val membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        val groupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew", membershipList1), Group(2, "Hüttengaudis", membershipList2))
        */
        whenever(mockGroupService.getGroupList(userId)).thenReturn(groupList)

        mockMvc.perform(MockMvcRequestBuilders.get("/groups")
                .param("userId", "$userId"))

                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(groupList)))
    }

}