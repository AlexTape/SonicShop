package de.alextape.sonicshop.userTypes;

import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;

/*
 * highest level user (e.g. registeredUser)
 */
/**
 * The Class AdministratorUser.
 */
public class AdministratorUser extends SupporterUser implements DefaultUser {

    /**
     * Instantiates a new administrator user.
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
    public AdministratorUser(int userID, SecurityLevel role, String name,
            String lastname, String email) {
        super(userID, role, name, lastname, email);
        // TODO Auto-generated constructor stub
    }

    // TODO some admin specific methods can be added here!
}
