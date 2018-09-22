/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.samples.apps.sunflower.PlantListFragment
import com.google.samples.apps.sunflower.PlantListFragmentDirections
import com.google.samples.apps.sunflower.data.Plant
import com.google.samples.apps.sunflower.databinding.ListItemPlantBinding

/**
 * Adapter for the [RecyclerView] in [PlantListFragment].
 */
class PlantAdapter : ListAdapter<Plant, PlantAdapter.ViewHolder>(PlantDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val plant = getItem(position)
        holder.apply {
            bind(createOnClickListener(), plant)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListItemPlantBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    private fun createOnClickListener(): OnPlantItemClickListener {
        return object : OnPlantItemClickListener {
            override fun onPlantItemClick(rootView: View, plant: Plant) {
                val binding = DataBindingUtil.getBinding<ListItemPlantBinding>(rootView)
                val navigatorExtras = FragmentNavigatorExtras(binding!!.plantItemImage to plant.plantId)

                val direction = PlantListFragmentDirections.ActionPlantListFragmentToPlantDetailFragment(plant.plantId)
                rootView.findNavController().navigate(direction, navigatorExtras)
            }
        }
    }

    class ViewHolder(
        private val binding: ListItemPlantBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: OnPlantItemClickListener, item: Plant) {
            binding.apply {
                clickListener = listener
                plant = item
                executePendingBindings()
            }
        }
    }

    interface OnPlantItemClickListener {
        fun onPlantItemClick(rootView: View, plant: Plant)
    }
}