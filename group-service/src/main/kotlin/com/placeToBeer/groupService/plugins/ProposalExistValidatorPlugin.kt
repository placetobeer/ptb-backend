package com.placeToBeer.groupService.plugins

import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Proposal
import com.placeToBeer.groupService.exceptions.GroupNotFoundException
import com.placeToBeer.groupService.exceptions.ProposalNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class ProposalExistValidatorPlugin {

    private var logger: Logger = LoggerFactory.getLogger(ProposalExistValidatorPlugin::class.java)

    fun validateAndReturn(proposal: Optional<Proposal>, proposalId: Long): Proposal {
        if(proposal.isEmpty){
            throwProposalNotFoundError(proposalId)
        }
        return proposal.get()
    }

    private fun throwProposalNotFoundError(proposalId: Long) {
        logger.error("No proposal with proposalId $proposalId found")
        throw ProposalNotFoundException(proposalId)
    }

}