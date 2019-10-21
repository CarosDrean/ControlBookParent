package xyz.drean.controlbookparent

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.android.synthetic.main.activity_sons.*
import xyz.drean.controlbookparent.adapter.AdapterSons
import xyz.drean.controlbookparent.pojo.Student
import java.util.*

class Sons : AppCompatActivity() {

    private var db: FirebaseFirestore? = null
    private var coll: CollectionReference? = null
    private var list: RecyclerView? = null
    private var adapter: AdapterSons? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sons)

        //updateCalendar()

        val contx = intent.getStringExtra("context")
        val idParent = intent.getStringExtra("idParent")
        /*if(contx != "assistance") {
            card_calendar.isVisible = false
        }*/

        //calendarClick()

        init()
        getData(contx, idParent)
    }

    private fun init() {
        list = findViewById(R.id.list_classrom)
        db = FirebaseFirestore.getInstance()
        coll = db?.collection("students")

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        list?.layoutManager = llm
    }

    private fun getData(contx: String, idParent: String) {
        val query: Query = coll?.whereEqualTo("idParent", idParent) as Query

        val options = FirestoreRecyclerOptions.Builder<Student>()
            .setQuery(query, Student::class.java)
            .build()

        adapter = AdapterSons(options, this, contx)
        list?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        list?.adapter = adapter
    }

    /*private fun updateCalendar(){
        calendar_class.setSelectedDate(Calendar.getInstance())
        tv_cal.text = "Fecha: Hoy"

        var date = tv_cal.text.toString().substring(7)
        if(date == "Hoy") {
            date = getDate(CalendarDay.today())
        }
        savePreference(date)
    }

    private fun calendarClick() {
        calendar_class.setOnDateChangedListener { w, date, s ->
            val dateSelect = getDate(date)
            tv_cal.text = "Fecha: $dateSelect"
            savePreference(dateSelect)
        }
    }

    private fun getDate(date: CalendarDay): String {
        return (String.format("%02d",date.day)
                + "/" + String.format("%02d",date.month + 1)
                + "/" + date.year)
    }

    private fun savePreference(value: String) {
        val sp = getSharedPreferences("config", Context.MODE_PRIVATE)
        val editor = sp?.edit()
        editor?.putString("dateAssistance", value)?.apply()
    }*/

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

}
