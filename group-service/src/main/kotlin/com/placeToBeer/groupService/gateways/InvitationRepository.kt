package com.placeToBeer.groupService.gateways

import com.placeToBeer.groupService.entities.Invitation
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface InvitationRepository : CrudRepository<Invitation, Long> {
}