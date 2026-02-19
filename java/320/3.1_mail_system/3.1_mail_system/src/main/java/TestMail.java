
public class TestMail {

	public static void main(String[] args) {
		// create a MailServer instance.
		MailServer mailServer = new MailServer();
		// create a MailClient instance. Here you have to pass the mail server as a
		// parameter, and you have to make up a name for this user.
		MailClient mailClientJohnny = new MailClient(mailServer, "Johnny");
		// create a second MailClient object with the same mail server and a different
		// user name.
		MailClient mailClientClint = new MailClient(mailServer, "Clint");
		// Use the "sendMessage" method of a MailClient object to send a message to the
		// other mail client.
		mailClientJohnny.sendMailItem("Clint", "Hey, there");
		// Use the "printNextMessage" method of the second mail client to receive the
		// message.
		mailClientClint.printNextMailItem();
		// send an answer to Johnny
		mailClientClint.sendMailItem("Johnny", "hello, Johnny, what's up?");
		// use printnextMessage from
		mailClientJohnny.printNextMailItem();

	}

}
