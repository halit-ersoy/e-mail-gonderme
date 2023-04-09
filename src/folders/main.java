package folders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class main extends UserReadClass {

	// .txt dosyalarının tanımlanması
	private static final String elit_uyeler_txt = "elit_uyeler.txt";
	private static final String genel_uyeler_txt = "genel_uyeler.txt";

	// UserAddClass'ın new'lenmesi
	static UserAddClass userAddClass = new UserAddClass();

	public static void main(String[] args) {

		// Scanner fonksiyonu çağırma işlemi
		Scanner input = new Scanner(System.in);

		// işlem sayısı sürekli tekrar edecek
		while (true) {

			// seçim tercihlerinin kullanıcılara aktarıldığı bölüm
			System.out.println("Yapmak istediginiz islem numarasini giriniz");
			System.out.println("Elit uye eklemek icin 1'e basiniz");
			System.out.println("Genel uye eklemek icin 2'ye basiniz");
			System.out.println("Mail gondermek icin 3'e basiniz");
			System.out.print("Seciniz: ");

			// kullanıcının seçim tercih girdisi
			int in = input.nextInt();
			input.nextLine();

			// seçim tercih sorgusu
			switch (in) {

			// seçim 1
			case 1:

				// kayıt edilmesi istenen elit üyenin kullanıcı girişlerinin yapıldığı bölüm
				System.out.println("\nKaydetmek istediginiz kisiye ait;");
				System.out.print("Isim: ");
				String elitName = input.nextLine();
				System.out.print("Soyisim: ");
				String elitSurname = input.nextLine();
				System.out.print("Email: ");
				String elitEmail = input.nextLine();

				// verileri girilen elit üyenin setter ile kaydedilmesi
				userAddClass.setSave(elit_uyeler_txt, elitName + "\t" + elitSurname + "\t" + elitEmail);
				userAddClass.userSave();

				break;

			// seçim 2
			case 2:

				// kayıt edilmesi istenen genel üyenin kullanıcı girişlerinin yapıldığı bölüm
				System.out.println("\nKaydetmek istediginiz kisiye ait;");
				System.out.print("Isim: ");
				String genelName = input.nextLine();
				System.out.print("Soyisim: ");
				String genelSurname = input.nextLine();
				System.out.print("Email: ");
				String genelEmail = input.nextLine();

				// verileri girilen genel üyenin setter ile kaydedilmesi
				userAddClass.setSave(genel_uyeler_txt, genelName + "\t" + genelSurname + "\t" + genelEmail);
				userAddClass.userSave();

				break;

			// seçim 3
			case 3:

				// mail tercihlerinin kullanıcılara aktarıldığı bölüm
				System.out.println("\nMail gonderme menusu;");
				System.out.println("Elit uyelere mail gondermek icin 1'e basiniz");
				System.out.println("Genel uyelere mail gondermek icin 2'ye basiniz");
				System.out.println("Tum uyelere mail gondermek icin 3'e basiniz");
				System.out.print("Seciniz: ");

				// kullanıcının seçim tercih girdisi
				int mailSelect = input.nextInt();
				input.nextLine(); // dummy

				// mail seçim sorgusu
				switch (mailSelect) {

				// seçim 1
				case 1:

					// elit üyelere mail ile gönderilecek mesajın girilmesi
					System.out.print("Elit uyelere gonderilecek mesaji giriniz: ");
					String elitMessage = input.nextLine();

					// elit üyelerin .txt dosyasından okunması
					ArrayList<String> elitmembers = userRead(elit_uyeler_txt);

					// mail gönderme işlemini gerçekleştirme
					UserSetMailClass.setMail(elitmembers, elitMessage);

					break;

				// seçim 2
				case 2:

					// genel üyelere mail ile gönderilecek mesajın girilmesi
					System.out.print("Genel uyelere gonderilecek mesaji giriniz: ");
					String getMessage = input.nextLine();

					// genel üyelerin .txt dosyasından okunması
					ArrayList<String> genelMembers = userRead(genel_uyeler_txt);

					// mail gönderme işlemini gerçekleştirme
					UserSetMailClass.setMail(genelMembers, getMessage);

					break;

				// seçim 3
				case 3:

					// tüm üyelere mail ile gönderilecek mesajın girilmesi
					System.out.print("Tum uyelere gonderilecek mesaji giriniz: ");
					String allMessage = input.nextLine();

					// hem elit hem genel üyelerin .txt dosyalarından okunması ve birleştirme
					// şileminin gerçekleştirilmesi
					ArrayList<String> allMembers = combine(userRead(elit_uyeler_txt), userRead(genel_uyeler_txt));

					// mail gönderme işlemini gerçekleştirme
					UserSetMailClass.setMail(allMembers, allMessage);

					break;

				// mail menüsü geçersiz seçim
				default:

					// geçersiz seçime dair hata mesajı
					System.out.println("Gecersiz rakam girdiniz.");

				}
				break;

			// kullanıcı girdisi geçersiz seçim
			default:

				// geçersiz seçime dair hata mesajı
				System.out.println("Gecersiz rakam girdiniz.");

			}
		}
	}

	// hem elit hem genel üyelerin birleştirme işleminin gerçekleştirilmesi
	private static ArrayList<String> combine(ArrayList<String> ilk, ArrayList<String> ikinci) {

		ArrayList<String> Combine = new ArrayList<>();
		Combine.addAll(ilk);
		Combine.addAll(ikinci);
		return Combine;

	}
}

