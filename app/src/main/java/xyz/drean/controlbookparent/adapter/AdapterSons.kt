package xyz.drean.controlbookparent.adapter

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import xyz.drean.controlbookparent.R
import xyz.drean.controlbookparent.fragment.DetailStudent
import xyz.drean.controlbookparent.pojo.Student

class AdapterSons(
    options: FirestoreRecyclerOptions<Student>,
    private val activity: Activity,
    private val contx: String
) : FirestoreRecyclerAdapter<Student, AdapterSons.SonsHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SonsHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_sons, parent, false)
        return SonsHolder(v)
    }

    override fun onBindViewHolder(holder: SonsHolder, p1: Int, model: Student) {
        holder.name.text = model.name
        holder.lastname.text = model.lastname

        holder.content.setOnClickListener { lead(model.id, model.name!!, model.lastname!!) }
    }

    private fun lead(idClass: String?, name: String, lastname: String) {
        val args = Bundle()
        args.putString("id", idClass)
        args.putString("contx", contx)
        args.putString("name", name)
        args.putString("lastname", lastname)
        val students = DetailStudent()
        students.arguments = args
        students.show((activity as AppCompatActivity).supportFragmentManager, "Add Class Rom")
    }

    inner class SonsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val name: TextView = itemView.findViewById(R.id.txt_name_sons)
        val lastname: TextView = itemView.findViewById(R.id.txt_lastname)
        val content: RelativeLayout = itemView.findViewById(R.id.content_sons)

    }
}