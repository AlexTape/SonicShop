package de.alextape.sonicshop.userTypes;

import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/*
 * second level user (e.g. registeredUser)
 */
/**
 * The Class SupporterUser.
 */
public class SupporterUser extends RegisteredUser implements DefaultUser {

    /**
     * Instantiates a new supporter user.
     *
     * @param userID
     *            the user id
     * @param role
     *            the role
     * @param name
     *            the name
     * @param lastname
     *            the lastname
     * @param email
     *            the email
     */
    public SupporterUser(int userID, SecurityLevel role, String name,
            String lastname, String email) {
        super(userID, role, name, lastname, email);
        // TODO Auto-generated constructor stub
    }
    // TODO some supporter specific methods can be added here!
}
