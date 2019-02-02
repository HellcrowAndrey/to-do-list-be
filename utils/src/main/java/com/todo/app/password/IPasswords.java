package com.todo.app.password;

/**
 * The interface IPasswords class use for generate salt (64, 32 byte length).
 * Do create hash and do check on is it correct.
 */
public interface IPasswords {

    /**
     * This method do generate random salt.
     *
     * @return a byte array with a 64 byte length salt.
     */
    byte[] getSalt64();

    /**
     * This method do generate random salt.
     *
     * @return a byte array with a 32 byte length salt.
     */
    byte[] getSalt32();

    /**
     * This method do generate a new salt, minimum 32 bytes length,
     * 64 bytes max.
     *
     * @param size
     * @return
     */
    byte[] getSalt(final int size);

    /**
     * This method do generate a new hash.
     *
     * @param password to be hashed
     * @param salt     the randomly generate hash
     * @return a hashed password
     */
    byte[] hash(final String password, final byte[] salt);

    /**
     * This method do check on this is expected password
     *
     * @param password to be verified
     * @param salt     the generate salt (coming from data base)
     * @param hash     the generate hash (coming from data base)
     * @return true if password matches or false if otherwise
     */
    boolean isExpectedPassword(final String password,
                               final byte[] salt, final byte[] hash);

    /**
     * Generate a random password
     *
     * @param length desired password length
     * @return a random password
     */
    String generateRandomPassword(final int length);

}
