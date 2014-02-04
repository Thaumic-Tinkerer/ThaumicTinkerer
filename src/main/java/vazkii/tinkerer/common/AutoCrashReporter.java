package vazkii.tinkerer.common;

import cpw.mods.fml.common.FMLLog;
import vazkii.tinkerer.common.lib.GMailAuthenticator;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Properties;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


//Original code from Minechem
//Heavilt based on code from RichardG

public class AutoCrashReporter extends Handler {

	public AutoCrashReporter() {
		FMLLog.getLogger().addHandler(this);
	}

	public static String readFileToString(File f) throws FileNotFoundException, IOException {

		BufferedReader reader = new BufferedReader(new FileReader(f));
		String ret = "";

		char[] buffer = new char[1024];
		int read = 0;
		while ((read = reader.read(buffer)) != -1) {
			ret += String.valueOf(buffer, 0, read);
		}

		reader.close();
		return ret;
	}

	@Override
	public void publish(LogRecord record) {
		if (record.getMessage().startsWith("This crash report has been saved to: ")) {
			String report;
			try {
				report = readFileToString(new File(record.getMessage().substring(37)));
			} catch (Throwable e) {
				StringWriter writer = new StringWriter();
				writer.write("Crash report could not be read!\r\n\r\n");
				e.printStackTrace(new PrintWriter(writer));
				report = writer.toString();
			}

			call(report);
		}
	}

	@Override
	public void flush() {

	}

	@Override
	public void close() throws SecurityException {

	}

	public void call(String crash) {
		if (crash.contains("minechem") && crash.indexOf("System Details") > crash.indexOf("minechem") && !crash.contains("occupied by")) {
			String to = "adlytempleton@gmail.com";
			String neko = "kat.swales+ThaumicTIinkerer@nekokittygames.com";
			String from = "thaumiccrashes@gmail.com";
			String host = "smtp.gmail.com";

			Properties properties = System.getProperties();

			properties.setProperty("mail.smtp.host", host);
			properties.setProperty("mail.smtp.starttls.enable", "true");
			properties.setProperty("mail.smtp.auth", "true");

			String pw = "INSERTPASSWORDHERE";

			Session session = Session.getDefaultInstance(properties, new GMailAuthenticator("thaumiccrashes", pw));

			try {

				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				message.addRecipient(Message.RecipientType.TO, new InternetAddress(neko));
				message.setSubject("Thaunic Tinkerer Crash crash");
				message.setText(crash);
				Transport.send(message);

			} catch (Exception mex) {
				mex.printStackTrace();
			}


		}
	}
}
