package de.jkrech.tutorial.chatty.application.vocab

data class VocabTrainerResponse(
//    val status: String, // "waiting for answer" oder "end of conversation"
//    val currentWord: String?,
//    val nextWord: String?,
//    val message: String,
//    val feedback: String?,
//    val correctAnswer: Boolean?,
//    val remainingWords: Int

     val status: String, // "active | completed", // "active" when there are more words, "completed" when all words are done
     val currentWord: String?, // "the current word to translate or null if no word is available",
     val nextWord: String?,
     val sentence: String?, //"the German sentence containing the current word, null if completed",
     val message: String, //"Message to the user",
     val feedback: String?, // "Feedback on the user's answer or null if no answer has been given yet",
     val isCorrect: Boolean?, //true | false | null,
     val pointsAwarded: Int, // 0 | 5 | 10,
     val totalPoints: Int, //number,
     val remainingWords: Int // number
)