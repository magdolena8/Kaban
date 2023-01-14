package com.begdev.kaban.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.R
import com.begdev.kaban.adapter.TrackedAdapter
import com.begdev.kaban.databinding.FragmentTrackedBinding
import com.begdev.kaban.model.TrackedModel
import com.begdev.kaban.utils.DBHelper

class TrackedFragment : Fragment() {

    private lateinit var binding: FragmentTrackedBinding
//    private val vmProjectsList: ProjectsListViewModel by viewModels()
    val trackedAdapter = TrackedAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {




        return inflater.inflate(R.layout.fragment_tracked, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db: DBHelper = DBHelper(requireContext())
        val array: ArrayList<TrackedModel> = db.getAllTracked()
        trackedAdapter.submitList(array)

        binding = FragmentTrackedBinding.bind(view)
        binding.apply {
            recyclerTracked.apply {
                adapter = trackedAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        trackedAdapter.setOnItemLongClickListener(object : TrackedAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int): Boolean {
                performOptionsMenuClick(position)
                return true
            }

        })
    }

    private fun performOptionsMenuClick(position: Int) {
        val popupMenu = PopupMenu(context , binding.recyclerTracked.getChildAt(position))
        popupMenu.inflate(R.menu.options_menu_tracked)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.editTracked -> {
                        val selectedTrack = trackedAdapter.currentList.get(position)
                        findNavController().navigate(TrackedFragmentDirections.actionTrackedFragmentToEditTrackedFragment(selectedTrack))

//                        findNavController().navigate(TableFragmentDirections.actionTableFragmentToAddTrackedFragment())
                        return true
                    }
                    R.id.deleteTracked -> {
                        val selectedTrack = trackedAdapter.currentList.get(position)
                        val db: DBHelper = DBHelper(requireContext())
                        db.deleteTracked(selectedTrack)
//                        findNavController().navigate(TableFragmentDirections.actionTableFragmentToAddTrackedFragment(qwe))
//                        findNavController().navigate(TableFragmentDirections.actionTableFragmentToAddTrackedFragment())
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }
}