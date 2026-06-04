/**
 * Checks if a password has been compromised using the HaveIBeenPwned API.
 * It uses the k-anonymity model: it hashes the password with SHA-1,
 * sends only the first 5 characters of the hash to the API, and checks
 * if the rest of the hash is in the response.
 * @param {string} password 
 * @returns {Promise<number>} Number of times the password was found in leaks.
 */
export async function checkPwnedPassword(password) {
    if (!password) return 0;

    // 1. Hash the password with SHA-1
    const buffer = new TextEncoder().encode(password);
    const hashBuffer = await crypto.subtle.digest('SHA-1', buffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('').toUpperCase();

    // 2. Split hash into prefix (first 5 chars) and suffix (the rest)
    const prefix = hashHex.slice(0, 5);
    const suffix = hashHex.slice(5);

    // 3. Query the API with the prefix
    try {
        const response = await fetch(`https://api.pwnedpasswords.com/range/${prefix}`);
        if (!response.ok) return 0;

        const text = await response.text();
        const lines = text.split('\n');

        // 4. Check if the suffix exists in the response
        for (const line of lines) {
            const [returnedSuffix, count] = line.split(':');
            if (returnedSuffix === suffix) {
                return parseInt(count.trim(), 10);
            }
        }
    } catch (e) {
        console.error("Failed to check HIBP API", e);
    }
    return 0;
}

/**
 * Generates a cryptographically strong, random password.
 * @returns {string} The generated password.
 */
export function generateSecurePassword() {
    const length = 16;
    const charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+~`|}{[]:;?><,./-=";
    let retVal = "";
    const randomValues = new Uint32Array(length);
    crypto.getRandomValues(randomValues);
    for (let i = 0, n = charset.length; i < length; ++i) {
        retVal += charset.charAt(randomValues[i] % n);
    }
    // Ensure it meets our regex constraints by explicitly adding one of each type if missing
    if (!/[A-Z]/.test(retVal)) retVal += 'A';
    if (!/[a-z]/.test(retVal)) retVal += 'a';
    if (!/[0-9]/.test(retVal)) retVal += '1';
    if (!/[@#$%^&+=!]/.test(retVal)) retVal += '!';
    return retVal;
}
