package com.dylan773.finalyearproject.render.windows

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.dylan773.finalyearproject.EducationGame.CLIENT
import com.dylan773.finalyearproject.entities.Player
import com.dylan773.finalyearproject.utilities.Assets.SKIN
import com.dylan773.finalyearproject.utilities.Utilities.addWindowLabel
import com.dylan773.finalyearproject.utilities.Utilities.destroyActor
import com.dylan773.finalyearproject.utilities.WindowBuilder

/**
 * # Level Information Window.
 *
 * This window will be shown at the start of the FIRST level in a game cycle, displaying basic game behaviour.
 *
 * @author Dylan Brand
 */
class LevelInfoWindow : WindowBuilder(1000f, 600f) {

    init {
        initWindow()
        Player.pauseMovement()
    }

    /** The content for this window. */
    override fun initWindow() {
        // Game info label
        val lblInfo = Label(
            "Each level consists of four questions from their respective topic.\n" +
                    "Your aim is to explore each map, unlocking new areas by correctly\n" +
                    "answering the prompted questions. But, you only have FOUR lives.", SKIN
        ).apply { setAlignment(Align.center) } // sets the label to the centre of its cell }

        // Player info label
        val lblLives = Label(
            "Each incorrect answer will deduct one life. If you run out of lives,\n" +
                    "the level will re-start, so choose carefully!", SKIN
        ).apply { setAlignment(Align.center) }

        val checkBox = CheckBox("Don't show again.", SKIN)

        val button = TextButton("OK!", SKIN)
        button.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                if (checkBox.isChecked) CLIENT.setLevelInfoVisibility(true)
                destroyActor(this@LevelInfoWindow)
                Player.resumeMovement()
            }
        })

        // Adding the actors to the window
        addWindowLabel("Welcome to Edu Quiz!", "subtitle", this).table.top().padTop(30f).row()
        addWindowLabel("-----------------", "subtitle", this).row() // kotlin .row calls getRow?
        add(lblInfo).padBottom(15f).row()
        add(lblLives).padBottom(15f).row()
        addWindowLabel("READY?", "default", this).row()
        add(checkBox).expandY().row()
        add(button)
    }
}