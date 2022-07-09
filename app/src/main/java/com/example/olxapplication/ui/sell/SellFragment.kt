package com.example.olxapplication.ui.sell

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.olxapplication.R
import com.example.olxapplication.model.CategoriesModel
import com.example.olxapplication.ui.sell.adapter.SellAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_sell.*

class SellFragment : Fragment(), SellAdapter.ItemClickListener {

    private lateinit var categoriesModel: MutableList<CategoriesModel>
    private val TAG: String= SellFragment::class.java.simpleName
    private lateinit var offeringAdapter: SellAdapter
    //private lateinit var dashboardViewModel: SellViewModel
    private val db = FirebaseFirestore.getInstance()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(
            R.layout.fragment_sell, container, false
        )
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCategoryList()
    }

    private fun getCategoryList() {
        db.collection("Categories").get().addOnSuccessListener {
            categoriesModel=it.toObjects(CategoriesModel::class.java)
            setAdapter()
        }
    }

    private fun setAdapter() {
        rv_offering.layoutManager = GridLayoutManager(context, 3)
        offeringAdapter = SellAdapter(categoriesModel, this)
        rv_offering.adapter = offeringAdapter
    }

    override fun onItemClick(position: Int) {
        TODO("Not yet implemented")
    }
}