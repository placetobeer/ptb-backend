package com.placeToBeer.groupService.restPresenters

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import com.placeToBeer.groupService.services.GroupService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


internal class GroupControllerTest {

    private val mockGroupService: GroupService = mock()

    private val groupController = GroupController(mockGroupService)

    @Test
    fun whenGetGroupListWithValidId_thenReturnValidAnswer(){
        val userId = 1

        var membership1 = Membership(1,Group(1,"ClubCrew"), User(1, "Tom"), Role.MEMBER)
        var membership2 = Membership(2,Group(1,"ClubCrew"), User(2, "Patrick"), Role.MEMBER)
        var membership3 = Membership(3,Group(2, "Hüttengaudis"),User(1, "Tom"), Role.MEMBER)
        var membership4 = Membership(4, Group(2, "Hüttengaudis"), User(3, "Lucie"), Role.MEMBER)

        //val membershipList1: MutableList<Membership> = mutableListOf(Membership(1,Group(1,"ClubCrew"), User(1, "Tom"), Role.MEMBER), Membership(2,Group(2, "Hüttengaudis"), User(2, "Patrick"), Role.MEMBER))
        //val membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        var shouldGroupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew"), Group(2, "Hüttengaudis"), Group(3, "Corga"))


        /*
        old code:
        var membershipList1: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(2, User(2, "Patrick"), Role.MEMBER))
        var membershipList2: MutableList<Membership> = mutableListOf(Membership(1, User(1, "Tom"), Role.MEMBER), Membership(3, User(3, "Lucie"), Role.MEMBER))
        var shouldGroupList: MutableList<Group> = mutableListOf(Group(1, "ClubCrew", membershipList1), Group(2, "Hüttengaudis", membershipList2))
        */




        /* Given */
        whenever(mockGroupService.getGroupList(userId)).thenReturn(shouldGroupList)

        /* When */
        val givenGroupList = groupController.getGroupListByUserId(userId)

        /* Then */
        assertThat(givenGroupList).isEqualTo(shouldGroupList)
    }
}