package xyz.drean.controlbookparent.fragment


import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_profile.view.*
import xyz.drean.controlbookparent.Login

import xyz.drean.controlbookparent.R
import xyz.drean.controlbookparent.abstraction.DataBase
import xyz.drean.controlbookparent.pojo.Parent

/**
 * A simple [Fragment] subclass.
 */
class Profile : Fragment() {

    var parent: Parent? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        getDataParent(v)

        v.save_acces_assist.setOnClickListener { saveAccess(v) }

        v.card_logout.setOnClickListener {
            savePreference("")
            val i = Intent(activity, Login::class.java)
            activity!!.startActivity(i)
        }

        return v
    }

    private fun savePreference(idAssistant: String) {
        val sp = activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val editor = sp?.edit()
        editor?.putString("idParent", idAssistant)?.apply()
    }

    private fun getPreference(field: String): String {
        val sp = activity?.getSharedPreferences("config", Context.MODE_PRIVATE)
        val date = sp?.getString(field, "Parent")
        return date!!
    }

    private fun getDataParent(v: View) {
        val db = FirebaseFirestore.getInstance()
        db.collection("parents")
            .document(getPreference("idParent"))
            .get()
            .addOnSuccessListener {document ->
                val parent = document.toObject(Parent::class.java)
                if(document.exists()){
                    this.parent = parent
                    v.name_aux.text = parent!!.name
                    v.et_user_profile.setText(parent.user)
                    v.et_password_profile.setText(parent.password)
                }
            }
    }

    private fun saveAccess(v: View) {
        val dab = DataBase(activity!!)
        v.save_acces_assist.setOnClickListener {
            parent!!.user = v.et_user_profile.text.toString()
            parent!!.password = v.et_password_profile.text.toString()
            dab.addItem(parent!!, getPreference("idAssistant"), "parents", "¡Datos Actualizados!")
        }
    }

}
