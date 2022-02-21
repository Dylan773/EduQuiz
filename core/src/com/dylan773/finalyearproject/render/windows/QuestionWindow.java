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
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;


/**
 *
 */
public class QuestionWindow extends WindowBuilder {

    // TODO - based on current level
    public Question currentQuestion = Assets.questions.get(getCurrentLevel().toString()).get(getQuestionIndex());
//        currentQuestion = (HashMap<String, Object>) ((HashMap<String, ArrayList>) Assets.questions.get("history")).get("en").get(9);

    // ======================================================

    public String questionText() { return currentQuestion.getQuestionText(); }

    public ArrayList<String> answers() { return currentQuestion.getAnswers(); }

    public int correctAnsIndex() { return currentQuestion.getCorrectAnsIndex(); }

    // ========================================================


    /**
     *
     */
    public QuestionWindow() {
        super(1000f, 600f);
        initWindow();
    }

    // TODO - dispose the window on window close?


    @Override
    protected void initWindow() {
        this.setVisible(true);

        ((GameLevel) CLIENT.getScreen()).disableUserInput();

        // Question hint label
        Label lblError = new Label("", SKIN, "font", Color.SALMON);
        lblError.setAlignment(Align.center);

        // Checkboxes for answer selection
        ButtonGroup<CheckBox> buttonGroup = new ButtonGroup();

        // Populating the btnSub group with CheckBox's
        answers().forEach(it -> buttonGroup.add(new CheckBox(it, SKIN)));
        buttonGroup.uncheckAll();

        debug();

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.align(Align.left);

        // Button submit handler
        TextButton btnSub = new TextButton("Submit", SKIN);
        btnSub.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                // TODO - reverse this
                if (buttonGroup.getCheckedIndex() == -1) {
                    lblError.setText("You have not selected an option.");
                    new DelayEvent(2000, () -> lblError.setText(""));
                } else if (buttonGroup.getChecked().getText().toString().equals(answers().get(correctAnsIndex()))) {
                    getStage().getActors().removeValue(QuestionWindow.this, true);
                    ((GameLevel) CLIENT.getScreen()).enableUserInput();
                    incrementQuestionIndex();
                } else {
                    lblError.setText("Incorrect answer.");
                    new DelayEvent(2000, () -> lblError.setText(""));
                }

            }
        });

        addLabel("Question", "subtitle").padBottom(20f).row();
        addLabel(questionText(), "default").padBottom(20f).row();

        buttonGroup.getButtons().forEach(verticalGroup::addActor);
        add(verticalGroup).row();
        add(btnSub).pad(20f, 0f, 20f, 0f).row();
        add(lblError);
    }

    /**
     *
     * @return
     */
    private GameLevel getScreen() {
        return (GameLevel) CLIENT.getScreen();
    }

    /**
     *
     * @return
     */
    private int getQuestionIndex() {
        return getScreen().questionIndex;
    }

    /**
     *
     * @return
     */
    private int incrementQuestionIndex() {
        return getScreen().questionIndex++;// TODO - THIS DOES NEED TO BE CHANGED
    }

    /**
     *
     * @return
     */
    private LevelFactory.Level getCurrentLevel() {
        return getScreen().currentLevel;
    }
}




