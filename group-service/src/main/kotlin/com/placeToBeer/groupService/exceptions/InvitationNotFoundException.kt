package com.placeToBeer.groupService.exceptions

class InvitationNotFoundException(invitationId: Long) : AbstractNotFoundException("Could not find invitation with invitaionId $invitationId"){
}