class UserAddClass {

	private static String fileName;
	private static String data;

	// set metodu ile gönderilen .txt dosya adı ve kullanıcı verisinin alınması
	public void setSave(String fileName, String data) {

		this.fileName = fileName;
		this.data = data;

	}

	// .txt dosya adına kullanıcının kaydedilmesi
	public void userSave() {

		try {

			// FileWriter fonksiyonu çağırma
			FileWriter writer = new FileWriter(fileName, true);

			// yazma işleminin gerçekleştirilmesi
			writer.write(data + "\n");

			// yazdırmayı kapatma
			writer.close();

			// yazdırma olayı başarılı ise kullanıcıyı bilgilendirme
			System.out.println("Uye basariyla kaydedildi.\n");

		} catch (IOException e) {

			// kayıt esnasında oluşacak hatanın kullanıcıya gösterilmesi
			System.out.println("Dosya yazma hatasi: " + e.getMessage());

		}
	}

}

class UserReadClass {

	// parametre ile gönderilen .txt dosya adından kullanıcıların okunması
	static ArrayList<String> userRead(String fileName) {

		// dündürülecek parametrenin tanımlanması
		ArrayList<String> members = new ArrayList<>();

		try {

			// File fonksiyonunun çağırılması
			File file = new File(fileName);

			// Scanner fonksiyonunun çağırılması
			Scanner reader = new Scanner(file);

			// satır kontrolü ile döngü işleminin gerçeklerştirilmesi
			while (reader.hasNextLine()) {

				// okunan satırın listeye eklenmesi
				members.add(reader.nextLine());

			}

			// okumayı kapatma
			reader.close();

		} catch (IOException e) {

			// dosya okuma esnasında oluşacak hatanın kullanıcıya gösterilmesi
			System.out.println("Dosya okuma hatasi: " + e.getMessage());

		}
		return members;
	}

}

class UserSetMailClass {

	// mail gönderme işleminin gerçekleştirilemesi
	static void setMail(ArrayList<String> members, String mesaj) {

		// parametre ile alının üyeler için tek tek işlem yaptırılması
		for (String member : members) {

			// üyenin kelimeler arasında bulunan TAB ile parçalara ayrılması
			String[] memberInformation = member.split("\t");

			// parçalanan üyenin bilgilerinin eşitlenmesi

			/*
			 * String name = uyeBilgileri[0]; String surname = uyeBilgileri[1];
			 */

			String email = memberInformation[2];

			// mail gönderecek hesabın bilgilerinin girilmesi
			final String username = "gonderenMail@gmail.com";
			final String password = "gonderenSifre";

			// Properties fonksiyonunun çağırılması
			Properties properties = new Properties();

			// verilerin girilmesi
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.smtp.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "587");

			// mail gönderecek hesabın giriş yapmasını sağlayan fonksiyon
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				// Message fonksiyonunun çağırılması
				Message message = new MimeMessage(session);

				// from alanının belirtilmesi
				message.setFrom(new InternetAddress("titabilgisayarciyim@gmail.com"));

				// gönderilecek e-mail'in verilmesi
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));

				// gönderilecek mesajın verilmesi
				message.setText(mesaj);

				// mail gönderme işlemi
				Transport.send(message);

				// e-posta gönderme işlemi başarılı ise kullanıcının bilgilendirilmesi
				System.out.println("E-posta gonderildi.\n");

			} catch (MessagingException e) {

				// e-posta gönderme işleminde oluşacak hatanın gösterilmesi
				throw new RuntimeException(e);

			}

		}
	}

}
