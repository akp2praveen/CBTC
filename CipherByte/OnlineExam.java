package onlineExamination;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class online_Exam {
	private static String username;
	private static String password;
	private static boolean isLoggedIn = false;
	private static Map<String, String> userProfile = new HashMap<>();
	private static Map<String, Map<String, String>> questions = new HashMap<>();

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		initializeUserProfile();
		initializeQuestions();

		while (true) {
			if (!isLoggedIn) {
				System.out.println(" ---login---\n");
				System.out.print("Username:");
				String inputUsername = scanner.nextLine();
				System.out.print("Password:");
				String inputPassword = scanner.nextLine();
				if (login(inputUsername, inputPassword)) {
					System.out.println("Login successful.");
				} else {
					System.out.println("invalid username or password.\nPlease try again.");
					continue;
				}
			}

			System.out.println("1.Update Profile and Password");
			System.out.println("2.Start Exam");
			System.out.println("3.Logout");
			System.out.print("Enter your choice:");
			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				updateProfileAndPassword(scanner);
				break;
			case 2:
				startExam(scanner);
				break;
			case 3:
				logout();
				break;
			default:
				System.out.println("Invalid choice.Please try again.");
			}
		}
	}

	private static boolean login(String inputUsername, String inputPassword) {
		if ("admin".equals(inputUsername) && "password".equals(inputPassword)) {
			username = inputUsername;
			password = inputPassword;
			isLoggedIn = true;
			return true;
		}
		return false;
	}

	private static void updateProfileAndPassword(Scanner scanner) {
		System.out.println("Update Profile and password:");
		System.out.print("Enter new username:");
		String newUsername = scanner.nextLine();
		System.out.print("Enter new password:");
		String newPassword = scanner.nextLine();
		username = newUsername;
		password = newPassword;
		System.out.println("Profile and password updated successfully.");
	}

	private static void startExam(Scanner scanner) {
		System.out.println("Welcome to the exam!You have 30 minutes to complete it.");
		int totalQuestions = questions.size();
		int score = 0;

		for (Map.Entry<String, Map<String, String>> entry : questions.entrySet()) {
			String question = entry.getKey();
			Map<String, String> options = entry.getValue();
			String correctAnswer = options.get("Correct");
			System.out.println("Question: " + question);
			System.out.println("Options:");
			for (Map.Entry<String, String> optionEntry : options.entrySet()) {
				String optionKey = optionEntry.getKey();
				String optionValue = optionEntry.getValue();
				if (!optionKey.equals("Correct")) {
					System.out.println("(" + optionKey + ")" + optionValue);
				}
			}
			System.out.print("Your answer:");
			String userAnswer = scanner.nextLine().toUpperCase();

			if (userAnswer.equals(correctAnswer)) {
				score++;
			}
		}

		System.out.println("Your score: " + score + "/" + totalQuestions);
		System.out.println("Time's up!Your exam is automatically submitted.");
	}

	private static void logout() {
		isLoggedIn = false;
		System.out.println("Logout successful.");
	}

	private static void initializeUserProfile() {
		userProfile.put("Name", "Alice");
		userProfile.put("Email", "Alice@gmail.com");
	}

	private static void initializeQuestions() {
		Map<String, String> question1Options = new HashMap<>();
		question1Options.put("A", "Paris");
		question1Options.put("B", "London");
		question1Options.put("C", "Berlin");
		question1Options.put("D", "Madrid");
		question1Options.put("Correct", "A");

		Map<String, String> question2Options = new HashMap<>();
		question2Options.put("A", "William Shakespeare");
		question2Options.put("B", "Jane Austen");
		question2Options.put("C", "Mark Twain");
		question2Options.put("D", "Leo Tolstoy");
		question2Options.put("Correct", "A");

		Map<String, String> question3Options = new HashMap<>();
		question3Options.put("A", "Au");
		question3Options.put("B", "Ag");
		question3Options.put("C", "AuPt");
		question3Options.put("D", "AuH");
		question3Options.put("Correct", "A");

		Map<String, String> question4Options = new HashMap<>();
		question4Options.put("A", "Leonardo da Vinci");
		question4Options.put("B", "Vincent van Gogh");
		question4Options.put("C", "Pablo Picasso");
		question4Options.put("D", "Michelangelo");
		question4Options.put("Correct", "A");

		questions.put("What is the capital of France?", question1Options);
		questions.put("Who wrote 'Romeo and Juliet'?", question2Options);
		questions.put("What is the chemical symbol for gold?", question3Options);
		questions.put("Who painted the Mona Lisa?", question4Options);
	}

}
