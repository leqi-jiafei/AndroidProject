package com.test.util;

import java.util.regex.Pattern;

/*
 * writed based on org.apache.commons.validator.EmailValidator,
 *
 */

/**
 * <p>
 * Perform email validations.
 * </p>
 * 
 * @author Qiu Le Qi.
 * @version $Revision:
 */
public class EmailValidator {

	private static final String SPECIAL_CHARS = "\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]";

	private static final String VALID_CHARS = "[^\\s" + SPECIAL_CHARS + "]";

	private static final String QUOTED_USER = "(\"[^\"]*\")";

	private static final String ATOM = VALID_CHARS + '+';

	private static final String WORD = "((" + VALID_CHARS + "|')+|"
			+ QUOTED_USER + ")";

	// Each pattern must be surrounded by /
	private static final String LEGAL_ASCII_PATTERN = "^[\\000-\\0177]+$";

	private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

	private static final String IP_DOMAIN_PATTERN = "^(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})$";

	private static final String TLD_PATTERN = "^([a-zA-Z]+)$";

	private static final String USER_PATTERN = "^\\s*" + WORD + "(\\." + WORD
			+ ")*$";

	private static final String DOMAIN_PATTERN = "^" + ATOM + "(\\." + ATOM
			+ ")*\\s*$";

	private static final String ATOM_PATTERN = "(" + ATOM + ")";

	/**
	 * Singleton instance of this class.
	 */
	private static final EmailValidator EMAIL_VALIDATOR = new EmailValidator();

	/**
	 * Returns the Singleton instance of this validator.
	 * 
	 * @return singleton instance of this validator.
	 */
	public static EmailValidator getInstance() {
		return EMAIL_VALIDATOR;
	}

	/**
	 * Protected constructor for subclasses to use.
	 */
	protected EmailValidator() {
		super();
	}

	/**
	 * <p>
	 * Checks if a field has a valid e-mail address.
	 * </p>
	 * 
	 * @param email
	 *            The value validation is being performed on. A
	 *            <code>null</code> value is considered invalid.
	 * @return true if the email address is valid.
	 */
	public boolean isValid(String email) {
		if (email == null) {
			return false;
		} else {
			email = email.trim();
		}

		if (!Pattern.matches(LEGAL_ASCII_PATTERN, email)) {
			return false;
		}

		// email = stripComments(email);

		// Check the whole email address structure
		if (!Pattern.matches(EMAIL_PATTERN, email)) {
			return false;
		}

		if (email.endsWith(".")) {
			return false;
		}
		Pattern patt = Pattern.compile("@");
		String[] splitedMail = patt.split(email);
		if (splitedMail.length != 2) {
			return false;
		}
		if (!isValidUser(splitedMail[0])) {
			return false;
		}

		if (!isValidDomain(splitedMail[1])) {
			return false;
		}

		return true;
	}

	/**
	 * Returns true if the domain component of an email address is valid.
	 * 
	 * @param domain
	 *            being validatied.
	 * @return true if the email address's domain is valid.
	 */
	protected boolean isValidDomain(String domain) {
		boolean symbolic = false;
		// System.out.println(Pattern.matches(IP_DOMAIN_PATTERN, domain));
		if (Pattern.matches(IP_DOMAIN_PATTERN, domain)) {
			if (!isValidIpAddress(domain)) {
				return false;
			} else {
				return true;
			}
		} else {
			// Domain is symbolic name
			// Perl5Util domainMatcher = new Perl5Util();
			symbolic = Pattern.matches(DOMAIN_PATTERN, domain);
		}

		if (symbolic) {
			if (!isValidSymbolicDomain(domain)) {
				return false;
			}
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Returns true if the user component of an email address is valid.
	 * 
	 * @param user
	 *            being validated
	 * @return true if the user name is valid.
	 */
	protected boolean isValidUser(String user) {
		return Pattern.matches(USER_PATTERN, user);
	}

	/**
	 * Validates an IP address. Returns true if valid.
	 * 
	 * @param ipAddressMatcher
	 *            Pattren matcher
	 * @return true if the ip address is valid.
	 */
	protected boolean isValidIpAddress(String ipAddress) {
		Pattern p = Pattern.compile("\\.");
		String[] splitedIPs = p.split(ipAddress);
		for (int i = 0; i < 4; i++) {
			String ipSegment = splitedIPs[i];
			if (ipSegment == null || ipSegment.length() <= 0) {
				return false;
			}

			int iIpSegment = 0;

			try {
				iIpSegment = Integer.parseInt(ipSegment);
			} catch (NumberFormatException e) {
				return false;
			}

			if (iIpSegment > 255) {
				return false;
			}

		}
		return true;
	}

	/**
	 * Validates a symbolic domain name. Returns true if it's valid.
	 * 
	 * @param domain
	 *            symbolic domain name
	 * @return true if the symbolic domain name is valid.
	 */
	protected boolean isValidSymbolicDomain(String domain) {
		Pattern p = Pattern.compile("\\.");
		String[] domainSegment = p.split(domain);
		boolean match = true;

		for (int j = 0; j < domainSegment.length; j++) {
			match = Pattern.matches(ATOM_PATTERN, domainSegment[j]);
			if (!match) {
				return false;
			}
		}

		int len = domainSegment.length;

		// Make sure there's a host name preceding the domain.
		if (len < 2) {
			return false;
		}

		// TODO: the tld should be checked against some sort of configurable
		// list
		String tld = domainSegment[len - 1];
		if (tld.length() > 1) {
			if (!Pattern.matches(TLD_PATTERN, tld)) {
				return false;
			}			
		} else {
			return false;
		}

		return true;
	}

	/**
	 * Recursively remove comments, and replace with a single space. The simpler
	 * regexps in the Email Addressing FAQ are imperfect - they will miss
	 * escaped chars in atoms, for example. Derived From Mail::RFC822::Address
	 * 
	 * @param emailStr
	 *            The email address
	 * @return address with comments removed.
	 */
	// protected String stripComments(String emailStr) {
	// String input = emailStr;
	// String result = emailStr;
	// String commentPat =
	// "s/^((?:[^\"\\\\]|\\\\.)*(?:\"(?:[^\"\\\\]|\\\\.)*\"(?:[^\"\\\\]|\111111\\\\.)*)*)\\((?:[^()\\\\]|\\\\.)*\\)/$1
	// /osx";
	// Perl5Util commentMatcher = new Perl5Util();
	// result = commentMatcher.substitute(commentPat,input);
	// // This really needs to be =~ or Perl5Matcher comparison
	// while (!result.equals(input)) {
	// input = result;
	// result = commentMatcher.substitute(commentPat,input);
	// }
	// return result;
	//
	// }
	// public static void main(String[] args) {
	// System.out.println(EmailValidator.getInstance().isValid(
	// null));
	//	 }
//	public static void main(String[] args) {
//		System.out.println(">>"
//				+ EmailValidator.getInstance().isValid("57765@10.1.1.1"));
//	}
}
