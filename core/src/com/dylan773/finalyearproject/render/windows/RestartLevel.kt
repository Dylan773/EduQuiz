package com.dylan773.finalyearproject.render.windows

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.dylan773.finalyearproject.EducationGame
import com.dylan773.finalyearproject.entities.Player
import com.dylan773.finalyearproject.level.GameLevel
import com.dylan773.finalyearproject.level.LevelFactory
import com.dylan773.finalyearproject.utilities.Assets.SKIN
import com.dylan773.finalyearproject.utilities.Utilities.addWindowLabel
import com.dylan773.finalyearproject.utilities.WindowBuilder

/**
 * A pop-up window that is instantiated when the user has no lives remaining in their current game session.
 *
 * This window notifies the user that they have no lives remaining and that the current level will re-start.
 *
 * @author Dylan Brand
 */
class RestartLevel : WindowBuilder(700f, 400f) {

    init {
        initWindow()
        Player.pauseMovement()
    }


    /**
     * Content to be displayed on the window.
     */
    override fun initWindow() {
        pad(30f)

        addWindowLabel("Uh-oh", "subtitle", this).table.top().row()
        addWindowLabel("You ran out of lives!", "default", this).pad(25f, 0f, 10f, 0f).row()
        addWindowLabel("Click the button below to restart the level.", "default", this).padBottom(70f).row()

        // Window close button
        TextButton("Try Again!", SKIN).apply {
            addListener(object : ClickListener() {
                override fun clicked(event: InputEvent?, x: Float, y: Float) {
                    disposeCurrentLevel()
                    EducationGame.CLIENT.screen = LevelFactory.newLevel(GameLevel.currentLevel)
                }
            })

            this@RestartLevel.add(this).padBottom(0f)
        }
    }
}


/**
 * ## Memory management method
 * Disposes the current Level's [GameLevel.map], and the Box2D [GameLevel.world]. */
private fun disposeCurrentLevel() {
    GameLevel.getMap().dispose()
    GameLevel.getWorld().dispose()
}

