package com.placeToBeer.groupService.gateways

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MembershipRepository : CrudRepository<Membership, Long> {

    fun findByMember(user: User): List<Membership>

    fun findByGroup(group: Group): List<Membership>

}