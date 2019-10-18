package xyz.drean.controlbookparent.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import xyz.drean.controlbookparent.Login

import xyz.drean.controlbookparent.R
import xyz.drean.controlbookparent.Sons
import xyz.drean.controlbookparent.pojo.Parent

/**
 * A simple [Fragment] subclass.
 */
class Home : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        v.card_asistencias.setOnClickListener {
            val i = Intent(activity, Sons::class.java)
            i.putExtra("context", "assistance")
            i.putExtra("idParent", getPreference("idParent"))
            activity?.startActivity(i)
        }

        v.card_observation.setOnClickListener {
            val i = Intent(activity, Sons::class.java)
            i.putExtra("context", "observation")
            i.putExtra("idParent", getPreference("idParent"))
            activity?.startActivity(i)
        }

        verifyLogin()

        return v
    }

    private fun verifyLogin() {
        if(getPreference("idParent") == "Parent" || getPreference("idParent") == "") {
            val i = Intent(activity, Login::class.java)
            startActivity(i)
        } else {
            getDataParent()
        }
    }

    private fun getPreference(field: String): String {
        val sp = activity?.getSharedPreferences("config", Context.MODE_PRIVATE)
        val date = sp?.getString(field, "Parent")
        return date!!
    }

    private fun getDataParent() {
        val db = FirebaseFirestore.getInstance()
        db.collection("parents")
            .document(getPreference("idParent"))
            .get()
            .addOnSuccessListener {document ->
                val parent = document.toObject(Parent::class.java)

                if(document.exists()){
                    name_par.text = parent!!.name
                }
            }
    }

}
