package com.placeToBeer.groupService.services

import com.placeToBeer.groupService.entities.Proposal
import com.placeToBeer.groupService.entities.requests.ProposalRequest
import com.placeToBeer.groupService.interactors.proposal.AddProposalInteractor
import com.placeToBeer.groupService.interactors.proposal.ProposalListInteractor
import org.springframework.stereotype.Service

@Service
class ProposalService(private var proposalListInteractor: ProposalListInteractor, private var addProposalInteractor: AddProposalInteractor) {

    fun getProposalListByGroupId(groupId: Long): List<Proposal> {
        return proposalListInteractor.execute(groupId)
    }

    fun createProposal(proposalRequest: ProposalRequest) : Proposal {
        return addProposalInteractor.execute(proposalRequest)
    }
}