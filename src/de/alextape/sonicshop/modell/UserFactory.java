package de.alextape.sonicshop.modell;

import de.alextape.sonicshop.observers.SecurityWrapper.SecurityLevel;
import de.alextape.sonicshop.userTypes.AdministratorUser;
import de.alextape.sonicshop.userTypes.DefaultUser;
import de.alextape.sonicshop.userTypes.RegisteredUser;
import de.alextape.sonicshop.userTypes.SupporterUser;

/*
 * this class returns a valid user for the given attributes
 */
/**
 * A factory for creating User objects.
 */
public class UserFactory {

    /**
     * Gets the user.
     *
     * @param userID
     *            the user id
     * @param name
     *            the name
     * @param lastname
     *            the lastname
     * @param email
     *            the email
     * @param isAdmin
     *            the is admin
     * @param isSupporter
     *            the is supporter
     * @return the user
     */
    public DefaultUser getUser(int userID, String name, String lastname,
            String email, boolean isAdmin, boolean isSupporter) {
        if (isAdmin) {
            return new AdministratorUser(userID, SecurityLevel.ADMINISTRATOR,
                    name, lastname, email);
        } else {
            if (isSupporter) {
                return new SupporterUser(userID, SecurityLevel.SUPPORTER, name,
                        lastname, email);
            } else {
                return new RegisteredUser(userID, SecurityLevel.USER, name,
                        lastname, email);
            }
        }
    }
}
