package szathmary.peter.pocketbrain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import java.lang.Exception
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val answerText: TextView = findViewById(R.id.resultText)
        val question: EditText = findViewById(R.id.question_edit)
        val search: Button = findViewById(R.id.search_button)
        search.setOnClickListener {
            val input = question.text.toString()
            var query: String = URLEncoder.encode(input, "utf-8")
            runBlocking {
                try {
                    query = fetchData(query)
                    answerText.text = query
                } catch (e: Exception) {
                    val splitted =  e.message?.split("Text: ")
                    answerText.text = splitted?.get(1)
                }
            }
        }
    }

    private suspend fun fetchData(input: String): String {
        HttpClient().use {
            val url =
                " http://api.wolframalpha.com/v1/result?appid=YG962V-EY3REXEEJT&i=$input&units=metric"
            val httpResponse: HttpResponse = it.get(url)
            return httpResponse.receive()
        }
    }
}