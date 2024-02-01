package laddergame.controller;

import java.util.List;
import laddergame.model.ErrorMessage;
import laddergame.model.LadderGenerator;
import laddergame.model.ladder.Height;
import laddergame.model.ladder.Ladder;
import laddergame.model.ladder.RungCreateDecider;
import laddergame.model.participant.Participant;
import laddergame.model.participant.Participants;
import laddergame.view.InputView;
import laddergame.view.OutputView;

public class LadderGameController {

  private final InputView inputView = new InputView();
  private final OutputView outputView = new OutputView();
  private final RungCreateDecider rungCreateDecider = new RungCreateDecider();
  private final LadderGenerator ladderGenerator = new LadderGenerator();

  public void runLadderGame() {
    Participants participants = createParticipants();
    Height height = createHeight();
    Ladder ladder = ladderGenerator.generateLadder(participants, height, rungCreateDecider);

    outputView.printResult(participants, ladder);
  }

  private Participants createParticipants() {
    try {
      List<String> receivedNames = inputView.receiveNames();
      return new Participants(receivedNames.stream()
          .map(Participant::new)
          .toList());
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
      return createParticipants();
    }
  }

  private Height createHeight() {
    try {
      int receivedColumn = inputView.receiveHeight();
      return new Height(receivedColumn);
    } catch (NumberFormatException exception) {
      System.out.println(ErrorMessage.NOT_A_NUMBER.getMessage());
      return createHeight();
    } catch (IllegalArgumentException exception) {
      System.out.println(exception.getMessage());
      return createHeight();
    }
  }
}