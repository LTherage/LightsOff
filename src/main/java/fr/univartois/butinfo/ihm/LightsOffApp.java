package fr.univartois.butinfo.ihm;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.Objects;


public class LightsOffApp {
        @FXML
        private LightsOffModel model;
        @FXML
        private GridPane gridPane;
        @FXML
        private Label affichage;

        @FXML
        private ImageView imageTriche;


        @FXML
        private void creerPartie() {

            gridPane.getChildren().clear();
            // Ne pas utiliser Scanner avec JavaFX
            // À la place, utilisez des valeurs fixes pour l'instant
            int longueur = 5; // valeur par défaut
            int largeur = 5;  // valeur par défaut

            // Initialisation du modèle
            model = new LightsOffModel(longueur, largeur);

            for (int i = 0; i < longueur; i++) {
                for (int j = 0; j < largeur; j++) {
                    Button cellule = new Button();
                    cellule.getStyleClass().add("grid-cell");
                    cellule.setMinSize(50, 50);
                    int finalI = i;
                    int finalJ = j;
                    cellule.setOnAction(e -> basculerLumiere(finalI, finalJ));
                    // Définir l'apparence initiale du bouton
                    mettreAJourApparenceBouton(cellule, i, j);
                    gridPane.add(cellule, i, j);
                }
            }
            mettreAJourAffichage();
        }

    @FXML
    private void randomiserGrille() {
        if (model != null) {
            model.randomiserGrille();
            mettreAJourAffichage();
        }
    }


    @FXML
    private void tricher() {
        try {
            if (model != null) {
                model.tricheGrille();
                mettreAJourAffichage();
            }

            // Créer une boîte de dialogue
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("C'est pas bien de tricher !");
            dialog.setHeaderText(null);  // On enlève le header text pour un look plus propre

            // Charger l'image
            String imagePath = "/fr/univartois/butinfo/ihm/c'est_pas_bien.jpg";
            Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(300);
            imageView.setFitHeight(300);
            imageView.setPreserveRatio(true);

            // Ajouter une transition de fondu
            imageView.setOpacity(0);  // L'image commence invisible

            // Configurer le contenu de la boîte de dialogue
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setContent(imageView);
            dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());

            // Ajouter un bouton personnalisé
            ButtonType fermerType = new ButtonType("J'ai compris", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().add(fermerType);

            // Animation de fondu

            imageTriche.setVisible(true);
            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), imageTriche);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();


            // Afficher la boîte de dialogue
            dialog.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
            affichage.setText("Erreur lors du chargement de l'image");
        }
    }

    @FXML
        private void mettreAJourAffichage() {
            // Mettre à jour l'apparence de tous les boutons
            for (int i = 0; i < model.getLongueur(); i++) {
                for (int j = 0; j < model.getLargeur(); j++) {
                    Button bouton = getBoutonAt(i, j);
                    if (bouton != null) {
                        mettreAJourApparenceBouton(bouton, i, j);
                    }
                }
            }

            // Vérifier si le jeu est terminé
            if (model.estTermine()) {
                affichage.setText("Félicitations ! Vous avez gagné !");
            }
        }

        private Button getBoutonAt(int i, int j) {
            for (var node : gridPane.getChildren()) {
                if (node instanceof Button &&
                        GridPane.getRowIndex(node) == j &&
                        GridPane.getColumnIndex(node) == i) {
                    return (Button) node;
                }
            }
            return null;
        }

    private void mettreAJourApparenceBouton(Button bouton, int i, int j) {
        bouton.getStyleClass().clear();
        bouton.getStyleClass().add("grid-cell");
        if (model.getEtatLumiere(i, j)) {
            bouton.getStyleClass().add("light-on");
        } else {
            bouton.getStyleClass().add("light-off");
        }
    }


    @FXML
        private void basculerLumiere(int i, int j) {
            model.basculerLumiere(i, j);
            mettreAJourAffichage();
        }
}


