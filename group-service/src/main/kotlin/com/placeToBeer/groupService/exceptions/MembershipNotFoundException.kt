package com.placeToBeer.groupService.exceptions

class MembershipNotFoundException(membershipId: Long) : AbstractNotFoundException("Could not find membership with membershipId $membershipId") {
}