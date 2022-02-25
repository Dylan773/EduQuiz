package com.dylan773.finalyearproject.render.windows

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.dylan773.finalyearproject.EducationGame
import com.dylan773.finalyearproject.level.GameLevel
import com.dylan773.finalyearproject.level.LevelFactory
import com.dylan773.finalyearproject.utilities.Assets.SKIN
import com.dylan773.finalyearproject.utilities.WindowBuilder

/**
 * A pop-up window that is instantiated when the user has no lives remaining during their current game session.
 *
 * This window notifies the user that they have no lives remaining and that the current level will re-start.
 *
 * @author Dylan Brand
 */
class RestartLevel : WindowBuilder(1000f, 600f) {

    // Window initialisation
    init {
        initWindow()
        this.debug = true
    }

    /**
     * Content to be displayed on the window.
     */
    override fun initWindow() {
        isVisible = true

        addLabel("Uh-oh", "subtitle").align(Align.bottom).padBottom(50f).row()
        addLabel("You ran out of lives!", "default").align(Align.center).row()
        addLabel("Ready to try again? Click the button below to restart the level.", "default").center().row()

        // Window close button
        TextButton("Restart", SKIN).apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    EducationGame.CLIENT.screen = LevelFactory.newLevel(GameLevel.currentLevel)
                }
            })

            this@RestartLevel.add(this)
        }
    }
}
