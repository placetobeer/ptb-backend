package com.placeToBeer.groupService.exceptions

class ProposalNotFoundException(proposalId: Long): AbstractNotFoundException("Could not find proposal with proposal id $proposalId") {
}