package com.example.olxapplication.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.olxapplication.R
import com.example.olxapplication.databinding.FragmentHomeBinding
import com.example.olxapplication.model.CategoriesModel
import com.example.olxapplication.ui.home.adapter.CategoriesAdapter
import com.example.olxapplication.utilities.Constants
import com.example.olxapplication.utilities.SharedPref
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), CategoriesAdapter.ItemClickListener {
   // private lateinit var categoriesModel: MutableList<CategoriesModel>
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var TAG = HomeFragment::class.java.simpleName
    val db = FirebaseFirestore.getInstance()
    private lateinit var categoriesModel: MutableList<CategoriesModel>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
      val root = inflater.inflate(R.layout.fragment_home,container,false)
        return root
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getCategoryList()
        textListener()

        tvCityName.text = SharedPref(requireActivity()).getString(Constants.CITY_NAME)
    }

    private fun textListener() {
        edSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                // filter your list from your input
                filterList(s.toString())
                //you can use runnable postDelayed like 500 ms to delay search text
            }
        })
    }

    private fun filterList(toString: String) {
        val temp: MutableList<CategoriesModel> = ArrayList()
        for (d in categoriesModel) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if (d.key.contains(toString.capitalize())||d.key.contains(toString)) {
                temp.add(d)
            }
        }
        //update recyclerview
        categoriesAdapter.updateList(temp)
    }

    private fun getCategoryList() {
        db.collection("Categories").get().addOnSuccessListener {
           categoriesModel=it.toObjects(CategoriesModel::class.java)
            setAdapter()
        }
    }

    private fun setAdapter() {
        rv_categories.layoutManager = GridLayoutManager(context, 3)!!
        var categoriesAdapter = CategoriesAdapter(categoriesModel,this)
        rv_categories.adapter = categoriesAdapter
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(  context,"hey"+position,Toast.LENGTH_LONG).show()
    }

}