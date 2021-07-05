package com.placeToBeer.groupService.gateways

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Proposal
import org.springframework.data.repository.CrudRepository

interface ProposalRepository : CrudRepository<Proposal, Long> {
    fun findAllByGroup(group: Group): List<Proposal>
}