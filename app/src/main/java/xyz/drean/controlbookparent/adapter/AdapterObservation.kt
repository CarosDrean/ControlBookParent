package xyz.drean.controlbookparent.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import xyz.drean.controlbookparent.R
import xyz.drean.controlbookparent.pojo.Observation

class AdapterObservation (
    options: FirestoreRecyclerOptions<Observation>,
    private val activity: Activity,
    private val contx: String
) : FirestoreRecyclerAdapter<Observation, ObservationHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObservationHolder {
        val va = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        val vo = LayoutInflater.from(parent.context).inflate(R.layout.item_observation, parent, false)
        return if (contx == "assistance") {
            StudentHolderAsist(va)
        } else {
            StudentHolderObs(vo)
        }
    }

    override fun onBindViewHolder(holder: ObservationHolder, i: Int, model: Observation) {
        holder.bind(model, i)
    }

    inner class StudentHolderAsist(itemView: View) : ObservationHolder(itemView) {

        private var name: TextView = itemView.findViewById(R.id.txt_name_student)
        private val lastname: TextView = itemView.findViewById(R.id.txt_lastname_student)
        private val assistance: CheckBox = itemView.findViewById(R.id.check_student)

        override fun bind(model: Observation, position: Int) {
            name.text = model.date

            lastname.text = "No Asistio"

            if(model.assistance != null) {
                lastname.text = "Asistio"
                assistance.isChecked = model.assistance!!.toBoolean()
            }

        }

    }

    inner class StudentHolderObs(itemView: View) : ObservationHolder(itemView) {

        private val date: TextView = itemView.findViewById(R.id.tv_date)
        private val text: TextView = itemView.findViewById(R.id.tv_text)

        override fun bind(model: Observation, position: Int) {
            date.text = model.date
            if(model.observation == "" || model.observation == null) {
                text.text = "Sin Observacion."
            } else {
                text.text = model.observation
            }

        }

    }
}

open class ObservationHolder (view: View): RecyclerView.ViewHolder(view) {
    open fun bind(model: Observation, position: Int) {}
}