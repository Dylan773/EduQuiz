package com.dylan773.finalyearproject.utilities

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.dylan773.finalyearproject.utilities.Assets.SKIN
import com.dylan773.finalyearproject.utilities.WindowBuilder.expandfill

open class TabPane @JvmOverloads constructor(
    val parentWindow: Window?,
    vararg tabs: Pair<String, Table>,
    val _skin: Skin = SKIN
) : Table() {

    // FIELDS
    private val tabBehaviour = ButtonGroup<TextButton>()
    private val tabLayout = HorizontalGroup().apply {
        add(this)
            .expandX()
            .fillX()
            .row()
        space(30f)
        align(Align.center).padTop(25f)
//        padRight(150f)

    } // add horizontal group to table


    private val contentCell = expandfill(add()) // a blank cell for later
    private val tabList = HashMap<TextButton, Table>()

    private val clickListener = object : ClickListener() {
        override fun clicked(event: InputEvent?, x: Float, y: Float) {
            with(event?.listenerActor as TextButton) {
                tabList[this]?.apply {
                    showTab(this)
                    parentWindow?.pack()
                }
            }
        }
    }

    init {
        tabs.forEach { addTab(it) }
        showTab(tabs[0].second)
    }

    fun showTab(tab: Pair<TextButton, Table>) = showTab(tab.second)
//    fun showTab(tab: String) = showTab(tab.second)
//    fun showTab(index: Integer) = showTab()
    fun showTab(tab: Table) {
        contentCell.setActor(tab)
    }


    fun addTab(tab: Pair<String, Table>) = addTab(tab.first, tab.second)

    fun addTab(tabName: String, tabContent: Table) {
        object : TextButton(tabName, _skin) {
            override fun getPrefWidth(): Float {
                return 250f
            }
        }.apply {
            tabLayout.addActor(this)
            tabBehaviour.add(this)
            tabList[this] = tabContent // basically .put


//            tabContent.invalidate()

            addListener(this@TabPane.clickListener)
        }
    }


}