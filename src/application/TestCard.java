package application;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import models.Card;
import models.Question;

public class TestCard {
	private static Card quizData;

    public static void main(String[] args) {
        chargerCartes("questions.json"); // Assure-toi que le fichier JSON est bien dans le dossier du projet
        jouer();
    }

    private static void chargerCartes(String fichier) {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(fichier)) {
            quizData = gson.fromJson(reader, Card.class);
            System.out.println("📂 Données chargées avec succès !");
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier JSON !");
            e.printStackTrace();
        }
    }

    private static void jouer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n🎯 Thème : " + quizData.getTheme());
        System.out.println("⚽ Sujet : " + quizData.getSubject());

        // 1️⃣ Sélection de la difficulté
        System.out.print("\nChoisissez une difficulté (1 à 4) : ");
        int difficulte = scanner.nextInt();
        scanner.nextLine(); // Consommer le retour à la ligne

        // 2️⃣ Trouver la question correspondante
        Question questionChoisie = null;
        for (Question q : quizData.getCard()) {
            if (q.getDifficulty() == difficulte) {
                questionChoisie = q;
                break;
            }
        }

        if (questionChoisie == null) {
            System.out.println("❌ Aucune question pour cette difficulté !");
            return;
        }

        // 3️⃣ Poser la question
        System.out.println("\n❓ " + questionChoisie.question);
        System.out.print("\nVotre réponse : ");
        String reponseUtilisateur = scanner.nextLine().trim();

        // 4️⃣ Vérification de la réponse
        if (reponseUtilisateur.equalsIgnoreCase(questionChoisie.answer)) {
            System.out.println("✅ Bonne réponse !");
        } else {
            System.out.println("❌ Mauvaise réponse ! La bonne réponse était : " + questionChoisie.answer);
        }

        scanner.close();
    }

}
