package com.dylan773.finalyearproject.render.windows;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.dylan773.finalyearproject.level.GameLevel;
import com.dylan773.finalyearproject.utilities.Assets;
import com.dylan773.finalyearproject.utilities.WindowBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import static com.dylan773.finalyearproject.EducationGame.CLIENT;
import static com.dylan773.finalyearproject.utilities.Assets.SKIN;
import static com.dylan773.finalyearproject.utilities.Utilities.addLabel;

public class QuestionWindow extends WindowBuilder {

    public HashMap<String, Object> currentQuestion;

    {
        currentQuestion = (HashMap<String, Object>) ((HashMap<String, ArrayList>) Assets.questions.get("science")).get("en").get(0);
    }

    public String questionText() {
        return (String) currentQuestion.get("q");
    }

    public ArrayList<String> answers() {
        return (ArrayList<String>) currentQuestion.get("a");
    }

    public int correctAnsIndex() {
        return (int) currentQuestion.get("i");
    }






    /**
     *
     */
    public QuestionWindow() {
        super(1000f, 600f);
        initWindow();
    }




    @Override
    protected void initWindow() {
        this.setVisible(true);

        TextButton button = new TextButton("Submit", SKIN);
        button.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getStage().getActors().removeValue(QuestionWindow.this, true);
                ((GameLevel) CLIENT.getScreen()).enableUserInput();
            }
        });

        ((GameLevel) CLIENT.getScreen()).disableUserInput();


        addLabel("Question", "subtitle").padBottom(50f).row();
        addLabel(questionText(), "default").padBottom(50f).row();

        ButtonGroup<CheckBox> buttonGroup = new ButtonGroup();
        buttonGroup.add(new CheckBox("answer one", SKIN));
        buttonGroup.add(new CheckBox("answer two", SKIN));
        buttonGroup.add(new CheckBox("answer three", SKIN));
        buttonGroup.add(new CheckBox("answer four", SKIN));


        int i = 0;
        for (CheckBox it : buttonGroup.getButtons()) {
            it.setText(answers().get(i));
            i++;
        }

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.align(Align.left);

        buttonGroup.getButtons().forEach(verticalGroup::addActor);
        add(verticalGroup).row();
        add(button);
    }
}



