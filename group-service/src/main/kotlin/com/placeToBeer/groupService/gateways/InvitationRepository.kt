package com.placeToBeer.groupService.gateways

import com.placeToBeer.groupService.entities.Invitation
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InvitationRepository : CrudRepository<Invitation, Long> {

    fun findAllByGroupId(groupId: Long): List<Invitation>

}