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
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

import xyz.drean.controlbookparent.R
import xyz.drean.controlbookparent.adapter.AdapterObservation
import xyz.drean.controlbookparent.pojo.Observation

/**
 * A simple [Fragment] subclass.
 */
class DetailStudent : BottomSheetDialogFragment() {

    private var db: FirebaseFirestore? = null
    private var coll: CollectionReference? = null
    private var list: RecyclerView? = null
    private var adapter: AdapterObservation? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_detail_student, container, false)

        val idStudent = arguments!!.getString("id")
        val contx = arguments?.getString("contx")

        init(v)
        getData(idStudent!!, contx!!)
        return v
    }

    private fun init(v: View) {
        list = v.findViewById(R.id.list_observations)
        db = FirebaseFirestore.getInstance()
        coll = db?.collection("observations")

        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        list?.layoutManager = llm
    }

    private fun getData(idStudent: String, contx: String) {
        val query: Query = coll?.whereEqualTo("idStudent", idStudent)?.orderBy("id", Query.Direction.DESCENDING) as Query

        val options = FirestoreRecyclerOptions.Builder<Observation>()
            .setQuery(query, Observation::class.java)
            .build()

        adapter = AdapterObservation(options, activity!!, contx)
        list?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        list?.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter?.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter?.stopListening()
    }

}
