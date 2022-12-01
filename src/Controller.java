import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class Controller implements Initializable {
  AnimationTimer gameLoop;

  @FXML
  private AnchorPane plane;
  @FXML
  private Rectangle bird;

  double yDelta = 0.02;
  double time  = 0;
  int jumpHeight = 100;
  Random random = new Random();

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    load();

    gameLoop = new AnimationTimer() {
      @Override
      public void handle(long l) {
        update();
      }
    };
    gameLoop.start();
  }

  @FXML
  void pressed(KeyEvent event) {
    if (event.getCode() == KeyCode.SPACE) {
      fly();
    }
  }

  private void fly() {
    if(bird.getLayoutY() + bird.getY() <= jumpHeight) {
      moveBirdY(-(bird.getLayoutY() + bird.getY()));
      time = 0;
      return;
    }

    moveBirdY(-jumpHeight);
    time = 0;
  }

  // Called every game frame
  private void update() {
    time++;
    moveBirdY(yDelta * time);

    if(isBirdDead()) {
      resetBird();
    }
  }

  // Everything called once, at the game start
  private void load() {
    System.out.println("Game Starting");
  }

  private void moveBirdY(double positionChange) {
    bird.setY(bird.getY() + positionChange);
  }

  private boolean isBirdDead() {
    double birdY = bird.getLayoutY() + bird.getY();
    return birdY >= plane.getHeight();
  }

  private void resetBird(){
    bird.setY(0);
    time = 0;
  }

  private void createObstacles() {
    int width = 25;
    double xPos = plane.getWidth() - 100;
    double space = 150;
    double recTopHeight = random.nextInt((int) (plane.getHeight() - space - 100) + 50);
    System.out.println(recTopHeight);
    double recBottomHeight = plane.getHeight() - space - recTopHeight;


    Rectangle rectangleTop = new Rectangle(xPos, 0, width, recTopHeight);
    Rectangle rectangleBottom = new Rectangle(xPos, recTopHeight + space, width, recBottomHeight);

    plane.getChildren().addAll(rectangleTop, rectangleBottom);
  }

}
