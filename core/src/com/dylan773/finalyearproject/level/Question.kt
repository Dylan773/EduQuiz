package com.dylan773.finalyearproject.level

import kotlin.collections.ArrayList

/**
 * Constructs a question.
 *
 * @property questionText The text of the question.
 * @property answers The ArrayList of possible answers.
 * @property correctAnsIndex The index of the correct answer in [answers]
 *
 * @author Dylan Brand
 */
class Question(
    val questionText: String,
    val answers: ArrayList<String>,
    val correctAnsIndex: Int
)

/**
 * An ArrayList of [Question], providing easy access to game questions.
 */
open class Questions : ArrayList<Question>()

/**
 * A class that represents a Level, extending the ArrayList of [Question]'s.
 */
class Level : Questions() // A Level stores an ArrayList of Question

/**
 * HashMap of Strings to Levels.
 *
 */
class QuestionData : HashMap<String, Level>() { // HashMap of String to ArrayList<Question> (Level)
    companion object {

        private const val QUESTION = "q"
        const val ANSWERS = "a"
        const val CORRECT_INDEX = "i"
        const val LANG = "en"

        /**
         * TODO - change this Javadoc
         * - Provides the [constructTree] with the XML file in the assets.
         * - Iterates through all keys in the file ("en", "de", "fr" etc), constructing a [Level] with each key as a
         *   HasMap<String, Object>. A [Level] is just an ArrayList<Question>.
         *
         * @param fromXML
         * @return
         */
        @JvmStatic
        fun constructTree(fromXML: HashMap<String, *>) = //provide the constructTree with XML file in assets
            QuestionData().apply {
                fromXML.keys.forEach { // it = "history", "maths", etc
                    this[it] = constructLevel(fromXML[it] as HashMap<String, *>)
                }
            }

        // TODO - look at apply, let and also methods.
        /**
         * - Accepts a HashMap<String, Object> as a parameter.
         * - Uses the [LANG] and casts it to an ArrayList of HashMap<String, Object>
         * - Then iterates through the ArrayList, constructing a [Question] object with each iteration,
         *   then adding (applying) each question to the [Level] ArrayList.
         *
         * @param fromXML
         * @return
         */
        private fun constructLevel(fromXML: HashMap<String, *>) =
            Level().apply { // Level = ArrayList<Question>
                (fromXML[LANG] as ArrayList<HashMap<String, *>>) // [EN] -> .get("en") in Java
                    .forEach { add(constructQuestion(it)) }

                shuffle()
            }


        /**
         * Helper method that constructs a singular game question.
         *
         * @param fromXML
         * @return
         */
        private fun constructQuestion(fromXML: HashMap<String, *>) =
            Question(
                fromXML[QUESTION] as String, // .get("q") then casts it as a String
                fromXML[ANSWERS] as ArrayList<String>, // .get("a") then casts it as an ArrayList<String>
                fromXML[CORRECT_INDEX] as Int // .get("i") then casts it as a Int
            )
    }


    fun shuffleLevels() {
        keys.forEach {
            get(it)?.shuffle()
        }
    }
}