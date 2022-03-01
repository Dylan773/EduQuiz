package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.level.LevelFactory;
import com.dylan773.finalyearproject.level.Question;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.DelayEvent;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import java.util.ArrayList;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.level.GameLevel.decreasePlayerLives;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.AudioController.playCorrectAns;
import static com.dylan773.finalyearproject.utilities.AudioController.playIncorrectAns;
import static com.dylan773.finalyearproject.utilities.Utilities.destroyActor;

// TODO - dispose the window on window close?

/**
 * <h1>Window that displays the in-game questions to the user</h1>
 *
 * @author Dylan Brand
 */
public class QuestionWindow extends WindowBuilder {

    /**
     * Initialises the window upon instantiation.
     */
    public QuestionWindow() {
        super(1000f, 600f);
        initWindow();
    }


    /**
     * Content to be displayed on the window.
     */
    @Override
    protected void initWindow() {
        this.setVisible(true);

        // Disables user input whilst the window is active/visible
        ((GameLevel) CLIENT.getScreen()).disableUserInput();

        // Question hint label
        Label lblError = new Label("", SKIN, "font", Color.SALMON);

        // Checkboxes for answer selection
        ButtonGroup<CheckBox> buttonGroup = new ButtonGroup();

        // Populating the btnSub group with CheckBox's
        answers().forEach(it -> buttonGroup.add(new CheckBox(it, SKIN)));
        buttonGroup.getButtons().shuffle();
        buttonGroup.uncheckAll();

        // Vertical group for answer options.
        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.columnAlign(Align.left);
        verticalGroup.space(3f);

        // TODO - theres A LOT going on here, refactor?
        // Button Submit handler
        TextButton btnSub = new TextButton("Submit", SKIN);
        btnSub.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (buttonGroup.getCheckedIndex() == -1) {
                    lblError.setText("You have not selected an option.");
                    new DelayEvent(2000, () -> lblError.setText("")); // Sets the label's text to empty after 2 seconds.
                } else if (buttonGroup.getChecked().getText().toString().equals(answers().get(correctAnsIndex()))) {
                    playCorrectAns(); // PLay a correct answer notification.
                    destroyActor(QuestionWindow.this); // Destroy/remove the question window.
                    incrementQuestionIndex();
                    ((GameLevel) CLIENT.getScreen()).enableUserInput(); // re-enable user input
                } else {
                    playIncorrectAns(); // Plays an incorrect answer notification.
                    lblError.setText("Incorrect answer."); // Display a label, alerting the user of an incorrect answer.
                    decreasePlayerLives(QuestionWindow.this); // TODO - not great providing an actor as a parameter, find a better solution.
                    new DelayEvent(2000, () -> lblError.setText("")); // Sets the label's text to empty after 2 seconds.
                }
            }
        });

        // Adding actors to the parent.
        addWindowLabel("Question", "subtitle").padTop(30f).row();
        addWindowLabel("----------", "subtitle").row();
        addWindowLabel(questionText(), "default").row();

        buttonGroup.getButtons().forEach(verticalGroup::addActor);

        add(verticalGroup).expandY().row();
        add(btnSub).pad(10f, 0f, 15f, 0f).row();
        add(lblError);
    }


    /**
     * The current question to be displayed, alongside with its possible answers.
     */
    public Question currentQuestion = Assets.questions.get(getCurrentLevel().toString()).get(getQuestionIndex());
    //currentQuestion = (HashMap<String, Object>) ((HashMap<String, ArrayList>) Assets.questions.get("history")).get("en").get(9);


    /**
     *
     * @return The current question's, answer text.
     */
    public String questionText() {
        return currentQuestion.getQuestionText();
    }


    /**
     *
     * @return An ArrayList of Strings, with each String representing a possible answer to the current question.
     */
    public ArrayList<String> answers() {
        return currentQuestion.getAnswers();
    }


    /**
     *
     * @return The index of the correct answer to the current question.
     */
    public int correctAnsIndex() {
        return currentQuestion.getCorrectAnsIndex();
    }


    /**
     * @return The {@link GameLevel}'s screen.
     */
    private GameLevel getScreen() {
        return (GameLevel) CLIENT.getScreen();
    }


    /**
     * @return The current question's index.
     */
    private int getQuestionIndex() {
        return getScreen().questionIndex;
    }


    /**
     * Increments the current question's index by +1.
     */
    private void incrementQuestionIndex() {
        getScreen().questionIndex++;
    }


    /**
     * @return The current {@link LevelFactory} level.
     */
    private LevelFactory.Level getCurrentLevel() {
        return getScreen().currentLevel;
    }
}