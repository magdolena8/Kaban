package com.begdev.kaban.ui

import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.R
import com.begdev.kaban.adapter.TrackedAdapter
import com.begdev.kaban.databinding.FragmentTrackedBinding
import com.begdev.kaban.model.TrackedModel
import com.begdev.kaban.utils.DBHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter

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

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_tracked, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.exportTracked -> {
                        val db = DBHelper(requireContext())
                        val trackedList = db.getAllTracked()
                        val gson = Gson()
                        val result: String = gson.toJson(trackedList)
                        val path = context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                        val externalFile = File(path, "kabantracked.json")
                        gson.toJson(result, FileWriter(externalFile))
                        FileOutputStream(externalFile).use {
                            it.write(result.toByteArray())
                        }
                        Toast.makeText(context, "Your Tracks was Exported", Toast.LENGTH_SHORT)
                            .show()
                        true
                    }
                    R.id.importTracked -> {
                        val gson = Gson()
                        val itemType = object : TypeToken<List<TrackedModel>>() {}.type
                        val path = context?.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
                        val externalFile = File(path, "kabantracked.json")
                        val qwe = externalFile.inputStream().readBytes().toString(Charsets.UTF_8)
                        var trackedList = gson.fromJson<List<TrackedModel>>(qwe, itemType)
//                        Log.d("qwe", "aweqwe")
                        db.clearDataBase()
                        db.fillDataBase(trackedList)

                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)

    }

    private fun performOptionsMenuClick(position: Int) {
        val popupMenu = PopupMenu(context, binding.recyclerTracked.getChildAt(position))
        popupMenu.inflate(R.menu.options_menu_tracked)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.editTracked -> {
                        val selectedTrack = trackedAdapter.currentList.get(position)
                        findNavController().navigate(
                            TrackedFragmentDirections.actionTrackedFragmentToEditTrackedFragment(
                                selectedTrack
                            )
                        )

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