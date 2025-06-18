package org.example.lld.linkedin;

public class LinkedIn {
}

interface InviteFactory {
    Invite create();
    void update(Invite invite);
    void accept(Invite invite);
    void delete(Invite invite);
}

interface UpdateInvite {
    void update(Invite invite);
}

interface AcceptInvite {
    void accept(Invite invite);
}

interface DeleteInvite {
    void delete(Invite invite);
}

abstract class Invite {
}

class ConnectInvite extends Invite {
}

class JoinGroupInvite extends Invite {
}

class ConnectInviteFactory implements InviteFactory {
    public Invite create() {
        return new ConnectInvite();
    }
    public void update(Invite invite) {
    }
    public void accept(Invite invite) {
    }
    public void delete(Invite invite) {
    }
}

class JoinGroupInviteFactory implements InviteFactory {
    public Invite create() {
        return new JoinGroupInvite();
    }
    public void update(Invite invite) {
    }
    public void accept(Invite invite) {
    }
    public void delete(Invite invite) {
    }
}

class User {
    InviteFactory inviteFactory;
    public void setInviteFactory(InviteFactory inviteFactory) {
        this.inviteFactory = inviteFactory;
    }
    public Invite createInvite() {
        return inviteFactory.create();
    }
    public void updateInvite(Invite invite) {
        inviteFactory.update(invite);
    }
    public void acceptInvite(Invite invite) {
        inviteFactory.accept(invite);
    }
    public void deleteInvite(Invite invite) {
        inviteFactory.delete(invite);
    }
}

class UserFactory {
    User create() {
        return new User();
    }
}

class Client {
    public static void main(String[] args) {
        User user = new UserFactory().create();
        user.setInviteFactory(new ConnectInviteFactory());
        Invite connectInvite = user.createInvite();
        user.updateInvite(connectInvite);
        user.acceptInvite(connectInvite);
        user.deleteInvite(connectInvite);
        user.setInviteFactory(new JoinGroupInviteFactory());
        Invite joinGroupInvite = user.createInvite();
        user.updateInvite(joinGroupInvite);
        user.acceptInvite(joinGroupInvite);
        user.deleteInvite(joinGroupInvite);
    }
}