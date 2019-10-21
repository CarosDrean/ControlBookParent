package xyz.drean.controlbookparent.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import xyz.drean.controlbookparent.R
import xyz.drean.controlbookparent.adapter.AdapterMeeting
import xyz.drean.controlbookparent.pojo.Meeting

/**
 * A simple [Fragment] subclass.
 */
class Register : Fragment() {

    private var db: FirebaseFirestore? = null
    private var collM: CollectionReference? = null
    private var listM: RecyclerView? = null
    private var adapterM: AdapterMeeting? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_register, container, false)
        initM(v)
        getDataM()
        return v
    }

    private fun initM(v: View) {
        listM = v.findViewById(R.id.list_meetings)
        db = FirebaseFirestore.getInstance()
        collM = db?.collection("meetings")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        listM?.layoutManager = llm
    }

    private fun getDataM() {
        val query: Query = collM as Query

        val options = FirestoreRecyclerOptions.Builder<Meeting>()
            .setQuery(query, Meeting::class.java)
            .build()

        adapterM = AdapterMeeting(options, activity!!)
        listM?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        listM?.adapter = adapterM
    }

    override fun onStart() {
        super.onStart()
        adapterM!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapterM!!.stopListening()
    }

}
