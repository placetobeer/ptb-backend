package com.placeToBeer.groupService.gateways
import com.placeToBeer.groupService.entities.Group
import com.placeToBeer.groupService.entities.Membership
import com.placeToBeer.groupService.entities.Role
import com.placeToBeer.groupService.entities.User
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
open class MembershipRepositoryIntegrationTest(@Autowired val membershipRepository: MembershipRepository) {

    @Test
    fun whenUserInput_ThenReturnMembershipList () {
        val user1 = User(5,"Lucie","lucie@mail.com")
        val group1 = Group(1, "Bratis Kartoffeln")
        val group2 = Group(2, "Hüttengaudis")
        val membership1 = Membership(5, group1, User(5, "Lucie", "lucie@mail.com"), Role.MEMBER)
        val membership2 = Membership(8, group2, User(5, "Lucie", "lucie@mail.com"), Role.ADMIN)
        val shouldMemberships = mutableListOf<Membership>(membership1, membership2)


        val isMemberships = membershipRepository.findByMember(user1)
        Assertions.assertThat(isMemberships).isEqualTo(shouldMemberships)
    }


    @Test
    fun whenGroupInput_ThenReturnMembershipList(){
        val group1 = Group(1, "Bratis Kartoffeln")
        val membership1 = Membership(1, group1, User(1,"Bea","bea@mail.com"), Role.OWNER)
        val membership2 = Membership(2, group1, User(2,"Jonas","jonas@mail.com"),Role.ADMIN)
        val membership3 = Membership(3, group1, User(3,"Katja","katja@mail.com"),Role.ADMIN)
        val membership4 = Membership(4, group1, User(4,"Patrick","patrick@mail.com"), Role.MEMBER)
        val membership5 = Membership(5, group1, User(5,"Lucie","lucie@mail.com"),Role.MEMBER)
        val membership6 = Membership(6, group1, User(6,"Tom","tom@mail.com"),Role.MEMBER)
        val shouldMemberships = mutableListOf<Membership>(membership1,membership2,membership3,membership4,membership5,membership6)

        val isMemberships = membershipRepository.findByGroup(group1)
        Assertions.assertThat(isMemberships).isEqualTo(shouldMemberships)
    }
}