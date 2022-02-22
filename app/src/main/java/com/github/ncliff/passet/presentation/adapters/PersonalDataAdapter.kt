package com.github.ncliff.passet.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.ncliff.passet.R
import com.github.ncliff.passet.presentation.models.DataStorage
import com.github.ncliff.passet.presentation.models.PersonalData
import java.lang.IllegalArgumentException

class PersonalDataAdapter(private val onClickListener: (position: Int) -> Unit) :
    RecyclerView.Adapter<PersonalDataAdapter.ViewHolder>() {
    private var personalList = ArrayList<PersonalData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            DataStorage.SOCIAL_ITEM -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_social, parent, false))
            DataStorage.WEB_ITEM -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_web, parent, false))
            DataStorage.SHOP_ITEM -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shop, parent, false))
            DataStorage.DEVICE_ITEM -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false))
            DataStorage.EMAIL_ITEM -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_email, parent, false))
            DataStorage.BANK_ITEM -> ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_bank, parent, false))
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    private fun getItem(position: Int) = personalList[position]

    override fun getItemCount() = personalList.size

    override fun getItemViewType(position: Int) = getItem(position).type

    @SuppressLint("NotifyDataSetChanged")
    fun addPersonalList(personalDataList: List<PersonalData>) {
        personalList = ArrayList(personalDataList)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val name: TextView? = view.findViewById(R.id.item_name)
      //private val lineColor: View? = view.findViewById(R.id.leftLine)
      //private val background: View? = view.findViewById(R.id.itemCardView)

        @SuppressLint("ResourceAsColor")
        fun bind(personalData: PersonalData) {
            name?.text = personalData.name
        }
    }
}